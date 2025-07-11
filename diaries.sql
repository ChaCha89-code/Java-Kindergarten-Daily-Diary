-- sql문 변경 이력 관리 파일

CREATE TABLE users
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '회원 식별자',
    email VARCHAR(50) NOT NULL COMMENT '회원 아이디(이메일)',
    user_name VARCHAR(50) NOT NULL COMMENT '회원 이름',
    password VARCHAR(255) NOT NULL COMMENT '회원 비밀번호',
    user_image VARCHAR(255) COMMENT '회원 프로필 사진',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '회원 정보 생성일',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '회원 정보 수정일'
);

CREATE TABLE diaries
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '게시글 식별자',
    user_id BIGINT COMMENT '회원 식별자',
    email VARCHAR(100) NOT NULL COMMENT '회원 이메일',
    user_name VARCHAR(50) NOT NULL COMMENT '회원 이름',
    title VARCHAR(100) NOT NULL COMMENT '게시글 제목',
    content TEXT NOT NULL COMMENT '게시글 내용',
    image VARCHAR(255) COMMENT '작성자가 업로드할 이미지',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '생성일',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '수정일',
    FOREIGN KEY (user_id) REFERENCES users(id)
);