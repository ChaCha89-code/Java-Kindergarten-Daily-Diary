package com.diary.demo.controller;

import com.diary.demo.dto.DiaryCreateRequestDto;
import com.diary.demo.dto.DiaryCreateResponseDto;
import com.diary.demo.dto.DiaryListErrorResponseDto;
import com.diary.demo.dto.DiaryListResponseDto;
import com.diary.demo.service.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diaries")
public class DiaryController {

    //속
    private final DiaryService diaryService;
    //생
    // 생성자 안넣어서 자꾸 500번대 에러뜸..
    public DiaryController(DiaryService diaryService){
        this.diaryService = diaryService;
    }
    //기

    /**
     * 조회를 해보기 위해 임의로 post 기능 구현
     * @param requestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<DiaryCreateResponseDto> createDiaryAPI(@RequestBody DiaryCreateRequestDto requestDto){
        // 데이터 준비
        DiaryCreateResponseDto diaryCreateResponseDto = diaryService.createDiaryService(requestDto);
        ResponseEntity<DiaryCreateResponseDto> response
                = new ResponseEntity<>(diaryCreateResponseDto, HttpStatus.CREATED);
        return response;
    }
    /**
     * 게시글 전체조회 기능
     * http://localhost:8080/api/diaries
     */
    @GetMapping
    public ResponseEntity<?> getDiaryListAPI(){
        // 1. 데이터 준비

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
                    = new ResponseEntity<>(errorListResponseDto,HttpStatus.NOT_FOUND);
            return errorResponseDto;
        }

    }
}
