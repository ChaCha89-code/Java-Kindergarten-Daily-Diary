package com.diary.demo.controller;

import com.diary.demo.service.UserService;
import com.diary.demo.userDto.UserUpdateErrorResponseDto;
import com.diary.demo.userDto.UserUpdateRequestDto;
import com.diary.demo.userDto.UserUpdateResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
