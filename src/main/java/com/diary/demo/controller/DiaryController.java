package com.diary.demo.controller;

import com.diary.demo.dto.DiaryDeleteRequestDto;
import com.diary.demo.dto.DiaryDeleteResponseDto;
import com.diary.demo.service.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diaries")
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
      this.diaryService = diaryService;
    }

    /**
     * 일정 삭제 API
     */
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteDiaryAPI(@PathVariable Long diaryId) {
        return diaryService.deleteDiaryService(diaryId);
    }
}
