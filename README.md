# 개발일지
## 2023.06.29 (2시간)
- 안드로이드에 내장된 백엔드 분리 시작
- DB 조작은 SQLAlchemy 사용
    - ORM 방식으로 보안적으로 우수하다
- config 파일 분리 방법 공부
    - 참조 : https://mingrammer.com/ways-to-manage-the-configuration-in-python/
    - 빌트인 데이터 구조 채택
- SQLAlchemy engine Database Url 연동까지 완료 및 config 파일 분리

## 2023.06.30 (5시간)
### 🌞오전
- SQLAlchemy를 이용해 앱에서 SQL문 분리
    - DB연결 및 login ORM 작성
    - 매핑 ( Members, Position ) 
- bluePrint를 이용하여 Rest Api 만들기
- ORM 방식을 처음 사용하다보니 시간이 꽤 걸렸다
### 🌙오후
- connection pool 공부 및 구현
- CRUD 중 CR 일부 작성
- 서비스 시 과부하로 서버가 터지는 것을 방지하기 위해 Recycle 30초로 축소
- pool = 20, maxsize = 10
- connection 개수는 pool_size + maxsize로 connection을 닫지 않고 재사용한다로 이해

## 2023.07.01 (8시간)
### 🌞오전
#### 공부
- GET 방식
    - Read, Retrieve할 때 사용되는 method
    - 주소 끝 파라미터에 포함 이것을 쿼리스트링이라 부름
    - 데이터 변형에 위험성이 없어 안전
    - 파라미터 내용은 노출, 민감한 데이터 사용 시 권장하지 않음
- POST 방식
    - 리소스를 생성/ 업데이트 하기위해 사용
    - body를 사용하기 때문에 대용량 전송 가능
    - 개발자 도구, Fiddler 같은 툴로 body 내용 확인이 가능함으로 암호화해서 전송 권장
- 로그인 및 회원가입 시 비밀번호는 솔트 해시와 해시 함수를 사용하여 보안
- 서비스 시 https로 바꾸고 SSL/TLS 프로토콜을 사용해 보안 강화
    - https://growingsaja.tistory.com/696 참고
#### 안드로이드
- Retrofit2를 사용하여 flask와 통신
    - Restful Api을 활용하여 구현 중
    - 현재 로그인 구현
- 로그인 파라미터값을 감추기 위해 post방식으로 구현

#### 백엔드
- 회원가입 구현
- Json 데이터 통신 방식으로 변경

### 🌙오후
#### 백엔드
- Jwt를 통해 Access 토큰발급
    - 사용자의 개인정보를 암호화하여 개인 저장소에 저장
    - 회원가입 시 사용자 고유키를 만들어서 저장하는 것도 보안에 좋을거같음
    - 클라이언트가 서버에 요청 시 토큰과 함께 넘겨줌 -> 복호화하여 DB와 일치하는지 비교
 
## 2023.07.02 (5시간)
### 🌞오전
#### 공부
- access 토큰
    - 신원확인 및 유효성 체크를 통해 서버에 접근 가능
    - 유효 기간이 길면 보안에 취약
    - 액세스 토큰은 짧은 유효기간을 주고, 리프레시 토큰으로 만료시 업데이트
- refresh 토큰
    - 액세스 토큰 재발급 용도
    - 유효 기간을 길게 하여 액세스 토큰 만료로 로그인 불편함 개선
- 대칭키
    - 암호화한 키로만 복호화 가능
- 비대칭키
    - 암호화한 키 뿐만 아니라 다른 키로도 복호화 가능
- SHA256
    - 단방향 해시
    - 복호화할 수 없다
- HS256 = HMAC + SHA256

#### 백엔드
- Access 토큰으로 신원 파악
    - 제일 앞단에 두어 올바른 값이 아니면 접근을 통제함

### 🌙오후
#### 공부
- Redis
    - 이미지   : https://itguny04.tistory.com/68
    - 함수정리 : https://nowonbun.tistory.com/365

- 레디스 
#### 백엔드
- 레디스 이미지화
- 레디스 해시값 저장 및 삭제 구현


# 결과
## 결과 동영상
https://www.youtube.com/watch?v=tRJWSizXI18

## 결과 사진
<img src="https://github.com/yunyechan9893/HealthApp/assets/125535111/fcdc3614-27b7-4fa5-b5f7-995cab0f4f26.png"  width="300" height="600"/>
<img src="https://github.com/yunyechan9893/HealthApp/assets/125535111/493bbf75-9a6f-4322-ac0c-6520a4e491a7.png"  width="300" height="600"/>
<img src="https://github.com/yunyechan9893/HealthApp/assets/125535111/5e0cfe6f-0670-42bc-a6c3-9e92459e04b1.png"  width="300" height="600"/>
<img src="https://github.com/yunyechan9893/HealthApp/assets/125535111/627336b7-78fa-43a5-871d-2b8396d063ff.png"  width="300" height="600"/>
<img src="https://github.com/yunyechan9893/HealthApp/assets/125535111/1252e063-f038-4a57-9ca7-4caf49161062.png"  width="300" height="600"/>


## ERD
![데이터베이스](https://github.com/yunyechan9893/HealthApp/assets/125535111/b8a7f64b-313f-4e88-8b17-e5bc9ae16bf4)

# 프로젝트 설명
## 프로젝트 배경
- 헬스장에 등록하기 위해서는 꼭 방문해서 인포메이션 직원과 상담 후 등록해야 했습니다. 굉장히 번잡하다는 생각이 들었습니다. 그래서 '이 과정을 생략할 방법이 없을까?'라는 생각으로 프로젝트를 시작하게 되었습니다. 이후 프로젝트를 구상하는 과정에서 '일정 조율과 헬스장 이벤트 등 다양한 정보를 회원에게 제공하면 더 좋은 프로젝트가 될 것이다'라는 아이디어가 떠올라, 지금의 프로젝트를 완성하게 되었습니다.

## 프로젝트 목표
- 각종 이벤트를 제공받을 수 있다.
- KG이니시스를 통해 결제를 할 수 있다.
- 회원바코드를 발급받을 수 있다.
- 인바디를 OCR라이브러리를 통해 등록할 수 있다.
- 식단 등록을 통해 오늘 먹은 영양상태를 알 수 있고, 트레이너와 공유하여 피드백을 받을 수 있다.
- 오늘 한 운동을 기록할 수 있고, 트레이너와 공유하여 피드백을 받을 수 있다.
- 트레이너와 운동 일정을 조율할 수 있다.
- 게시판을 통해 회원간 교류가 가능하다.
- 각종 헬스장 정보를 얻을 수 있다.
- 트레이너 소개와 리뷰를 통해 회원에게 맞는 트레이너를 고를 수 있다.

## 프로젝트의 개요, 범위, 내용, 방법
### 회원
- 헬스장 정보를 쉽게 얻을 수 있다
    - 헬스장 운영 시간, 위치, 내부 시설, 운동 기구, 정보, 회원권 가격, 이벤트 등등의 정보를 볼 수 있음
- 헬스장 등록을 간편하게 할 수 있다.
    - 이니시스 결제 시스템을 적용하여 모바일로 결제가 가능하고, 이벤트를 통해 할인된 가격으로 등록할 수 있다.
- 트레이너와 편리하게 상호작용할 수있다.
    - 트레이너와 일정신청을 통해 간편하게 일정조율을 할 수 있으며, 식단공유, 운동공유를 통해 각종 피드백을 받을 수 있음.
- 게시판을 통해 회원간 교류가 가능하다.
    - 회원들간 정보 공유를 통해 궁금한 점을 손쉽게 해결할 수 있다.

### 직원
- 담당 회원 관리
     - 트레이너가 맡은 회원의 일정, 식단, 오늘 한 운동 공유를 통해 수업에 참고 가능


### 프로젝트 개요
- 회원은 이름, 주민번호, 전화번호, 아이디, 비밀번호, 닉네임, 헬스장 코드(없으면 안해도됨)를 입력하여 가입한다.
- 아이디와 비밀번호를 입력하여 로그인한다, 자동로그인 기능이 있어 체크 시 다음 실행 시 아이디 비밀번호 입력을 안해도 된다. 
- 회원은 이벤트 및 쿠폰등을 받아 할인된 금액으로 결제를 할 수 있으며, 결제 후 회원바코드를 제공한다.
- 회원 바코드는 회원 아이디와 등록날짜, 마감날짜, 고유번호를 조합하여 바코드로 만든다.
- 회원은 소개에서 필요한 정보를 얻을 수 있다.
- 회원은 자신의 몸상태, 식단, 운동기록을 기록할 수 있으며, PT회원의 경우 트레이너와 공유할 수 있으며 트레이너 일정 및 PT신청을 할 수 있다.
- 몸상태는 인바디 검사지를 사진 촬영하여 OCR 라이브러리를 이용해 글자를 추출한다.(공사중으로 띄울 예정)
- 식단은  오픈 API를 이용하여 칼로리 정보를 얻어와 고객이 선택할 수 있게 제공할 예정이다. 선택을 했다면, 탄수화물, 단백질 , 지방, 염분, 비타민 기타 등등 정보를 가져와 하루 섭취한 칼로리를 환산해 보여줄 예정이다. 그리고 그 정보는 트레이너에게 제공할 것이다.
- 운동기록은 방식을 할 수 있다. 타바타를 선택한다면, 운동종목 운동시간 쉬는시간 세트 무게를 작성할 수 있게 하고, 세트운동을 선택한다면 운동종목, 무게, 개수, 세트, 쉬는시간을 작성할 수 있게 한다. 다 입력했다면 트레이너와 공유할 수 있다.
- 일정은 회원아이디, 직원아이디, 시작시간, 종료시간, 내용을 추가하여, 트레이너에게 신청할 수 있으며, 승인 시 일정에 등록된다.
- 회원은 게시판에 카테고리선택, 제목, 컨텐츠 등을 입력하여 게시물을 등록할 수 있으며, 헬스장 리뷰를 할 수 있다.PT를 받는 회원의 경우 트레이너 리뷰를 할 수 있다. 트레이너는 회원의 닉네임을 제공받을 수 없어 리뷰를 등록한 사람이 누군지 알 수 없는 익명성을 보장한다.
- 회원은 마이페이지에서 자신의 이력을 볼 수 있다. 공지사항, 이벤트, 결제내역, 쿠폰북, 자주묻는질문, 회원탈퇴, 문의 등이 있다.
- PT를 가입한 회원은 실시간으로 트레이너와 채팅을 주고 받을 수 있다.   
- 채팅은  내용을 보낼떄에는  회원아이디, 트레이너아이디, 입력시간과  ,내용으로 구성된다.  사진을 보낼 때에는 내용을 비우고 회원아이디, 트레이너아이디, 입력시간으로 구성된다.
- 직원은 게시판을 관리할 수 있다. PT회원이 공유한 식단, 운동기록을 볼 수 있고, 식단 공지사항과 회원이 신청한 PT일정을 관리할 수 있다.


