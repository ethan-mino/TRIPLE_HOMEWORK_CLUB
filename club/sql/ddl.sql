CREATE TABLE user (
    id VARCHAR(255) NOT NULL COMMENT 'id',
    username VARCHAR(255) UNIQUE NOT NULL COMMENT '유저 아이디',
    password VARCHAR(255) NOT NULL COMMENT '유저 비밀번호',
    created_at DATE NOT NULL COMMENT '생성 일자',
    updated_at DATE NOT NULL COMMENT '수정 일자',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE place (
      id VARCHAR(255) NOT NULL COMMENT 'id',
      name VARCHAR(255) NOT NULL COMMENT '장소 이름',
      user_id VARCHAR(255) NOT NULL COMMENT 'user 테이블의 id',
      latitude VARCHAR(255) NOT NULL COMMENT '위도',
      longitude VARCHAR(255) NOT NULL COMMENT '경도',
      created_at DATE NOT NULL COMMENT '생성 일자',
      updated_at DATE NOT NULL COMMENT '수정 일자',
      PRIMARY KEY (id),
      FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;