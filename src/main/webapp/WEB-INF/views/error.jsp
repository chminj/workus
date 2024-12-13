<%-- WEB-INF/views/error.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/tags.jsp" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/resources/css/error.css">
    <title>접근 거부</title>
</head>
<body>
    <main>
        <img src="/resources/images/errorIcon.png" />
        <h1>접근 권한이 없습니다.</h1>
        <p>이 페이지에 접근할 수 있는 권한이 없습니다.</p>
        <a href="${pageContext.request.contextPath}/" class="button">홈으로 돌아가기</a>
    </main>
</body>
</html>