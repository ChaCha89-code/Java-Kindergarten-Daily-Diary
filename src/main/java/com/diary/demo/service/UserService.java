package com.diary.demo.service;

import com.diary.demo.domain.Users;
import com.diary.demo.repository.UserRepository;
import com.diary.demo.userDto.UserUpdateRequestDto;
import com.diary.demo.userDto.UserUpdateResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    // 속성
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // 생성자
    public UserService (UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 기능

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
}
