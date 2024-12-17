<%-- WEB-INF/views/error.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/tags.jsp" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${s3}/resources/css/error.css">
    <title>WORKUS ㅣ ERROR PAGE</title>
</head>
<body>
    <main>
        <img src="/resources/images/errorIcon.png" />
        <h1>해당 페이지로 이동할 수 없습니다.</h1>
        <h3>Error: ${status != null ? status : '알 수 없는 오류'}</h3>
        <p>${message != null ? message : '문제가 발생했습니다. 나중에 다시 시도해 주세요.'}</p>
        <a href="${pageContext.request.contextPath}/" class="button">홈으로 돌아가기</a>
    </main>
</body>
</html>