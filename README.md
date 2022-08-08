# Gaship

## 서비스 소개

MSA 기반의 가구 쇼핑몰 서비스

## 서비스 플로우
### shopping mall 아키텍처

![쇼핑몰 아키텍처](https://user-images.githubusercontent.com/60546275/183345555-b1da2978-d581-4344-b7c3-7234a0ff892b.jpg)

- - -
### CI/CD 아키텍처

![CI-CD](https://user-images.githubusercontent.com/60546275/183345684-31307b9e-dd92-44ca-953e-455f62966737.jpg)

## 주요 기능

### 상품

* 담당자 : 유호철 / 김보민
* 상품 등록, 수정, 삭제, 조회 기능
* 상품검색시 Elastic Search 사용
    * elastic Search 데이터 save 시 기존 DB와 Transcational 분리
    * ealstic Search 분석기를 활용해
        * 가구 (rkrn) , ㄱㄱ, 동의어 검색 완료
* 동적쿼리를 통해 여러조건에 대한 조회 처리 완료
* ELK 활용예정
    * Elasticsearch 와 Logstash ,Kibana 를 활용하여 데이터 처리예정

### 지역주소

* 담당자 : 유호철
* shop.gaship.gashipshoppingmall.addresslocal.\*
* 지역주소 조회, 수정 기능
* 배달가능 여부만 수정 가능
* 조회시 최상위 지역을 기준으로 하위지역 조회

### 지역별 물량

* 담당자 : 유호철
* shop.gaship.gashipshoppingmall.daylabor.\*
* 물량 생성, 수정, 조회
* 물량수정시 최대물량양만 수정가능

### 직원

* 담당자 : 유호철
* shop.gaship.gashipshoppingmall.employee.\*
* 직원 생성, 수정, 조회
* 직원 수정시 개인정보 내용수정
* 직원 정보 암호화 추가 예정

### 수리설치 스케줄

* 담당자 : 유호철
* shop.gaship.gashipshoppingmall.repairschedule.\*
* 스케줄 생성, 수정, 조회
* 스케줄 조회시 날짜를 통해 스케줄 조회

### 매출

* 담당자 : 유호철
* shop.gaship.gashipshoppingmall.totalsale.\*
* 매출 조회
* 매출 조회시 시작날짜, 종료날짜 를 통해 전체 매출내용 조회

### 카테고리

* 담당자: 김보민
* shop.gaship.gashipshoppingmall.category.\*
* 카테고리 생성
    * 부모, 하위 카테고리를 구분하여 생성
* 카테고리 조회/수정/삭제

### 상품평

* 담당자: 김보민
* shop.gaship.gashipshoppingmall.productreview.\*
* 상품평 생성/조회/수정/삭제

### 공통파일

* 담당자: 김보민
* shop.gaship.gashipshoppingmall.commonfile.\*
* shop.gaship.gashipshoppingmall.util.FileUploadUtil
* 서버 내 모든 파일을 공통파일로 관리 (상품, 상품평)
* 파일 업로드/삭제 유틸 작성
* 파일 다운로드
* front view에서 해당 rest api url을 통해 img src제공
* 추후 object storage 확장 고려

### 회원 등급

* 담당자 : 김세미
* shop.gaship.shoppingmall.membergrade.\*
* 새로운 등급 생성 / 기존 등급 수정 / 등급 삭제 / 등급 조회 기능을 제공합니다.

### 등급 이력

* 담당자 : 김세미
* shop.gaship.shoppingmall.gradehistory.\*
* 회원의 등급이 갱신될 때
    * 해당 회원의 등급 이력 등록 기능을 제공합니다.

### 상태 코드

* 담당자 : 김세미
* shop.gaship.shoppingmall.statuscode.\*
* 기능 전체적으로 필요한 상태에 대한 데이터를 관리합니다.

### 태그

* 담당자 : 최정우
* shop.gaship.shoppingmall.tag.\*
* jpa Repository을 이용해 태그 개발
* 상품과 회원에게 부여되는 태그를 관리자가 등록 수정 하는 서비스 개발

### 회원(수정, 삭제, 조회)

* 담당자 : 최정우
* shop.gaship.shoppingmall.member.\*
* jpa Repository를 이용한 회원을 관리 개발
* 관리자가 회원의 상태(휴면,활성)를 수정하고 회원도 회원의 기본정보를 수정할 수 있게하는 서비스를 개발

### 회원 태그

* 담당자 : 최정우
* shop.gashiop.shoppingmall.membertag.\*
* jpa Repository를 이용하여 회원이 태그를 선택해 자신에게 추천되는 상품을 고를 수 있는 서비스 개발

### 상품추천

* 담당자 : 최정우
* jpa Repository를 이용한 회원 관리 개발
* 회원태그와 상품태그를 통해 상품평 ,주문상세 db 이용해 회원마다 맞춤 상품을 추천해주는 서비스 개발

### 문의

* 담당자 : 최겸준
* shop.gaship.gashipfront.inquiry.\*
* QueryDsl로 동적쿼리를 이용하여 고객문의, 상품문의 조회기능개발
* JPA의 엔티티 생명주기를 이용하여 회원이 삭제되면 고객문의 및 상품문의 삭제
* JPA의 엔티티 생명주기를 이용하여 상품이 삭제되면 상품문의 삭제
* 고객문의
    * client에서 고객문의 등록, 삭제를 요청받고 처리
    * 고객문의 답변을 등록, 수정, 삭제
* 상품문의
    * client에서 상품문의 등록, 수정, 삭제를 요청받고 처리
    * 상품문의 답변을 등록, 수정, 삭제

### 주문
* 담당자 : 김민수
* shop.gaship.gashipshoppingmall.order.*
* shop.gaship.gashipshoppingmall.orderproduct.*
* spring event를 이용하여 주문의 등록
* 주문자의 기본 주문정보(실수령자 이름, 전화번호, 주소 등) 수정
* 주문의 취소, 환불, 반품, 교환 등의 서비스 제공

### 회원
* 담당자 : 김민수
* shop.gaship.gashipshoppingmall.member.* (아래 기능 관련 패키지)
* 자사 회원가입시 데이터베이스에 개인정보를 암호화하여 저장 (AES-256)
* 로그인시  SHA-512 암호화를 이용하여 데이터베이스에 질의
* AOP를 이용한 쇼핑몰 서버의 회원 권한 접근 검증.

### 인프라
* 담당자 : 김민수
* 깃헙 액션 - 프론트 서버 및 게이트웨이 CI/CD 구축
* 젠킨스 - API, Auth, Batch, Payment 서버 CI/CD 관리
* 프론트 서버의 Nginx 웹서버 설치 및 HTTPS 보안 적용

### 팀원 공통
코드 품질 및 컨벤션 체크, 정적 코드 분석기에서 알리는 약점 개선(체크스타일, 소나린트, 큐브)을 위한 작업.

## 기술

![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white) <img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&amp;logo=java&amp;logoColor=white"> <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&amp;logo=Spring&amp;logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&amp;logo=springboot&amp;logoColor=white"><br/>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&amp;logo=mysql&amp;logoColor=white"> <img src="https://img.shields.io/badge/hibernate-59666C?style=for-the-badge&amp;logo=hibernate&amp;logoColor=white"> <img src="https://img.shields.io/badge/Jpa-FF0000?style=for-the-badge&amp;logo=Jpa&amp;logoColor=white"> <img src="https://img.shields.io/badge/Querydsl-0769AD?style=for-the-badge&amp;logo=Querydsl&amp;logoColor=white"><br/>
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens) ![ElasticSearch](https://img.shields.io/badge/-ElasticSearch-005571?style=for-the-badge&logo=elasticsearch)<br/>
<img src="https://img.shields.io/badge/SonarLint-CB2029?style=for-the-badge&amp;logo=SonarLint&amp;logoColor=white"> <img src="https://img.shields.io/badge/SonarQube-4E9BCD?style=for-the-badge&amp;logo=SonarQube&amp;logoColor=white"><br/>
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white) ![Jenkins](https://img.shields.io/badge/jenkins-%232C5263.svg?style=for-the-badge&logo=jenkins&logoColor=white) ![Nginx](https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white) <img src="https://img.shields.io/badge/linux-FCC624?style=for-the-badge&amp;logo=linux&amp;logoColor=black"> ![Ubuntu](https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white) ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
<br/><img src="https://img.shields.io/badge/github-181717?style=for-the-badge&amp;logo=github&amp;logoColor=white"> ![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

## 팀원
