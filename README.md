# 주주클럽  
경기도 유기동물, 분양서비스 커뮤니티

조장
 - 강보균 : 프로젝트 설계, 팀원 역할분담, 팀원 서포트, Readme 작성 
	 + Member : 회원가입, 로그인
	 + Board: 분양게시판, 메인페이지

조원 
 - 장용범 : Readme 작성
 	+ Member : 비밀번호 찾기
 	+ Board : 유기게시판, 메인페이지
 - 장희동
 	+ Member : 아이디 찾기
 	+ Board : 자유게시판
 - 최병호
 	+ Member : -
 	+ Board : 병원게시판, 메인페이지

공통 : DB 설계 / 피그마 / Readme / 주제선정

## 법령

제20조(동물의 소유권 취득) 시ㆍ도와 시ㆍ군ㆍ구가 동물의 소유권을 취득할 수 있는 경우는 다음 각 호와 같다.  
<개정 2013. 4. 5., 2017. 3. 21.>

1. 「유실물법」 제12조 및 「민법」 제253조에도 불구하고 제17조에 따라 공고한 날부터 10일이 지나도 동물의 소유자등을 알 수 없는 경우

2. 제14조제1항제3호에 해당하는 동물의 소유자가 그 동물의 소유권을 포기한 경우

3. 제14조제1항제3호에 해당하는 동물의 소유자가 제19조제2항에 따른 보호비용의 납부기한이 종료된 날부터 10일이 지나도 보호비용을 납부하지 아니한 경우

4. 동물의 소유자를 확인한 날부터 10일이 지나도 정당한 사유 없이 동물의 소유자와 연락이 되지 아니하거나 소유자가 반환받을 의사를 표시하지 아니한 경우

## 프로젝트 선정 이유

 - 1인 가구 증가로 반려동물 픽업 빈도의 증가

 - 책임감 없이 반려동물을 데려오고 못키우겠어서 유기되는 동물의 수도 증가

 - 그에따라 건강한 반려동물 사회 구축에 이바지 할 수 있는 정보가 많이 공유 됐으면 하는 바람에 선정

## 개발환경

운영체제 : Window10

Front-end : HTML, CSS, Bootstrap

Back-end : JDK11, Intelli J, SpringBoot

Database : MYSQL

Server : 내장서버

VersionControl : GitHub

## 개발일정

2022.01.21 ~ 2022.02.21
|날짜|내용|
|---|---|
|2022.01.21|계획 설계|
|2022.01.24|레이아웃 설계 및 수정|
|2022.01.25|레이아웃 설계 및 수정|
|2022.01.26|레이아웃 설계 및 수정|
|2022.01.27|로그인, 회원가입, 아이디/비밀번호 찾기 설계 및 구현|
|2022.02.07|로그인, 회원가입, 아이디/비밀번호 찾기 설계 및 구현 마무리,  게시판 등록, 리스트, 상세보기 설계 및 구현|
|2022.02.08|게시판 등록, 리스트, 상세보기 설계 및 구현|
|2022.02.10|게시판 수정, 삭제, 검색 설계 및 구현|
|2022.02.11|게시판 수정, 삭제, 검색 설계 및 구현|
|2022.02.15|세부기능 구현 및 오류 디버깅|
|2022.02.16|세부기능 구현 및 오류 디버깅|
|2022.02.17|세부기능 구현 및 오류 디버깅|
|2022.02.18|세부기능 구현 및 오류 디버깅|
|2022.02.21|마무리 및 발표준비|
|2022.02.21|발표|

## 주요기능 개발 우선순위
|우선순위|기능|
|---|---|
|1|Member : 로그인 / 회원가입 / 아이디찾기 / 비밀번호찾기<br>Board : 글 등록 / 글 상세보기 / 파일관리 / 관련 API 가져오기|
|2|Member : 내정보찾기 / 정보수정 / 회원탈퇴<br>Board : 글 수정 / 글 삭제 / 글 상세보기 / 관련 API 활용하기|
|3|Board : 페이징, 검색, 댓글|
|4|Board : 기타 세부기능 

---

## ZooZoo 협업 규칙

- Project : PascalCase
- Folder, File : PascalCase
- Class, Interface : PascalCase
메소드
- 이동(Mapping) : goTo—
- 컨트롤러(Mapping) : —Controller
- 주석 : Controller(Mapping) / Service(Method) 설명기재
- 코드공유 : GitHub, Kakaotalk
- 폰트 : 어그로체L

application.properties

```
server.port = 

spring.datasource..driver-class-name = 
com.mysql.cj.jdbc.Driver
spring.datasource.url = 
jdbc:mysql://localhost:3307/zoozoo?serverTimezone=Asia/Seoul
spring.datasource.username = root
spring.datasource.password = 1234

spring.jpa.hibernate.ddl-auto = update
spring.jpa.show_sql = true
#spring.jpa.properties.hibernate.format_sql = true
logging.level.org.hibernate.type.descriptor.sql = trace
spring.jpa.database.platform = 
org.hibernate.dialect.MySQL8Dialect

spring.devtools.restart.enabled = true
```

build.gradle

```
스프링부트 제공
implementation 
'org.springframework.boot:spring-boot-starter-web' 

스프링부트 테스트
testImplementation 
'org.springframework.boot:spring-boot-starter-test' 

롬북 라이브러리 제공
compileOnly 
'org.projectlombok:lombok'

롬북 연결 라이브러리 제공
annotationProcessor 
'org.projectlombok:lombok' 

타임리프
implementation 
'org.springframework.boot:spring-boot-starter-thymeleaf' 

JPA사용
implementation 
'org.springframework.boot:spring-boot-starter-data-jpa' 

DB연동
runtimeOnly 'mysql:mysql-connector-java' 

자동빌드 라이브러리
developmentOnly 
'org.springframework.boot:spring-boot-devtools' 

API
implementation group:
'com.googlecode.json-simple', name: 'json-simple', version: '1.1.1'

채팅
implementation 
'org.springframework.boot:spring-boot-starter-websocket' 

크롤링
implementation 
'org.jsoup:jsoup:1.14.2' 
```

## ERD
- 2022.01.21 db 일부 수정
- 수정 내용 : 컬럼 이름 변경(통일), 중복 삭제, FK 추가  
- 변경 전  
![springdb](https://user-images.githubusercontent.com/87436495/150328430-bf0783b7-b791-406b-b0e9-879cfca1ab90.png)

- 변경 후  
![db](https://user-images.githubusercontent.com/91529033/150495603-a300e48c-a9ca-4013-b259-905fd7a1f7e5.png)
