# SPRING PLUS

### 1. 코드 개선 퀴즈 - @Transactional의 이해 
- TodoService의 saveTodo 메서드 레벨에 따로 Transaction 설정

### 2. 코드 추가 퀴즈 - JWT의 이해
- SignupRequest → User 엔티티 → JWT 토큰(claim) → JwtFilter(파싱) → AuthUserArgumentResolver → AuthUser
- 리퀘스트, 엔티티에 필드로 받아오고, 토큰 claim에 추가, 필터에 파싱 해준 뒤 AuthUser에 저장

### 3. 코드 개선 퀴즈 - JPA의 이해
- 컨트롤러에 날씨, LocalDate 형식으로 수정일 기준 검색의 시작과 끝 파라미터로 받기
- 서비스에서 null체크 후 LocalDateTime으로 변환하며 null체크 후 조회
- 레포지토리에서 각각의 기준이 null일 때와 아닐 때 동적으로 조회할 수 있도록 JPQL 작성

### 4. 테스트 코드 퀴즈 - 컨트롤러 테스트의 이해
- 테스트 결과로서 400이 떨어지기에 기대값을 BAD_REQUEST에 맞춰 수정

### 5. 코드 개선 퀴즈 - AOP의 이해
- 실행 전 로깅이 동작해야하므로 어노테이션을 @Before로 수정

### 6. JPA Cascade
- Todo 엔티티의 managers 필드에 cascade 옵션 추가

### 7. N+1
- CommentRepository에서 join을 join fetch로 변경하여 한 번의 쿼리로 가져오도록 수정

### 8. QueryDSL
- 의존성 추가 후 QuerydslConfig 작성, 실행 시켜서 Q클래스 생성
- customRepo 생성 후 impl 클래스 생성해서 상속
- 조건에 맞게 조회 할 수 있도록 Querydsl 작성

### 9. Spring Security
- build.gradle 의존성 추가 
- UserDetailsImpl 작성 (Security 인증 객체)
- UserDetailsServiceImpl 작성 (DB에서 유저 조회)
- JwtFilter 수정
- SecurityConfig 작성 (경로별 권한 설정)
- AuthUserArgumentResolver 수정 (SecurityContextHolder에서 꺼내도록)
- 기존 FilterConfig 삭제