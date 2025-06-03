package com.diary.demo.service;

import com.diary.demo.domain.Diary;
import com.diary.demo.domain.Users;
import com.diary.demo.repository.DiaryRepository;
import com.diary.demo.repository.UserRepository;
import com.diary.demo.userDto.UserDetailResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // 속
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;
    // 생

    public UserService(UserRepository userRepository, DiaryRepository diaryRepository) {
        this.userRepository = userRepository;
        this.diaryRepository = diaryRepository;
    }
    //기

    /**
     *
     * @param userId 으로 받은 데이터는 userRepository에서 데이터 조회하여 메서드 로직을 실행 할 수 있게 했습니다
     * @return userId가 null이 아니라면 userDetailResponseDto;를 null이 있다면 NullPointException 발생시켰습니다
     */
    @Transactional
    public UserDetailResponseDto getUserDetailService(Long userId) {
        // 데이터 준비(유저 정보 조회)
        Optional<Users> usersOptional = userRepository.findById(userId);

        // diaryRepository에 저장된 게시글 리스트를 모두 조회
        List<Diary> diaryRepositoryAll = diaryRepository.findAll();
        boolean usersOptionalPresent = usersOptional.isPresent();
        List<UserDetailResponseDto.UserDiaryList> usersDiaryList = new ArrayList<>();
        /**
         * userRepository에서 findById을 조회 하여 Optional타입으로 변수선언을 진행하였습니다
         * 변수선언한 데이터로 if문을 통해 null 처리 진행. null 발생 시에 throw new NullPointException 생성자 생성하였습니다
         * 또한 for문으로 저장된 user의 게시글을 조회하여 리스트화 하여 데이터 출력 하였습니다
         */
        // usersOptionalPresent != null 이라면 아래의 로직 실행
        if (usersOptionalPresent) {
            Users findUser = usersOptional.get();

            Long findUserId = findUser.getId();
            String findUserEmail = findUser.getEmail();
            String findUserUserName = findUser.getUserName();
            String findUserUserImage = findUser.getUserImage();
            // 게시글 전체를 배열화 하기 위한 로직
            for (Diary diary : diaryRepositoryAll) {
                UserDetailResponseDto.UserDiaryList userResponseDiaryList
                        = new UserDetailResponseDto.UserDiaryList(diary.getTitle(), diary.getCreatedAt());

                usersDiaryList.add(userResponseDiaryList);
            }
            // usersOptionalPresent가 null이 아닐 때 성공 응답
            // 응답Dto 생성
            UserDetailResponseDto userDetailResponseDto
                    = new UserDetailResponseDto(findUserId, findUserEmail, findUserUserName,
                                                findUserUserImage, usersDiaryList);
            // 데이터 반환
            return userDetailResponseDto;

        } else {
            //null 발생 시에 예외처리 던지는 로직
            throw new NullPointerException();
        }




    }
}
