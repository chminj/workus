<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../common/tags.jsp" %>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="../common/common.jsp" %>
  <link href="../../../resources/css/communitymain.css" rel="stylesheet"/>
  <title>workus template</title>
</head>
<body>
<div id="divWrapper">
  <div id="divContents">
    <%@ include file="../common/header.jsp" %>
    <section class="verticalLayoutFixedSection">
      <%@ include file="../common/nav.jsp" %>
      <main class="noLnb">
        <h3 class="title1">Workus Community</h3>
        <div class="content">
          <div class="wrap">
            <div class="search">
              <input type="text" placeholder="검색"/>
              <button>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                  <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                </svg>
              </button>
            </div>
            <div>
              <button type="button" class="btn btn-outline-dark"><a href="form">글 작성</a></button>
            </div>

          </div>
          <!---MAIN--->
          <main class="container wrap">
            <!--FEED BOARD-->
            <div class="feed_board"></div>

            <!--//FEED BOARD-->
          </main>
        </div>
      </main>
    </section>
  </div>
</div>
</body>
<script>
  /*
    let isLiked = false; // 좋아요 상태 초기화
    let likes = 0; // 초기 좋아요 수

    const toggle = document.getElementById('dropdownToggle'); // 드롭다운 토글 요소
    const menu1 = document.getElementById('dropdownMenu1'); // 첫 번째 드롭다운 메뉴
    const menu2 = document.getElementById('dropdownMenu2'); // 두 번째 드롭다운 메뉴

    // 드롭다운 토글 클릭 시 첫 번째 메뉴 표시/숨기기
    toggle.addEventListener('click', (event) => {
      event.stopPropagation(); // 클릭 이벤트 전파 방지
      menu1.classList.toggle('show'); // 첫 번째 드롭다운 메뉴 표시/숨기기
    });

    // 클릭 외부 시 드롭다운 닫기
    window.addEventListener('click', (event) => {
      if (!toggle.contains(event.target) && !menu1.contains(event.target)) {
        menu1.classList.remove('show'); // 첫 번째 드롭다운 숨기기
      }
    });

    // 팝업 헤더의 더 많은 옵션 클릭 시 두 번째 드롭다운 메뉴 표시/숨기기
    function toggleDropdown() {
      menu2.classList.toggle('show'); // 두 번째 드롭다운 메뉴 표시/숨기기
    }

    // 클릭 외부 시 두 번째 드롭다운 닫기
    window.addEventListener('click', (event) => {
      if (!document.querySelector('.more-options').contains(event.target) && !menu2.contains(event.target)) {
        menu2.classList.remove('show'); // 두 번째 드롭다운 숨기기
      }
    });

   */

  //팝업 열기
  //  function openPopup() {
  //    document.getElementById("popupOverlay").style.display = "flex";
  //  }
  //
  // //팝업 닫기
  //  function closePopup() {
  //    document.getElementById("popupOverlay").style.display = "none";
  //  }
  /*
    // 좋아요 토글
    function toggleLike() {
      isLiked = !isLiked; // 좋아요 상태 토글
      updateLikeButton(); // 버튼 업데이트
    }

    function updateLikeButton() {
      const likeBtn = document.getElementById("likeBtn");
      const popupLikeBtn = document.querySelector('.popup-footer .like-btn'); // 팝업 내 좋아요 버튼
      const likesCount = document.getElementById("likesCount");

      if (isLiked) {
        likeBtn.innerHTML = '<i class="bi bi-heart-fill" style="color: red;"></i>'; // 채워진 하트
        popupLikeBtn.innerHTML = '<i class="bi bi-heart-fill" style="color: red;"></i>'; // 팝업 내 버튼도 채워진 하트
        likes++;
      } else {
        likeBtn.innerHTML = '<i class="bi bi-heart"></i>'; // 빈 하트
        popupLikeBtn.innerHTML = '<i class="bi bi-heart"></i>'; // 팝업 내 버튼도 빈 하트
        likes--;
      }
      likesCount.textContent = `좋아요 ${likes}개`;
  }

  // 좋아요 버튼 업데이트
  function updateLikeButton() {
    const likeBtn = document.getElementById("likeBtn"); // 피드 내 버튼
    const likesCount = document.getElementById("likesCount");
    const popupLikeBtn = document.getElementById("popupLikeBtn"); // 팝업 내 버튼

    if (isLiked) {
      likeBtn.innerHTML = '<i class="bi bi-heart-fill large-heart" style="color: red;"></i>'; // 피드 내 채워진 하트
      popupLikeBtn.innerHTML = '<i class="bi bi-heart-fill small-heart" style="color: red;"></i>'; // 팝업 내 채워진 하트
      likes++;
    } else {
      likeBtn.innerHTML = '<i class="bi bi-heart large-heart"></i>'; // 피드 내 빈 하트
      popupLikeBtn.innerHTML = '<i class="bi bi-heart small-heart"></i>'; // 팝업 내 빈 하트
      likes--;
    }
    likesCount.textContent = `좋아요 ${likes}개`;
  }

  // 댓글 추가
  function addComment() {
    const commentInput = document.getElementById("commentInput"); // 입력 필드
    const commentText = commentInput.value.trim(); // 입력된 댓글 텍스트
    if (commentText) {
      const commentSection = document.querySelector('.post-comments'); // 댓글이 추가될 영역
      const comment = document.createElement('div'); // 새로운 댓글 요소 생성
      comment.innerHTML = `<img src="profile.jpg" alt="Profile Image"> <span>${commentText}</span>`; // 댓글 내용
      commentSection.appendChild(comment); // 댓글 추가
      commentInput.value = ""; // 입력 필드 초기화
    }
  }
  */

  let currentPage = 1;
  let canRequest = true;

  $(window).scroll(function() {
    let windowHeight = $(window).height();
    let documentHeight = $(document).height();
    let scrollHeight = $(window).scrollTop();

    if (windowHeight + scrollHeight + 100 > documentHeight) {
      if (canRequest) {
        canRequest = false;
        getFeeds(currentPage);
      }
    }
  });

  getFeeds(currentPage);

  function getFeeds(page) {
    $.ajax({
      type: "get",
      url: "items?page=" + page,
      dataType: "json",
      success: function (items) {
        appendFeeds(items);
        currentPage++;
        canRequest = true;
      }
    })
  }

  function appendFeeds(items) {
    for (let feed of items) {

      let content = `
      <!--FEED COMPONENT-->
      <article class="feed">
        <!--top-->
        <div class="new_poster">
          <div class="poster_img">
            <img alt="follower profile image" class="round_img" src="../../../resources/images/\${feed.mediaUrl}" />
          </div>
          <a href="#n" class="poster_id txt_id">\${feed.user.name}</a>
          <div class="dropdown-container">
            <svg class="x1lliihq x1n2onr6 x5n08af" fill="currentColor" height="24" role="img" viewBox="0 0 24 24" width="24" id="dropdownToggle">
              <circle cx="12" cy="12" r="1.5"></circle>
              <circle cx="6" cy="12" r="1.5"></circle>
              <circle cx="18" cy="12" r="1.5"></circle>
            </svg>
            <div class="dropdown" id="dropdownMenu1">
              <div class="dropdown-item">수정</div>
              <div class="dropdown-item">삭제</div>
            </div>
          </div>
        </div>

        <!--content-->
        <section class="feed_imgs">
          <img alt="지정된 media가 없습니다" src="../../../resources/images/\${feed.mediaUrl}" />
          <div class="interactions">
            <div class="my_emotion">
              <!-- 피드 내 하트 버튼 -->
              <span class="like-btn" id="likeBtn" onclick="toggleLike()">
                          <i class="bi bi-heart large-heart"></i> <!-- 기본 빈 하트 아이콘 -->
                      </span>
              </button></span>
              <button class="open-popup-btn" onclick="openPopup()">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-chat" viewBox="0 0 16 16">
                  <path d="M2.678 11.894a1 1 0 0 1 .287.801 11 11 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8
                  8 0 0 0 8 14c3.996 0 7-2.807 7-6s-3.004-6-7-6-7 2.808-7 6c0 1.468.617 2.83 1.678 3.894m-.493 3.905a22 22 0 0 1-.713.129c-
                  .2.032-.352-.176-.273-.362a10 10 0 0 0 .244-.637l.003-.01c.248-.
                  72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9 9 0 0 1-2.347-.306c-.
                  52.263-1.639.742-3.468 1.105"/>
                </svg>
              </button>
              <div class="popup-overlay" id="popupOverlay">
                <div class="popup">
                  <div class="popup-left">
                    <img src="../../../resources/images/\${feed.mediaUrl}" alt="Post Image">
                  </div>
                  <div class="popup-right">
                    <!-- 버튼 -->
                    <div class="popup-header">
                      <img src="../../../resources/images/\${feed.mediaUrl}" alt="Profile Picture" class="profile">
                      <div class="username">\${feed.user.name}</div>
                      <div class="more-options" onclick="toggleDropdown()">⋮</div>
                      <div class="dropdown-menu" id="dropdownMenu2">
                        <div>수정</div>
                        <div>삭제</div>
                      </div>
                      <div class="popup-close" onclick="closePopup()">×</div>
                    </div>
                    <div class="popup-content">
                      <p><span class="bold">\${feed.title}</span></p>
                      <p>\${feed.content}}</p>
                    </div>
                    <div id="tags-popup\${feed.no}" style="margin-left: 30px;"></div>
                    <div class="comments-section" id="commentsSection"></div>
                    <!-- 팝업 내 하트 버튼 -->
                    <div class="popup-footer">
                      <span class="likes" id="likesCount">좋아요 0개</span>
                      <span class="like-btn" id="popupLikeBtn" onclick="toggleLike()">
                  <i class="bi bi-heart small-heart"></i> <!-- 팝업 내 하트 아이콘 -->
              </span>
                    </div>
                    <form>
                      <div class="add-comment">
                      <input type="text" id="commentInput" placeholder="댓글 달기...">
                        <button  onclick="addComment()">게시</button>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <p>
              <a href="#n" class="like_user">
                <img alt="like user image" class="round_img" src="../../../resources/images/\${feed.mediaUrl}" />
                <span class="txt_id">좋아요누른 유저이름</span>
              </a>
              님
              <a href="#n" class="like_num txt_id">외 [게시글 좋아요 수]명</a>
              이 좋아합니다
            </p>
          </div>
        </section>

        <!--feed text-->
        <section class="feed_txt">
            <div><strong>\${feed.title}</strong><div>
            <p>\${feed.content}<a href="#n" class="more">더보기</a></p>
            <div id="tags-\${feed.no}" style="margin: 10px 0;"></div>
        </section>


        <!--comment-->
        <div class="comments">
          <div id="listComment" class="list_comment">
            <p class="txt_comment">
                      <span>
                          <a href="#n" class="txt_id"></a>
                          <span>댓글입니다 댓글입니다 !</span>
                      </span>
              <button id="delete" type="button">댓글삭제</button>
            </p>
            <form method="post" action="insertComment">
            <div class="add-comment">
              <input type="hidden" name="feedNo" value="\${feed.no}">
              <input type="text" name="comment" placeholder="댓글 달기...">
              <button  type="submit">게시</button>
            </div>
          </form>
        </div>

      </article>
    `;

      $("div.feed_board").append(content);

      let tags = "";
      for (let tag of feed.hashTags) {
        tags += `
         <a href=""><span style=" color: #3a9cfa; padding: 1px 1px; margin-right: 3px;">\${tag.name}</span></a>
        `
      }
      console.log(tags);

      $("#tags-" + feed.no).append(tags);
      $("#tags-popup" + feed.no).append(tags);

    }
  }
</script>
</html>