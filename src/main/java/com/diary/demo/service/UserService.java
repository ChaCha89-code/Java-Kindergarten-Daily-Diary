package com.diary.demo.service;

import com.diary.demo.diaryDto.DiaryDetailResponseDto;

import com.diary.demo.domain.Diary;
import com.diary.demo.domain.Users;
import com.diary.demo.repository.DiaryRepository;
import com.diary.demo.repository.UserRepository;
import com.diary.demo.userDto.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    // 속성
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DiaryRepository diaryRepository;

    @Value("${file.path}")
    private String uploadFolder;

    // 생성자
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       DiaryRepository diaryRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.diaryRepository =diaryRepository;
    }

    // 기능
    //회원 가입
    @Transactional
    public ResponseEntity<?> createUserService(UserCreateRequestDto requestDto) {
        // 1. 데이터 준비
        String userName = requestDto.getUserName();
        String email = requestDto.getEmail();
        String password = requestDto.getPassword().trim();
        String checkPassword = requestDto.getCheckPassword().trim();
        MultipartFile image = requestDto.getUserImage();

        // 1) 빈 값 체크
        if (userName.isBlank() || email.isBlank() || password.isBlank() || checkPassword.isBlank()) {
            UserCreateErrorResponseDto error = new UserCreateErrorResponseDto(400, "회원가입 입력정보 중 빈 항목이 있습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        // 2) 비밀번호 복잡도 체크
        System.out.println("비밀번호 입력값: [" + password + "]");
        System.out.println("일치 여부: " + password.equals(checkPassword));

        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s])[\\S]{8,20}$")) {
            UserCreateErrorResponseDto error = new UserCreateErrorResponseDto(400,
                    "비밀번호는 최소 8자리, 최대 20자리이며, 대소문자, 숫자, 특수문자를 포함해야 합니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        // 3) 비밀번호 확인 체크
        if (!password.equals(checkPassword)) {
            UserCreateErrorResponseDto error = new UserCreateErrorResponseDto(400,
                    "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        // 4) 이미지 저장 로직
        String url = null;
        if (image != null && !image.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String imageFileName = uuid + "_" + image.getOriginalFilename(); // getOriginalFilename() - 실제 이미지 파일의 이름
            System.out.println("이미지 파일 이름: " + imageFileName);

            url = uploadFolder + imageFileName;
            Path imageFilePath = Paths.get(url);

            try {
                Files.write(imageFilePath, requestDto.getUserImage().getBytes());
            } catch (Exception e) {
                e.printStackTrace();
                UserCreateErrorResponseDto error = new UserCreateErrorResponseDto(500, "이미지 저장 중 오류가 발생했습니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
            }
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 2. 유저 엔티티 생성
        Users newUser = new Users(userName, email, encodedPassword, url);

        // 3. 저장
        userRepository.save(newUser);

        // 4. 응답 반환
        return ResponseEntity.ok(new UserCreateResponseDto(201, "회원가입이 정상적으로 완료 되었습니다."));

    }

    // 회원 정보 수정
    @Transactional
    public UserUpdateResponseDto updateUserService(Long userId, UserUpdateRequestDto updateRequestDto) {
        // 1. 데이터 준비
        String userImage = updateRequestDto.getUserImage();
        String password = updateRequestDto.getPassword();
        String newPassword = updateRequestDto.getNewPassword();
        String checkNewPassword = updateRequestDto.getCheckNewPassword();

        // 2. 조회
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 사용자를 찾을 수 없습니다."));

        // 2-1. 비밀번호 확인
        if (!passwordEncoder.matches(password, users.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 2-2. 비밀번호 형식 검증
        if (!isValidPassword(newPassword)) {
            throw new IllegalArgumentException("비밀번호는 최소 8자리, 최대 20자리이며, 대소문자, 숫자, 특수문자를 포함해야 합니다.");
        }
        // 2-3. 기존 비밀번호와 중복 체크
        if (passwordEncoder.matches(newPassword, users.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호와 동일한 비밀번호를 사용할 수 없습니다.");
        }
        // 2-4. 새 비밀번호 확인
        if (!newPassword.equals(checkNewPassword)) {
            throw new IllegalArgumentException("새로운 비밀번호와 일치하지 않습니다.");
        }

        // 3. 수정
        users.changePassword(passwordEncoder.encode(newPassword));
        users.updateUserImage(userImage);

        // 4. 성공 응답 반환
        return new UserUpdateResponseDto(200, "비밀번호 변경을 완료했습니다.");
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,20}$";
        return password.matches(regex);
    }

    // 회원 삭제 기능
    @Transactional
    public UserDeleteResponseDto deleteUserService(Long id) {
        Optional<Users> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            userRepository.delete(user);
            return new UserDeleteResponseDto(200, "성공적으로 회원탈퇴가 되었습니다.");
        } else {
            throw new IllegalArgumentException("해당 ID의 사용자를 찾을 수 없습니다.");
        }
    }


    /**
     * 회원 조회 기능
     *
     * @param userId 으로 받은 데이터는 userRepository에서 데이터 조회하여 메서드 로직을 실행 할 수 있게 했습니다
     * @return userId가 null이 아니라면 userDetailResponseDto;를 null이 있다면 NullPointException 발생시켰습니다
     */
    @jakarta.transaction.Transactional
    public UserDetailResponseDto getUserDetailService(Long userId) {

        Optional<Users> usersOptional = userRepository.findById(userId);
        if (usersOptional.isPresent()) {
            Users findUser = usersOptional.get();

            List<Diary> allByUserId = diaryRepository.findAllByUserName(findUser.getUserName());

            List<UserDetailDairyListResponseDto> dtoList = new ArrayList<>();

            for (Diary diary : allByUserId) {

                UserDetailDairyListResponseDto detailResponseDto
                        = new UserDetailDairyListResponseDto( diary.getTitle(),diary.getCreatedAt());

                dtoList.add(detailResponseDto);
            }

            UserDetailResponseDto userDetailResponseDto
                    = new UserDetailResponseDto(findUser, dtoList);
            return userDetailResponseDto;

        } else {
            throw new NullPointerException();
        }


    }
}