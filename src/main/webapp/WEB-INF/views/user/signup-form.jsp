<%--
  Created by IntelliJ IDEA.
  User: jhta
  Date: 2024-11-11
  Time: 오후 2:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SignUp page</title>
    <link rel="stylesheet" href="../../../resources/css/signup-form.css">
</head>
<body>
<div class="container">
    <h1>WORKUS</h1>

    <h2>회원가입</h2>

    <form action="" method="post">

        <div class="input__block">
            <input type="text" placeholder="사번을 입력해주세요" name="no" required />
            <button type="button" class="google__btn">사번확인</button>
        </div>

        <div class="input__block">
            <input type="text" placeholder="ID를 입력해주세요" name="id" required />
            <button type="button" class="google__btn">중복확인</button>
        </div>

        <div class="input__block">
            <input type="password" placeholder="비밀번호를 입력해주세요" name="password" required />
        </div>

        <div class="input__block">
            <input type="tel" placeholder="연락처를 입력해주세요" name="phone" required />
            <button type="button" class="google__btn">인증번호 전송</button>
        </div>

        <div class="input__block">
            <input type="text" placeholder="인증번호를 입력해주세요" name="verification_code" required />
            <button type="button" class="google__btn">인증번호 확인</button>
        </div>

        <div class="input__block">
            <input type="text" placeholder="우편번호를 입력해주세요" name="postal_code" required />
            <button type="button" class="google__btn">우편번호 찾기</button>
        </div>

        <div class="input__block">
            <input type="text" placeholder="주소" name="" required readonly/>
        </div>

        <div class="input__block">
            <input type="text" placeholder="상세주소" name="address" required />
        </div>

        <button class="signup__btn" type="submit">회원가입</button>

    </form>
</div>
</body>
</html>
