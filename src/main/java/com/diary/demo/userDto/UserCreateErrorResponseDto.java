package com.diary.demo.userDto;

public class UserCreateErrorResponseDto {
    private final int status;
    private final String message;

    public UserCreateErrorResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
