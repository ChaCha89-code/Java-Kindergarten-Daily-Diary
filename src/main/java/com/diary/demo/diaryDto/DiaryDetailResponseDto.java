package com.diary.demo.diaryDto;

import java.time.LocalDateTime;

public class DiaryDetailResponseDto {
    private Long id;
    private String email;
    private String userName;
    private String title;
    private String content;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DiaryDetailResponseDto(Long id, String email, String userName, String title, String content, String image, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public DiaryDetailResponseDto(String title, LocalDateTime createdAt){
        this.title = title;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getUserName() {
        return userName;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public String getImage() {
        return image;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
