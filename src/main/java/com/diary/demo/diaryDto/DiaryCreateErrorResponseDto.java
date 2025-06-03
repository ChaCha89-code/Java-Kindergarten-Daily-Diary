package com.diary.demo.diaryDto;

public class DiaryCreateErrorResponseDto {

    private final int status;
    private final String message;

    public DiaryCreateErrorResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    // This object is read-only.
    // All the values are set in the constructor at once.
}
