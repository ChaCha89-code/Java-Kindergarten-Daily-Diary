package com.diary.demo.controller;

import com.diary.demo.service.UserService;
import com.diary.demo.userDto.UserCreateRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    // 속성
    private final UserService userService;

    // 생성자
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 기능 - @ModelAttribute : form-data 가능 (이미지 파일 등)
    @PostMapping
    public ResponseEntity<?> createUserAPI(@ModelAttribute UserCreateRequestDto requestDto) {
        return userService.createUserService(requestDto);
    }
}
