<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../common/tags.jsp" %>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="../common/common.jsp" %>
  <link rel="stylesheet" href="/resources/css/attendance.css">
  <%-- day.js --%>
  <script src="
https://cdn.jsdelivr.net/npm/dayjs@1.11.13/dayjs.min.js
"></script>
  <title>workus ㅣ 근태</title>
</head>
<body>
<div id="divWrapper">
  <div id="divContents">
    <%@ include file="../common/header.jsp" %>
    <section class="verticalLayoutFixedSection">
      <%@ include file="../common/nav.jsp" %>
      <div class="lnb">
        <ul class="list1 myAtdInfo">
          <li class=""><a href="">내 근태 현황</a></li>
        </ul>
        <p class="listTitle">근태 내역</p>
        <ul class="list2 myAtdList">
          <li><a href="">내 신청 내역</a></li>
          <li><a href="">내 결재 내역</a></li>
          <li><a href="">내 참조 내역</a></li>
        </ul>
      </div>
      <main>
        <h3 class="title1">
          내 근태 현황
        </h3>
        <div id="atdCheck" class="content">
          <div class="applyItem">
            <button type="button" class="applyBtn btn btn-lg border border-secondary d-flex justify-content-between align-items-center" id="atdApplyBtnForModal">
              <span>
                휴가 신청
                <span class="holiday">
                    <span class="usable">${attendanceDto.unusedAnnualLeave}</span>
                    <span class="slash">/</span>
                    <span class="total">${attendanceDto.annualLeaveCount}</span>
                </span>
              </span>
              <i class="bi bi-arrow-right"></i>
            </button>
            <a href="/attendance/my-approval/" class="applyWaitBtn btn btn-lg border border-secondary d-flex justify-content-between align-items-center">
                <span>
                  결재 대기
                  <span class="flex count">
                    <span class="data">0</span>건
                  </span>
                </span>
                <i class="bi bi-arrow-right"></i>
            </a>
          </div>
          <div id="fullCalendarInAtd" class="mgt40">
          </div>
        </div>
      </main>
      <dialog id="atdRequestForm">
        <div class="wrapper">
          <div class="modalHeader flex justify-content-between align-items-center">
            <h5 class="modalTitle">연차 신청하기</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">×</span>
            </button>
          </div>
          <form method="dialog">
            <div class="modalBody sec2">
              <div class="modalLeft">
                <div class="reqFormSec">
                  <label for="category" class="reqFormTit">종류</label>
                  <select id="category" class="form-select w-25">
                    <option>연차</option>
                    <option>병가</option>
                  </select>
                  <%-- 연차일 경우, 병가일 경우 밑에 옵션 안보이게 처리할 것 --%>
                  <div class="annualLeaveOnly mgt10">
                    <label for="oneDay">
                      <input type="radio" name="dayCategory" value="oneDay" id="oneDay">
                      <span class="mgr10 mgl5">연차</span>
                    </label>
                    <label for="halfDay">
                      <input type="radio" name="dayCategory" value="halfDay" id="halfDay">
                      <span class="mgr10 mgl5">반차</span>
                    </label>
                    <label for="quarterDay">
                      <input type="radio" name="dayCategory" value="quarterDay" id="quarterDay">
                      <span class="mgr10 mgl5">반반차</span>
                    </label>

                  </div>
                  <p class="description">* 근속 연차 :
                    <span class="current">${attendanceDto.unusedAnnualLeave}</span>
                    /
                    <span class="total">${attendanceDto.annualLeaveCount}</span>일
                  </p>
                </div>
                <div class="reqFormSec">
                  <label for="atdFromDate" class="reqFormTit">기간</label>
                  <input type="date" name="fromDate" id="atdFromDate" class="form-control wd150 inlineBlock">
                  ~
                  <label for="atdToDate">
                    <input type="date" name="toDate" id="atdToDate" class="form-control wd150">
                  </label>
                  <span class="dayCheck">총 <span class="dayTotal">1</span>일</span>
                </div>
                <div class="reqFormSec">
                  <label for="reasonSec" class="reqFormTit">사유</label>
                  <textarea name="reason" id="reasonSec"></textarea>
                </div>
              </div>
              <div class="modalRight">
                <p class="reqFormTit mgb10">결재 및 참조선</p>
                <div class="d-flex">
                  <div class="listSec wholeList">
                    <ul class="atdList">
                      <li class="d-flex">
                        <input type="checkbox" id="checkingUser1" class="mgr5">
                        <label for="checkingUser1">
                          <span class="name mgr5">김재언</span>
                        </label>
                        <span class="gray">
                              <span class="dept">개발1팀</span>
                              <span>/</span>
                              <span class="position">부장</span>
                            </span>
                      </li>
                    </ul>
                  </div>
                  <div class="listSec checkedList">
                    <div class="d-flex">
                      <div class="btnW">
                        <button type="button" id="apvLineAdd">
                          <i class="bi bi-caret-right-square"></i>
                        </button>
                        <button type="button" id="apvLineCancel">
                          <i class="bi bi-caret-left-square"></i>
                        </button>
                      </div>
                      <div class="atdList">
                        <p class="sTit">결재선</p>
                        <ul>
                          <li class="d-flex">
                            <input type="checkbox" id="checkingUser" class="mgr5">
                            <label for="checkingUser">
                              <span class="name mgr5">김재언</span>
                            </label>
                            <span class="gray">
                              <span class="dept">개발1팀</span>
                              <span>/</span>
                              <span class="position">부장</span>
                            </span>
                          </li>
                        </ul>
                      </div>
                    </div>
                    <div class="d-flex">
                      <div class="btnW">
                        <button type="button" id="refLineAdd">
                          <i class="bi bi-caret-right-square"></i>
                        </button>
                        <button type="button" id="refLineCancel">
                          <i class="bi bi-caret-left-square"></i>
                        </button>
                      </div>
                      <div class="atdList">
                        <p class="sTit">참조선</p>
                        <ul>
                          <li></li>
                        </ul>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="modalFooter d-flex justify-content-center">
              <button value="cancel" class="btn btn-sm btn-secondary">취소하기</button>
              <button value="confirm" class="btn btn-sm btn-primary" id="confirmModalBtn">신청하기</button>
            </div>
          </form>
        </div>
      </dialog>
    </section>
  </div>
</div>
<script>
  $(function(){ // 이중 모달 구현 예정
    $("#confirmModalBtn").on("click",function (){
      confirm("해당 정보로 연차를 신청하겠습니까?");
    });
  })
</script>
</body>
</html>