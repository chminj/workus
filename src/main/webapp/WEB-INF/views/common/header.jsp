<%@ page pageEncoding="UTF-8"%>
<%@ include file="tags.jsp" %>
<header class="layout header">
    <a href="/home" class="logo">
        <span>WORKUS</span>
    </a>
    <div class="globalMenu">
        <i class="bi bi-person-circle"></i>
        <sec:authorize access="isAuthenticated()">
            <sec:authentication property="principal.no" var="LOGIN_USERNO" scope="request"/>
            <sec:authentication property="principal.name"/> 님 환영합니다.
        </sec:authorize>
        <div>
            <i class="bi bi-box-arrow-left"></i>
            <a href="/logout">logout</a>
        </div>
    </div>
</header>


