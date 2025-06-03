package com.diary.demo.controller;

import com.diary.demo.service.UserService;
import com.diary.demo.userDto.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    // 기능
    // 회원 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUserAPI(
            @PathVariable("id") Long userId,
            @RequestBody UserUpdateRequestDto updateRequestDto
    ) {
        try {
            UserUpdateResponseDto updateResponseDto = userService.updateUserService(userId, updateRequestDto);

            ResponseEntity<?> response = new ResponseEntity<>(updateResponseDto, HttpStatus.OK);
            return response;

        } catch (IllegalArgumentException e) {
            UserUpdateErrorResponseDto errorResponseDto = new UserUpdateErrorResponseDto(400, e.getMessage());

            ResponseEntity<?> errorResponse = new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
            return errorResponse;

        } catch (EntityNotFoundException e) {
            UserUpdateErrorResponseDto errorResponseDto = new UserUpdateErrorResponseDto(400, e.getMessage());

            ResponseEntity<?> errorResponse = new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
            return errorResponse;
        }

    }

    // 기능 - @ModelAttribute : form-data 가능 (이미지 파일 등)
    @PostMapping
    public ResponseEntity<?> createUserAPI(@ModelAttribute UserCreateRequestDto requestDto) {
        return userService.createUserService(requestDto);
    }
    /**
     * 회원 삭제 API
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserAPI(@RequestBody UserDeleteRequestDto requestDto) {
        try {
            UserDeleteResponseDto responseDto = userService.deleteUserService(requestDto);
            ResponseEntity<?> response = new ResponseEntity<>(responseDto, HttpStatus.OK);
            return response;
        } catch (IllegalArgumentException e) {
            UserDeleteErrorResponseDto deleteerrorResponseDto = new UserDeleteErrorResponseDto(400, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
