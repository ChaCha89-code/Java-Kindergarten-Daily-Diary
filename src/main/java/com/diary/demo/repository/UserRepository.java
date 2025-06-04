package com.diary.demo.repository;

import com.diary.demo.domain.Diary;
import com.diary.demo.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmailAndPassword(String email, String password);
    Optional<Users> findByEmail(String email);

}
