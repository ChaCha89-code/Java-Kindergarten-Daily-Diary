package com.diary.demo.userDto;

public class UserDetailRequestDto {
    private Integer status;
    private String message;

    public UserDetailRequestDto(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
