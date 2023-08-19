-- HealthApp 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS HealthApp CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci; 

-- HealthApp 데이터베이스 사용
USE HealthApp;

-- .sql 파일 내의 문자 인코딩 설정
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

-- position 테이블 생성
CREATE TABLE position (
  no INT AUTO_INCREMENT PRIMARY KEY, -- 기본키, 자동 증가
  positionName CHAR(20) -- 관리자, 회원, PT회원, 트레이너 값이 들어있는 컬럼
);

INSERT INTO `position` (positionName) VALUES ('관리자');

-- members 테이블 생성
CREATE TABLE members (
  id CHAR(20) PRIMARY KEY, -- 회원의 아이디를 저장 (기본키)
  password CHAR(64), -- SHA256으로 해싱된 회원 비밀번호가 저장됨
  name NVARCHAR(12), -- 회원의 이름이 저장됨
  phone CHAR(11), -- "-" 빼고 순수 핸드폰 번호가 저장됨
  nickname NVARCHAR(15) UNIQUE, -- 회원의 닉네임을 저장함 (고유값)
  position INT, -- 외래키, position 테이블 no를 참조
  FOREIGN KEY (position) REFERENCES position (no) -- position 테이블의 no를 참조하는 외래키
);

INSERT INTO `members` (id, password, name, phone, nickname,position) 
VALUES ('test0000', '8323374b8fa8e87985a19d1f7c8fb4ffcd5ec7afc13a33b60762d3280fae4055', '윤예찬', '01023299893', '예찬찬', 1);

-- bb_category 테이블 생성
CREATE TABLE bb_category (
  id INT AUTO_INCREMENT PRIMARY KEY, -- 기본키, 자동 증가
  name NVARCHAR(30) -- 카테고리 이름을 저장
);

-- member_bulletin_board 테이블 생성
CREATE TABLE member_bulletin_board (
  no INT AUTO_INCREMENT PRIMARY KEY, -- 기본키, 자동 증가
  member_id CHAR(20), -- 외래키, members 테이블 id를 참조
  category INT, -- 외래키, bb_category 테이블 id를 참조
  title NVARCHAR(100), -- 게시글의 제목을 담당
  contents TEXT, -- 게시글의 콘텐츠 부분을 담당
  date CHAR(20), -- 글 쓴 날짜와 시간을 저장 (예시: 2023-08-08 15:08:00)
  FOREIGN KEY (member_id) REFERENCES members (id), -- members 테이블의 id를 참조하는 외래키
  FOREIGN KEY (category) REFERENCES bb_category (id) -- bb_category 테이블의 id를 참조하는 외래키
);

-- bulletin_recommend 테이블 생성
CREATE TABLE bulletin_recommend (
  no INT AUTO_INCREMENT PRIMARY KEY, -- 기본키, 자동 증가
  bulletin_id INT, -- 외래키, member_bulletin_board 테이블 no를 참조
  member_id CHAR(20), -- 외래키, members 테이블 id를 참조
  FOREIGN KEY (bulletin_id) REFERENCES member_bulletin_board (no), -- member_bulletin_board 테이블의 no를 참조하는 외래키
  FOREIGN KEY (member_id) REFERENCES members (id) -- members 테이블의 id를 참조하는 외래키
);

-- diet 테이블 생성
CREATE TABLE diet (
  no INT AUTO_INCREMENT PRIMARY KEY, -- 기본키, 자동 증가
  member_id CHAR(20), -- 외래키, members 테이블 id를 참조
  type_of_meal NCHAR(10), -- 아침, 점심, 저녁, 간식, 아점 등 어떤 걸 먹었는지 저장
  meal_time CHAR(8), -- 몇 시에 먹었는지 저장 (예시: 15:19:00)
  comment NVARCHAR(500), -- 식단에 대해 간단한 평가를 남길 수 있음
  date CHAR(10), -- 며칠에 먹었는지 저장 (예시: 2023.08.08)
  share INT, -- 트레이너와 공유를 하고 싶을 때 사용, 1은 공유, 0은 안함
  url VARCHAR(500), -- 식단 사진 URL 저장
  FOREIGN KEY (member_id) REFERENCES members (id) -- members 테이블의 id를 참조하는 외래키
);

INSERT INTO `diet` (member_id, type_of_meal, meal_time, comment, date, share) 
VALUES ('test0000', '아침', '15:19:00', '너모 맛있었다', '23.08.19', 1);

INSERT INTO `diet` (member_id, type_of_meal, meal_time, comment, date, share) 
VALUES ('test0000', '점심', '15:19:00', '너모 맛있었다', '23.08.19', 1);

-- ate_food 테이블 생성
CREATE TABLE ate_food (
  no INT AUTO_INCREMENT PRIMARY KEY, -- 기본키, 자동 증가
  diet_no INT, -- 외래키, diet 테이블 no를 참조
  name NVARCHAR(30),
  amount INT, -- 음식의 양을 저장
  kcal INT,
  carbohydrate INT,
  protein INT,
  fat INT,
  sugars INT,
  sodium INT,
  cholesterol INT,
  saturated_fat INT,
  trans_fat INT,
  FOREIGN KEY (diet_no) REFERENCES diet (no) -- diet 테이블의 no를 참조하는 외래키
); 

INSERT INTO `ate_food` (diet_no, name, amount, kcal, carbohydrate,protein, fat, sugars, sodium, cholesterol, saturated_fat, trans_fat) 
VALUES (1, '제육볶음', 200, 150, 10, 10, 10, 10, 1, 1, 1, 1);

INSERT INTO `ate_food` (diet_no, name, amount, kcal, carbohydrate,protein, fat, sugars, sodium, cholesterol, saturated_fat, trans_fat) 
VALUES (1, '가지볶음', 200, 150, 10, 10, 10, 10, 1, 1, 1, 1);

INSERT INTO `ate_food` (diet_no, name, amount, kcal, carbohydrate,protein, fat, sugars, sodium, cholesterol, saturated_fat, trans_fat) 
VALUES (2, '자장면', 200, 150, 10, 10, 10, 10, 1, 1, 1, 1);

INSERT INTO `ate_food` (diet_no, name, amount, kcal, carbohydrate,protein, fat, sugars, sodium, cholesterol, saturated_fat, trans_fat) 
VALUES (2, '단무지', 200, 150, 10, 10, 10, 10, 1, 1, 1, 1);


-- target_kcal 테이블 생성
CREATE TABLE target_kcal (
  no INT AUTO_INCREMENT PRIMARY KEY, -- 기본키, 자동 증가
  id CHAR(20), -- 외래키, members 테이블 id를 참조
  date CHAR(10), -- 날짜를 저장 (예시: 2023.08.08)
  kcal INT,
  FOREIGN KEY (id) REFERENCES members (id) -- members 테이블의 id를 참조하는 외래키
);