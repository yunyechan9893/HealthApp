# 사용할 도커 이미지 선택
FROM mysql:latest

# 환경 변수 설정 (root 비밀번호, 데이터베이스 생성 등)
ENV MYSQL_ROOT_PASSWORD=9893
ENV MYSQL_DATABASE=HealthApp
ENV MYSQL_USER=yyc
ENV MYSQL_PASSWORD=9893

# MySQL 초기화 파일 복사
COPY ./init.sql /docker-entrypoint-initdb.d/

# 컨테이너 내부에서 사용할 포트 설정 (기본 MySQL 포트: 3306)
EXPOSE 3306