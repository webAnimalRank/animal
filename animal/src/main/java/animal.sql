CREATE TABLE member (
    member_no     INT AUTO_INCREMENT PRIMARY KEY,
    member_id     VARCHAR(50)  NOT NULL,
    member_pw     VARCHAR(100) NOT NULL,
    member_name   VARCHAR(50)  NOT NULL,
    member_email  VARCHAR(100),
    isactive      TINYINT(1)   NOT NULL DEFAULT 1 COMMENT '0:비활성화, 1: 활성화',
    create_date   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date   DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--    board_no는 member 테이블에서 사용 할 수 없음. 한명이 여러 게시물글을 적을 수 있기 때문
CREATE TABLE board (
    board_no INT AUTO_INCREMENT PRIMARY KEY,
    board_title VARCHAR(300) NOT NULL,
    board_content VARCHAR(3000) NOT NULL,
    board_writer VARCHAR(100) NOT NULL,
    create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_date DATETIME DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    isactive TINYINT DEFAULT 1 COMMENT '0:비활성화, 1:활성화',
    member_no INT,
    CONSTRAINT fk_board_member
    FOREIGN KEY (member_no)
    REFERENCES member(member_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE villager_type_code (
    villager_type   TINYINT PRIMARY KEY ,
    type_name VARCHAR(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE villager (
    villager_no        INT AUTO_INCREMENT PRIMARY KEY,
    villager_category  TINYINT(1)  NOT NULL COMMENT '1:일반, 2:특수', 
    villager_type      TINYINT    NOT NULL,
    villager_name      VARCHAR(50) NOT NULL,
    villager_name_en   VARCHAR(50),
    villager_name_jp   VARCHAR(50),
    villager_image     VARCHAR(255),
    villager_birth     DATE,
    villager_sex       TINYINT(1)  COMMENT '0:여, 1:남',
    villager_vote      INT         DEFAULT 0,
    CONSTRAINT fk_villager_type
    FOREIGN KEY (villager_type)
    REFERENCES villager_type_code(villager_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

select * from board;
select * from member;
insert into member (member_id, member_pw, member_name, member_email) values ('jisu123', 'password123', '김지수', 'jisu123@gmail.com');
insert into board (board_title, board_content, board_writer, member_no) values ('첫번째 게시글', '안녕하세요 첫번째 게시글입니다.', '김지수', 1);