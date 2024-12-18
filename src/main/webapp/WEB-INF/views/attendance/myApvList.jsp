<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="../common/tags.jsp" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="../common/common.jsp" %>
    <link rel="stylesheet" href="${s3}/resources/css/attendance.css">
    <%-- day.js --%>
    <script src="
https://cdn.jsdelivr.net/npm/dayjs@1.11.13/dayjs.min.js
"></script>
    <script src="${s3}/resources/js/attendance.js"></script>
    <c:set var="menuTitle" value="결재 내역"/>
    <title>workus ㅣ 근태 ${menuTitle}</title>
</head>
<body>
<div id="divWrapper">
    <div id="divContents">
        <c:set var="menu" value="attendance"/>
        <%@ include file="../common/header.jsp" %>
        <section class="verticalLayoutFixedSection">
            <%@ include file="../common/nav.jsp" %>
            <c:set var="lnb" value="myApvList"/>
            <div class="lnb">
                <ul class="list1 myAtdInfo">
                    <li class="${lnb eq 'myAtdInfo' ? 'on' : '' }"><a href="list">내 근태 현황</a></li>
                </ul>
                <p class="listTitle">근태 내역</p>
                <ul class="list2 myAtdList">
                    <li class="${lnb eq 'myReqList' ? 'on' : '' }"><a href="myReqList">내 신청 내역</a></li>
                    <li class="${lnb eq 'myApvList' ? 'on' : '' }"><a href="${lnb}">내 ${menuTitle}</a></li>
                    <li class="${lnb eq 'myRefList' ? 'on' : '' }"><a href="myRefList">내 참조 내역</a></li>
                </ul>
            </div>
            <main>
                <h3 class="title1">
                    내 ${menuTitle}
                </h3>
                <div id="apvListW" class="content">
                    <div class="optW d-flex justify-content-between">
                        <div class="buttonW">
                            <button type="button" id="apvButton">승인</button>
                        </div>
                        <div class="searchW mgb30">
                            <form id="searchOption" method="get" action="myApvList" class="d-flex">
                                <input type="hidden" name="page"/>
                                <div class="input-group">
                                    <label for="searchOpt1">
                                        <select name="status" id="searchOpt1" class="form-select">
                                            <option value="name" ${param.status eq 'name' ? 'selected' : '' }>작성자</option>
                                            <option value="reason" ${param.status eq 'reason' ? 'selected' : '' }>사유
                                            </option>
                                        </select>
                                    </label>
                                </div>
                                <div class="input-group">
                                    <label for="searchText">
                                        <input type="text" id="searchText" name="keyword" class="form-control">
                                    </label>
                                    <button id="searchButton" class="btn btn-outline-secondary" type="button">검색
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="tableW">
                        <c:choose>
                            <c:when test="${empty forms}">
                                <div class="noData">
                                    <img src="${s3}/resources/images/noDataImg.png" class="noDataImg"/>
                                    <p class="noDataText">조회된 내역이 없습니다.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <table class="table">
                                    <colgroup>
                                        <col style="width:5%">
                                        <col style="width:5%">
                                        <col style="width:10%">
                                        <col style="width:12%">
                                        <col style="width:15%">
                                        <col style="width:auto">
                                        <col style="width:15%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th>
                                            <label for="checkAll">
                                                <input type="checkbox" id="checkAll">
                                            </label>
                                        </th>
                                        <th>No.</th>
                                        <th>요청자</th>
                                        <th>유형</th>
                                        <th>요청일</th>
                                        <th>사유</th>
                                        <th>상태</th>
                                    </tr>
                                    </thead>
                                    <c:forEach var="form" items="${forms }" varStatus="loop">
                                        <tr>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${form.status == 'I'}">
                                                        <label for="${form.atdNo}">
                                                            <input type="checkbox" id="${form.atdNo}" class="eachCheck">
                                                        </label>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <label for="${form.atdNo}">
                                                            <input type="checkbox" id="${form.atdNo}" disabled>
                                                        </label>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${loop.count }</td>
                                            <td>${form.reqUserName}</td>
                                            <td>
                                                ${form.categoryName }
                                            <c:choose>
                                                <c:when test="${form.time != null}">
                                                    (${form.time})
                                                </c:when>
                                            </c:choose>
                                            </td>
                                            <td><fmt:formatDate value="${form.createdDate }"/></td>
                                            <td class="text-start">${form.reason}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${form.status == 'I'}">
                                                        <span class="badge text-bg-primary">결재 대기</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge border bg-secondary">결재 완료</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:otherwise>
                        </c:choose>
                        <!-- paging -->
                        <c:if test="${not empty forms }">
                            <div class="row mb-3">
                                <div class="col-12">
                                    <nav>
                                        <ul class="pagination justify-content-center">
                                            <li class="page-item ${paging.first ? 'disabled' : '' }">
                                                <a class="page-link" onclick="changePage(${paging.nextPage}, e)"
                                                   href="myApvList?page=${paging.prevPage}">이전</a>
                                            </li>
                                            <c:forEach var="num" begin="${paging.beginPage }" end="${paging.endPage }">
                                                <li class="page-item ${paging.page eq num ? 'active' : '' }">
                                                    <a class="page-link" onclick="changePage(${num }, e)"
                                                       href="myApvList?page=${num }">
                                                            ${num }
                                                    </a>
                                                </li>
                                            </c:forEach>
                                            <li class="page-item ${paging.last ? 'disabled' : '' }">
                                                <a class="page-link" onclick="changePage(${paging.nextPage}, e)"
                                                    href="myApvList?page=${paging.nextPage}">다음</a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </c:if>
                        <!-- //paging -->
                    </div>
                </div>
            </main>
        </section>
    </div>
</div>
<script>
    $(function () {
        let refSearch = document.querySelector("#searchOption");
        let pageInput = document.querySelector("input[name=page]");

        // 페이징 번호 클릭 시
        function changePage(page, e) {
            e.preventDefault();
            pageInput.value = page;

            refSearch.submit();
        }

        $("#searchButton").on("click", function () {
            pageInput.value = 1;

            $("#searchOption").submit();
        })

        $("#checkAll").on("change", function () {
            if ($(this).is(":checked")) {
                $(".tableW .eachCheck").prop("checked", true);
            } else {
                $(".tableW .eachCheck").prop("checked", false);
            }
        });

        // 승인 로직
        $("#apvButton").on("click", function () {
            let checkedBoxes = $(".eachCheck:checked");
            let requestDtoList = Array.from(checkedBoxes).map(box => ({
                atdNo: box.id
            }));

            // 선택된 항목이 없을 경우 check
            if (requestDtoList.length === 0) {
                alert('승인할 항목을 선택해 주세요.');
                return;
            } else {
               if (!confirm('승인하겠습니까?')) {
                    return; // 사용자가 취소를 선택한 경우
                }
            }

            // 데이터 전송
            fetch('/api/attendances/approve', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestDtoList),
            })
            .then(response => {
                if (!response.ok) {
                    // 서버에서 에러가 발생한 경우
                    return Promise.reject(new Error(response.message));
                }
                return response.json();
            })
            .then(data => {
                // 페이지 새로고침
                location.reload();
            })
            .catch(error => {
                alert(error.message);
            });
        })
    });
</script>
</body>
</html>