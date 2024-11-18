function toggleModal() {
    const modal = document.getElementById('modal');
    modal.style.display = modal.style.display === 'block' ? 'none' : 'block';
}

// 계정 추가 및 로그아웃 함수
function addAccount() {
    alert('계정 추가 기능을 구현하세요.');
    toggleModal();
}

function logout() {
    alert('로그아웃 기능을 구현하세요.');
    toggleModal();
}

// 모달 외부 클릭 시 닫기
window.onclick = function(event) {
    const modal = document.getElementById('modal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
}