<%@ page pageEncoding="UTF-8"%>
<%@ include file="tags.jsp" %>
<header class="layout header">
    <a href="/home" class="logo">
        <span>WORKUS</span>
    </a>
    <div class="globalMenu">
        <a href="#">
            <i class="bi bi-person-circle"></i>
        </a>
    </div>
</header>

<!-- 모달창 -->
<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close" >&times;</span>
        <div class="modal-header">
            <img src="path/to/profile.jpg" alt="Profile Picture" class="profile-pic">
            <h2 class="member-name">이름을 여기에 넣으세요</h2>
        </div>
        <div class="modal-footer">
            <button class="modal-button" onclick="location.href='/user/myinfo'">내 정보 수정</button>
            <form action="/logout" method="post">
                <button class="modal-button" onclick="location.href='/logout'">로그아웃</button>
            </form>
        </div>
    </div>
</div>

<!-- 모달창 제어 스크립트 -->
<script>

</script>
