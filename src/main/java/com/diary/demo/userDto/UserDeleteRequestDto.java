package com.diary.demo.userDto;

public class UserDeleteRequestDto {
    private String email;
    private String password;

    public UserDeleteRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

}
