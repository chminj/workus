<%-- WEB-INF/views/error.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/tags.jsp" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/resources/css/error.css">
    <title>오류 발생</title>
</head>
<body>
    <main>
        <img src="/resources/images/errorIcon.png" />
        <h1>오류 발생</h1>
        <p>에러 메시지 : ${requestScope.errorMessage}</p>
<%--      <p>상태 코드: ${status}</p>
    <p>메시지: ${message}</p>  --%>

        <a href="${pageContext.request.contextPath}/" class="button">홈으로 돌아가기</a>
    </main>
</body>
</html>