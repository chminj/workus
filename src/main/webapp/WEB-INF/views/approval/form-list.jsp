<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="../common/tags.jsp" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="../common/common.jsp" %>
    <link rel="stylesheet" href="/resources/css/approval.css">
    <script src="/resources/js/approval.js"></script>
    <title>workus ㅣ 결재</title>
</head>
<body>
<div id="divWrapper">
    <div id="divContents">
        <c:set var="menu" value="approval"/>
        <%@ include file="../common/header.jsp" %>
        <section class="verticalLayoutFixedSection">
            <%@ include file="../common/nav.jsp" %>
            <c:set var="lnb" value="signOff"/>
            <div class="lnb">
                <ul class="list1">
                    <li class="${lnb eq 'signOff' ? 'on' : '' }"><a href="form-list">결재 요청하기</a></li>
                    <li class="${lnb eq 'myReqList' ? 'on' : '' }"><a href="myReqList">요청 내역</a></li>
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
                                    <li class="${lnb eq 'myWaitList' ? 'on' : '' }"><a href="myWaitList">대기건</a></li>
                                    <li class="${lnb eq 'myEndList' ? 'on' : '' }"><a href="myEndList">종결건</a></li>
                                    <li class="${lnb eq 'myDelList' ? 'on' : '' }"><a href="myEndList">반려건</a></li>
                                    <li class="${lnb eq 'myRefList' ? 'on' : '' }"><a href="myRefList">열람건</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <main>
                <h3 class="title1">
                    결재 요청하기
                </h3>
                <div class="containerW">
                    <nav class="sidebar">
                        <ul>
                            <c:forEach var="category" items="${categories}">
                                <li onClick="showForm('${category.no}', '${category.name}')">${category.name}</li>
                            </c:forEach>
                        </ul>
                    </nav>
                    <div class="content">
                        <div id="100" class="form">
                            <h2 class="fs-4 text-center">양식 1</h2>
                            <form>
                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <th class="table-active title">제목</th>
                                        <td colspan="5"><input type="text" class="form-control" value="카테고리 이름"/></td>
                                    </tr>
                                    <tr>
                                        <th class="table-active title">열람/공람자</th>
                                        <td colspan="5">${leader.name}</td>
                                    </tr>
                                    </tbody>
                                </table>
                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <th class="table-active">휴직 기간</th>
                                        <td colspan="5">
                                            <div class="d-flex">
                                                <input type="date" name="fromDate" class="form-control wd150"
                                                       id="apvFromDate"/>
                                                <span class="mgl5 mgr5">~</span>
                                                <input type="date" name="todate" class="form-control wd150"
                                                       id="apvToDate"/>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="table-active">휴직 사유</th>
                                        <td colspan="5">
                                            <textarea></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="table-active">업무 대행자</th>
                                        <td colspan="2">
                                            <textarea></textarea>
                                        </td>
                                        <th class="table-active">인수인계 상황</th>
                                        <td colspan="2">
                                            <textarea></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="table-active">비고</th>
                                        <td colspan="5">
                                            <textarea></textarea>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div class="btnW mgt40 d-flex justify-content-center">
                                    <input type="reset" value="입력 취소" class="btn btn-secondary"/>
                                    <button type="submit" class="btn btn-dark">결재 요청하기</button>
                                </div>
                            </form>
                        </div>
                        <div id="200" class="form">
                            <h2 class="fs-4 text-center">양식 2</h2>
                            <form>
                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <th class="table-active title">제목</th>
                                        <td colspan="5"><input type="text" class="form-control" value="카테고리 이름"/></td>
                                    </tr>
                                    <tr>
                                        <th class="table-active title">열람/공람자</th>
                                        <td colspan="5">팀장 이름 넣기</td>
                                    </tr>
                                    </tbody>
                                </table>
                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <th class="table-active">휴직 기간</th>
                                        <td colspan="5">
                                            <div class="d-flex">
                                                <input type="date" name="toDate" class="form-control wd150"/>
                                                <span class="mgl5 mgr5">~</span>
                                                <input type="date" name="fromDate" class="form-control wd150"/>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="table-active">휴직 사유</th>
                                        <td colspan="5">
                                            <textarea></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="table-active">병명</th>
                                        <td colspan="5">
                                            <textarea></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="table-active">업무 대행자</th>
                                        <td colspan="2">
                                            <textarea></textarea>
                                        </td>
                                        <th class="table-active">인수인계 상황</th>
                                        <td colspan="2">
                                            <textarea></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="table-active">기타사항</th>
                                        <td colspan="5">
                                            <textarea></textarea>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div class="btnW mgt40 d-flex justify-content-center">
                                    <input type="reset" value="입력 취소" class="btn btn-secondary"/>
                                    <button type="submit" class="btn btn-dark">결재 요청하기</button>
                                </div>
                            </form>
                        </div>
                        <div id="300" class="form">
                            <h2>양식 3</h2>
                            <form>
                                <label for="address">주소:</label>
                                <input type="text" id="address" name="address" required>
                                <label for="phone">전화번호:</label>
                                <input type="text" id="phone" name="phone" required>
                                <button type="submit">제출</button>
                            </form>
                        </div>
                        <div id="400" class="form">
                            <h2>양식 3</h2>
                            <form>ㄴㄷㄷ
                            </form>
                        </div>
                        <div id="500" class="form">
                            <h2>양식 3</h2>
                            <form>
                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <th class="table-active title">제목</th>
                                        <td colspan="5">

                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="table-active title">열람/공람자</th>
                                        <td colspan="5">팀장 이름 넣기</td>
                                    </tr>
                                    </tbody>
                                </table>

                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <th class="table-active">방문 날짜</th>
                                        <td colspan="5">
                                            <input type="text" value="">
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="table-active">방문처</th>
                                        <td colspan="5">
                                            <textarea></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="table-active">방문 동기</th>
                                        <td colspan="2">
                                            <textarea></textarea>
                                        </td>
                                        <th class="table-active">방문 시간</th>
                                        <td colspan="2">
                                            <textarea></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="table-active">특이사항</th>
                                        <td colspan="5">
                                            <textarea></textarea>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div class="btnW d-flex justify-content-center">
                                    <input type="reset" value="입력 취소" class="btn btn-secondary"/>
                                    <button type="submit" class="btn btn-dark">결재 요청하기</button>
                                </div>
                            </form>
                        </div>
                        <div id="600" class="form">
                            <h2>양식 3</h2>
                            <form>
                                ㅈㄷㅈㄷ
                            </form>
                        </div>
                        <div id="700" class="form">
                            <h2>양식 3</h2>
                            <form>
                                ㅇㄹㅇㄹ
                            </form>
                        </div>
                        <div id="800" class="form">
                            <h2>양식 8</h2>
                            <form>
                                <%--                                --%>
                            </form>
                        </div>
                    </div>
                </div>
            </main>
        </section>
    </div>
</div>
<script>
    function showForm(formId, categoryName) {
        // 모든 양식 숨기기
        const forms = document.querySelectorAll('.form');
        forms.forEach(form => {
            form.style.display = 'none';
        });

        // 선택한 양식만 보이기
        let selectedForm = document.getElementById(formId);
        selectedForm.style.display = 'block';

        // 제목 업데이트
        let titleElement = selectedForm.querySelector('h2');
        // let titleElementInTable = selectedForm.querySelector('td:first-child');
        titleElement.textContent = categoryName;
        // titleElementInTable.textContent = categoryName;
    }

    // 페이지 로드 시 첫 번째 양식 보이기
    document.addEventListener('DOMContentLoaded', () => {
        showForm('100', '휴직원');
    });
</script>
</body>
</html>