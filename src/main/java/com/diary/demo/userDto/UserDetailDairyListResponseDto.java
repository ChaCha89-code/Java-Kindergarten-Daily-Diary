package com.diary.demo.userDto;

import java.time.LocalDateTime;

public class UserDetailDairyListResponseDto {
    private String title;
    private LocalDateTime createdAt;
    public UserDetailDairyListResponseDto(String title, LocalDateTime createdAt){
        this.title = title;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
