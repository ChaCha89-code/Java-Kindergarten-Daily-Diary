package com.diary.demo.dto;

import java.io.File;

public class DiaryCreateRequestDto {
    private String email;
    private String userName;
    private String title;
    private String content;
//    private String image;

    public DiaryCreateRequestDto(String email, String userName, String title, String content, String image) {
        this.email = email;
        this.userName = userName;
        this.title = title;
        this.content = content;
//        this.image = image;
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

//    public String getImage() {return image; }
}
