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

--  주민 생일 컬럼 형식 변경
alter table villager MODIFY villager_birth VARCHAR(5);

--데이터베이스
--  MEMBER 
insert into member (member_id, member_pw, member_name, member_email) values ('jisu123', 'password123', '김지수', 'jisu123@gmail.com');

--  BOARD
insert into board (board_title, board_content, board_writer, member_no) values ('첫번째 게시글', '안녕하세요 첫번째 게시글입니다.', '김지수', 1);
--  VILLAGER_TYPE_CODE
insert into villager_type_code (villager_type, type_name) values
(1,'개'),
(2,'개구리'),
(3,'개미핥기'),
(4,'고릴라'),
(5,'고양이'),
(6,'곰'),
(7,'꼬마곰'),
(8,'늑대'),
(9,'다람쥐'),
(10,'닭'),
(11,'독수리'),
(12,'돼지'),
(13,'말'),
(14,'문어'),
(15,'사슴'),
(16,'사자'),
(17,'새'),
(18,'생쥐'),
(19,'소'),
(20,'악어'),
(21,'양'),
(22,'염소'),
(23,'오리'),
(24,'원숭이'),
(25,'캥거루'),
(26,'코끼리'),
(27,'코뿔소'),
(28,'코알라'),
(29,'타조'),
(30,'토끼'),
(31,'펭귄'),
(32,'하마'),
(33,'햄스터'),
(34,'호랑이');

--  VILLAGER
insert into villager (villager_category, villager_type, villager_name, villager_name_en, villager_name_jp, villager_image, villager_birth, villager_sex) values
(1,1,'럭키','Lucky','ラッキー','lucky.png','11-04',1),
(1,1,'로빈','Biskit','ロビン','robin.png','05-13',1),
(1,1,'존','Butch','ジョン','john.png','11-01',1),
(1,1,'토미','Bones','トミ','tommy.png','08-04',1);

select * from board