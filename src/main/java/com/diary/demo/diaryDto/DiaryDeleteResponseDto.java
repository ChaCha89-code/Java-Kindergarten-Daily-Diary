package com.diary.demo.diaryDto;

public class DiaryDeleteResponseDto {
    private final int status;
    private final String message;

    public DiaryDeleteResponseDto(int status, String message) {
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
