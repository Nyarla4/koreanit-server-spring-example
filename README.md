# Spring Boot API Server

## 1. 프로젝트 개요

이 프로젝트는 Spring Boot 기반의 RESTful API 서버입니다. 사용자 인증(로그인, 회원가입), 게시물(Post) 관리(CRUD), 댓글(Comment) 관리(CRUD) 기능을 제공하며, 프론트엔드 애플리케이션의 백엔드 역할을 수행합니다. 세션 기반 인증 방식을 사용하며, Tailwind CSS를 사용하는 프론트엔드와 연동하도록 설계되었습니다.

## 2. 기술 스택

-   **언어**: Java 17
-   **프레임워크**: Spring Boot 3.x
-   **빌드 도구**: Gradle
-   **데이터베이스**: H2 Database (개발용 인메모리), MySQL (운영용)
-   **인증**: Spring Security (세션 기반)
-   **기타 라이브러리**: Lombok, Jakarta Validation

## 3. 프로젝트 구조

주요 디렉토리 및 파일 구조는 다음과 같습니다.

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── koreanit
│   │   │           └── spring
│   │   │               ├── common        // 공통 유틸리티, 에러 처리, 응답 포맷
│   │   │               ├── comment       // 댓글 관련 도메인, DTO, Repository, Service, Controller
│   │   │               ├── post          // 게시물 관련 도메인, DTO, Repository, Service, Controller
│   │   │               ├── security      // Spring Security 설정, 인증 필터, 유틸리티
│   │   │               └── user          // 사용자 관련 도메인, DTO, Repository, Service, Controller
│   │   └── resources
│   │       ├── application.properties  // Spring Boot 설정 파일
│   │       └── schema.sql              // (선택) 데이터베이스 스키마 정의 (현재는 없음)
│   └── test
│       └── java
│           └── com
│               └── koreanit
│                   └── spring
│                       └── ...           // 테스트 코드
├── build.gradle                          // Gradle 빌드 설정 파일
└── gradlew                               // Gradle Wrapper 스크립트
```

## 4. 주요 기능

### 인증 및 사용자 관리

-   **회원가입**: 새로운 사용자 계정 생성
    -   `POST /api/users`
-   **로그인**: 사용자 인증 및 세션 발급
    -   `POST /api/login`
-   **로그아웃**: 현재 세션 무효화
    -   `POST /api/logout`
-   **내 정보 조회**: 현재 로그인된 사용자 정보 조회
    -   `GET /api/me`
-   **사용자 정보 수정**: 닉네임, 비밀번호, 이메일 변경
    -   `PUT /api/users/{id}/nickname`
    -   `PUT /api/users/{id}/password`
    -   `PUT /api/users/{id}/email`

### 게시물 관리

-   **게시물 생성**: 새로운 게시물 작성 (로그인 필요)
    -   `POST /api/posts`
-   **게시물 목록 조회**: 모든 게시물 목록 조회 (페이지네이션 지원)
    -   `GET /api/posts?page={page}&limit={limit}`
-   **게시물 상세 조회**: 특정 게시물 상세 내용 조회
    -   `GET /api/posts/{id}`
-   **게시물 수정**: 특정 게시물 수정 (로그인 및 작성자 본인 확인 필요)
    -   `PUT /api/posts/{id}`
-   **게시물 삭제**: 특정 게시물 삭제 (로그인 및 작성자 본인 확인 필요)
    -   `DELETE /api/posts/{id}`

### 댓글 관리

-   **댓글 생성**: 특정 게시물에 댓글 작성 (로그인 필요)
    -   `POST /api/posts/{postId}/comments`
-   **댓글 목록 조회**: 특정 게시물의 댓글 목록 조회
    -   `GET /api/posts/{postId}/comments?limit={limit}`
-   **댓글 삭제**: 특정 댓글 삭제 (로그인 및 작성자 본인 확인 필요)
    -   `DELETE /api/comments/{id}`

## 5. 실행 방법

### 전제 조건

-   Java 17 이상 설치
-   Gradle (내장된 `gradlew` 스크립트 사용 권장)

### 프로젝트 클론

```bash
git clone <repository_url>
cd koreanit-server/spring
```

### 백엔드 서버 실행

프로젝트 루트 디렉토리에서 다음 명령어를 실행하여 Spring Boot 애플리케이션을 시작합니다.

```bash
./gradlew bootRun
```

서버는 기본적으로 `http://localhost:8080` 포트에서 실행됩니다.

### 프론트엔드 연동

이 백엔드 서버는 별도의 React 프론트엔드 프로젝트와 연동됩니다. 프론트엔드 개발 시 Vite 개발 서버의 프록시 설정을 통해 이 백엔드 서버로 API 요청이 전달되도록 구성해야 합니다. (예: `/api/**` 요청을 `http://localhost:8080`으로 프록시)

---