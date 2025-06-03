package com.diary.demo.userDto;

public class UserDetailErrorResponseDto {
    private Integer status;
    private String message;

    public UserDetailErrorResponseDto(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
