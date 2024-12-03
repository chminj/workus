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
        <h3 class="title1">신규 직원 등록</h3>
        <p class="description">※ 우리 회사의 새로운 직원을 등록하세요 </p>
        <div class="content">

          <form id="form-insert-employee" action="/address-book/insert" method="post" >

            <div class="input__block">
                <label>사번 발급</label>
                <input type="text" placeholder="회사 사번을 발급합니다." id="user-no" name="no" readonly/>
                <button type="button" class="google__btn user-check">사번발급</button>
            </div>

            <div class="input__block">
                <label>이름</label>
                <input type="text" placeholder="이름을 입력해주세요" id="user-name" name="name" />
            </div>

            <div class="input__block">
                <label>생년월일</label>
                <input type="date" placeholder="생년월일을 선택해주세요." id="user-birthdate" name="birthDate" />
            </div>

            <div class="input__block">
                <label>입사일</label>
                <input type="date" placeholder="입사일을 입력해주세요." id="user-hireDate" name="hireDate" />
            </div>

            <div class="input__block">
                <label>부서</label>
                <select class="filter-select" id="user-department" name="dept">
                      <option value="1001" ${param.dept eq '1001' ? 'selected' : ''}>인사팀</option>
                      <option value="1002" ${param.dept eq '1002' ? 'selected' : ''}>개발1팀</option>
                      <option value="1003" ${param.dept eq '1003' ? 'selected' : ''}>개발2팀</option>
                      <option value="1004" ${param.dept eq '1004' ? 'selected' : ''}>영업1팀</option>
                      <option value="1005" ${param.dept eq '1005' ? 'selected' : ''}>영업2팀</option>
                      <option value="1006" ${param.dept eq '1006' ? 'selected' : ''}>기술연구소</option>
                      <option value="1007" ${param.dept eq '1007' ? 'selected' : ''}>해외연구소</option>
                      <option value="1008" ${param.dept eq '1008' ? 'selected' : ''}>운영1팀</option>
                </select>
            </div>

            <div class="input__block">
                <label>직책</label>
                <select class="filter-select" id="user-position" name="position">
                      <option value="all">모두</option>
                      <option value="11" ${param.dept eq '1001' ? 'selected' : ''}>사장</option>
                      <option value="12" ${param.dept eq '1002' ? 'selected' : ''}>부장</option>
                      <option value="13" ${param.dept eq '1003' ? 'selected' : ''}>과장</option>
                      <option value="14" ${param.dept eq '1004' ? 'selected' : ''}>대리</option>
                      <option value="15" ${param.dept eq '1005' ? 'selected' : ''}>사원</option>
                </select>
            </div>

            <div class="input__block">
                <label>연차 일수</label>
                <input type="text" placeholder="잔여 연차일수는 자동으로 계산됩니다" id="user-annual_leave" name="unusedAnnualLeave" readonly />
            </div>

            <button class="signup__btn" type="submit">신규 사원 등록</button>

          </form>
        </div>
      </main>
    </section>
  </div>
</div>
<script>
  // 페이지 링크 클릭 시
  $(".page-link").click(function(e) {
      e.preventDefault(); // 제출을 막는다.
      let page = $(this).attr("data-page"); // this는 현재 이벤트가 발생한 DOM 요소,  attr()은 속성 값 관리

      $("input[name=page]").val(page);

      $("#form-search").submit();
  })

  // 검색 버튼 클릭 시
  $(".search-button").click(function(e) {
    let form = $("#form-search"); // 검색 폼
    let page = $("input[name=page]"); // 현재 페이지

    page.val(1); // 클릭 시에 현재 페이지는 1로 돌아간다.
    form.submit(); // 제출
  })
</script>
</body>
</html>