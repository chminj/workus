<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/tags.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="../common/common.jsp" %>

    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/calendar.css' />">
    <script src="/resources/js/calendar.js"></script>

    <title>Calendar</title>
</head>
<body>
<div id="divWrapper">
    <div id="divContents">
        <%@ include file="../common/header.jsp" %>
        <section class="verticalLayoutFixedSection">
            <%@ include file="../common/nav.jsp" %>
            <div class="lnb">
                <!-- 일정 추가 버튼 -->
                <div class="lnb-btn text-center mb-4">
                    <button type="button" class="btn btn-dark" id="addScheduleBtn">일정 추가하기</button>
                </div>

                <!-- LNB 메뉴 -->
                <div class="lnb-menu">
                    <div class="mb-5">
                        <div class="form-check mb-2">
                            <input class="form-check-input" type="checkbox" value="" id="selectAll" onclick="toggleCheckBoxes('personal')">
                            <label class="form-check-label" for="selectAll">
                                전체 선택
                            </label>
                        </div>
                    </div>
                    <!-- 내 캘린더 섹션 -->
                    <div class="mb-5">
                        <h4 class="font-weight-bold mb-3">내 캘린더</h4>
                        <!-- 체크박스 - 내 캘린더 -->
                        <div class="form-check mb-2">
                            <input class="form-check-input personal-checkbox" type="checkbox" value="" id="division1">
                            <label class="form-check-label" for="division1">
                                [기본] 내 캘린더
                            </label>
                        </div>
                    </div>

                    <!-- 팀 캘린더 섹션 -->
                    <div>
                        <h4 class="font-weight-bold mb-3">팀 캘린더</h4>
                        <!-- 체크박스 - 팀 캘린더 -->
                        <div class="form-check mb-2">
                            <input class="form-check-input team-checkbox" type="checkbox" value="" id="division0">
                            <label class="form-check-label" for="division0">
                                팀 캘린더
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            <main>
                <div class="content">
                    <!-- calendar 태그 -->
                    <div id='calendar-container'>
                        <div id='calendar'></div>
                    </div>
                </div>
            </main>
        </section>
    </div>
</div>
<!-- Modal -->
<div
        class="modal fade"
        id="calendarModal"
        tabindex="-1"
        aria-labelledby="calendarModalLabel"
        aria-hidden="true"
>
    <div class="modal-dialog row mb-3">
        <div class="modal-content col-12">
            <div class="modal-header">
                <h5 class="modal-title" id="calendarModalLabel">일정 추가하기</h5>
                <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                ></button>
            </div>
            <div class="modal-body">
                일정이름 : <input type="text" id="name" /><br />
                장소     : <input type="text" id="location" /><br />
                시작시간 : <input type="datetime-local" id="startDate" /><br />
                종료시간 : <input type="datetime-local" id="endDate" /><br />
                캘린더 : <select id="division">
                            <option value="1">
                                내 캘린더
                            </option>
                            <option value="0">
                                팀 캘린더
                            </option>
                        </select><br />
                내용 : <input type="text" id="content" /><br />
            </div>
            <div class="modal-footer">
                <button
                        type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal"
                >
                    취소
                </button>
                <button type="button" class="btn btn-primary" id="save">
                    추가
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
