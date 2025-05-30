package com.diary.demo.service;

import com.diary.demo.domain.Diary;
import com.diary.demo.dto.DiaryCreateErrorResponseDto;
import com.diary.demo.dto.DiaryCreateRequestDto;
import com.diary.demo.dto.DiaryCreateResponseDto;
import com.diary.demo.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

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
        Diary newDiary = new Diary(email, userName, title, content, url); // image 추가하면 여기에 넣어주기

        // 3. 저장
        Diary savedDiary = diaryRepository.save(newDiary);

        // 4. responseDto 반환
        return ResponseEntity.ok(new DiaryCreateResponseDto(savedDiary));
    }
}
