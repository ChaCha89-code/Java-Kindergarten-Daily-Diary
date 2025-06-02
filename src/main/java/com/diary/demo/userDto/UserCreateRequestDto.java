package com.diary.demo.userDto;

import org.springframework.web.multipart.MultipartFile;

public class UserCreateRequestDto {

    private String userName;
    private String email;
    private String password;
    private String checkPassword;
    private MultipartFile userImage;

    public UserCreateRequestDto(String userName, String email, String password, String checkPassword, MultipartFile userImage) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.checkPassword = checkPassword;
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public MultipartFile getUserImage() {
        return userImage;
    }
}
