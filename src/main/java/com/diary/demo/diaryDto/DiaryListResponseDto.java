package com.diary.demo.diaryDto;

import java.time.LocalDateTime;

import java.util.List;



public class DiaryListResponseDto {
    /**
     * final 선언으로 참조를 재할당 불가능하게 만듦
     */
    private final List<DiaryList> diaryList;


    public DiaryListResponseDto(List<DiaryList> diaryList){
        this.diaryList = diaryList;

    }


    public List<DiaryList> getDiaryList() {
        return diaryList;
    }


    /**
     * DiaryListResponseDto 의 class그룹화를 위해 Inner class 생성
     */
    public static class DiaryList{
        private Long id;
        private String email;
        private String userName;
        private String title;
        private String content;
        private String image;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;

        public DiaryList(Long id, String email, String userName, String title,
                         String content, String image, LocalDateTime createAt,
                         LocalDateTime updateAt)
        {
            this.id = id;
            this.email = email;
            this.userName = userName;
            this.title = title;
            this.content = content;
            this.image = image;
            this.createAt = createAt;
            this.updateAt = updateAt;
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

        public LocalDateTime getCreateAt() {
            return createAt;
        }

        public LocalDateTime getUpdateAt() {
            return updateAt;
        }
    }

}
