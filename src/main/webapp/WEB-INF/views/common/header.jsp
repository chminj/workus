<%@ page pageEncoding="UTF-8"%>
<%@ include file="tags.jsp" %>
<header class="layout header">
    <a href="/home" class="logo">
        <span>WORKUS</span>
    </a>
    <ul class="globalMenu d-flex">
        <li class="d-flex align-items-center">
        <sec:authorize access="isAuthenticated()">
            <sec:authentication property="principal.no" var="LOGIN_USERNO" scope="request"/>
            <sec:authentication property="principal.id" var="LOGIN_USERID" scope="request"/>
            <sec:authentication property="principal.name"/> 님 환영합니다.
        </sec:authorize>
            <a href="/user/myinfo?userNo=${LOGIN_USERNO}" class="mgl5">
                <span class="badge text-bg-success modify">수정</span>
            </a>
        </li>
        <li class="d-flex align-items-center">
            <a href="/logout" class="mgl">
                <span class="badge text-bg-danger logout">로그아웃</span>
            </a>
        </li>
    </ul>
</header>


