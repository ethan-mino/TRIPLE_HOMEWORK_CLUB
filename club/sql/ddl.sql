CREATE TABLE user (
    id VARCHAR(255) NOT NULL COMMENT '유저 아이디',
    username VARCHAR(255) UNIQUE NOT NULL COMMENT '유저 아이디',
    password VARCHAR(255) NOT NULL COMMENT '유저 비밀번호',
    created_at DATETIME NOT NULL COMMENT '생성 일자',
    updated_at DATETIME NOT NULL COMMENT '수정 일자',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE place (
      id VARCHAR(255) NOT NULL COMMENT '장소 아이디',
      name VARCHAR(255) NOT NULL COMMENT '장소 이름',
      writer_id VARCHAR(255) NOT NULL COMMENT '장소 생성자 아이디',
      address VARCHAR(255) NOT NULL COMMENT '주소',
      latitude VARCHAR(255) NOT NULL COMMENT '위도',
      longitude VARCHAR(255) NOT NULL COMMENT '경도',
      created_at DATETIME NOT NULL COMMENT '생성 일자',
      updated_at DATETIME NOT NULL COMMENT '수정 일자',
      PRIMARY KEY (id),
      FOREIGN KEY (writer_id) REFERENCES user(id),
      UNIQUE KEY `unique_address` (address, latitude, longitude)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE review(
    id VARCHAR(255) NOT NULL COMMENT '리뷰 아이디',
    place_id VARCHAR(255) NOT NULL COMMENT '장소 아이디',
    writer_id VARCHAR(255) NOT NULL COMMENT '작성자 아이디',
    content TEXT NULL COMMENT '리뷰 내용',
    point INTEGER NOT NULL COMMENT '리뷰 포인트',
    created_at DATETIME NOT NULL COMMENT '생성 일자',
    updated_At DATETIME NOT NULL COMMENT '수정 일자',
    PRIMARY KEY (id),
    UNIQUE KEY(place_id, writer_id),
    FOREIGN KEY (writer_id) REFERENCES user(id),
    FOREIGN KEY (place_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE file(
    id VARCHAR(255) NOT NULL COMMENT '파일 아이디',
    owner_id VARCHAR(255) NOT NULL COMMENT '소유자 아이디',
    url TEXT NOT NULL COMMENT 'url',
    content_type VARCHAR(255) NULL COMMENT 'content 유형',
    created_at DATETIME NOT NULL COMMENT '생성 일자',
    updated_at VARCHAR(255) NOT NULL COMMENT '수정 일자',
    PRIMARY KEY (id),
    FOREIGN KEY (owner_id) REFERENCES user(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE review_image(
    image_file_id VARCHAR(255) NOT NULL COMMENT '이미지 파일 아이디',
    review_id VARCHAR(255) NOT NULL COMMENT '리뷰 아이디',
    FOREIGN KEY (image_file_id) REFERENCES file(id) ON DELETE CASCADE,
    FOREIGN KEY (review_id) REFERENCES review(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;