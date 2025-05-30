package com.diary.demo.dto;

public class DiaryDeleteResponseDto {
    private Integer status;
    private String message;

    public DiaryDeleteResponseDto(Integer status, String message) {
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
