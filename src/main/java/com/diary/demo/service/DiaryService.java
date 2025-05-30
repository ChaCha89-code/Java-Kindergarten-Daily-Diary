package com.diary.demo.service;

import com.diary.demo.domain.Diary;
import com.diary.demo.dto.DiaryCreateRequestDto;
import com.diary.demo.dto.DiaryCreateResponseDto;
import com.diary.demo.dto.DiaryListErrorResponseDto;
import com.diary.demo.dto.DiaryListResponseDto;
import com.diary.demo.repository.DiaryRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class DiaryService {
    //속
    private final DiaryRepository diaryRepository;

    //생
    // 생성자 안넣어서 자꾸 500번대 에러뜸..
    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    //기
    public DiaryCreateResponseDto createDiaryService(DiaryCreateRequestDto requestDto) {
        // 1. 데이터 준비
        String email = requestDto.getEmail();
        String userName = requestDto.getUserName();
        String title = requestDto.getTitle();
        String content = requestDto.getContent();
        String image = requestDto.getImage();
        System.out.println("email = " + email);
        System.out.println("userName = " + userName);
        System.out.println("title = " + title);
        System.out.println("content = " + content);
        System.out.println("image = " + image);
        // 2. 엔티티 만들기
        Diary diaryEntity = new Diary(title, email, userName, content, image);
        System.out.println("diaryEntity = " + diaryEntity);
        // 3. 저장
        Diary saveDiary = diaryRepository.save(diaryEntity);
        System.out.println("saveDiary = " + saveDiary);
        // 4. responseDto 반환
        DiaryCreateResponseDto responseDto = new DiaryCreateResponseDto(saveDiary);
        return responseDto;
    }

    /**
     * createDiary를 List형태로 조회하는 로직
     *
     * @return
     */
    @Transactional
    public DiaryListResponseDto getDiaryListService() {
        // 1. 데이터 준비(조회)
        List<Diary> diaryList = diaryRepository.findAll();

        List<DiaryListResponseDto.DiaryList> diaryDtoList = new ArrayList<>();

        if (!diaryList.isEmpty()) {
            for (Diary diary : diaryList) {
                DiaryListResponseDto.DiaryList diaryListResponse
                        = new DiaryListResponseDto.DiaryList(diary.getId(), diary.getEmail(),
                        diary.getUserName(), diary.getTitle(), diary.getContent(),
                        diary.getImage(), diary.getCreatedAt(), diary.getUpdatedAt());

               diaryDtoList.add(diaryListResponse);
            }
            DiaryListResponseDto listResponseDto = new DiaryListResponseDto(diaryDtoList);
            return listResponseDto;

        } else {
            throw new NullPointerException();


        }

        // 2. 반환 Dto 만들기
    }

}