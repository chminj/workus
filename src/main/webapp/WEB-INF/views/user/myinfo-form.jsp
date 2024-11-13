<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../common/tags.jsp" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="../common/common.jsp" %>
    <link rel="stylesheet" href="../../../resources/css/myinfo-form.css">
    <title>workus template</title>
</head>
<body>
<div id="divWrapper">
    <div id="divContents">
        <%@ include file="../common/header.jsp" %>
        <section class="verticalLayoutFixedSection">
            <%@ include file="../common/nav.jsp" %>
            <div class="lnb">
                <ul class="">
                    <li class="">
                        <a href="/user/myinfo">내 정보 수정</a>
                    </li>
                    <li class="">
                        <a href="/address-book/list">사원 목록</a>
                    </li>
                </ul>
            </div>
            <main>
                <h3 class="title1">내 정보 수정</h3>
                <div class="content">
                    <form action="" method="post" enctype="multipart/form-data">

                        <div class="input__block" style="justify-content: center;">
                            <img src="" alt="" class="profile-image" id="profileImage" onclick="document.getElementById('fileInput').click();">
                            <input type="file" id="fileInput" class="file-input" name="profileImage" accept="image/*" onchange="previewImage(event)">
                        </div>

                        <div class="input__block">
                            <input type="email" placeholder="이메일을 입력해주세요" name="email" required />
                        </div>

                        <div class="input__block">
                            <input type="password" placeholder="비밀번호를 입력해주세요" name="password" required />
                        </div>

                        <div class="input__block">
                            <textarea placeholder="자기소개를 입력해주세요" name="bio" required></textarea>
                        </div>

                        <div class="input__block">
                            <input type="tel" placeholder="연락처를 입력해주세요" name="phone" required />
                        </div>

                        <div class="input__block">
                            <input type="text" placeholder="우편번호를 입력해주세요" name="postal_code" required />
                            <button type="button" class="google__btn">우편번호 찾기</button>
                        </div>

                        <div class="input__block">
                            <input type="text" placeholder="주소" name="address" required readonly/>
                        </div>

                        <div class="input__block">
                            <input type="text" placeholder="상세주소" name="detailed_address" required />
                        </div>

                        <button class="signup__btn">회원정보 수정</button>

                    </form>
                </div>
            </main>
        </section>
    </div>
</div>
</body>
</html>