package com.diary.demo.controller;

import com.diary.demo.service.UserService;
import com.diary.demo.userDto.UserDetailErrorResponseDto;
import com.diary.demo.userDto.UserDetailResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    // 속성
    private final UserService userService;

    // 생성자


    public UserController(UserService userService) {
        this.userService = userService;
    }

    //기능

    /**
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
