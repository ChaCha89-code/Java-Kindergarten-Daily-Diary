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
    public ResponseEntity<?> deleteDiaryService(Long diaryId) {
        Optional<Diary> optionalDiary = diaryRepository.findById(diaryId);

        if (optionalDiary.isPresent()) {
            diaryRepository.delete(optionalDiary.get());
            DiaryDeleteResponseDto success = new DiaryDeleteResponseDto(200, "삭제 완료");
            return ResponseEntity.ok(success); // 200 OK, body 없음
        } else {
            DiaryDeleteErrorResponseDto error = new DiaryDeleteErrorResponseDto(404, "삭제하려는 일정이 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    /// //////////위가 최종본, 아래와의 차이점 알아보기
//    public ResponseEntity<?> deleteDiaryService(Long diaryId) {
//        Optional<Diary> optionalDiary = diaryRepository.findById(diaryId);
//
//        if (optionalDiary.isEmpty()) {
//            DiaryDeleteErrorResponseDto error = new DiaryDeleteErrorResponseDto(404, "해당 일정이 존재하지 않습니다");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//        }
}
