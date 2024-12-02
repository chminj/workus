<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../common/tags.jsp" %>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="../common/common.jsp" %>
   <link href="../../../resources/css/communitymain.css" rel="stylesheet"/>\
  <title>workus template</title>
</head>
<body>
<div id="divWrapper">
  <div id="divContents">
    <%@ include file="../common/header.jsp" %>
    <section class="verticalLayoutFixedSection">
      <%@ include file="../common/nav.jsp" %>
      <main class="noLnb">
        <h3 class="title1" ><a href="list">Workus Community</a></h3>
        <div class="content">
          <div class="wrap">
            <form id="form-search" onsubmit="searchKeyword(event)">
                <div class="search">
                  <select name="opt">
                    <option value="title" > 제목</option>
                    <option value="content" > 내용</option>
                    <option value="hashtag" > 해쉬태그</option>
                  </select>
                  <input type="text" name="keyword"  placeholder="검색"/>
                  <button type="button" onclick="searchKeyword(event)">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                      <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                    </svg>
                  </button>
                </div>
            </form>
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
  <%--팝업 창 기본 숨김  --%>
  <div class="popup-overlay" id="popupOverlay">
    <div class="popup">
      <div class="popup-left">
        <img src="" id="postImage" alt="Post Image">
      </div>
      <div class="popup-right">
        <!-- 버튼 -->
        <div class="popup-header">
          <img src="" id="postProfile" alt="Profile Picture" class="profile">
          <div class="username" id="postUsername"></div>
          <div class="more-options" onclick="toggleDropdown()">⋮</div>
          <div class="dropdown-menu" id="dropdownMenu2">
            <div>수정</div>
            <div>삭제</div>
          </div>
          <div class="popup-close" onclick="closePopup()">×</div>
        </div>
        <div class="popup-content">
          <p id="postTitle"><span class="bold"></span></p>
          <p id="postContent"></p>
        </div>
        <div id="tags-popup\${feed.no}" style="margin-left: 30px;"></div>
        <div class="comments-section" id="postReplys"></div>
        <div class="popup-footer">
          <span class="likes" id="likesCount">좋아요 0개</span>
        </div>
        <form method="post" action="insertReply">
          <div class="add-reply">
            <input type="hidden" name="feedNo" id="postFeedNo"/>
            <input type="hidden" name="name" id="postReplyUsername"/>
            <input type="text" name="comment" id="postComment" placeholder="댓글 달기...">
            <button type="button" key onclick="inserReplyPopup(${feed.no})">게시</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
</body>


<%-- 스크립트 시작 --%>
<script>
<%--  무한스크롤 js --%>
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

$(document).ready(function() {
  // 댓글 입력 폼에서 엔터키 눌렀을 때 처리
  $(document).on("keydown", "#feedinsertReply", function(event) {
    if (event.key === "Enter") {
      event.preventDefault();

      var feedNo = $(this).closest("article").attr("id").replace('feed-', ''); // feed-no 추출

      // 댓글 추가 함수 호출 (예: inserReply)
      inserReply(feedNo);
    }
  });
});

$(document).ready(function() {
  $("#postComment").on("keydown", function(event) {
    if (event.key === "Enter") {
      event.preventDefault();
      inserReplyPopup();
    }
  });
});

 // 댓글 추가
 function inserReply(feedNo){
   $.ajax({
     type: "post",
     url: "insertReply",
     data:{
       feedNo:feedNo,
       name:$(`#feed-\${feedNo} input[name=name]`).val(),
       comment:$(`#feed-\${feedNo} input[name=comment]`).val()
     },
     dataType: "json",
     success:function (reply){
       $(`#feed-\${reply.feed.no} .reply-name`).text(reply.user.name);
       $(`#feed-\${reply.feed.no} .reply-content`).text(reply.content);
     }
   })
 }
function inserReplyPopup(){
  $.ajax({
    type: "post",
    url: "insertReply",
    data:{feedNo:$("#postFeedNo").val(),
          comment:$("#postComment").val()},
          name:$("#postReplyUsername").val(),
    dataType: "json",
    success:function (reply){
        let content = `
         <div class="popup-reply">
           <p>
              <strong><span style="margin-right: 10px;">\${reply != null ? reply.user.name : ''} :</span></strong>
              <span>\${reply != null ? reply.content : ''}</span>
           </p>
         <div>
        `;

      $(`#feed-\${reply.feed.no} .reply-name`).text(reply.user.name);
      $(`#feed-\${reply.feed.no} .reply-content`).text(reply.content);
      $("#postReplys").prepend(content);
    }
  })
}

 function openPopup(feedNo) {
   $.ajax({
     type: "get",
     url: `feed/\${feedNo}`,
     dataType: "json",
     success: function (feed) {
       $("#postImage").attr("src", "../../../resources/repository/communityfeedfile/"+feed.mediaUrl);
       $("#postProfile").attr("src", "../../../resources/repository/communityfeedfile/"+feed.mediaUrl);
       $("#postUsername").text(feed.user.name);
       $("#postTitle").text(feed.title);
       $("#postContent").text(feed.content);
       $("#postFeedNo").val(feed.no);


       let replys = feed.replys;
       let content = "";
       for (let reply of replys) {3
         content += `
         <div class="popup-reply">
           <p>
              <strong><span style="margin-right: 10px;">\${reply != null ? reply.user.name : ''} :</span></strong>
              <span>\${reply != null ? reply.content : ''}</span>
           </p>
         <div>
        `;
       }
       $("#postReplys").html(content);

       document.getElementById("popupOverlay").style.display = "flex";
     }
   })
 }

  function closePopup() {
  document.getElementById("popupOverlay").style.display = "none";
  }

  getFeeds(currentPage);

  function getFeeds(page) {
    let data = {
      page: page
    };

    let opt = $("select[name=opt]").val();
    let keyword = $("input[name=keyword]").val();

    if (opt != "" && keyword != "") {
      data["opt"] = opt;
      data["keyword"] = keyword;
    }

    $.ajax({
      type: "get",
      url: "items",
      data: data,
      dataType: "json",
      success: function (items) {
        appendFeeds(items);
        currentPage++;
        canRequest = true;
      }
    })
  }
  function  searchKeyword(event){
    event.preventDefault();
    currentPage = 1;
    $.ajax({
      type:"get",
      url:"items",
      data:{
        page: currentPage,
        opt: $("select[name=opt]").val(),
        keyword: $("input[name=keyword]").val()
      },
      dataType:"json",
      success:function (feeds){
        currentPage++;
        $("div.feed_board").empty();

        appendFeeds(feeds);

      }
    })
}

  function appendFeeds(items) {
    for (let feed of items) {

      let content = `
      <!--FEED COMPONENT-->
      <article class="feed" id="feed-\${feed.no}">
        <!--top-->
        <div class="new_poster">
          <div class="poster_img">
            <img alt="follower profile image" class="round_img" src="../../../resources/repository/communityfeedfile/\${feed.mediaUrl}" />
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
          <img alt="지정된 media가 없습니다" src="../../../resources/repository/communityfeedfile/\${feed.mediaUrl}" />
          <div class="interactions">
            <div class="my_emotion">
              <!-- 피드 내 하트 버튼 -->
              <span class="like-btn" id="likeBtn" onclick="toggleLike()">
                          <i class="bi bi-heart large-heart"></i> <!-- 기본 빈 하트 아이콘 -->
                      </span>
              </button></span>
              <button class="open-popup-btn" onclick="openPopup(\${feed.no})">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-chat" viewBox="0 0 16 16">
                  <path d="M2.678 11.894a1 1 0 0 1 .287.801 11 11 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8
                  8 0 0 0 8 14c3.996 0 7-2.807 7-6s-3.004-6-7-6-7 2.808-7 6c0 1.468.617 2.83 1.678 3.894m-.493 3.905a22 22 0 0 1-.713.129c-
                  .2.032-.352-.176-.273-.362a10 10 0 0 0 .244-.637l.003-.01c.2fffffffffffffffffff48-.
                  72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9 9 0 0 1-2.347-.306c-.
                  52.263-1.639.742-3.468 1.105"/>
                </svg>
              </button>
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
            <p>\${feed.content}></p>
            <div id="tags-\${feed.no}" style="margin: 10px 0;"></div>
        </section>


        <!--comment-->
        <div class="comments">
          <div id="listComment" class="list_comment">
            <div class="txt_comment">
            <p class="reply-name">\${feed.reply != null ? feed.reply.user.name : ''}</p>
            <p class="reply-content">\${feed.reply != null ? feed.reply.content : ''}</p>
            </div>

            <form method="post" action="insertReply">
                <div class="add-reply">
                <input type="hidden" name="feedNo" value="">
                <input type="hidden" name="name">
                <input type="text" name="comment" id="feedinsertReply" placeholder="댓글 달기...">
                <button  type="button" onclick="inserReply(\${feed.no})">게시</button>
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

      $("#tags-" + feed.no).append(tags);
      $("#tags-popup" + feed.no).append(tags);

    }
  }
</script>
</html>