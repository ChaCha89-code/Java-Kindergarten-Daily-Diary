package com.diary.demo.dto;

public class DiaryListErrorResponseDto {
    private Integer status;
    private String message;

    public DiaryListErrorResponseDto(Integer status, String message){
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
