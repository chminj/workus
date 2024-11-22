<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
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
    $('.user-check').click(function() {
        const userNo = $('input[name="no"]').val(); //
        const checkIcon = $('.no-icon');

        if (isNaN(userNo) || userNo.trim() === "") { // 숫자가 아니거나 값이 비어있는지 확인
            alert("사번은 숫자만 입력 가능합니다.");
            return;
        }

        $.ajax({ // Ajax 요청 보내기
            url: `/ajax/user/check-no/` + userNo, // 사번을 URL에 추가
            method: 'GET', // GET 요청
            success: function(data) { // 응답 성공 시 실행
                if (data.data) {
                    alert("사번이 확인되었습니다.");
                    checkIcon.css('display', 'inline');
                } else {
                    alert("존재하지 않는 사번입니다.");
                }
            }
        });
    });

    // 사번 입력값 변경 시 아이콘 숨기기
    $('#user-no').on('input', function() { // input 필드에서 값이 바뀔 때마다
        $('.no-icon').css('display', 'none');
    });

    // 아이디 확인
    $('.id-check').click(function() {
        const userId = $('input[name="id"]').val();
        const checkIcon = $('.id-icon');
        const idPattern = /^[a-zA-Z][a-zA-Z0-9]{0,17}$/; // 아이디의 정규식 패턴

        if (!idPattern.test(userId)) { // 아이디가 패턴에 맞는지 확인
            alert("아이디는 영문자로 시작하고, 영문자나 숫자만 포함가능하며, 최대 18자입니다.");
            return;
        }

        $.ajax({ // Ajax 요청 보내기
            url: `ajax/user/check-id/` + userId, // 아이디를 URL에 추가
            method: 'GET', // GET 요청
            success: function(data) {
                if (data.data) {
                    alert("이미 존재하는 아이디입니다.");
                } else {
                    alert("사용 가능한 아이디입니다.");
                    checkIcon.css('display', 'inline');
                }
            }
        });
    });

    // 아이디 입력값 변경 시 아이콘 숨기기
    $('#user-id').on('input', function() {
        $('.id-icon').css('display', 'none');
    });

    // 비밀번호 확인
    $('.password-check').click(function() {
        const userPw = $('input[name="password"]').val();
        const userConfirmPw = $('input[name="passwordConfirm"]').val();
        const pwPattern = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d!@#$%^&*()_+={}:;"'<>,.?/~`-]{8,20}$/; // 비밀번호의 정규식 패턴
        const checkIcon = $('.pw-icon');

        if ((!pwPattern.test(userPw)) || (!pwPattern.test(userConfirmPw))) { // 패턴이 맞지 않으면
            alert("비밀번호는 영문자, 숫자, 특수문자가 포함되며 총 길이는 8자리 이상 20자리 이하입니다.");
            return;
        }

        if (userPw === userConfirmPw) { // 비밀번호와 비밀번호 확인이 같으면
            alert("비밀번호가 일치합니다.");
            checkIcon.css('display', 'inline');
        } else {
            alert("비밀번호가 일치하지 않습니다.");
        }
    });

    // 비밀번호 또는 비밀번호 확인 입력값 변경 시 아이콘 숨기기
    $('#user-pw').on('input', function() {
        $('.pw-icon').css('display', 'none');
    });

    $('#user-confirmpw').on('input', function() {
        $('.pw-icon').css('display', 'none');
    });
</script>
</body>
</html>
