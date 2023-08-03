# DongEui-SugangApp 
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"><img src="https://img.shields.io/badge/PHP-777BB4?style=for-the-badge&logo=PHP&logoColor=white"><img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"><img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=Android&logoColor=white">

## <목차>
[1. 프로젝트 설명](#1-프로젝트-설명)<br/>
[2. 설계 및 구현](#2-설계-및-구현)<br/>
[3. 테스팅](#3-테스팅)<br/>
[4. 프로젝트 설치 및 실행 방법](#4-프로젝트-설치-및-실행-방법)<br/>
[5. 프로젝트 사용 방법](#5-프로젝트-사용-방법)<br/>
[6. 팀원](#6-팀원)<br/>

------------
  
## 1. 프로젝트 설명
- 프로젝트 기간: 2023.03.21 ~ 2023.06.13
- 프로젝트 목적: JAVA를 기반으로 스마트폰을 이용하여 언제 어디서든 수강신청을 사용할 수 있도록 하는 어플리케이션을 개발함으로써 프로젝트 관련 경험을 쌓는다.
- 구현 환경: Windows 10, MySQL
- 개발 언어: JAVA, PHP
- 개발 도구: 안드로이드 스튜디오
- 개발 규모 산정
  
  ![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/90b621dd-8657-4a67-b64c-8499a9b11cfa)
  
- 간트 차트
  
  ![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/a41b5b57-32af-40ab-b84e-a6931eb7442f)
  
------------
  
## 2. 설계 및 구현
- 기능 요구사항
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/8eef09e2-a7e7-4741-9b2f-071aac482148)
  
- 클래스 설계
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/16f4dffb-ee02-43d1-b5ea-8902b618e80d)
  
- 수강신청 등록 시퀀스 다이어그램
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/7c3b02df-a50c-4072-97a7-2f571b525db1)
  
- 수강신청 삭제 시퀀스 다이어그램
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/3f0c09d8-a490-43c9-93b9-27807d34ca22)
  
- 장바구니 등록 시퀀스 다이어그램
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/d9a15873-ab21-4b63-885d-8d5eb78ae648)
  
- 장바구니 삭제 시퀀스 다이어그램
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/7521e8fd-1ce8-45ac-96d6-6512c2defe23)
  
- ERD
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/b8872a8c-6bf6-44fd-b81e-dcadae40a677)
  
------------
  
## 3. 테스팅
### 3-1 화이트박스(장바구니 등록)
- 활동 다이어그램
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/895f2e06-cb49-4bfe-b704-7a867167cf0a)
  
- 분기 커버리지
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/d6533e09-220c-4191-b693-8a8d260e06fe)
  
### 3-2 블랙박스(수강신청 등록)
- 의사 결정 테이블
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/9f151fcd-26ba-4a9a-87f7-228c8b53b91e)
  
------------
  
## 4. 프로젝트 설치 및 실행 방법
### 4-1 DB 구성
- Admin
    
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/2f84b1d4-7a24-4769-9948-95b19e74256d)
  
- Lecture
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/59fa2ddb-1a33-4534-83d1-0c436e66dc2c)
  
- Registration
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/2a697b66-20f0-4880-9292-6029c69e1e09)
  
- Shopping_basket
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/b5ddb714-4e5e-4a0d-9359-66513943d1ad)

- Student
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/f9a0fcd6-1aba-4dd4-91b4-459a9b0e4841)
  
  
### 4-2 PHP DB 코드 추가
```
    $con = mysqli_connect("sever", "name", "pw", "DB");
    mysqli_query($con,'SET NAMES utf8');
```
- sever : 주소:포트번호
- name : DB 사용자명
- pw : DB 비밀번호
- DB : 스키마명
  
------------
  
## 5. 프로젝트 사용 방법
- 메인 화면
  
DB에 저장 되어있는 20210001(학번)과 해당 학생의 pw로 로그인을 한다. 로그인에 성공하면 수강신청 시스템의 메뉴를 선택할 수 있는 화면이 출력되게 된다.
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/34c2708d-d168-42b4-904f-042de13e3197)
  
- 메뉴 선택
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/5f1421c3-729c-4abf-98ec-78649baa45ea)
  
- 수강신청 등록
  
검색하고자 하는 강의의 이수 구분을 선택하고 강의번호를 입력한다. 입력한 강의번호와 동일한 강의가 있다면 출력해준다. 중복 신청, 인원 초과, 시간표 중복, 21학점 초과 총 4가지의 조건에 모두 만족한다면 정상적으로 신청을 하게 된다. 신청한 과목은 신청 완료 과목 메뉴를 클릭하여 조회할 수 있다.
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/5765b4b6-7bff-470e-9774-a9324ac8da8e)![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/a405e24a-8f2a-439b-862b-0e71e7958549)
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/88f06694-0755-403c-b6a9-bcf27fc22953)![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/5371ccad-cab6-4909-8367-c6aa4f186f0e)
  
- 수강신청 삭제
  
삭제하고자 하는 강의를 클릭하면 삭제하겠냐는 메시지를 띄운다. 삭제 버튼을 누르면 삭제되었습니다. 메시지를 출력하고 다시 신청 완료 과목 메뉴를 클릭하여 조회할 수 있다.
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/3ac537d6-4960-412b-8acd-5fc782ef1b7f)![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/f334e3e0-7cc4-4c2a-9fc3-8d3753a8f1dc)
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/345552eb-831c-4ca6-ae7f-69b843a84f70)
  
- 장바구니 담기
  
검색하고자 하는 강의의 이수 구분을 선택하고 강의번호를 입력한다. 입력한 강의번호와 동일한 강의가 있다면 출력해준다. 중복 신청이 아니라면 정상적으로 신청을 하게 된다. 신청한 과목은 담은 과목 메뉴를 클릭하여 조회할 수 있다.
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/65bacfed-0db8-4365-a486-42a74c44dfda)![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/9b72e41c-00b2-4df7-9676-70a4d3f1961d)
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/e1a7af9d-536b-4994-b523-98472ad29cd9)![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/a71df0a9-f215-41f4-9d42-0086846ffb95)
  
- 장바구니 삭제
  
삭제하고자 하는 강의를 클릭하면 삭제하겠냐는 메시지를 띄운다. 삭제 버튼을 누르면 삭제되었습니다. 메시지를 출력하고 다시 담은 과목 메뉴를 클릭하여 조회할 수 있다.
  
![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/8a242eb7-c447-465a-b110-dbe9ffd42b77)![image](https://github.com/Jung2312/DongEui-SugangApp/assets/97083703/3433b7dd-0364-46d4-94fa-e19e861bfb65)
  
------------
  
## 6. 팀원
- [@JeongminHW](https://github.com/JeongminHW)
- [@jeonmo](https://github.com/jeonmo)
- [@Jung2312](https://github.com/Jung2312)
- [@sshhsohui](https://github.com/sshhsohui)










