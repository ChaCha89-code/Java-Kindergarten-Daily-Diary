package com.diary.demo.diaryDto;

public class DiaryDeleteErrorResponseDto {

    private final int status;
    private final String message;

    public DiaryDeleteErrorResponseDto(int status, String message) {
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
