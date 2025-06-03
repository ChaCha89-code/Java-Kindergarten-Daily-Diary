package com.diary.demo.diaryDto;

import org.springframework.web.multipart.MultipartFile;

public class DiaryCreateRequestDto {
    private Long userId;
    private String email;
    private String userName;
    private String title;
    private String content;
    private MultipartFile image; // the file itself at local

    public DiaryCreateRequestDto(String email, String userName, String title, String content, MultipartFile image) {
        this.email = email;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public Long getUserId() {
        return userId;
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

    public String getContent() {return content; }

    public MultipartFile getImage() {return image; }
}
