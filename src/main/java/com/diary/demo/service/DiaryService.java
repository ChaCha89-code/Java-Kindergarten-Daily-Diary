package com.diary.demo.service;

import com.diary.demo.domain.Diary;
import com.diary.demo.dto.DiaryCreateErrorResponseDto;
import com.diary.demo.dto.DiaryCreateRequestDto;
import com.diary.demo.dto.DiaryCreateResponseDto;
import com.diary.demo.repository.DiaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Transactional
    public ResponseEntity<?> createDiaryService(DiaryCreateRequestDto requestDto) {

        // 1. 데이터 준비
        String email = requestDto.getEmail();
        String userName = requestDto.getUserName();
        String title = requestDto.getTitle();
        String content = requestDto.getContent();
//        String image = requestDto.getImage();

        if(email.isBlank() || userName.isBlank() || title.isBlank() || content.isBlank()) {
            DiaryCreateErrorResponseDto error = new DiaryCreateErrorResponseDto(400, "게시글 입력 항목 중 빈 내용이 있습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        // 2. 엔티티 만들기
        Diary newDiary = new Diary(email, userName, title, content); // image 추가하면 여기에 넣어주기

        // 3. 저장
        Diary savedDiary = diaryRepository.save(newDiary);

        // 4. responseDto 반환
        return ResponseEntity.ok(new DiaryCreateResponseDto(savedDiary));
    }
}
