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
    villager_birth     VARCHAR(5),
    villager_sex       TINYINT(1)  COMMENT '0:여, 1:남',
    villager_vote      INT         DEFAULT 0,
    CONSTRAINT fk_villager_type
    FOREIGN KEY (villager_type)
    REFERENCES villager_type_code(villager_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
(1,1,'토미','Bones','トミ','tommy.png','08-04',1),
(1,1,'벤','Walker','ベン','06-10',1)

select * from board

select * from member;
SHOW CREATE TABLE member;

create Table test;

-- =========================================
-- 1️⃣ 기존 테이블 삭제 (존재하면 삭제)
-- =========================================
DROP TABLE IF EXISTS board;
DROP TABLE IF EXISTS villager;
DROP TABLE IF EXISTS villager_type_code;
DROP TABLE IF EXISTS member;

-- =========================================
-- 2️⃣ 테이블 생성
-- =========================================

-- 2-1. Member 테이블
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

-- 2-2. Villager Type Code 테이블
CREATE TABLE villager_type_code (
    villager_type   TINYINT PRIMARY KEY,
    type_name       VARCHAR(20) NOT NULL,
    type_name_en VARCHAR(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2-3. Villager 테이블
CREATE TABLE villager (
    villager_no        INT AUTO_INCREMENT PRIMARY KEY,
    villager_category  TINYINT(1)  NOT NULL COMMENT '1:일반, 2:특수', 
    villager_type      TINYINT    NOT NULL,
    villager_name      VARCHAR(50) NOT NULL,
    villager_name_en   VARCHAR(50),
    villager_name_jp   VARCHAR(50),
    villager_image     VARCHAR(255),
    villager_image_icon VARCHAR(255),  -- 아이콘 이미지 컬럼 추가
    villager_birth     VARCHAR(5),  -- 날짜 형식 변경
    villager_debut VARCHAR(50),  -- 데뷔 컬럼 추가
    villager_sex       TINYINT(1)  COMMENT '0:여, 1:남',
    villager_vote      INT         DEFAULT 0,
    CONSTRAINT fk_villager_type
    FOREIGN KEY (villager_type)
    REFERENCES villager_type_code(villager_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2-4. Board 테이블
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
    board_kind VARCHAR(20) NOT NULL DEFAULT 'notice',
    CONSTRAINT fk_board_member
    FOREIGN KEY (member_no)
    REFERENCES member(member_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================================
-- 완료 메시지 (선택 사항)
-- =========================================
SELECT '모든 테이블 삭제 후 새로 생성 완료!' AS result;

-- ========================================================================================
--  02-03 Board 공지/자유게시판 분기점을 위한 컬럼 추가 및 기존 데이터는 공지로 지정하기 위한 수정dml
-- ========================================================================================
ALTER TABLE board
ADD COLUMN board_kind VARCHAR(20) NOT NULL DEFAULT 'notice';
UPDATE board
SET board_kind = 'notice'
WHERE board_kind IS NULL OR board_kind = '';

-- =========================================================
--  02-03 Villager 테이블에 아이콘 이미지 컬럼 및 데뷔 컬럼 추가, 실제 데이터 입력          
-- =========================================================
ALTER TABLE villager
ADD villager_image_icon VARCHAR(255) AFTER villager_image;

ALTER TABLE villager
ADD villager_debut VARCHAR(50) AFTER villager_birth;


-- 기존 이미지 데이터 초기화 및 villager_type_code 테이블에 영문 타입명 컬럼 

-- 1. 기존 이미지 데이터 초기화
UPDATE villager
SET villager_image = NULL,
    villager_image_icon = NULL;

-- 2. villager_type_code 테이블에 영문 타입명 컬럼 추가
ALTER TABLE villager_type_code
ADD type_name_en VARCHAR(20) NOT NULL AFTER type_name;

UPDATE villager_type_code SET type_name_en='Dog'       WHERE villager_type=1;   -- 개
UPDATE villager_type_code SET type_name_en='Frog'      WHERE villager_type=2;   -- 개구리
UPDATE villager_type_code SET type_name_en='Anteater'  WHERE villager_type=3;   -- 개미핥기
UPDATE villager_type_code SET type_name_en='Gorilla'   WHERE villager_type=4;   -- 고릴라
UPDATE villager_type_code SET type_name_en='Cat'       WHERE villager_type=5;   -- 고양이
UPDATE villager_type_code SET type_name_en='Bear'      WHERE villager_type=6;   -- 곰
UPDATE villager_type_code SET type_name_en='Bear cub'  WHERE villager_type=7;   -- 꼬마곰 (공백 포함) :contentReference[oaicite:1]{index=1}
UPDATE villager_type_code SET type_name_en='Wolf'      WHERE villager_type=8;   -- 늑대
UPDATE villager_type_code SET type_name_en='Squirrel'  WHERE villager_type=9;   -- 다람쥐
UPDATE villager_type_code SET type_name_en='Chicken'   WHERE villager_type=10;  -- 닭
UPDATE villager_type_code SET type_name_en='Eagle'     WHERE villager_type=11;  -- 독수리
UPDATE villager_type_code SET type_name_en='Pig'       WHERE villager_type=12;  -- 돼지
UPDATE villager_type_code SET type_name_en='Horse'     WHERE villager_type=13;  -- 말
UPDATE villager_type_code SET type_name_en='Octopus'   WHERE villager_type=14;  -- 문어
UPDATE villager_type_code SET type_name_en='Deer'      WHERE villager_type=15;  -- 사슴
UPDATE villager_type_code SET type_name_en='Lion'      WHERE villager_type=16;  -- 사자
UPDATE villager_type_code SET type_name_en='Bird'      WHERE villager_type=17;  -- 새
UPDATE villager_type_code SET type_name_en='Mouse'     WHERE villager_type=18;  -- 생쥐
UPDATE villager_type_code SET type_name_en='Cow'       WHERE villager_type=19;  -- 소
UPDATE villager_type_code SET type_name_en='Alligator' WHERE villager_type=20;  -- 악어
UPDATE villager_type_code SET type_name_en='Sheep'     WHERE villager_type=21;  -- 양
UPDATE villager_type_code SET type_name_en='Goat'      WHERE villager_type=22;  -- 염소
UPDATE villager_type_code SET type_name_en='Duck'      WHERE villager_type=23;  -- 오리
UPDATE villager_type_code SET type_name_en='Monkey'    WHERE villager_type=24;  -- 원숭이
UPDATE villager_type_code SET type_name_en='Kangaroo'  WHERE villager_type=25;  -- 캥거루
UPDATE villager_type_code SET type_name_en='Elephant'  WHERE villager_type=26;  -- 코끼리
UPDATE villager_type_code SET type_name_en='Rhinoceros'WHERE villager_type=27;  -- 코뿔소
UPDATE villager_type_code SET type_name_en='Koala'     WHERE villager_type=28;  -- 코알라
UPDATE villager_type_code SET type_name_en='Ostrich'   WHERE villager_type=29;  -- 타조
UPDATE villager_type_code SET type_name_en='Rabbit'    WHERE villager_type=30;  -- 토끼
UPDATE villager_type_code SET type_name_en='Penguin'   WHERE villager_type=31;  -- 펭귄
UPDATE villager_type_code SET type_name_en='Hippo'     WHERE villager_type=32;  -- 하마
UPDATE villager_type_code SET type_name_en='Hamster'   WHERE villager_type=33;  -- 햄스터
UPDATE villager_type_code SET type_name_en='Tiger'     WHERE villager_type=34;  -- 호랑이

INSERT INTO villager_type_code (villager_type, type_name, type_name_en)
VALUES (35, '황소', 'Bull');

INSERT INTO villager_type_code (villager_type, type_name, type_name_en)
VALUES (0, '기타', 'Other')
ON DUPLICATE KEY UPDATE type_name=VALUES(type_name), type_name_en=VALUES(type_name_en);

ALTER TABLE villager
ADD UNIQUE KEY uk_villager_name_en (villager_name_en);

-- 데이터 넣는 방법

-- sql부터 정리 하기
-- 1. select * from villager로 데이터 있는지 확인
-- 2. 있으면 delete from villager;로 삭제(villager_no까지 초기화 하고 싶으면 TRUNCATE TABLE villager; 로 초기화 하기)
-- 3. villager_type_code 테이블에 type_name_en 컬럼 추가하기 (
-- ALTER TABLE villager_type_codeADD type_name_en VARCHAR(20) NOT NULL AFTER type_name; )
-- 4. villager_type_code 테이블에 영문 타입명 업데이트 하기 (209줄 부터 249줄 까지 한줄 한줄 , 순서 중요)
-- 5. villager 테이블에 villager_name_en 컬럼에 유니크 제약조건 추가하기 (  251번줄 : ALTER TABLE villagerADD UNIQUE KEY uk_villager_name_en (villager_name_en);  )

-- powerShell,cmd,bash:  
--curl -X POST http://localhost:8080/api/admin/nookipedia/sync (입력하기 & 엔터)
-- 결과가 417 나오면 417개의 데이터가 들어갔다는 의미. 즉 성공 
 
