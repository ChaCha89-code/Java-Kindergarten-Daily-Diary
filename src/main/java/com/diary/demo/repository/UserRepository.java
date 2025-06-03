package com.diary.demo.repository;

import com.diary.demo.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

}
