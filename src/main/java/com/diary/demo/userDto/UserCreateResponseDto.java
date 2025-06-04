package com.diary.demo.userDto;

public class UserCreateResponseDto {

    private int status;
    private String message;

    public UserCreateResponseDto(int status, String message) {
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

