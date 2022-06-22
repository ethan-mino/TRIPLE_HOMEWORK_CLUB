CREATE TABLE user (
    id VARCHAR(255) NOT NULL COMMENT 'id',
    username VARCHAR(255) UNIQUE NOT NULL COMMENT '유저 아이디',
    password VARCHAR(255) NOT NULL COMMENT '유저 비밀번호',
    created_at DATE NOT NULL COMMENT '생성 일자',
    updated_at DATE NOT NULL COMMENT '수정 일자',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;