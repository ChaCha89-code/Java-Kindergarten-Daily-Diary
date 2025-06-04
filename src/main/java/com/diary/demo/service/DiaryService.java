package com.diary.demo.service;

import com.diary.demo.domain.Diary;
import com.diary.demo.diaryDto.*;
import com.diary.demo.repository.DiaryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class DiaryService {
    // 속성
    private final DiaryRepository diaryRepository;

    @Value("${file.path}")
    private String uploadFolder;

    // 생성자
    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    // 기능
    @Transactional
    public ResponseEntity<?> createDiaryService(DiaryCreateRequestDto requestDto) {

        // 1. 데이터 준비
        Long id = requestDto.getUserId();
        String email = requestDto.getEmail();
        String userName = requestDto.getUserName();
        String title = requestDto.getTitle();
        String content = requestDto.getContent();
        MultipartFile image = requestDto.getImage();


        if(email.isBlank() || userName.isBlank() || title.isBlank() || content.isBlank()) {
            DiaryCreateErrorResponseDto error = new DiaryCreateErrorResponseDto(400, "게시글 입력 항목 중 빈 내용이 있습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        String url = null;
        if(image != null) {
            UUID uuid = UUID.randomUUID();
            String imageFileName = uuid + "_" + requestDto.getImage().getOriginalFilename();
            System.out.println("이미지 파일 이름: " + imageFileName);

            url = uploadFolder + imageFileName;
            Path imageFilePath = Paths.get(url);

            try {
                Files.write(imageFilePath, requestDto.getImage().getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 2. 엔티티 만들기
        Diary newDiary = new Diary(id,email, userName, title, content, url);

        // 3. 저장
        Diary savedDiary = diaryRepository.save(newDiary);

        // 4. responseDto 반환
        return ResponseEntity.ok(new DiaryCreateResponseDto(savedDiary));
    }

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

        } else {
            // 실패 응답
            throw new EntityNotFoundException("해당 ID의 게시글이 없습니다.");
        }

    }

    /**
     * createDiary를 List형태로 조회하는 로직
     *
     * @return listResponseDto를 반환
     * diaryList가 null이 아니면 데이터를 조회하여 diaryDtoList에 add하여 for문으로 배열화를 진행하였습니다
     * null 발생 시 NullPointerException throw 하여 Controller에서 catch할 수 있게 하였습니다
     */
    @jakarta.transaction.Transactional
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
            // diaryList.isEmpty()이 아닐 때 성공 응답
            DiaryListResponseDto listResponseDto = new DiaryListResponseDto(diaryDtoList);
            return listResponseDto;

        } else {
            // null 발생시 예외처리
            throw new NullPointerException();
        }

        // 2. 반환 Dto 만들기
    }

    // 게시글 수정
    @Transactional
    public DiaryUpdateResponseDto updateDiaryService(Long diaryId, DiaryUpdateRequestDto updateRequestDto) {
        // 1. 데이터 준비
        String title = updateRequestDto.getTitle();
        String content = updateRequestDto.getContent();
        String image = updateRequestDto.getImage();

        // 2. 조회
        Optional<Diary> diaryOptional = diaryRepository.findById(diaryId);
        if (diaryOptional.isPresent()) {
            Diary diary = diaryOptional.get();
            diary.updateDiary(title, content, image);

            // 3. dto 반환
            DiaryUpdateResponseDto updateResponseDto = new DiaryUpdateResponseDto(
                    diary.getId(),
                    diary.getEmail(),
                    diary.getUserName(),
                    diary.getImage(),
                    diary.getTitle(),
                    diary.getContent(),
                    diary.getCreatedAt(),
                    diary.getUpdatedAt()
            );
            return updateResponseDto;

        } else {
            // 4. 실패 응답 dto 반환
            throw new EntityNotFoundException("해당 ID의 게시글이 없습니다.");
        }
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
