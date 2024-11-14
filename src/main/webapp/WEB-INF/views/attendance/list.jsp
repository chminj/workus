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
  <title>workus template</title>
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
                    <span class="usable">17</span>
                    <span class="slash">/</span>
                    <span class="total">20</span>
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
        <p>연차 신청하기</p>
        <form method="dialog" class="flex justify-between">
          <button value="cancel" class="btn btn-sm btn-secondary">취소하기</button>
          <button value="confirm" class="btn btn-sm btn-primary">신청하기</button>
        </form>
      </dialog>
    </section>
  </div>
</div>
</body>
</html>