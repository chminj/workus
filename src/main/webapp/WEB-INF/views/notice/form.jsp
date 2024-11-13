<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판 글쓰기</title>
    <link href="../../../resources/css/notice.css" rel="stylesheet"/>
    <%@ include file="../home.jsp" %>
</head>
<body>
    <main>
        <form class="write">
            <input type="text" placeholder="공지사항을 입력해주세요." required>
            <input type="text" placeholder="오늘의 공지는?" class="text" required>
            <div class="button">
                <a href="인서트jsp 실행" class="btn-register">공지등록</a>
                <a href="list" class="btn-cancel">취소</a>
            </div>
        </form>
    </main>
</body>
</html>
