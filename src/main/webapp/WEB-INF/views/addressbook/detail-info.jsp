<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../common/tags.jsp" %>
<c:set var="menu" value="address-book"/>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="../common/common.jsp" %>
  <title>workus template</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  <link rel="stylesheet" href="../../../resources/css/insertEmployee-form.css">
</head>
<body>
<div id="divWrapper">
  <div id="divContents">
    <%@ include file="../common/header.jsp" %>
    <section class="verticalLayoutFixedSection">
      <%@ include file="../common/nav.jsp" %>
      <div class="lnb" style="position: fixed;">
        <div class="lnb-btn text-center mb-4">
          <button type="button" class="btn btn-dark" id="addScheduleBtn">내 정보 보기</button>
        </div>

        <!-- LNB 메뉴 -->
        <div class="lnb-menu">
            <!-- 직원정보 관리 시스템 -->
            <p class="listTitle">직원정보 관리 시스템</p>
            <label class="menu-label">
                <input class="checkbox-input" type="checkbox" checked="checked" id="division1">
                <a href="/address-book/list">직원 정보 조회</a>
            </label>
            <label class="menu-label">
                <input class="checkbox-input" type="checkbox" id="division0">
                <a href="/address-book/insert">신규 직원 등록</a>
            </label>
            <label class="menu-label">
                <input class="checkbox-input" type="checkbox" id="division2">
                <a>기존 직원 관리</a>
            </label>
        </div>

      </div>
      <main>
        <h3 class="title1">직원 상세 정보</h3>
        <p class="description">※ 직원의 상세 정보를 확인해보세요. </p>
        <div class="content">

            <div class="input__block">
                <label>사번</label>
                <input type="text" placeholder="회사 사번" id="user-no" name="no" readonly/>
                <label>이름</label>
                <input type="text" placeholder="이름을 입력해주세요" id="user-name" name="name" readonly/>
            </div>

            <div class="input__block">
                <label>생년월일</label>
                <input type="date" placeholder="생년월일" id="user-birthdate" name="birthDate" readonly/>
                <label>입사일</label>
                <input type="date" placeholder="입사일" id="user-hireDate" name="hireDate" readonly/>
            </div>

            <div class="input__block">
                <label>부서</label>
                <input type="text" placeholder="부서" id="user-department" name="department" readonly/>
                <label>직책</label>
                <input type="text" placeholder="부서" id="user-position" name="position" readonly/>
            </div>

            <div class="input__block">
                <label>잔여 연차 일수</label>
                <input type="text" placeholder="잔여 연차일수는 자동으로 계산됩니다" id="user-annual_leave" name="unusedAnnualLeave" readonly />
            </div>

        </div>
      </main>
    </section>
  </div>
</div>
<script>
</script>
</body>
</html>