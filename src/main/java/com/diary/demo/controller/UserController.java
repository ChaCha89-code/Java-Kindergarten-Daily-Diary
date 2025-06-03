package com.diary.demo.controller;

import com.diary.demo.service.UserService;
import com.diary.demo.userDto.UserDeleteErrorResponseDto;
import com.diary.demo.userDto.UserDeleteRequestDto;
import com.diary.demo.userDto.UserDeleteResponseDto;
import org.hibernate.sql.results.internal.RowTransformerTupleTransformerAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    //속성
    private final UserService userService;

    //생성자
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //기능
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
            UserDeleteErrorResponseDto deleteerrorResponseDto = new UserDeleteErrorResponseDto(404, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
