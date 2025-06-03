package com.diary.demo.diaryDto;

import com.diary.demo.domain.Diary;

import java.time.LocalDateTime;

public class DiaryCreateResponseDto {
    private long id;
    private String email;
    private String userName;
    private String title;
    private String content;
    private String image;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

    public DiaryCreateResponseDto(Diary diary) {
        this.id = diary.getId();
        this.email = diary.getEmail();
        this.userName = diary.getUserName();
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.image = diary.getImage();
        this.createAt = diary.getCreatedAt();
        this.updatedAt = diary.getUpdatedAt();

    }

    public long getId() {
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

    public String getImage() { return image; }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
