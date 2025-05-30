package com.diary.demo.controller;

import com.diary.demo.dto.DiaryCreateRequestDto;
import com.diary.demo.service.DiaryService;
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
     * 일정 생성 API
     */
    @PostMapping
    public ResponseEntity<?> createDiaryAPI(@RequestBody DiaryCreateRequestDto requestDto) {
        return diaryService.createDiaryService(requestDto);
    }
}
