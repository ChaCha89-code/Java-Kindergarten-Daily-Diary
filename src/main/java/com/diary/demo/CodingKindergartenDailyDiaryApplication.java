package com.diary.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CodingKindergartenDailyDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingKindergartenDailyDiaryApplication.class, args);
	}

}
