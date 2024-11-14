<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../common/tags.jsp" %>
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="../common/common.jsp" %>
<title>채팅방</title>
<style>
	main {
		padding: 20px 25px;
		width: calc(100% - 76px);
		margin-left: 45px;
	}
</style>
</head>
<body>
	<div id="divWrapper">
		<div id="divContents">
			<%@ include file="../common/header.jsp" %>
			<section class="verticalLayoutFixedSection">
				<%@ include file="../common/nav.jsp" %>
				<main>
					<div class="container-fluid vh-100">
						<div class="row h-100">
							<!-- 채팅방 목록 -->
							<div class="col-4 border-end p-0 overflow-auto">
								<!-- 채팅방 -->
								<c:forEach var="chatroom" items="${chatrooms}">
								<div class="border-bottom p-3 chatroom" role="button" data-chatroom-no="${chatroom.chatroomNo}">
									<div class="d-flex justify-content-between align-items-start">
										<h6 class="mb-1">${chatroom.chatroomTitle}</h6>
										<small class="text-muted"><fmt:formatDate value="${chatroom.lastChatDate}" pattern="yyyy년 MM월 dd시 HH시 mm분" /></small>
									</div>
									<p class="mb-1 text-muted">${chatroom.lastChatAuthor.name}</p>
									<div class="d-flex justify-content-between align-items-center">
										<small class="text-muted text-truncate me-2">${chatroom.lastChat}</small>
										<span class="badge bg-primary rounded-pill">읽지 않은 메시지 수</span>
									</div>
								</div>
								</c:forEach>
							</div>

							<div class="col-8 p-0 d-flex flex-column h-100" id="chat">

							</div>
						</div>
					</div>
				</main>
			</section>
		</div>
	</div>

	<!-- 프로필 모달 -->
	<div class="modal fade" id="profileModal" tabindex="-1" aria-labelledby="profileModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="profileModalLabel">프로필 정보</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="row">
						<!-- 왼쪽 프로필 사진 영역 -->
						<div class="col-4 text-center">
							<i class="bi bi-person-circle fs-1"></i>
						</div>
						<!-- 오른쪽 정보 영역 -->
						<div class="col-8">
							<div class="mb-3">
								<h5 class="fw-bold" id="profileName">홍길동</h5>
							</div>
							<div class="mb-2">
								<span class="text-muted">직책:</span>
								<span id="profilePosition">책임연구원</span>
							</div>
							<div class="mb-2">
								<span class="text-muted">부서:</span>
								<span id="profileDept">AI연구소</span>
							</div>
							<div class="mb-2">
								<span class="text-muted">이메일:</span>
								<span id="profileEmail">hong@example.com</span>
							</div>
							<div class="mt-3">
								<span class="text-muted">한 줄 소개:</span>
								<p class="mt-1" id="profilePr">AI 기술 연구 및 개발을 담당하고 있습니다. 새로운 기술에 대한 열정이 넘치며 팀워크를 중요시합니다.</p>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary btn-warning" data-bs-dismiss="modal">수정</button>
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>
	<script>
		// 모달 객체 생성
		const myModal = new bootstrap.Modal('#profileModal');

		// 모달 등장
		$('#chat').on('click', '.participant-name', async function() {
			try {
				// 모달 열기
				myModal.show();
				const userNo = $(this).data("userNo");

				// ajax로 이름을 클릭하면 data-user-no의 값으로 유저 정보들을 가져온다.
				const response = await fetch('/ajax/chat/profile/' + userNo);
				const result = await response.json();
				const data = await result.data;

				// 가져온 정보들을 집어넣는다.
				if (response.ok) {
					$('#profileName').text(data.name);
					$('#profilePosition').text(data.positionName);
					$('#profileDept').text(data.deptName);
					$('#profileEmail').text(data.email);
					$('#profilePr').text(data.pr);
				} else {
					console.log('프로필 불러오기 실패', data.message);
				}
			} catch (error) {
				console.log('에러 메시지', error);
			}
		});

		// 날짜와 시간 포맷
		function formatTime(unformattedTime) {
			const time = new Date(unformattedTime);

			const year = time.getFullYear();
			const month = time.getMonth() + 1;
			const day = time.getDate();

			let hours = time.getHours();
			const minutes = time.getMinutes();
			const ampm = hours >= 12 ? '오후' : '오전';
			hours = hours >= 12 ? hours - 12 : hours;

			const timeStr = `\${ampm} \${hours}시 \${minutes}분`;
			const dateStr = `\${year}년 \${month}월 \${day}일`;

			return {
				time: timeStr,
				date: dateStr
			};
		}

		// data의 기존의 date타입 대신 포맷된 date타입을 집어넣는다.
		function replaceFormatTime(data) {
			data.forEach(data => {
				data.time = formatTime(data.time);
			})
		}

		// 채팅방 입장
		$(".chatroom").on('click', async function () {
			// data-chatroom-no 속성 가져오기
			const chatroomNo = $(this).data("chatroomNo");
			const titleAndUsersDiv = await ajaxTitleAndUsersData(chatroomNo);
			const chatDiv = await ajaxChatsData(chatroomNo);
			const div = titleAndUsersDiv + chatDiv;
			await $('#chat').html(div);
		});

		// 채팅방 제목과 참여중인 유저들을 ajax로 불러온다.
		async function ajaxTitleAndUsersData(chatroomNo) {
			try {
				const response = await fetch('/ajax/chatroom/' + chatroomNo);
				const result = await response.json();
				const data = await result.data;
				if (response.ok) {
					return loadTitleAndUsers(data);
				} else {
					console.log(data.message);
				}
			} catch (error) {
				console.log('에러메시지', error);
			}
		}

		// 채팅방에 해당하는 채팅들을 ajax로 불러온다.
		async function ajaxChatsData(chatroomNo) {
			try {
				const response = await fetch('/ajax/chat/' + chatroomNo);
				const result = await response.json();
				const data = result.data;
				if (response.ok) {
					replaceFormatTime(data);
					return loadChats(data);
				} else {
					console.log('채팅을 불러오는데 실패했습니다.', result.message);
				}
			} catch (error) {
				console.log('에러메시지', error);
			}
		}

		// 채팅방 제목과 참여자 목록 불러오기
		function loadTitleAndUsers(data) {
			return `
					<!-- 채팅방 헤더 -->
					<div class="border-bottom p-3 bg-light d-flex justify-content-between align-items-center">
						<h5 class="mb-0">\${data.chatroomTitle}</h5>
						<div class="dropdown">
							<button class="btn btn-light" data-bs-toggle="dropdown">
								<i class="fas fa-users"></i>
							</button>
							<!-- 참여자 목록 드롭다운 -->
							<ul class="dropdown-menu dropdown-menu-end p-3" style="width: 250px;">
								<li><h6 class="border-bottom pb-2">참여자 목록</h6></li>
								<li>
									\${data.users.map((user) => `
										<div class="d-flex align-items-center my-2">
										<img src="" alt="프로필" class="rounded-circle me-2">
										<span role="button" class="participant-name" data-user-no="\${user.no}">\${user.name}</span>
										</div>
									`).join('')}
								</li>
								<!-- 추가 참여자들... -->
							</ul>
						</div>
					</div>
			`;
		}

		// 클릭한 채팅방에 맞는 채팅들 불러오기
		function loadChats(data) {
			return `
					<!-- 채팅 내용 영역 -->
					<div class="flex-grow-1 overflow-auto p-3">
						<!-- 날짜 구분선 -->
						<div class="d-flex align-items-center my-4">
							<div class="border-bottom flex-grow-1"></div>
							<span class="mx-3 text-muted">2024년 3월 7일</span>
							<div class="border-bottom flex-grow-1"></div>
						</div>

						\${data.map((d) => `
							<div class="mb-3 w-75">
								<div class="d-flex align-items-center mb-1">
									<img src="" alt="프로필" class="rounded-circle me-2">
									<strong>\${d.user.name}</strong>
								</div>
								<div class="d-flex">
									<div class="bg-light rounded p-2 mb-1">
										\${d.content}
									</div>
								</div>
								<small class="text-muted">\${d.time.time}</small>
							</div>
						`).join('')}

						<!-- 상대방 메시지
						<div class="mb-3 w-75">
							<div class="d-flex align-items-center mb-1">
								<img src="" alt="프로필" class="rounded-circle me-2">
								<strong>홍길동</strong>
							</div>
							<div class="d-flex">
								<div class="bg-light rounded p-2 mb-1">
									안녕하세요!
								</div>
							</div>
							<small class="text-muted">오전 10:30</small>
						</div>
						-->

						<!-- 내 메시지
						<div class="mb-3 w-75 ms-auto">
							<div class="text-end mb-1">
								<strong>나</strong>
							</div>
							<div class="d-flex justify-content-end">
								<div class="bg-primary text-white rounded p-2 mb-1">
									네, 안녕하세요!
								</div>
							</div>
							<div class="text-end">
								<small class="text-muted">오전 10:31</small>
							</div>
						</div>
						-->
					</div>

					<!-- 메시지 입력 영역 -->
					<div class="border-top p-3">
						<div class="input-group">
							<input type="text" class="form-control" placeholder="메시지를 입력하세요.">
							<button class="btn btn-primary">전송</button>
						</div>
					</div>
				`;
		}
	</script>
</body>
</html>