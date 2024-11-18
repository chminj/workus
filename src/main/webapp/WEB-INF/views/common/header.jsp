<%@ page pageEncoding="UTF-8"%>
<%@ include file="tags.jsp" %>
<header class="layout header">
    <a href="/home" class="logo">
        <span>WORKUS</span>
    </a>
    <div class="globalMenu">
        <a href="#" onclick="toggleModal(); return false;">
            <i class="bi bi-person-circle"></i>
        </a>
    </div>
</header>

<!-- 모달창 -->
<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
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

<!-- 모달창 스타일 -->
<style>
    .modal {
        display: none;
        position: fixed;
        z-index: 1;
        left: 50%; /* 가운데 정렬 */
        top: 50%; /* 가운데 정렬 */
        transform: translate(-50%, -50%); /* 중앙으로 이동 */
        width: 100%; /* 전체 너비 */
        height: 100%; /* 전체 높이 */
    }

    .modal-content {
        background-color: #ffe6cc; /* 따뜻한 색깔 */
        padding: 20px; /* 패딩 조정 */
        border: 2px solid #ffcc99; /* 명확한 테두리 색 */
        border-radius: 8px; /* 모서리 둥글게 */
        width: 400px; /* 모달 크기 조정 */
        margin: auto; /* 가운데 정렬 */
        position: absolute; /* 절대 위치로 고정 */
        left: 50%; /* 가운데 정렬 */
        top: 50%; /* 가운데 정렬 */
        transform: translate(-50%, -50%); /* 중앙으로 이동 */
        text-align: center; /* 텍스트 중앙 정렬 */
    }

    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
    }

    .close:hover,
    .close:focus {
        color: black;
        text-decoration: none;
        cursor: pointer;
    }

    .profile-pic {
        width: 100px; /* 프로필 사진 크기 조정 */
        height: 100px;
        border-radius: 50%;
        margin-bottom: 10px; /* 사진과 이름 사이 여백 */
    }

    .member-name {
        font-size: 20px; /* 회원 이름 글씨 크기 조정 */
        margin: 10px 0; /* 여백 조정 */
    }

    .modal-footer {
        display: flex;
        justify-content: space-between;
        margin-top: 15px; /* 버튼과 이름 사이 여백 */
    }

    .modal-button {
        font-size: 18px; /* 버튼 글씨 크기 */
        padding: 10px 15px; /* 버튼 패딩 */
        border: none;
        border-radius: 5px;
        background-color: #ffcc99; /* 버튼 배경 색 */
        cursor: pointer;
    }

    .modal-button:hover {
        background-color: #ffb366; /* 버튼 호버 색상 */
    }
</style>

<!-- 모달창 제어 스크립트 -->
<script>
    function toggleModal() {
        var modal = document.getElementById("myModal");
        modal.style.display = modal.style.display === "block" ? "none" : "block";
    }

    function closeModal() {
        document.getElementById("myModal").style.display = "none";
    }

    // 모달창 외부 클릭 시 닫기
    window.onclick = function(event) {
        var modal = document.getElementById("myModal");
        if (event.target === modal) {
            closeModal();
        }
    }
</script>
