package com.diary.demo.service;

import com.diary.demo.domain.Users;
import com.diary.demo.repository.UserRepository;
import com.diary.demo.userDto.UserDeleteRequestDto;
import com.diary.demo.userDto.UserDeleteResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    // 속성
    private final UserRepository userRepository;

    // 생성자
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 기능
    @Transactional
    public UserDeleteResponseDto deleteUserService(UserDeleteRequestDto requestDto) {

        // 1.데이터 준비
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        // 2.조회
        Optional<Users> userOptional = userRepository.findByEmail(email);

        //3.삭제 및 responseDto 만들기
        //회원 조회
        if (userOptional.isPresent()) {
            Users user = userOptional.get();

            //4.dto 반환
            //비밀번호 일치 여부 확인
            if (!user.getPassword().equals(password)) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            //성공 처리 및 응답 반환
            userRepository.delete(user);
            return new UserDeleteResponseDto(200, "성공적으로 회원탈퇴가 되었습니다.");
        } else {
            //회원 조회 실패 시 응답 반환
            throw new IllegalArgumentException("아이디가 일치하지 않습니다.");
        }
    }
}
