package com.diary.demo.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class Users {
    // 속성
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_image")
    private String userImage;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 생성자
    public Users(String userName, String email, String password, String userImage) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userImage = userImage;
    }

    public Users() {}

    // 기능
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserImage() {
        return userImage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateUserImage(String newUserImage) {
        this.userImage = newUserImage;
    }
}
