<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WorkUs Login Page</title>
    <link rel="stylesheet" href="../../../resources/css/login-form.css">
</head>
<body>
<div class="container">
    <!-- Heading -->
    <h1>WORKUS</h1>

    <!-- Links -->
    <ul class="links">
        <li>
            <a href="/login" id="signin">로그인</a>
        </li>
        <li>
            <a href="/signup" id="signup">회원가입</a>
        </li>
        <li>
            <a href="/findpw" id="reset">비밀번호 찾기</a>
        </li>
    </ul>

    <!-- Form -->
    <form action="/login" method="post" novalidate="novalidate" id="login">
        <!-- email input -->
        <div class="first-input input__block first-input__block">
            <input type="text" placeholder="ID를 입력해주세요" class="input" name="id" id="id" />
        </div>
        <!-- password input -->
        <div class="input__block">
            <input type="password" placeholder="비밀번호를 입력해주세요" class="input" name="password" id="password"   />
        </div>
        <!-- sign in button -->
        <button class="signin__btn" type="submit">
            로그인
        </button>
    </form>
    <!-- separator -->
    <div class="separator">
        <p>OR</p>
    </div>
    <!-- google button -->
    <button class="google__btn">
        <i class="fa fa-google"></i>
        Sign in with Google
    </button>
    <!-- google button -->
</div>
</body>
<footer>
</footer>
</html>
