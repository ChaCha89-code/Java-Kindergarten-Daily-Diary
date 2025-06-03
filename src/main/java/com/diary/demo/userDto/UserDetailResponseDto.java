package com.diary.demo.userDto;



import com.diary.demo.diaryDto.DiaryDetailResponseDto;
import com.diary.demo.domain.Users;


import java.util.List;

public class UserDetailResponseDto {
    //속
    private Long id;
    private String email;
    private String userName;
    private String userImage;
    private final List<UserDetailDairyListResponseDto> userDiaryList;


    //생
    public UserDetailResponseDto(Users users, List<UserDetailDairyListResponseDto> userDiaryList
                                 ) {
        this.id = users.getId();
        this.email = users.getEmail();
        this.userImage = users.getUserImage();
        this.userName = users.getUserName();
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

    public List<UserDetailDairyListResponseDto> getUserDiaryList() {
        return userDiaryList;
    }
    // class 그룹화를 위해 이너클래스 생성
//    public static class UserDiaryList {
//        private String title;
//        private LocalDateTime createAt;
//
//        public UserDiaryList(String title, LocalDateTime createAt) {
//            this.title = title;
//            this.createAt = createAt;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public LocalDateTime getCreateAt() {
//            return createAt;
//        }
//    }

}
