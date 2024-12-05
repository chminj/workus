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
    <title>workus ㅣ 결재 ${menuTitle}</title>
</head>
<body>
<p>${pageContext.request.contextPath}</p>
<div id="divWrapper">
    <div id="divContents">
        <c:set var="menu" value="approval"/>
        <%@ include file="/WEB-INF/views/common/header.jsp" %>
        <section class="verticalLayoutFixedSection">
            <%@ include file="/WEB-INF/views/common/nav.jsp" %>
            <c:set var="lnb" value="myWaitList"/>
            <c:set var="menuTitle" value="대기건"/>
            <div class="lnb">
                <ul class="list1">
                    <li class="${lnb eq 'signOff' ? 'on' : '' }"><a href="../form-list">결재 요청하기</a></li>
                    <li class="${lnb eq 'myReqList' ? 'on' : '' }"><a href="../myReqList">요청 내역</a></li>
                </ul>
                <div class="approvalDepth accordion" id="accordionPanelsStayOpenExample">
                    <div class="accordion-item">
                        <h2 class="accordion-header">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="false"
                                    aria-controls="panelsStayOpen-collapseOne">
                                내 결재함
                            </button>
                        </h2>
                        <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse show">
                            <div class="accordion-body">
                                <ul class="list2 myAtdList">
                                    <li class="${lnb eq 'myWaitList' ? 'on' : '' }"><a
                                            href="my/waitList">${menuTitle}</a>
                                    </li>
                                    <li class="${lnb eq 'myEndList' ? 'on' : '' }"><a href="my/endList">종결건</a></li>
                                    <li class="${lnb eq 'myDelList' ? 'on' : '' }"><a href="my/backList">반려건</a></li>
                                    <li class="${lnb eq 'myRefList' ? 'on' : '' }"><a href="my/refList">열람건</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <main>
                <h3 class="title1">
                    ${menuTitle}
                </h3>
                <div class="containerW">
                    ${menuTitle}${menuTitle}${menuTitle}${menuTitle}${menuTitle} ㅇㅅㅇ
                </div>
            </main>
        </section>
    </div>

</div>
<script>
    // form category show
    function showForm(formId, categoryName) {
        $('.form').hide();
        $('#' + formId).show();

        // 제목 업데이트
        let titleElement = $('#' + formId).find('h2');
        titleElement.text(categoryName);

        // 제목 기본값 설정
        let titleInput = $('#' + formId).find('.titleInput');
        titleInput.val(categoryName);
    }

    // 초기 양식 설정
    function initializeForms() {
        $('.form').each(function () {
            let categoryNo = $(this).attr('id');

            // label이 없으면 추가
            if ($(this).find('.titleInput+label').length === 0) {
                console.log('없');
                $(this).find('.titleInput').before(`<label for="title${categoryNo}" class="titleDefault">제목은 ex) '[사번] 양식명 + 추가 내용' 형식으로 작성 부탁드립니다.</label>`);
            } else {
                console.log('있');
            }
            // titleInput의 id 설정
            let titleInput = $(this).find('.titleInput');
            titleInput.attr('id', 'title' + categoryNo);

            // label의 for 속성 설정
            $(this).find('label').attr('for', 'title' + categoryNo);
        });
    }

    // 페이지 로드 시 초기화
    $(document).ready(() => {
        initializeForms(); // 폼 초기화
        showForm('100', '휴직원'); // 첫 화면 표시
    });

    // sidebar click event
    $('.category-item').on('click', function () {
        $('.category-item').removeClass('active');
        $(this).addClass('active');

        const formId = $(this).data('id');
        const categoryName = $(this).data('name');

        showForm(formId, categoryName);
    });
</script>
</body>
</html>