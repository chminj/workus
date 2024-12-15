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
    <style>

            img {
                border: none;
                height: 100%;
                width: 100%;
                object-fit: contain;
            }
            video{
                border: 2px solid #000000;    /* 테두리 설정 */
                width: 100%;
                height: 600px;
                object-fit: contain;
            }

        /* lnb 메뉴 설정 (예시) */
        #lnb {
            position: fixed;
            top: 0;
            left: 0;
            width: 200px; /* LNB 너비 */
            height: 100%; /* 화면 전체 높이 */
            background-color: #333; /* 배경색 */
            color: white;
            padding: 20px;
            box-shadow: 2px 0 5px rgba(0, 0, 0, 0.2);
        }

        /* 메인 그리드 레이아웃 */
        #main-grid {
            display: flex;
            flex-wrap:wrap;
            gap: 20px; /* 섹션 간 간격 */
            padding: 20px;
            margin-left: 220px; /* LNB 너비(200px) + 여백(20px) */
        }
        #main-grid>div {width:calc(50% - 10px);}

        /* 개별 섹션 스타일 */
        .grid-item {
            border: 1px solid #ddd; /* 테두리 */
            border-radius: 8px;     /* 모서리 둥글게 */
            padding: 16px;
            background-color: #f9f9f9; /* 배경색 */
            overflow: hidden;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* 박스 그림자 */
        }


        /* 채팅 */
        /*#chatting-section {*/
        /*    grid-column: 1; !* 첫 번째 열 *!*/
        /*    grid-row: 1;    !* 첫 번째 행 *!*/
        /*}*/

        /*!* 회의실 *!*/
        /*#meeting-section {*/
        /*    grid-column: 2; !* 두 번째 열 *!*/
        /*    grid-row: 1;    !* 첫 번째 행 *!*/
        /*}*/

        /*!* 커뮤니티 *!*/
        /*#community-section {*/
        /*    grid-column: 1; !* 첫 번째 열 *!*/
        /*    grid-row: 2;    !* 두 번째 행 *!*/
        /*}*/

        /*!* 일정 *!*/
        /*#calendar-section {*/
        /*    grid-column: 2; !* 두 번째 열 *!*/
        /*    grid-row: 2;    !* 두 번째 행 *!*/
        /*}*/
    </style>
</head>
<body>
<c:set var="menu" value="home"/>
<div id="divWrapper">
    <div id="divContents">
        <%@ include file="common/header.jsp" %>
        <section class="verticalLayoutFixedSection">
            <%@ include file="common/nav.jsp" %>
            <main>
                <section id="main-grid">
                    <!-- 채팅 -->
                    <div class="grid-item" id="chatting-section">
                        <h2>최근 채팅</h2>
                        <div>
                        <c:if test="${not empty chatroomDto}">
                            <div class="border rounded p-4 mb-3 bg-white" id="chatroom" role="button" data-chatroom-no="${chatroomDto.chatroomNo}">
                                <div class="d-flex justify-content-between align-items-start mb-3">
                                    <div>
                                        <h5 class="mb-2">${chatroomDto.chatroomTitle}</h5>
                                        <span class="badge bg-light text-dark fs-6 me-2">${chatroomDto.lastChatAuthor.name}</span>
                                    </div>
                                    <div class="text-end">
                                        <small class="text-muted fs-6 d-block"><fmt:formatDate value="${chatroomDto.lastChatDate}" pattern="MM/dd HH:mm" /></small>
                                        <c:if test="${chatroomDto.notReadCount != 0}">
                                            <span class="badge bg-danger rounded-pill mt-2 fs-6 not-read-count${chatroomDto.chatroomNo}">${chatroomDto.notReadCount}</span>
                                        </c:if>
                                    </div>
                                </div>
                                <p class="mb-0 text-muted fs-6 text-truncate">${chatroomDto.lastChat}</p>
                            </div>
                            </c:if>
                        </div>
                    </div>

                    <!-- 회의실 -->
                    <div class="grid-item" id="meeting-section">
                        <h2>회의실</h2>
                        <div id="meeting"></div>
                    </div>

                    <!-- 커뮤니티 -->
                   <div class="grid-item" id="community-section">
                        <div>
                            <h1><a href="community/list">Wokrus Community 📰</a></h1>
                            <h3>${feed.title}</h3>
                            <h4>${feed.content}</h4>
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
                    </div>



                    <!-- 일정 -->
                    <div class="grid-item" id="calendar-section">
                        <h2>일정</h2>
                        <div id="calendar"></div>
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