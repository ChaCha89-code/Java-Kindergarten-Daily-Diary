package com.diary.demo.controller;

import com.diary.demo.dto.DiaryDetailErrorResponseDto;
import com.diary.demo.dto.DiaryDetailResponseDto;
import com.diary.demo.service.DiaryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.diary.demo.dto.DiaryCreateRequestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diaries")
public class DiaryController {

    // 속성
    private final DiaryService diaryService;

    // 생성자
    public DiaryController(DiaryService diaryService)  {
        this.diaryService = diaryService;
    }

    // 기능
    /**
     * 일정 생성 API
     */
    @PostMapping
    public ResponseEntity<?> createDiaryAPI(@ModelAttribute DiaryCreateRequestDto requestDto) {
        return diaryService.createDiaryService(requestDto);
    }
    // @ModelAttribute : form-data 가능 (이미지 파일 등)


    // 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getDiaryDetailAPI(@PathVariable("id") Long diaryId) {
        try {
            DiaryDetailResponseDto detailResponseDto = diaryService.getDiaryDetailService(diaryId);
            ResponseEntity<?> response = new ResponseEntity<>(detailResponseDto, HttpStatus.OK);
            return response;

        } catch(EntityNotFoundException e) {
            DiaryDetailErrorResponseDto detailErrorResponseDto = new DiaryDetailErrorResponseDto(404, "게시글 정보를 조회할 수 없습니다.");
            ResponseEntity<?> errorResponse = new ResponseEntity<>(detailErrorResponseDto, HttpStatus.NOT_FOUND);
            return errorResponse;
        }
    }

}
