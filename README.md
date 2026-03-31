# SPRING PLUS

## 1. 코드 개선 퀴즈 - @Transactional의 이해 
- TodoService의 saveTodo 메서드 레벨에 따로 Transaction 설정

## 2. 코드 추가 퀴즈 - JWT의 이해
- SignupRequest → User 엔티티 → JWT 토큰(claim) → JwtFilter(파싱) → AuthUserArgumentResolver → AuthUser
- 리퀘스트, 엔티티에 필드로 받아오고, 토큰 claim에 추가, 필터에 파싱 해준 뒤 AuthUser에 저장

## 3. 코드 개선 퀴즈 - JPA의 이해
- 컨트롤러에 날씨, LocalDate 형식으로 수정일 기준 검색의 시작과 끝 파라미터로 받기
- 서비스에서 null체크 후 LocalDateTime으로 변환하며 null체크 후 조회
- 레포지토리에서 각각의 기준이 null일 때와 아닐 때 동적으로 조회할 수 있도록 JPQL 작성

## 4. 테스트 코드 퀴즈 - 컨트롤러 테스트의 이해