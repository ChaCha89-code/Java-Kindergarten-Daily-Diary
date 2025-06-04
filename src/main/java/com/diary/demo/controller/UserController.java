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
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserAPI(@PathVariable Long id) {
        try {
            UserDeleteResponseDto responseDto = userService.deleteUserService(id);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            UserDeleteErrorResponseDto errorResponseDto = new UserDeleteErrorResponseDto(400, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
        }
    }


    /**
     * 회원 조회 API
     *  try-catch로 id값이 null일 경우 해당 errorResponse로 실행 실패 메세지를 구현하였습니다
     * @param userId 를 받아 userService로직을 실행합니다
     * @return 성공적으로 반환시 userDetailResponse을 응답하고, id가 null일 경우 userService에서 예외처리한 것을 받아
     *  errorResponseMessage을 응답하게 구현했습니다
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserDetailAPI(@PathVariable("id") Long userId){
        try {
            UserDetailResponseDto userDetailResponseDto = userService.getUserDetailService(userId);
            ResponseEntity<?> userDetailResponse
                    = new ResponseEntity<>(userDetailResponseDto, HttpStatus.FOUND);
            return userDetailResponse;
        } catch (Exception e) {
            UserDetailErrorResponseDto errorResponseDto
                    = new UserDetailErrorResponseDto(404,"해당 회원 정보가 존재하지 않습니다.");
            ResponseEntity<?> errorResponseMessage
                    = new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
            return errorResponseMessage;
        }

    }
}
