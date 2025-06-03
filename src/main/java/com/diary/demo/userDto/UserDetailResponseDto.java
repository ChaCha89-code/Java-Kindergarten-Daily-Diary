package com.diary.demo.userDto;

import java.time.LocalDateTime;
import java.util.List;

public class UserDetailResponseDto {
    //속
    private Long id;
    private String email;
    private String userName;
    private String userImage;
    private final List<UserDiaryList> userDiaryList;


    //생
    public UserDetailResponseDto( Long id, String email,
                                  String userName, String userImage,
                                 List<UserDiaryList> userDiaryList) {
        this.id = id;
        this.email = email;
        this.userImage = userImage;
        this.userName = userName;
        this.userDiaryList = userDiaryList;
    }
    //기

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public List<UserDiaryList> getUserDiaryList() {
        return userDiaryList;
    }

    // class 그룹화를 위해 이너클래스 생성
    public static class UserDiaryList {
        private String title;
        private LocalDateTime createAt;

        public UserDiaryList(String title, LocalDateTime createAt) {
            this.title = title;
            this.createAt = createAt;
        }

        public String getTitle() {
            return title;
        }

        public LocalDateTime getCreateAt() {
            return createAt;
        }
    }

}
