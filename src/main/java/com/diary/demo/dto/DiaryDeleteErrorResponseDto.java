package com.diary.demo.dto;

public class DiaryDeleteErrorResponseDto {

    private Integer status;
    private String errorMessage;

    public DiaryDeleteErrorResponseDto(int status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public Integer getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
