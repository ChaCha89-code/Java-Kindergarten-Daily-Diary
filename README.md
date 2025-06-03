Create - 게시글 생성 / 회원가입 (담당자: 차순영)
1) 게시글 생성
⛔문제 상황 : 
포스트맨 form-data 형식으로 이미지 파일 저장하는 과정에서 단순 String으로 이미지 주소를 저장하려 했으나 회원들이 소장하고 있는 파일을 업로드해야 하는 상황과 맞지 않았습니다.
1)의 문제 해결 후, 포스트맨 form-data 형식으로 이미지 저장하는 과정 중  500 internal server error 발생했습니다.
🔎 원인 분석 :
requestDto 속성의 자료타입이 파일을 값을 받을 수 없는 String 으로 지정되어 있었습니다. 또한 파일 명이 겹치지 않게 하기 위한 장치가 필요했습니다.
파일 경로에 명시된 폴더 이름에 오타가 있어 파일경로가 제대로 적용되지 못함.
💥 해결 과정 :
문제 해결을 위해 요청 속성 값의 자료타입을 MultipartFile으로 변환하고 UUID.randomUUID() 기능을 사용해 이미지 파일이 같은 이름을 갖지 않도록 url값을 지정했습니다. 또한 application.properties 에 file.path=C:/…/ 를 추가하고 서비스 속성 Private String uploadFolder 에 @Value("${file.path}") 를 추가하여 파일 경로를 확보했습니다.
application.properties의 file.path의 값을 실제 컴퓨터의 파일경로를 대조하여여 폴더명의 오타를 수정했습니다.


2) 회원가입
⛔문제 상황 : 사용자의 비밀번호를 해시화하기 위해 PasswordEncoder를 사용해야해서 build.graddle에 implementation 'org.springframework.boot:spring-boot-starter-security'을 삽입해 Spring Security 의존성을 추가했습니다. 하지만 포스트맨으로 회원가입 API 요청을  했을 때 401 Unauthorized 에러가 발생했습니다.
🔎 원인 분석 : 포스트맨으로 회원 가입 요청시 Spring Securiy가 자동으로 인증을 요구하도록 설정되어 있었기 때문입니다.
💥 해결 과정 : 로그인을 안해도 되는 회원가입 API(api/users)를 막지 않도록 SecurityConfig.java에 SecurityFilterChain을 추가하여 포스트맨으로 회원가입 API 요청시 인증 없이 회원 가입을 요청할 수 있게 했습니다.

==================================================================================================

Read - 게시글 상세 조회 / 게시글 목록 조회 / 회원 정보 조회
1) 게시글 상세 조회 (담당자 : 최희정)
문제 상황상세 조회 API 404 오류
⛔문제 상황 :  Postman에서 /diaries/1 호출했는데, 응답으로 404 Not Found가 뜸.
{
  "status": 404,
  "message": "게시글 정보를 조회할 수 없습니다."
}
하지만 DB에는 id=1 게시글이 분명 있음.
🔎 원인 분석 :  
DiaryRepository.findById(diaryId) 결과가 Optional.empty()
이유: JPA의 @GeneratedValue(strategy = GenerationType.IDENTITY) 쓸 때, 테스트 중 DB가 날아가거나 ID값이 연속되지 않는 경우가 있음.
또는 DB에는 있지만 테스트 데이터가 H2 등 메모리 DB로 교체된 경우도 있음.
💥 해결 과정 :
1. DB를 직접 확인해서 해당 diaryId가 존재하는지 확인
2. 실제 사용하는 DB와 애플리케이션이 연결되어 있는지 확인 (application.yml)
3. diaryRepository.findAll() 로 전체 게시글 조회해서 ID 범위 확인
✅ 최종 해결 :
실제 데이터가 없는 상태였음 → 더미 데이터 추가하거나 ID 확인
또는 DB 연결이 잘못되어 있었음 → DB 설정 확인 후 수정

==================================================================================================

2) 게시글 목록 조회 (담당자: 김광수)
⛔문제 상황 : Serivce에서 diaryRepository.findAll(); 하였을 때 null값이 있을 경우 예외처리 하여 Controller에서 그에 관한 메세지를 반환해야 하는 상황이었습니다. 

🔎 원인 분석 :  에러응답메세지 클래스를 만들어 객체를 생성하고 그 객체를 ResponseEntity<>의 body부분으로 써야 했는데, 성공시에 반환 시킬 데이터 타입과 실패시 반환 시킬 데이터 타입이 달라서 어떻게 데이터를 반환 시키나 고민 했습니다  

💥 해결 과정 : 2가지 방법을 찾았습니다. 
공통 부모 타입 클래스를 만들어 해당 클래스를 각 응답Dto에 상속시켜 부모 클래스를 반환타입으로 설정
ResponseEntity<>의 제네릭부분에 ?를 넣어 모든 데이터 타입을 반환하게 설정

1번의 경우 객체 지향적 설계와 유지보수를 위한 안정성 면에서 1번의 방법을 택하기 합당하지만 본 학생은 현재 마감기한에 쫓겨 현재로선 추가로 공부를 해야하는 방법에 대해 택할 여유가 없는 상황이었습니다. 
하여 유연성이 뛰어난 2번 타입을 사용 하였습니다. 추후 abstract을 사용 하는 방법을 찾아 리팩토링하는 과정을 거쳐보면 좋을 것 같습니다.


3) 회원 정보 조회 (담당자: 김광수)
⛔문제 상황 : 
 회원정보 성공시에 응답해야 할 내용입니다. id, email, userName, userImage 는 회원id로 조회시에 정상적으로 출력이 되었지만 userDiaryList는 null이 나왔습니다. 

🔎 원인 분석 :  userDiaryList의 데이터는 게시글 생성시에 DiaryRepository에 저장이 되는 데이터였고, UserRepository에서 데이터를 찾으려하니 계속 null값이 나오는 상황이었습니다. 

💥 해결 과정 : 그럼 DiaryRepository에서 데이터를 찾아서 그걸 활용하면 되지 않나? 라는 생각이 들어 Service에 DiaryRepository를 의존할 수 있게 속성값과 생성자를 추가해 주었고, DiaryRepository에서 데이터를 조회하여 로직을 만들었습니다.
의존성 추가 및 생성자에 DiaryRepository추가

DiaryRepository에서 데이터를 찾아서 배열화.

==================================================================================================

Update - 게시글 수정 / 회원 정보 수정 (담당자: 최희정)
1) 게시글 수정
게시글 수정 API 400 오류
⛔문제 상황 :  PATCH /diaries/2 요청 시 400 Bad Request 발생
Postman에서는 아래처럼 보냈음 :
{
  "title": "새 제목",
  "content": "새 내용",
  "image": "new.png"
}
🔎 원인 분석 :  
DTO인 DiaryUpdateRequestDto에 Getter는 있는데 기본 생성자가 없거나,
클래스에 생성자가 빠짐
또는 필드명 오타로 인해 title, content 등이 바인딩 안 됨

💥 해결 과정 :
1. DiaryUpdateRequestDto 클래스 확인
생성자가 있는지 확인

title, content, image 필드명과 JSON 키가 일치하는지 확인
2. 요청 로그 찍어서 DTO가 제대로 들어오는지 확인

✅ 최종 해결 :
    public DiaryUpdateRequestDto(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public String getImage() {
        return image;
    }
→ 생성자 추가 후 정상 작동

==================================================================================================

2) 회원 정보 수정
회원 비밀번호 변경 API 실패 (같은 비밀번호인데도 불일치 오류)
⛔문제 상황 :  PATCH /users/1 호출 시
{
  "status": 400,
  "message": "비밀번호가 일치하지 않습니다."
}
요청한 비밀번호는 기존 비밀번호와 맞는데 계속 실패함
🔎 원인 분석 :  
비밀번호 비교 시 passwordEncoder.matches() 사용 중
기존 비밀번호를 암호화할 때 passwordEncoder.encode() 로 암호화 방식이 달라졌거나,
테스트 데이터의 비밀번호가 평문이거나 다른 인코더로 저장된 경우

💥 해결 과정 :
1. DB에서 해당 유저의 password 필드 직접 확인 → 인코딩된 값인지?
2. 테스트 유저 만들 때 passwordEncoder.encode() 를 써서 저장했는지 확인
3. 암호화 알고리즘이 프로젝트 변경 도중 바뀌었는지도 체크 (ex. BCrypt → SCrypt 등)

✅ 최종 해결 :
테스트 유저의 비밀번호를 평문으로 저장하고 있었음
users.setPassword(passwordEncoder.encode("1234")); 로 바꿔서 해결

==================================================================================================

Delete - 게시글 삭제 / 회원 탈퇴 (담당자: 안상아)
1) 게시글 삭제
⛔문제 상황 :
일정 삭제 API에서 IllegalArgumentException.getMessage()가 null로 응답됨

🔎 원인 분석 및 💥해결 과정 : 
throw new IllegalArgumentException()호출 시 메세지를 생략함(getMessage()가 null반환)

✅ 최종 해결 :
예외 생성 시 항상 메세지 포함 시키기
throw new IllegalArgumentException(“해당 글이 존재하지 않습니다.”)


2) 회원 탈퇴
⛔문제 상황 1:
userService class에서 조회 시 findByEmail()과 랑 getPassword()가 빨간 글씨 컴파일 오류 발생
🔎 원인 분석 : 
메서드 누락
User 엔티티에 필드 누락 혹은 필드 이름 불일치
접근 제한자 문제
💥 해결 과정 : 
UserRepository 인터페이스에 findByEmail 메서드가 선언되지 않았고, User 클래스에 getPassword 메서드 누락

 ✅ 최종 해결 : 
UserRepository에는 Optional<Users>findByEmail(String Email) 선언하고, User 클래스에는 public String getPassword() 메서드 생성


⛔문제 상황 2 : 
회원 탈퇴 처리 시 로직이 제대로 작동하지 않음

🔎 원인 분석 : 
회원 탈퇴 로직의 흐름을 제대로 파악해야 함

💥 해결 과정 : 
이메일 불일치(회원 없음) 확인 >회원 없는 경우 바로 예외 던지고. 메서드 종료
회원 존재 시 비밀번호 확인 > 비밀번호 틀리면 예외 던지고 메서드 종료
비밀번호 맞으면 정상 처리(회원 삭제 후 성공 응답 반환)

 ✅ 최종 해결 : 





