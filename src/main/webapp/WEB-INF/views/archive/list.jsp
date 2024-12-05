<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/tags.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="../common/common.jsp" %>

    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/archive.css' />">

    <title>Archive</title>
</head>
<body>
<c:set var="menu" value="archive"/>
<div id="divWrapper">
    <div id="divContents">
        <%@ include file="../common/header.jsp" %>
        <section class="verticalLayoutFixedSection">
            <%@ include file="../common/nav.jsp" %>
            <c:set var="lnb" value="archiveAll"/>
            <div class="lnb">
                <!-- 파일 업로드 버튼 -->
                <div class="lnb-btn text-center mb-4">
                    <button type="button" class="btn btn-dark" id="addScheduleBtn">파일 업로드</button>
                </div>

                <!-- LNB 메뉴 -->
                <div class="lnb-menu">
                    <p class="listTitle ${lnb eq 'archiveAll' ? 'on' : '' }" id="archiveAll">파일 저장소</p>
                    <ul class="list2 roomList">
                        <li class="${lnb eq 'archiveA' ? 'on' : '' }"><a href="#" id="archiveA">내 저장소</a></li>
                        <li class="${lnb eq 'archiveB' ? 'on' : '' }"><a href="#" id="archiveB">팀 저장소</a></li>
                    </ul>
                </div>
            </div>
            <main>
                <div class="container my-3">
                    <div class="row mb-3">
                        <div class="col">
                            <div class="border p-2 bg-dark text-white fw-bold">게시글 목록</div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col">
                            <div class="border p-2 bg-light">
                                <table class="table">
                                    <colgroup>
                                        <col width="7%">
                                        <col width="*">
                                        <col width="15%">
                                        <col width="15%">
                                        <col width="15%">
                                    </colgroup>
                                    <thead>
                                        <tr>
                                            <th>번호</th>
                                            <th>이름</th>
                                            <th>업로더</th>
                                            <th>용량</th>
                                            <th>등록일</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:choose>
                                        <c:when test="${empty boards }">
                                            <tr>
                                                <td colspan="5" class="text-center">
                                                    조회된 파일이 없습니다.
                                                </td>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="board" items="${boards }">
                                                <tr>
                                                    <td>${board.no }</td>
                                                    <td><a href="detail?no=${board.no }">${board.title }</a></td>
                                                    <td>${board.user.nickname }</td>
                                                    <td><fmt:formatDate value="${board.createdDate }" /></td>
                                                    <td><fmt:formatDate value="${board.updatedDate }" /></td>
                                                </tr>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                    </tbody>
                                </table>
                                <div class="col-12 mt-3">
                                    <div class="text-end">
                                        <a href="#" class="btn btn-primary btn-sm">파일 업로드</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </section>
    </div>
</div>

<script src="/resources/js/archive.js"></script>

</body>
</html>

