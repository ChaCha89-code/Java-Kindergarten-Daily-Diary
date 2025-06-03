package com.diary.demo.diaryDto;

public class DiaryUpdateRequestDto {
    private String title;
    private String content;
    private String image;

    public DiaryUpdateRequestDto(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
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
}
