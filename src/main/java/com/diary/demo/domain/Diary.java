package com.diary.demo.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "diaries")
@EntityListeners(AuditingEntityListener.class)
public class Diary {

    // 속성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "image")
    private String image;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

 
    // 생성자
    public Diary(Long id, String email, String userName, String title, String content, String image) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public Diary() {

    }

    // 기능
    public Long getId() {
        return id;
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

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void updateDiary(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }
}
