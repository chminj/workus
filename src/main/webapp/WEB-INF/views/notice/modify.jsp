<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지사항 수정</title>
    <link href="../../../resources/css/notice.css" rel="stylesheet"/>
    <%@ include file="../home.jsp" %>
</head>
<body>
<main>
    <form class="write" action="update" method="post">
        <input type="hidden" name="id" value="${notice.id}">
        <input type="text" name="title" value="${notice.title}" placeholder="공지사항을 입력해주세요." required>
        <input type="text" name="content" value="${notice.content}" placeholder="오늘의 공지는?" class="text" required>

        <div class="button">

            <!-- 공지수정 버튼 -->
            <button type="submit" class="btn-modify">공지수정</button>

            <!-- 취소 버튼 -->
            <a href="list" class="btn-cancel">취소</a>
        </div>


    </form>
</main>

</body>
</html>
