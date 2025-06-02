package com.diary.demo.service;

import com.diary.demo.domain.Users;
import com.diary.demo.repository.UserRepository;
import com.diary.demo.userDto.UserCreateErrorResponseDto;
import com.diary.demo.userDto.UserCreateRequestDto;
import com.diary.demo.userDto.UserCreateResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UserService {

    // 속성
    private final UserRepository userRepository;

    @Value("${file.path}")
    private String uploadFolder;

    // 생성자
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 기능
    @Transactional
    public ResponseEntity<?> createUserService(UserCreateRequestDto requestDto) {
        // 1. 데이터 준비
        String userName = requestDto.getUserName();
        String email = requestDto.getEmail();
        String password = requestDto.getPassword().trim();
        String checkPassword = requestDto.getCheckPassword().trim();
        MultipartFile image = requestDto.getUserImage();

        // 1) 빈 값 체크
        if(userName.isBlank() || email.isBlank() || password.isBlank() || checkPassword.isBlank()) {
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
        if(image != null) {
            UUID uuid = UUID.randomUUID();
            String imageFileName = uuid + "_" + requestDto.getUserImage().getOriginalFilename(); // getOriginalFilename() - 실제 이미지 파일의 이름
            System.out.println("이미지 파일 이름: " + imageFileName);

            url = uploadFolder + imageFileName;
            Path imageFilePath = Paths.get(url);

            try {
                Files.write(imageFilePath, requestDto.getUserImage().getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // 2. 엔티티 만들기 (유저 저장)
        Users newUser = new Users(userName, email, password, url);

        // 3. 저장 (유저 저장)
        Users savedUser = userRepository.save(newUser);

        // 4. responseDto 반환
        return ResponseEntity.ok(new UserCreateResponseDto(201, "회원가입이 정상적으로 완료 되었습니다."));

    }
}
