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

-- 주민 데이터 넣는 방법

-- sql부터 정리 하기
-- 1. select * from villager로 데이터 있는지 확인
-- 2. 있으면 delete from villager;로 삭제(villager_no까지 초기화 하고 싶으면 TRUNCATE TABLE villager; 로 초기화 하기)
-- 3. villager_type_code 테이블에 type_name_en 컬럼 추가하기 (
-- ALTER TABLE villager_type_codeADD type_name_en VARCHAR(20) NOT NULL AFTER type_name; )
-- 4. villager_type_code 테이블에 영문 타입명 업데이트 하기 (209줄 부터 249줄 까지 한줄 한줄 , 순서 중요)
-- 5. villager 테이블에 villager_name_en 컬럼에 유니크 제약조건 추가하기 (  251번줄 : ALTER TABLE villagerADD UNIQUE KEY uk_villager_name_en (villager_name_en);  )

-- powerShell,cmd,bash:  
--curl -X POST http://localhost:8080/api/admin/nookipedia/sync (입력하기 & 엔터)
--위 문구는 bootrun 후에 실행 해야함.
-- 결과가 417 나오면 417개의 데이터가 들어갔다는 의미. 즉 성공 
 
 -- 02.11 villager_name_en,jp,ko 변환 작업

 -- 1. 번역할 테이블 생성
 CREATE TABLE IF NOT EXISTS villager_name_translation (
  villager_name_en VARCHAR(50) PRIMARY KEY,
  villager_name_ko VARCHAR(50) NOT NULL,
  villager_name_jp VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. 번역 데이터 삽입
INSERT INTO villager_name_translation
(villager_name_en, villager_name_ko, villager_name_jp)
VALUES
('Ace','페더','フェザー'),
('Admiral','일섭','イッテツ'),
('Agent S','2호','２ごう'),
('Agnes','아그네스','アグネス'),
('Al','우락','たもつ'),
('Alfonso','알베르트','アルベルト'),
('Alice','멜버른','メルボルン'),
('Alli','크로크','クロコ'),
('Amelia','안데스','アンデス'),
('Anabelle','아롱이','あるみ'),
('Anchovy','안쵸비','アンチョビ'),
('Angus','반데스','セルバンテス'),
('Anicotti','라자냐','ラザニア'),
('Ankha','클레오','ナイル'),
('Annalisa','설백','みやび'),
('Annalise','실부플레','シルブプレ'),
('Antonio','퍼머거','マコト'),
('Apollo','아폴로','アポロ'),
('Apple','애플','アップル'),
('Astrid','펑키맘','キッズ'),
('Audie','모니카','モニカ'),
('Aurora','오로라','オーロラ'),
('Ava','에바','ドミグラ'),
('Avery','쿠스케처','クスケチャ'),
('Axel','엑스엘리','エックスエル'),
('Azalea','페튜니아','ペチュニア'),
('Baabara','트로와','トロワ'),
('Bam','록키','タケル'),
('Bangle','루주','ルーズ'),
('Barold','곰시','ニッシー'),
('Bea','베이글','ベーグル'),
('Beardo','베어드','ベアード'),
('Beau','피터','ペーター'),
('Becky','아리아','アリア'),
('Bella','이자벨','イザベラ'),
('Benedict','페니실린','ぺしみち'),
('Benjamin','땡칠이','ハチ'),
('Bertha','베티','あんこ'),
('Bettina','마르카','マルコ'),
('Bianca','백희','コユキ'),
('Biff','가브리엘','ガブリエル'),
('Big Top','3호','３ごう'),
('Bill','코코아','ピータン'),
('Billy','힘드러','アーシンド'),
('Biskit','로빈','ロビン'),
('Bitty','비티','エーミー'),
('Blaire','실루엣','シルエット'),
('Blanche','신옥','しのぶ'),
('Bluebear','글루민','グルミン'),
('Bob','히죽','ニコバン'),
('Bonbon','미미','ミミィ'),
('Bones','토미','トミ'),
('Boomer','팽기','ショーイ'),
('Boone','만복이','まんたろう'),
('Boots','풍작','ホウサク'),
('Boris','보리','ダリー'),
('Boyd','보이드','ボイド'),
('Bree','사라','サラ'),
('Broccolo','브로콜리','ブロッコリー'),
('Broffina','히킨','カサンドラ'),
('Bruce','브루스','ブルース'),
('Bubbles','차코','チャコ'),
('Buck','바야시코프','ヴァヤシコフ'),
('Bud','선글','グラさん'),
('Bunnie','릴리안','リリアン'),
('Butch','존','ジョン'),
('Buzz','근엄','ひでよし'),
('Cally','파슬리','パセリ'),
('Camofrog','충성','フルメタル'),
('Canberra','캔버라','キャンベラ'),
('Candi','사탕','かんゆ'),
('Carmen','초코','チョコ'),
('Caroline','캐롤라인','キャロライン'),
('Carrie','마미','マミィ'),
('Cashmere','캐시미어','ラムール'),
('Cece','나기사','なぎさ'),
('Celia','티파니','ティファニー'),
('Cephalobot','기가','ギーガー'),
('Cesar','앨런','アラン'),
('Chabwick','펭구','のぶお'),
('Chadder','치즈','チーズ'),
('Chai','피카','フィーカ'),
('Champ','몽돌이','さるお'),
('Charlise','챠미','チャーミー'),
('Chelsea','첼시','チェルシー'),
('Cheri','아세로라','アセロラ'),
('Cherry','한나','ハンナ'),
('Chester','팬타','パンタ'),
('Chevre','윤이','ユキ'),
('Chief','대장','チーフ'),
('Chops','돈후앙','トンファン'),
('Chow','츄양','チャウヤン'),
('Chrissy','크리스틴','クリスチーヌ'),
('Claude','비니거','ビネガー'),
('Claudia','신디','マリリン'),
('Clay','햄둥','どぐろう'),
('Cleo','아이소토프','アイソトープ'),
('Clyde','마철이','デースケ'),
('Coach','철소','テッチャン'),
('Cobb','박사','ハカセ'),
('Coco','이요','やよい'),
('Cole','아마민','アマミン'),
('Colton','안소니','アンソニー'),
('Cookie','베리','ペリーヌ'),
('Cousteau','왕서방','ハルマキ'),
('Cranston','타키','トキオ'),
('Croque','투투','タイシ'),
('Cube','빙수','ビス'),
('Curlos','카를로스','カルロス'),
('Curly','햄까스','ハムカツ'),
('Curt','뚝심','ガンテツ'),
('Cyd','펑크스','パンクス'),
('Cyrano','사지마','さくらじま'),
('Daisy','바닐라','バニラ'),
('Deena','마리모','まりも'),
('Deirdre','나디아','ナディア'),
('Del','파도맨','ヤマト'),
('Deli','델리','デリー'),
('Derwin','봉','ボン'),
('Diana','나탈리','ナタリー'),
('Diva','아이다','アイーダ'),
('Dizzy','휴지','ヒュージ'),
('Dobie','켄','けん'),
('Doc','토니','トビオ'),
('Dom','차둘','ちゃちゃまる'),
('Dora','티미','とめ'),
('Dotty','서머','マーサ'),
('Drago','용남이','タツオ'),
('Drake','푸아그라','フォアグラ'),
('Drift','덕','ドク'),
('Ed','꺼벙','キザノホマレ'),
('Egbert','김희','しもやけ'),
('Elise','몽자','モンこ'),
('Ellie','에끌레르','エクレア'),
('Elmer','샤브렌','サブレ'),
('Eloise','엘레핀','エレフィン'),
('Elvis','킹','キング'),
('Epona','에포나','エポナ'),
('Erik','자끄','チャック'),
('Étoile','에뜨와르','エトワール'),
('Eugene','코알','ロッキー'),
('Eunice','곱슬이','モヘア'),
('Faith','마치','マーチ'),
('Fang','시베리아','シベリア'),
('Fauna','솔미','ドレミ'),
('Felicity','예링','みかっち'),
('Felyne','아이루','アイルー'),
('Filbert','리키','リッキー'),
('Filly','7호','7ごう'),
('Flip','원승','さすけ'),
('Flo','레이라','レイラ'),
('Flora','플라라','フララ'),
('Flurry','뽀야미','ゆきみ'),
('Francine','프랑소와','フランソワ'),
('Frank','헐크','ハルク'),
('Freckles','다랑어','マグロ'),
('Frett','샹펜','シャンペン'),
('Freya','산드라','ツンドラ'),
('Friga','사브리나','サブリナ'),
('Frita','웬디','ウェンディ'),
('Frobert','구리구리','コージィ'),
('Fuchsia','제시카','ジェシカ'),
('Gabi','패티카','ペチカ'),
('Gala','꽃지','ためこ'),
('Ganon','가논','ガノン'),
('Gaston','대길','モサキチ'),
('Gayle','앨리','アリゲッティ'),
('Genji','토시','ゲンジ'),
('Gigi','린다','リンダ'),
('Gladys','빅토리아','ちとせ'),
('Gloria','마릴린','スワンソン'),
('Goldie','카라멜','キャラメル'),
('Gonzo','근성','ゴンゾー'),
('Goose','건태','ケンタ'),
('Graham','글라햄','グラハム'),
('Greta','복자','ふくこ'),
('Grizzly','무뚝','ムー'),
('Groucho','거무틱','クロー'),
('Gruff','빌리','ビリー'),
('Gwen','폴라','ポーラ'),
('Hamlet','햄스틴','ハムスケ'),
('Hamphrey','햄쥐','ハムジ'),
('Hans','스나일','スナイル'),
('Harry','올리버','オリバー'),
('Hazel','아이리스','アイリス'),
('Henry','헨리','ヘンリー'),
('Hippeux','데이빗','ディビッド'),
('Holden','노리보','のりぼう'),
('Hopkins','홉킨스','プースケ'),
('Hopper','달만이','ダルマン'),
('Hornsby','뿌람','みつお'),
('Huck','스트로','ストロー'),
('Hugh','먹고파','クッチャネ'),
('Iggly','김말이','のりまき'),
('Ike','대공','ダイク'),
('Inkwell','멍무리','スミダクン'),
('Ione','스피카','スピカ'),
('Jacob','야곱','ジャコテン'),
('Jacques','쪼끼','ジョッキー'),
('Jambette','에스메랄다','エスメラルダ'),
('Jay','참돌이','ツバクロ'),
('Jeremiah','드리미','クワトロ'),
('Jitters','딩요','ジーニョ'),
('Joey','리처드','リチャード'),
('Judy','미애','みすず'),
('Julia','줄리아','ジュリア'),
('Julian','유니오','ジュリー'),
('June','메이','メイ'),
('Kabuki','가북희','かぶきち'),
('Katt','쵸이','ちょい'),
('Keaton','프랭크','フランク'),
('Ken','오골','クロベエ'),
('Ketchup','케첩','ケチャップ'),
('Kevin','멧지','イノッチ'),
('Kid Cat','1호','１ごう'),
('Kidd','염두리','やさお'),
('Kiki','캐비어','キャビア'),
('Kitt','애플리케','アップリケ'),
('Kitty','쇼콜라','ショコラ'),
('Klaus','곰도로스','クマロス'),
('Knox','금끼오','キンカク'),
('Kody','아이다호','アイダホ'),
('Kyle','리카르도','リカルド'),
('Leonardo','범호','ヒョウタ'),
('Leopold','티처','ティーチャー'),
('Lily','레이니','レイニー'),
('Limberg','단무지','らっきょ'),
('Lionel','라이오넬','ライオネル'),
('Lobo','늑태','ブンジロウ'),
('Lolly','사이다','ラムネ'),
('Lopez','톰슨','トムソン'),
('Louie','머슬','マッスル'),
('Lucha','마스카라스','マスカラス'),
('Lucky','럭키','ラッキー'),
('Lucy','루시','ルーシー'),
('Lyman','오즈먼드','オズモンド'),
('Mac','챔프','チャンプ'),
('Maddie','마롱','マロン'),
('Maelle','앤','アンヌ'),
('Maggie','마가렛','マーガレット'),
('Mallary','스미모','スミモモ'),
('Maple','메이첼','メープル'),
('Marcel','에드워드','もんじゃ'),
('Marcie','마리아','マリア'),
('Margie','샐리','サリー'),
('Marina','문리나','タコリーナ'),
('Marlo','보스터','ドンチャン'),
('Marshal','쭈니','ジュン'),
('Marty','마티','マーティー'),
('Mathilda','안젤라','アザラク'),
('Medli','메들리','メドリ'),
('Megan','캔디','キャンディ'),
('Melba','아델레이드','アデレード'),
('Merengue','스트로베리','パティ'),
('Merry','유네찌','さっち'),
('Midge','핑글이','うずまき'),
('Mineru','미넬','ミネル'),
('Mint','민트','ミント'),
('Mira','미랑','ミラコ'),
('Miranda','미란다','ミランダ'),
('Mitzi','마르','マール'),
('Moe','진상','ジンペイ'),
('Molly','귀오미','カモミ'),
('Monique','제인','ジェーン'),
('Monty','몽티','サルモンティ'),
('Moose','핑','ピン'),
('Mott','릭','リック'),
('Muffy','프릴','フリル'),
('Murphy','머피','のりお'),
('Nan','순이','スミ'),
('Nana','키키','チッチ'),
('Naomi','화자','ハナコ'),
('Nate','박하스','バッカス'),
('Nibbles','그리미','ガリガリ'),
('Norma','미자','いさこ'),
('O''Hare','산토스','サントス'),
('Octavian','문복','おくたろう'),
('Olaf','안토니오','アントニオ'),
('Olive','올리브','ピッコロ'),
('Olivia','올리비아','オリビア'),
('Opal','오팔','オパール'),
('Ozzie','동동이','ドングリ'),
('Pancetti','브리트니','ブリトニー'),
('Pango','패트라','パトラ'),
('Paolo','파올로','パオロ'),
('Papi','마사마','オカッピ'),
('Pashmina','바바라','バーバラ'),
('Pate','나키','ナッキー'),
('Patty','밀크','カルピ'),
('Paula','레이첼','レイチェル'),
('Peaches','말자','ドサコ'),
('Peanut','핑키','ももこ'),
('Pecan','레베카','レベッカ'),
('Peck','문대','ふみたろう'),
('Peewee','덤벨','ダンベル'),
('Peggy','체리','ちえり'),
('Pekoe','재스민','ジャスミン'),
('Penelope','찍순이','チューこ'),
('Petri','리카','リカ'),
('Phil','케인','ケイン'),
('Phoebe','휘니','ヒノコ'),
('Pierce','세바스찬','セバスチャン'),
('Pietro','피엘','ジュペッティ'),
('Pinky','링링','タンタン'),
('Piper','파이프','レイコ'),
('Pippy','로타','ロッタ'),
('Plucky','파타야','パタヤ'),
('Pompom','주디','のりっぺ'),
('Poncho','봉추','ポンチョ'),
('Poppy','다람','グミ'),
('Portia','블랜더','ブレンダ'),
('Prince','카일','カール'),
('Puck','하키','ホッケー'),
('Puddles','가위','チョキ'),
('Pudge','우띠','きんぞう'),
('Punchy','빙티','ビンタ'),
('Purrl','타마','たま'),
('Queenie','택주','タキュ'),
('Quillson','덕근','タックン'),
('Quinn','시온','シオン'),
('Raddle','개군','カックン'),
('Rasher','글레이','グレオ'),
('Raymond','잭슨','ジャック'),
('Renée','뿔님이','おさい'),
('Reneigh','리아나','リアーナ'),
('Rex','렉스','サンデー'),
('Rhonda','론다','ユメコ'),
('Ribbot','철컥','ガチャ'),
('Ricky','갈가리','カジロウ'),
('Rilla','릴라','リラ'),
('Rio','데자네','デジャネイロ'),
('Rizzo','조르쥐','ちょろきち'),
('Roald','펭수','ペンタ'),
('Robin','파틱','パーチク'),
('Rocco','곤잘레스','ゴンザレス'),
('Rocket','4호','４ごう'),
('Rod','쟝','ジャン'),
('Rodeo','로데오','ロデオ'),
('Rodney','지미','ジミー'),
('Rolf','호랭이','チョモラン'),
('Rooney','마이크','マイク'),
('Rory','아더','アーサー'),
('Roscoe','슈베르트','シュバルツ'),
('Rosie','부케','ブーケ'),
('Roswell','삐로코','ピロンコン'),
('Rowan','고메스','ゴメス'),
('Ruby','루나','ルナ'),
('Rudy','찰스','チャス'),
('Sally','라라미','ララミー'),
('Samson','피스','ピース'),
('Sandy','샌디','ラン'),
('Sasha','미첼','ミッチェル'),
('Savannah','사반나','サバンナ'),
('Scoot','지키미','マモル'),
('Shari','젤리','シェリー'),
('Sheldon','크리스','クリス'),
('Shep','밥','ボブ'),
('Sherb','래미','レム'),
('Shino','요비','よしの'),
('Simon','시몬','エテキチ'),
('Skye','릴리','リリィ'),
('Sly','하이드','ハイド'),
('Snake','닌토','モモチ'),
('Snooty','스누티','こまち'),
('Soleil','샨티','シャンティ'),
('Sparro','춘섭','ちゅんのすけ'),
('Spike','스쿼트','スクワット'),
('Spork','포크','ポーク'),
('Sprinkle','크리미','フラッペ'),
('Sprocket','헤르츠','ヘルツ'),
('Static','스파크','スパーク'),
('Stella','아크릴','アクリル'),
('Sterling','은수리','ギンカク'),
('Stinky','땀띠','アセクサ'),
('Stitches','패치','パッチ'),
('Stu','모리스','モーリス'),
('Sydney','시드니','シドニー'),
('Sylvana','실바나','もんぺ'),
('Sylvia','실비아','シルビア'),
('T-Bone','티본','ボルシチ'),
('Tabby','호냥이','トラこ'),
('Tad','텀보','タンボ'),
('Tammi','에이프릴','エイプリル'),
('Tammy','아네사','アネッサ'),
('Tangy','백프로','ヒャクパー'),
('Tank','탱크','くるぶし'),
('Tasha','나타샤','ナターシャ'),
('Teddy','병태','たいへいた'),
('Tex','볼트','ボルト'),
('Tia','티나','ティーナ'),
('Tiansheng','제천','テンシン'),
('Tiffany','바슬레','バズレー'),
('Timbra','잔디','つかさ'),
('Tipper','마틸다','まきば'),
('Toby','토비','トビー'),
('Tom','밴덤','バンタム'),
('Truffles','탱고','トンコ'),
('Tucker','맘모','はじめ'),
('Tulin','튤리','チューリ'),
('Tutu','연유','れんにゅう'),
('Twiggy','핀틱','ピーチク'),
('Tybalt','티볼트','ハリマオ'),
('Ursala','네이아','ネーヤ'),
('Velma','벨마','ピティエ'),
('Vesta','메리어스','メリヤス'),
('Vic','노르망','ノルマン'),
('Viché','미사키','みさき'),
('Victoria','센트엘로','セントアロー'),
('Violet','줌마','ウズメ'),
('Vivian','바네사','ヴァネッサ'),
('Vladimir','곰비','ガビ'),
('W. Link','울프 링크','ウルフリンク'),
('Wade','호떡','カマボコ'),
('Walker','벤','ベン'),
('Walt','관록','カンロク'),
('Wart Jr.','샘','サム'),
('Weber','아잠만','アチョット'),
('Wendy','눈송이','みぞれ'),
('Whitney','비앙카','ビアンカ'),
('Willow','마리','マリー'),
('Winnie','카로틴','マキバスター'),
('Wolfgang','로보','ロボ'),
('Yuka','유카리','ユーカリ'),
('Zell','넬슨','ネルソン')
;
-- 3. villager 테이블의 villager_name_en 컬럼을 기준으로 villager_name_translation 테이블과 조인하여
--    villager_name 및 villager_name_jp 컬럼을 업데이트
UPDATE villager v
JOIN villager_name_translation t
  ON t.villager_name_en = v.villager_name_en
SET v.villager_name    = t.villager_name_ko,
    v.villager_name_jp = t.villager_name_jp;

-- 4. 완료 후 누키피디아에 없는 한국 이름 수동 업데이트 ( 총 2명 진행 탁호, 소면 추가)
UPDATE villager
SET villager_name = '탁호',
    villager_name_jp = 'タコヤ'
WHERE villager_name_en = 'Zucker';

UPDATE villager
SET villager_name = '소면',
    villager_name_jp = 'ビーフン'
WHERE villager_name_en = 'Zoe';

-- 5. 전체 이미지가 없는 주민 insert문
UPDATE villager
SET villager_image = 'https://dodo.ac/np/images/e/e1/Marty_NH_Model.png'
WHERE villager_no = 242;

update villager
set villager_image = 'https://dodo.ac/np/images/d/d9/Étoile_NH_Model.png'
where villager_no = 138;

UPDATE villager
SET villager_image = 'https://dodo.ac/np/images/c/c1/Chelsea_NH_Model.png'
WHERE villager_no = 84;

UPDATE villager
SET villager_image = 'https://dodo.ac/np/images/8/84/Chai_NH_Model.png'
WHERE villager_no = 82;

UPDATE villager
SET villager_image = 'https://dodo.ac/np/images/2/25/Rilla_NH_Model.png'
WHERE villager_no = 322

UPDATE villager
SET villager_image = 'https://dodo.ac/np/images/5/59/Toby_NH_Model.png'
WHERE villager_no = 387;

select * from villager;