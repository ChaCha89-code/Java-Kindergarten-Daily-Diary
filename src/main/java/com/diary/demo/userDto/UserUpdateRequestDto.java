package com.diary.demo.userDto;

public class UserUpdateRequestDto {
    // 속성
    private String userImage;
    private String password;
    private String newPassword;
    private String checkNewPassword;

    // 생성자
    public UserUpdateRequestDto(String userImage, String password, String newPassword, String checkNewPassword) {
        this.userImage = userImage;
        this.password = password;
        this.newPassword = newPassword;
        this.checkNewPassword = checkNewPassword;
    }

    // 기능
    public String getUserImage() {
        return userImage;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getCheckNewPassword() {
        return checkNewPassword;
    }
}
