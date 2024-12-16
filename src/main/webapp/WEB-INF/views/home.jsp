<%--
  Created by IntelliJ IDEA.
  User: jhta
  Date: 2024-11-06
  Time: 오후 4:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/tags.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="common/common.jsp" %>
    <title>workus ㅣ 중앙HTA 2404기 2조 FINAL PROJECT</title>
</head>
<body>
<c:set var="menu" value="home"/>
<div id="divWrapper">
    <div id="divContents">
        <%@ include file="common/header.jsp" %>
        <section class="verticalLayoutFixedSection mgt0">
            <%@ include file="common/nav.jsp" %>
            <main class="noLnb main">
                <section id="main-grid">
                    <div class="main1 mainSection justify-content-between">
                        <div class="main1Section">
                            <div class="grid-item" id="calendar-section">
                            <h2 class="sectionTit">일정</h2>
                            <div id="calendar"></div>
                            </div>
                        </div>
                        <div class="mainSectionHalf">
                            <!-- 채팅 -->
                            <div class="grid-item" id="chatting-section">
                                <h2 class="sectionTit">최근 채팅</h2>
                                <div>
                                <c:if test="${not empty chatroomDto}">
                                    <div class="border chatMain rounded p-3 mb-3 bg-white" id="chatroom" role="button" data-chatroom-no="${chatroomDto.chatroomNo}">
                                        <div class="d-flex justify-content-between align-items-start">
                                            <div>
                                                <h5 class="mb-2">${chatroomDto.chatroomTitle}</h5>
                                                <div class="d-flex">
                                                    <span class="badge bg-light text-dark fs-6 me-2">${chatroomDto.lastChatAuthor.name}</span> :
                                                    <p class="mb-0 mgl10 text-muted fs-6 text-truncate">${chatroomDto.lastChat}</p>
                                                </div>
                                            </div>
                                            <div class="text-end">
                                                <small class="text-muted fs-6 d-block"><fmt:formatDate value="${chatroomDto.lastChatDate}" pattern="MM/dd HH:mm" /></small>
                                                <c:if test="${chatroomDto.notReadCount != 0}">
                                                    <span class="badge bg-danger rounded-pill mt-2 fs-6 not-read-count${chatroomDto.chatroomNo}">${chatroomDto.notReadCount}</span>
                                                </c:if>
                                            </div>
                                        </div>

                                    </div>
                                    </c:if>
                                </div>
                            </div>
                            <%-- community --%>
                            <div class="grid-item" id="community-section">
                                <div>
                                    <h2 class="sectionTit"><a href="community/list">Wokrus Community 📰</a></h2>
                                    <div class="communityText">
                                        <div class="d-flex align-items-center justify-content-between titleBox">
                                            <span class="tit">${feed.title}</span>
                                        </div>
                                        <div class="contentBox">
                                        <c:choose>
                                            <c:when test="${not empty feed.mediaUrl}">
                                                <c:if test="${fn:contains(feed.mediaUrl, '.jpg') || fn:contains(feed.mediaUrl, '.png') || fn:contains(feed.mediaUrl, '.gif')}">
                                                    <img src="${s3}/resources/repository/communityfeedfile/${feed.mediaUrl}"/>
                                                </c:if>
                                                <c:if test="${fn:contains(feed.mediaUrl, '.mp4') || fn:contains(feed.mediaUrl, '.webm') || fn:contains(feed.mediaUrl, '.ogg')}">
                                                    <video controls>
                                                        <source src="${s3}/resources/repository/communityfeedfile/${feed.mediaUrl}" type="video/mp4">
                                                    </video>
                                                </c:if>
                                            </c:when>
                                        </c:choose>
                                        </div>
                                         <div class="d-flex align-items-center justify-content-between titleBox titleBoxBot">
                                            <span>${feed.content}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </section>
            </main>
        </section>
    </div>
</div>
<script>
    $('#chatroom').click(function() {
        let chatroomNo = $(this).data('chatroomNo');
        window.location.href = "/chatroom/list?prevChtroomNo=" + chatroomNo;
    })
</script>
<script src="/resources/js/meeting.js"></script>
<script src="/resources/js/calendar.js"></script>
</body>
</html>