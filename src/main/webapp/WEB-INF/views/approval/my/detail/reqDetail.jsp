<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/views/common/tags.jsp" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="/WEB-INF/views/common/common.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/approval.css">
    <script src="/resources/js/approval.js"></script>
    <!-- Include the Quill library -->
    <link href="https://cdn.jsdelivr.net/npm/quill@2.0.3/dist/quill.snow.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/quill@2.0.3/dist/quill.js"></script>
    <c:set var="menuTitle" value="요청 내역"/>
    <title>workus ㅣ 결재 ${menuTitle}</title>
</head>
<body>
<%--<%--%>
<%--    String no = request.getParameter("no");--%>
<%--    String categoryNo = request.getParameter("categoryNo");--%>

<%--    String formHtml = "";--%>

<%--    try {--%>


<%--        switch (category) {--%>
<%--            case TYPE_1:--%>
<%--                formHtml = "<div>양식 1 내용</div>";--%>
<%--                break;--%>
<%--            case TYPE_2:--%>
<%--                formHtml = "<div>양식 2 내용</div>";--%>
<%--                break;--%>
<%--            case TYPE_3:--%>
<%--                formHtml = "<div>양식 3 내용</div>";--%>
<%--                break;--%>
<%--        }    } catch (IllegalArgumentException e) {--%>
<%--        formHtml = "<div>유효하지 않은 결재 종류입니다.</div>";--%>
<%--    }--%>
<%--%>--%>
<div id="divWrapper">
    <div id="divContents">
        <c:set var="menu" value="approval"/>
        <%@ include file="/WEB-INF/views/common/header.jsp" %>
        <section class="verticalLayoutFixedSection">
            <%@ include file="/WEB-INF/views/common/nav.jsp" %>
            <c:set var="lnb" value="myReqList"/>
            <div class="lnb">
                <ul class="list1">
                    <li class="${lnb eq 'signOff' ? 'on' : '' }"><a
                            href="${pageContext.request.contextPath}/approval/form-list">결재 요청하기</a></li>
                    <li class="${lnb eq 'myReqList' ? 'on' : '' }"><a
                            href="${pageContext.request.contextPath}/approval/my/reqList">요청 내역</a></li>
                </ul>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_LEADER')">
                    <div class="approvalDepth accordion" id="accordionPanelsStayOpenExample">
                        <div class="accordion-item">
                            <h2 class="accordion-header">
                                <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="false"
                                        aria-controls="panelsStayOpen-collapseOne">
                                    결재함
                                </button>
                            </h2>
                            <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse show">
                                <div class="accordion-body">
                                    <ul class="list2 myAtdList">
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')">
                                            <li class="${lnb eq 'myWaitList' ? 'on' : '' }"><a
                                                    href="${pageContext.request.contextPath}/approval/my/waitList">대기건</a>
                                            </li>
                                            <li class="${lnb eq 'myEndList' ? 'on' : '' }"><a
                                                    href="${pageContext.request.contextPath}/approval/my/endList">종결건</a>
                                            </li>
                                            <li class="${lnb eq 'myDelList' ? 'on' : '' }"><a
                                                    href="${pageContext.request.contextPath}/approval/my/backList">반려건</a>
                                            </li>
                                        </sec:authorize>
                                        <sec:authorize access="hasRole('ROLE_LEADER')">
                                            <li class="${lnb eq 'myRefList' ? 'on' : '' }"><a
                                                    href="${pageContext.request.contextPath}/approval/my/refList">열람건</a>
                                            </li>
                                        </sec:authorize>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </sec:authorize>
            </div>
            <main>
                <h3 class="title1">
                    ${menuTitle}
                </h3>
                <%--                    <%= formHtml %>--%>
                <div id="apvListW" class="containerW">
                    <div class="tableW mgt40">
                        <div class="btn-group" role="group" aria-label="Basic example">
                            <button type="button" class="btn btn-secondary">승인</button>
                            <button type="button" class="btn btn-warning btn-custom2" data-bs-toggle="modal"
                                    data-bs-target="#exampleModal" data-bs-whatever="@mdo">반려
                            </button>
                        </div>
                        <table class="table">
                            <tbody>
                            <tr>
                                <td>${reqByNo.categoryName }</td>
                                <td><fmt:formatDate value="${reqByNo.createdDate }"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${reqByNo.status == 'pending'}">
                                            <span class="badge text-bg-primary">승인 대기</span>
                                        </c:when>
                                        <c:when test="${reqByNo.status == 'rejected'}">
                                            <span class="badge text-bg-danger">반려</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge border bg-secondary">처리 완료</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <c:choose>
                        <c:when test="${reqByNo.status != 'pending'}">
                            <c:when test="${reqByNo.status == 'completed'}">
                                <div class="additionalW">
                                <p class="userInfoHeader">결재자 정보
                                </p>
                                <ul class="userInfoW">
                                <li class="d-flex align-items-center">
                                <img src="/resources/images/userIcon.png" alt="결재자 이미지"/>
                                <div class="userInfo">
                                <p class="name">강태오 <span class="position">부장</span></p>

                            </c:when>
                            <c:when test="${reqByNo.status == 'rejected'}">
                                <p class="status">반려</p>
                                <p class="status">사유 : </p>
                                </div>
                                </li>
                                </ul>
                                </div>
                            </c:when>
                        </c:when>

                    </c:choose>
                </div>
            </main>
        </section>
    </div>
</div>
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">결재 요청을 반려하시겠습니까?</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p class="description">사유를 입력해야 성공적으로 처리됩니다.</p>
                <form method="POST" name="/approval/rejected">
                    <div class="form-floating mb-3">
                        <textarea class="form-control" id="rejectedReason" placeholder="사유를 남겨주세요." required></textarea>
                        <label for="rejectedReason">반려 사유를 입력해주세요.</label>
                    </div>
                </form>
            </div>
            <div class="modal-footer justify-content-center">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소하기</button>
                <button type="button" class="btn btn-primary">처리하기</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>