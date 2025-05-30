package com.diary.demo.service;


import com.diary.demo.domain.Diary;
import com.diary.demo.dto.DiaryDeleteErrorResponseDto;
import com.diary.demo.dto.DiaryDeleteResponseDto;
import com.diary.demo.repository.DiaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }


    /**
     * 일정 삭제 서비스
     */
    public void deleteDiaryService(Long diaryId) {
        Optional<Diary> diaryOptional = diaryRepository.findById(diaryId);
        if (diaryOptional.isPresent()) {
            diaryRepository.delete(diaryOptional.get());
        } else {
            throw new IllegalArgumentException("삭제하려는 일정이 존재하지 않습니다.");
        }
    }
}
