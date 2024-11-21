<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

    <form:form id="form-signup" action="/signup" method="post" modelAttribute="signupForm" >

        <div class="input__block">
            <span class="check-icon no-icon" style="display:none;">✔️</span>
            <form:input placeholder="사번을 입력해주세요" id="user-no" path="no" />
            <button type="button" class="google__btn user-check">사번확인</button>
        </div>

        <div class="input__block">
            <span class="check-icon id-icon" style="display:none;">✔️</span>
            <form:input placeholder="ID를 입력해주세요" id="user-id" path="id" />
            <button type="button" class="google__btn id-check">중복확인</button>
        </div>

        <div class="input__block">
            <form:password placeholder="비밀번호를 입력해주세요" id="user-pw" path="password" />
        </div>

        <div class="input__block">
            <span class="check-icon pw-icon" style="display:none;">✔️</span>
            <form:password placeholder="비밀번호를 다시 입력해주세요" id="user-confirmpw" path="passwordConfirm" />
            <button type="button" class="google__btn password-check">비밀번호 확인</button>
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
            <input type="text" placeholder="주소" name="address" readonly/>
        </div>

        <div class="input__block">
            <input type="text" placeholder="상세주소" name="detailAddress" required />
        </div>

        <button class="signup__btn" type="submit">회원가입</button>

    </form:form>
</div>
<script>
    // 사번 확인
    document.querySelector('.user-check').addEventListener('click', function() {
        const userNo = document.querySelector('input[name="no"]').value;
        const checkIcon = document.querySelector('.no-icon');

        if (isNaN(userNo) || userNo.trim() === "") {
            alert("사번은 숫자만 입력 가능합니다.");
            return;
        }

        fetch(`/ajax/user/check-no/` + userNo) // fetch 요청
            .then(response => response.json()) // JSON 파싱
            .then(data => {
                if (data.data) {
                    alert("사번이 확인되었습니다.");
                    checkIcon.style.display = 'inline';
                } else {
                    alert("존재하지 않는 사번입니다.");
                }
            });
    })

    document.querySelector('#user-no').addEventListener('input', function() { // 입력값 변경 시에
        document.querySelector('.no-icon').style.display = 'none'; // 숨김
    });


    // ID 확인
    document.querySelector('.id-check').addEventListener('click', function() {
        const userId = document.querySelector('input[name="id"]').value;
        const checkIcon = document.querySelector('.id-icon');
        const idPattern = /^[a-zA-Z][a-zA-Z0-9]{0,17}$/; // 정규식 사용

        if(!idPattern.test(userId)){
            alert("아이디는 영문자로 시작하고, 영문자나 숫자만 포함가능하며, 최대 18자입니다.");
            return;
        }

        fetch(`ajax/user/check-id/` + userId)
            .then(response => response.json())
            .then(data => {
                console.log(userId)
                if (data.data) {
                    alert("이미 존재하는 아이디입니다.");
                } else {
                    alert("사용 가능한 아이디입니다.");
                    checkIcon.style.display = 'inline';
                }
            })
    })

    document.querySelector('#user-id').addEventListener('input', function() { // 입력값 변경 시에
        document.querySelector('.id-icon').style.display = 'none'; // 숨김
    });

    // 비밀번호 확인
    document.querySelector('.password-check').addEventListener('click', function() {
        const userPw = document.querySelector('input[name="password"]').value;
        const userConfirmPw = document.querySelector('input[name="passwordConfirm"]').value;
        const pwPattern = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d!@#$%^&*()_+={}:;"'<>,.?/~`-]{8,20}$/;
        const checkIcon = document.querySelector('.pw-icon');

        if((!pwPattern.test(userPw)) || (!pwPattern.test(userConfirmPw))) {
            alert("비밀번호는 영문자, 숫자, 특수문자가 포함되며 총 길이는 8자리 이상 20자리 이하입니다.");
            return;
        }

        if(userPw === userConfirmPw) {
            alert("비밀번호가 일치합니다.");
            checkIcon.style.display = 'inline';
        } else {
            alert("비밀번호가 일치하지 않습니다.");
        }
    })

    // 비밀번호, 비밀번호 확인 중 하나의 입력 필드만 변경되도 체크 표시 숨김
    document.querySelector('#user-pw').addEventListener('input', function() { // 입력값 변경 시에
        document.querySelector('.pw-icon').style.display = 'none'; // 숨김
    });

    document.querySelector('#user-confirmpw').addEventListener('input', function() { // 입력값 변경 시에
        document.querySelector('.pw-icon').style.display = 'none'; // 숨김
    });
</script>
</body>
</html>
