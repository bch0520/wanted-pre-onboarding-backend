###지원자 : 백철현

####애플리케이션 실행방법

1.이메일과 패스워드 입력하여 회원가입(http://localhost:8080/members)

2.이메일과 패스워드 입력하여 로그인, 토큰 발급(http://localhost:8080/members/login)

3.토큰 적용 후 제목, 내용 입력하여 게시물생성, 전체 게시물 조회(http://localhost:8080/boards)

4.특정게시물 조회, 수정, 삭제(http://localhost:8080/boards/{boardId})


####[데이터베이스 테이블 구조](https://dbdiagram.io/d/64d88fbb02bd1c4a5eb0e8ab)


####[API 동작 데모 영상](https://youtu.be/5EnUNwcVgY8)


####구현방법 및 이유

요구사항만 충족시키도록 최소한으로 구현.
Member 부분은 회원가입과 로그인만 포스트 매핑하여 작성하였고, dto로 유효성 검사 적용.
Board 부분은 페이지당 게시물 10개씩 출력되도록 구현했고, 
게시물에 작성자 함께 나오도록 member와 다대일 매핑을 적용. 


#####[API 명세서](https://bch0520.tistory.com/10)
