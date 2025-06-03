package com.diary.demo.userDto;

public class UserUpdateResponseDto {
    private final int status;
    private final String message;

    public UserUpdateResponseDto(int status, String message) {
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
