<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ include file="../common/tags.jsp" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지사항 상세보기</title>
    <link href="../../../resources/css/notice.css" rel="stylesheet">
    <%@ include file="../home.jsp" %>
</head>
<body>
    <div class="container">
        <div class="notice-header">
            <h2>공지사항 제목 예시</h2>
        </div>
        <div class="notice-info">
            <span>작성자: 관리자 | </span>
            <span>등록일: 2024-11-12</span>
        </div>
        <div class="notice-content">
            <p>안녕하세요, 공지사항 내용 예시입니다.</p>
            <p>이곳에는 공지사항의 상세 내용이 표시됩니다. 사용자는 이 부분을 통해 공지의 세부 내용을 확인할 수 있습니다.</p>
            <p>공지 내용은 여러 줄로 구성될 수 있으며, 필요한 만큼 길게 작성 가능합니다.</p>
        </div>
        <div class="button-group2">
            <a href="list"/>목록으로</a>
            <a href="modify"/>수정</a>
            <a href="">삭제</a>
        </div>
    </div>
</body>
</html>
