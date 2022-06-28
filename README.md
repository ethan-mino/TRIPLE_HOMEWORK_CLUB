# TRIPLE_HOMEWORK
Triple Open Recruitment Assignment

<p align = "center">
  <img src= "https://user-images.githubusercontent.com/53431518/176169705-66d52a9c-8e39-4347-badd-b21e848b7bbd.png"></img>
  클럽 마일리지 서비스 ERD
</p>

## 📃 Document
* 접속하실 수 없으시면, 초대해드리겠습니다!
* API 명세 [Postman](https://cloudy-comet-98520.postman.co/workspace/My-Workspace~e5f512fc-bd24-413a-89a3-aa73b3a0ae7d/documentation/17630551-b8fe4125-6f18-409f-a7cb-ac43f24b8781)

## 💻 API 
    User
        - GET Sign-Up (회원가입) 
        - GET Sign-In (로그인)
    Place
        - POST Create Place (여행 장소 생성)
        - GET All Places    (여행 장소 조회)
        - GET Specific Place (특정 여행 장소 조회)
        - PUT Update Place  (여행 장소 생성)
    Review
        - POST Create Review (리뷰 생성)
        - GET Reivew BY Place Id (장소 아이디로 리뷰 조회)
        - DELETE Delete Review  (리뷰 삭제)
        - PUT Update Review (리뷰 수정) 
        - GET My point  (유저 포인트 조회)

## 📣 Description
    - 실행 환경을 통일시키기 위해 Docker를 사용했습니다. 실행 전 Docker 실행 부탁드립니다.

    - 리뷰 이미지 파일은 AWS S3에 저장되도록 했습니다. 로컬에 업로드 되지 않습니다.

    - 리뷰에 대한 생성/수정/삭제 API도 구현하였고, 리뷰 조작과 포인트 적립은 하나의 트랜잭션이라고 생각하여, 
      /events로 전달하는 대신 Review API의 Service 단에서 리뷰 조작 시 포인트를 계산/부여하도록 했습니다.

    - 포인트 증감 이력 조회 API는 작성하지 않았습니다. 리뷰를 조작할 때 서버 내부에서 이력을 기록합니다.

    - 작성한 테스트 코드는 없습니다.

## 🚀 Execution
    1. Clone repository
    2. cd TRIPLE_HOMEWORK_CLUB/club
    3. ./mvnw clean package
    4. docker-compose up