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
  <script src="/resources/js/attendance.js"></script>
  <title>workus ㅣ 근태</title>
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
          <li class="${lnb eq 'myAtdInfo' ? 'on' : '' }"><a href="">내 신청 내역</a></li>
        </ul>
        <p class="listTitle">근태 내역</p>
        <ul class="list2 myAtdList">
          <li class="${lnb eq 'myApvList' ? 'on' : '' }"><a href="myApvList">내 신청 내역</a></li>
          <li><a href="">내 결재 내역</a></li>
          <li><a href="">내 참조 내역</a></li>
        </ul>
      </div>
      <main>
        <h3 class="title1">
          내 근태 현황
        </h3>
        <div id="apvListW" class="content">
          <div class="search-container d-flex mgb30">
            <div class="input-group">
              <label for="startDate" class="d-none">시작 날짜</label>
              <input type="date" id="startDate" name="startDate" class="form-control">
              <span class="simpleText">~</span>
              <label for="endDate" class="d-none">종료 날짜</label>
              <input type="date" id="endDate" name="endDate" class="form-control">
            </div>
            <div class="input-group">
              <label for="searchOpt1">
                <select name="searchOpt1" id="searchOpt1" class="form-select">
                  <option value="writer">작성자</option>
                  <option value="reason">사유</option>
                </select>
              </label>
            </div>
            <div class="input-group">
            <label for="searchText">
            <input type="text" id="searchText" name="text" class="form-control">
            </label>
            <button id="searchButton" class="btn btn-outline-secondary" type="button">검색</button>
            </div>
          </div>
          <div class="">
            <table class="table table-hover">
              <colgroup>
                <col style="width:10%" >
                <col style="width:20%" >
                <col style="width:" >
                <col style="width:15%" >
                <col style="width:20%" >
              </colgroup>
              <thead>
                <tr>
                  <th>No.</th>
                  <th>유형</th>
                  <th>사유</th>
                  <th>요청자</th>
                  <th>요청일</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>seq</td>
                  <td class="">categoryName</td>
                  <td class="">reason</td>
                  <td>userNo</td>
                  <td>createdDate</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </main>
    </section>
  </div>
</div>
</body>
</html>