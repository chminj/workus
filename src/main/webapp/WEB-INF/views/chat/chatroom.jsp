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
</head>
<body>
	<div id="divWrapper">
		<div id="divContents">
			<%@ include file="../common/header.jsp" %>
			<section class="verticalLayoutFixedSection">
				<%@ include file="../common/nav.jsp" %>
				<main class="noLnb">
					<div class="container-fluid vh-100">
						<div class="row h-100">
							<!-- 채팅방 목록 -->
							<div class="col-4 border-end p-0" style="overflow-y: auto;">
							  <!-- 채팅방 생성 버튼 -->
								<div class="border-bottom p-3">
								<button class="btn btn-primary w-100" data-bs-toggle="modal" data-bs-target="#createChatroomModal">
								  <i class="bi bi-plus-circle me-2"></i>새 채팅방 만들기
								</button>
								</div>
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
										<c:if test="${chatroom.notReadCount != 0}">
										<span class="badge bg-primary rounded-pill not-read-count${chatroom.chatroomNo}">${chatroom.notReadCount}</span>
										</c:if>
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
								<h5 class="fw-bold" id="profileName"></h5>
							</div>
							<div class="mb-2">
								<span class="text-muted">직책:</span>
								<span id="profilePosition"></span>
							</div>
							<div class="mb-2">
								<span class="text-muted">부서:</span>
								<span id="profileDept"></span>
							</div>
							<div class="mb-2">
								<span class="text-muted">이메일:</span>
								<span id="profileEmail"></span>
							</div>
							<div class="mt-3">
								<span class="text-muted">한 줄 소개:</span>
								<p class="mt-1" id="profilePr"></p>
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

<!-- 채팅방 생성 모달 -->
<div class="modal fade" id="createChatroomModal" tabindex="-1" aria-labelledby="createChatroomModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-md">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="createChatroomModalLabel">새 채팅방 만들기</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form id="createChatroomForm">
          <div class="mb-4">
            <label for="chatroomTitle" class="form-label">채팅방 제목</label>
            <input type="text" class="form-control" id="chatroomTitle" required>
          </div>

          <div class="mb-3">
            <label class="form-label">참여자 선택</label>
            <input type="text" class="form-control mb-3" id="userSearch" placeholder="이름 검색">

            <!-- 부서별 사용자 목록 -->
            <div class="border rounded p-3" style="height: 300px; overflow-y: auto;">
            	<%-- 부서 --%>
				<c:forEach var="dept" items="${depts}" varStatus="deptStatus">
                <div class="department-group mb-3">
                  <div class="form-check">
                    <input class="form-check-input dept-check" type="checkbox" id="dept${dept.deptNo}" data-dept="${dept.deptNo}">
                    <button class="btn btn-link p-0 text-decoration-none" type="button"
                        data-bs-toggle="collapse"
                        data-bs-target="#deptCollapse${dept.deptNo}"
                        aria-expanded="true">
                        ${dept.deptName}
					</button>
                  </div>
                  <div class="collapse ms-4 mt-2" id="deptCollapse${dept.deptNo}">
                  	<%-- 부서에 속한 유저 --%>
					<c:forEach var="map" items="${participantInfos[deptStatus.index]}">
					<c:forEach var="participantInfo" items="${map.value}">
					<div class="form-check mb-2">
						<input class="form-check-input user-check" type="checkbox" value="${participantInfo.userNo}" id="createChatroomParticipant${participantInfo.userNo}" data-dept="${dept.deptNo}">
						  <span class="text-muted">[${participantInfo.positionName}]</span> ${participantInfo.userName}
					</div>
					</c:forEach>
					</c:forEach>
                  </div>
                </div>
				</c:forEach>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
        <button type="submit" form="createChatroomForm" class="btn btn-primary">생성</button>
      </div>
    </div>
  </div>
</div>
	<script>
		const LOGIN_USERNO = ${LOGIN_USERNO};
		// 모달 객체 생성
		const profileModal = new bootstrap.Modal('#profileModal');
		const createChatroomModal = new bootstrap.Modal('#createChatroomModal');

		// 부서 체크박스 체크와 해제 이벤트
		$('.dept-check').change(function() {
			if ($('.dept-check').is(':checked')) {
				let deptNo = $(this).data('dept');
				console.log('deptNo: ', deptNo);
				$(`[data-dept=\${deptNo}]`).prop("checked", true);
			} else {
				let deptNo = $(this).data('dept');
				console.log('deptNo: ', deptNo);
				$(`[data-dept=\${deptNo}]`).prop("checked", false);
			}
		})

		// 페이지 변화 시 연결 시간 업데이트
		$(document).on('visibilitychange', async function() {
			const chatroomNo = $('.chat-text').data('chatroomNo');
			try {
				await fetch('/ajax/chatroom/' + chatroomNo, {
					method: 'PUT'
				})
			} catch (error) {
				console.log('페이지 변화에 대한 이벤트 에러가 발생했습니다.', error);
			}
		})

		// 프로필 모달 등장
		$('#chat').on('click', '.participant-name', async function() {
			try {
				// 프로필 모달 열기
				profileModal.show();
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

		// 채팅 전송 후 화면에 내 채팅 보이기
		function appearSubmittedMyChat(data) {
			return `
					\${data.isFirst === 'Y' ? `
						<!-- 날짜 구분선 -->
						<div class="d-flex align-items-center my-4">
							<div class="border-bottom flex-grow-1"></div>
							<span class="mx-3 text-muted">\${data.time.date}</span>
							<div class="border-bottom flex-grow-1"></div>
						</div>
					` : ``}
					<div class="mb-3 w-75 ms-auto">
						<div class="text-end mb-1">
							<strong>나</strong>
						</div>
						<div class="d-flex justify-content-end">
							<div class="bg-primary text-white rounded p-2 mb-1">
								\${data.content}
							</div>
						</div>
						<div class="text-end">
							<small class="text-muted">\${data.time.time}</small>
						</div>
					</div>
			`;
		}

		// 채팅 전송
		$('#chat').on('click', '#submitChat', async function() {
			try {
				// closest: 가장 가까운 form을 찾는다.
				// [0]: jquery객체를 벗어던지고 일반 DOM요소로 반환한다.
				const form = $(this).closest('form')[0];
				const formData = new FormData(form);
				const chatroomNo = $('.chat-text').data('chatroomNo');
				const response = await fetch(`/ajax/chat/\${chatroomNo}`, {
					method: 'POST',
					body: formData
				});
				const result = await response.json();
				const data = await result.data;
				if (response.ok) {
					replaceFormatTime(data);
					const div = appearSubmittedMyChat(data);
					$('.chat-div').append(div);
					$('input[name=content]').val('');
				} else {
					console.log('채팅이 정상적으로 전송되지 않았습니다.');
				}
			} catch (error) {
				console.log('에러메시지', error);
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
			data.time = formatTime(data.time);
		}

		// 채팅방 입장
		$(".chatroom").on('click', async function () {
			// data-chatroom-no 속성 가져오기
			const chatroomNo = $(this).data("chatroomNo");
			// 읽지 않은 메시지 수 화면에서 삭제
			$(`.not-read-count\${chatroomNo}`).text("");
			const titleAndUsersDiv = await ajaxTitleAndUsersData(chatroomNo);
			const chatDiv = await ajaxChatsData(chatroomNo);
			const chatSubmitForm = loadSubmitChatForm(chatroomNo);
			const div = titleAndUsersDiv + chatDiv + chatSubmitForm;
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
					data.map(item => replaceFormatTime(item));
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
					<div class="flex-grow-1 p-3 chat-div" style="overflow-y: auto;">
						\${data.map((chat) => `
							\${chat.isFirst === 'Y' ? `
								<!-- 날짜 구분선 -->
								<div class="d-flex align-items-center my-4">
									<div class="border-bottom flex-grow-1"></div>
									<span class="mx-3 text-muted">\${chat.time.date}</span>
									<div class="border-bottom flex-grow-1"></div>
								</div>
							` : ``}
							\${LOGIN_USERNO === chat.user.no ? `
								<div class="mb-3 w-75 ms-auto">
									<div class="text-end mb-1">
										<strong>나</strong>
									</div>
									<div class="d-flex justify-content-end">
										<div class="bg-primary text-white rounded p-2 mb-1">
											\${chat.content}
										</div>
									</div>
									<div class="text-end">
										<small class="text-muted">\${chat.time.time}</small>
									</div>
								</div>
							` : `
							<!-- 상대 메시지 -->
							<div class="mb-3 w-75">
								<div class="d-flex align-items-center mb-1">
									<img src="" alt="프로필" class="rounded-circle me-2"/>
									<strong>\${chat.user.name}</strong>
								</div>
								<div class="d-flex">
									<div class="bg-light rounded p-2 mb-1">
										\${chat.content}
									</div>
								</div>
								<small class="text-muted">\${chat.time.time}</small>
							</div>
							`}
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
				`;
		}

		// 메시지 입력 폼 영역 불러오기
		function loadSubmitChatForm(chatroomNo) {
			return `
					<!-- 메시지 입력 영역 -->
					<form action="ajax/chat" method="post" encType="multipart/form-data">
						<div class="border-top p-3">
							<div class="input-group mb-3">
								<input type="file" class="form-control" id="fileInput" name="file" />
							</div>
							<div class="input-group chat-text" data-chatroom-no="\${chatroomNo}">
								<input type="text" class="form-control" name="content" required placeholder="메시지를 입력하세요." />
								<button type="button" id="submitChat" class="btn btn-primary">전송</button>
							</div>
						</div>
					</form>
			`;
		}
	</script>
</body>
</html>