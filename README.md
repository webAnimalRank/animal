# Animal Backend

동물의 숲 주민 정보를 기반으로 검색, 투표, 회원 관리, 게시판 기능을 제공하는 Spring Boot 백엔드 서버입니다.

## 기술 스택

- Java 17
- Spring Boot 3
- MyBatis
- MySQL
- TiDB
- Gradle
- Lombok
- Render

## 주요 기능

- 주민 목록 조회
- 주민 상세 조회
- 조건별 주민 검색
- 주민 인기 투표 및 내 투표 조회
- 회원가입 / 로그인 / 로그아웃 / 마이페이지
- 게시판 조회 / 작성 / 수정 / 삭제
- 관리자용 Nookipedia 데이터 동기화 API

## 사용 기술 및 서비스

- 외부 주민 데이터 수집을 위해 `Nookipedia API`를 사용했습니다.
- 개발 및 빌드 환경 데이터베이스는 `MySQL`을 사용했습니다.
- 배포 환경 데이터베이스는 클라우드 DB인 `TiDB`를 사용했습니다.
- 백엔드 서버 배포는 `Render`를 사용했습니다.

## 주요 API

- `/api/villagers`
- `/api/members`
- `/api/boards`
- `/api/admin/nookipedia`

## 실행 환경

- Java 버전: `17`
- 기본 포트: `8080`
- 필수 환경 변수: `API_KEY`
- 기본 설정 파일: `src/main/resources/application.properties`
- 운영 설정 파일: `src/main/resources/application-prod.properties`
- `bootRun` 실행 시 `local` 프로필로 동작합니다.
- Gradle Wrapper를 사용하므로 별도 Gradle 설치 없이 실행할 수 있습니다.

## 실행 방법

### Windows

```bash
gradlew.bat bootRun
```

### Mac / Linux

```bash
./gradlew bootRun
```

## 빌드

### Windows

```bash
gradlew.bat build
```

### Mac / Linux

```bash
./gradlew build
```

## 테스트

### Windows

```bash
gradlew.bat test
```

### Mac / Linux

```bash
./gradlew test
```

## 참고 사항

- 세션 기반 로그인 방식을 사용합니다.
- MyBatis 매퍼 XML은 `src/main/resources/mapper` 경로에서 관리합니다.
- 주민 데이터 동기화 기능은 `Nookipedia API`와 `API_KEY` 환경 변수가 필요합니다.
- 개발 환경과 배포 환경의 데이터베이스가 다르므로 환경별 설정 분리가 필요합니다.
- Docker 배포를 위한 `Dockerfile`이 포함되어 있습니다.


