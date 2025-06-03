package com.diary.demo.userDto;

public class UserDeleteErrorResponseDto {
    private final int status;
    private final String message;

    public UserDeleteErrorResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
}
