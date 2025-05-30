package com.diary.demo.service;

import com.diary.demo.domain.Diary;
import com.diary.demo.dto.DiaryDetailResponseDto;
import com.diary.demo.repository.DiaryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import com.diary.demo.dto.DiaryCreateErrorResponseDto;
import com.diary.demo.dto.DiaryCreateRequestDto;
import com.diary.demo.dto.DiaryCreateResponseDto;
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

    // 생성자
    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    // 기능
    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public ResponseEntity<?> createDiaryService(DiaryCreateRequestDto requestDto) {

        // 1. 데이터 준비
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
            String imageFileName = uuid + "_" + requestDto.getImage().getOriginalFilename(); // getOriginalFilename() - 실제 이미지 파일의 이름
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
        Diary newDiary = new Diary(email, userName, title, content, url);

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
}
