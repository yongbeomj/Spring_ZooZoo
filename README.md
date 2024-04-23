# ZooZoo Club  
경기도 유기동물, 분양동물 커뮤니티 사이트

### 시연영상 ([바로가기](https://youtu.be/Poy6dSYqSqc))

## 1. 개요 
### 1.1 프로젝트 목적

 - 건강한 반려동물 사회 구축에 이바지 할 수 있는 정보 공유
 	- 1인 가구 증가로 인한 반려동물 픽업 빈도 증가 추세
 	- 책임감없이 반려동물을 데려오고 못키우겠어서 유기되는 동물의 수 증가 추세
 - 수업내용 개념 재확립 및 계층형 게시판 구조 설계와 구조에 맞는 게시글/댓글 CRUD 기능 구현

### 1.2 Target
- 유기동물 및 반려동물 입양을 원하는 고객
- 그 외 동물 관련 정보를 원하는 고객(동물병원정보, 입양후기 등)

### 1.3 개발환경
- 운영체제 : Windows10
- Front-end : HTML, CSS, JavaScript, Bootstrap
- Back-end : JDK 11, Spring Framework
- Database : MySQL
- Server : Tomcat
- Version Control : Git

## 2. 개발일정

- 기간 : 2022.01.21 ~ 2022.02.21 (총 32일)
- History  

|날짜|내용|
|---|---|
|2022.01.21|계획 설계|
|2022.01.24<br>~ 2022.01.26|레이아웃 설계 및 수정|
|2022.01.27|로그인, 회원가입, 아이디/비밀번호 찾기 설계 및 구현|
|2022.02.07|로그인, 회원가입, 아이디/비밀번호 찾기 설계 및 구현 마무리,  게시판 등록,<br>리스트, 상세보기 설계 및 구현|
|2022.02.08|게시판 등록, 리스트, 상세보기 설계 및 구현|
|2022.02.10<br>~ 2022.02.11|게시판 수정, 삭제, 검색 설계 및 구현|
|2022.02.15<br>~ 2022.02.18|세부기능 구현 및 디버깅|
|2022.02.21|마무리 및 발표준비|
|2022.02.22|프로젝트 발표|

## 3. 주요기능 개발 우선순위
|우선순위|기능|
|---|---|
|1|Member : 로그인 / 회원가입 / 아이디찾기 / 비밀번호찾기<br>Board : 게시글 (등록, 전체보기, 상세보기) / Open API 호출 및 출력|
|2|Member : 마이페이지 / 정보수정 / 회원탈퇴<br>Board : 게시글 (수정, 삭제) / Open API 좌표 Kakaomap 마커 표기|
|3|Board : 페이징, 검색 (키워드, 다중옵션), 댓글 (등록, 수정, 삭제)|
|4|Main : 통계, 관련기사 웹 크롤링<br>Member : 내가 쓴 글, 댓글 보기<br>Board : 별점 평가 / 추천 (좋아요)|

## 4. 개발 인원 및 역할 분담
 - 강보균(조장) : 프로젝트 설계, 팀원 역할분담, 팀원 서포트, Readme 작성 
	 + Member : 회원가입, 로그인
	 + Board: 분양동물 게시판
	 + others : 메인페이지
 - 장용범 : Readme 작성
 	+ Member : 비밀번호 찾기, 마이페이지
 	+ Board : 유기동물 게시판
 	+ others : 메인페이지
 - 장희동
 	+ Member : 아이디 찾기
 	+ Board : 자유 게시판
 - 최병호
 	+ Board : 동물병원 게시판
 	+ others : 메인페이지
 - 공통 : 주제선정 / UI 설계 (Figma 정리) / DB 설계

## 5. 프로젝트 협업 규칙
### 5.1 명명규칙
- Project : PascalCase(UpperCamelCase) 적용
- Folder, File : PascalCase(UpperCamelCase) 적용
- Class, Interface : PascalCase(UpperCamelCase) 적용
- Method
	- 이동(Mapping) : goTo—
	- 컨트롤러(Mapping) : —Controller
- 주석 : Controller(Mapping) / Service(Method) 설명기재

### 5.2 코드공유방식 : GitHub, Kakaotalk
### 5.3 Font : 어그로체L

## 6. ERD
- Table : Total 6EA
<details>
<summary>여기를 눌러주세요</summary>
<div markdown="1">       

![image](https://user-images.githubusercontent.com/87436495/155252830-5f25632f-2045-4153-92e4-c120b55fea9b.png)
  
</div>
</details>

## 7. 주요 코드

### 7.1 Open API 호출

<details>
<summary>XML (여기를 눌러주세요)</summary>
<div markdown="1">       

![untitled (2)](https://user-images.githubusercontent.com/87436495/158093663-979682da-889c-4245-9ca9-418db6da08a8.png)
	
</div>
</details>

<details>
<summary>JSON (여기를 눌러주세요)</summary>
<div markdown="1">       

![image](https://user-images.githubusercontent.com/87436495/157838586-aaefadb1-31bd-4b58-a41c-5cb7ab708f93.png)
  
</div>
</details>

### 7.2 다중옵션 검색 (유기동물 게시판)
<details>
<summary>여기를 눌러주세요</summary>
<div markdown="1">       

![untitled (1)](https://user-images.githubusercontent.com/87436495/158093849-ef7b6fc0-f301-4ed9-bec3-cf5da79bd276.png)
	
</div>
</details>

## 8. 보완할 점
- 모든 게시판에 무한 계층형 댓글 기능 추가 (현재 일부 페이지만 1depth까지 대댓글 구현)
