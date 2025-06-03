package com.diary.demo.controller;

import com.diary.demo.diaryDto.*;
import com.diary.demo.diaryDto.DiaryDeleteResponseDto;
import com.diary.demo.diaryDto.DiaryDetailErrorResponseDto;
import com.diary.demo.diaryDto.DiaryDetailResponseDto;
import com.diary.demo.service.DiaryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    /**
     * 게시글 전체조회 기능
     * @return 성공 응답 및 null값이 있다면 예외처리 후 실패메세지 반환
     */
    @GetMapping
    public ResponseEntity<?> getDiaryListAPI() {

        // 2. 반환
        // try로 게시글에 null이 있을 경우 service에서 nullpointexception 발생 시킨 로직을 처리
        try {
            DiaryListResponseDto listResponseDto = diaryService.getDiaryListService();
            ResponseEntity<DiaryListResponseDto> response
                    = new ResponseEntity<>(listResponseDto, HttpStatus.CREATED);
            return response;
        } catch (Exception e) {
            DiaryListErrorResponseDto errorListResponseDto
                    = new DiaryListErrorResponseDto(404, "게시글 정보 목록을 조회할 수 없습니다.");
            ResponseEntity<DiaryListErrorResponseDto> errorResponseDto
                    = new ResponseEntity<>(errorListResponseDto, HttpStatus.NOT_FOUND);
            return errorResponseDto;
        }
    }

    // 게시글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDiaryAPI(
            @PathVariable("id") Long diaryId,
            @RequestBody DiaryUpdateRequestDto updateRequestDto
    ) {
        try {
            DiaryUpdateResponseDto updateResponseDto = diaryService.updateDiaryService(diaryId, updateRequestDto);
            ResponseEntity<?> response = new ResponseEntity<>(updateResponseDto, HttpStatus.OK);
            return response;
        }
        catch (EntityNotFoundException e) {
            DiaryUpdateErrorResponseDto updateErrorResponseDto = new DiaryUpdateErrorResponseDto(404, "해당 게시글을 찾을 수 없습니다.");
            ResponseEntity<?> errorResponse = new ResponseEntity<>(updateErrorResponseDto, HttpStatus.NOT_FOUND);
            return errorResponse;
        }
    }
    /**
     * 일정 삭제 API
     */
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteDiaryAPI(@PathVariable Long diaryId) {
        try {
            diaryService.deleteDiaryService(diaryId);
            return ResponseEntity.ok(new DiaryDeleteResponseDto(200, "deleted"));
        } catch (IllegalArgumentException e) {
            //errorDto에 status와 메시지 넣어 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DiaryDeleteErrorResponseDto(404, e.getMessage()));
        }
    }
}
