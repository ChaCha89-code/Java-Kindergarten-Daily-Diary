package com.diary.demo.service;

import com.diary.demo.domain.Diary;
import com.diary.demo.dto.DiaryDetailResponseDto;
import com.diary.demo.repository.DiaryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DiaryService {
    // 속성
    private final DiaryRepository diaryRepository;

    // 생성자
    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    // 기능
    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public DiaryDetailResponseDto getDiaryDetailService(Long diaryId) {
        // 1. 데이터 준비

        // 2. 조회
        Optional<Diary> diaryOptional = diaryRepository.findById(diaryId);

        // 3. responseDto 만들기
        if (diaryOptional.isPresent()) {
            Diary foundDiary = diaryOptional.get();
            Long id = foundDiary.getId();
            String email = foundDiary.getEmail();
            String userName = foundDiary.getUserName();
            String title = foundDiary.getTitle();
            String content = foundDiary.getContent();
            String image = foundDiary.getImage();
            LocalDateTime createdAt = foundDiary.getCreatedAt();
            LocalDateTime updatedAt = foundDiary.getUpdatedAt();

            // 4. dto 반환
            DiaryDetailResponseDto detailResponseDto = new DiaryDetailResponseDto(id, email, userName, title, content, image, createdAt, updatedAt);
            return detailResponseDto;

        } else  {
            // 실패 응답
            throw new EntityNotFoundException("해당 ID의 게시글이 없습니다.");
        }
    }
}
