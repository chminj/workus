<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>게시물 작성 | Workus Community</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
  <link href="../../../resources/css/communitymain.css" rel="stylesheet"/>
  <%@ include file="../home.jsp" %>
</head>
<body>

  <div class="container">
    <div class="left-section">
      <!-- 미리보기 영역 -->
      <div class="preview-section" id="previewSection">
        <div class="preview-content">
          <div class="file-preview" id="filePreview"></div>
          <div class="preview-text" id="previewText"></div>
          <div class="preview-hashtags" id="previewHashtags"></div>
        </div>
      </div>
    </div>

    <div class="right-section">
      <div class="header">새 게시물 작성</div>
      <form id="postForm" enctype="multipart/form-data">
        
        <!-- 내용 입력 -->
        <div class="form-group">
          <textarea id="postContent" rows="5" placeholder="게시물 내용을 작성하세요" required></textarea>
        </div>

        <!-- 해시태그 입력 -->
        <div class="form-group">
          <input type="text" id="postHashtags" placeholder="예: #여행 #개발 #인스타그램">
        </div>

        <!-- 이미지/동영상 업로드 -->
        <div class="form-group file-input-wrapper">
          <input type="file" id="postFiles" accept="image/*, video/*" multiple>
        </div>

        <!-- 게시 버튼 -->
        <button type="submit" class="btn-post">게시하기</button>
        <button type="button" class="btn-cancel" onclick="cancelPost()">취소</button>
      </form>
    </div>
  </div>

  <script>
    // 파일 미리보기 기능
    document.getElementById('postFiles').addEventListener('change', function(event) {
      const files = event.target.files;
      const previewContainer = document.getElementById('filePreview');
      previewContainer.innerHTML = ''; // 기존 미리보기 내용 초기화

      for (const file of files) {
        const reader = new FileReader();
        const fileType = file.type.split('/')[0]; // 'image' 또는 'video'

        reader.onload = function(e) {
          const fileUrl = e.target.result;
          const fileElement = document.createElement(fileType === 'image' ? 'img' : 'video');
          
          fileElement.src = fileUrl;
          fileElement.classList.add('file-preview-item');
          fileElement.controls = (fileType === 'video'); // 비디오일 경우 컨트롤러 표시

          previewContainer.appendChild(fileElement);
        };

        reader.readAsDataURL(file); // 파일을 base64로 읽어오기
      }

      // 파일이 있으면 미리보기 내용의 위치 조정
      updatePreviewLayout();
    });

    // 게시물 미리보기 업데이트
    document.getElementById('postForm').addEventListener('input', function() {
      const content = document.getElementById('postContent').value;
      const hashtags = document.getElementById('postHashtags').value;
      
      // 미리보기 업데이트
      document.getElementById('previewText').innerText = content;
      document.getElementById('previewHashtags').innerHTML = hashtags.split(' ').map(tag => `<span class="hashtag">${tag}</span>`).join(' ');

      // 미리보기 레이아웃 업데이트
      updatePreviewLayout();
    });

    // 미리보기 레이아웃 업데이트
    function updatePreviewLayout() {
      const previewText = document.getElementById('previewText').innerText;
      const previewContainer = document.getElementById('filePreview');

      if (previewContainer.children.length > 0) {
        // 이미지나 비디오가 있으면, 내용이 아래로 배치
        document.getElementById('previewText').style.marginTop = '20px';
      } else {
        // 이미지나 비디오가 없으면, 내용이 바로 위에 배치
        document.getElementById('previewText').style.marginTop = '0px';
      }
    }

    // 폼 제출 시 동작 (예시로 alert만 추가)
    document.getElementById('postForm').addEventListener('submit', function(event) {
      event.preventDefault(); // 폼 제출 기본 동작 방지
      alert('게시물이 작성되었습니다!');
    });

    // 취소 버튼 클릭 시 동작
    function cancelPost() {
      if (confirm('게시물을 취소하시겠습니까?')) {
        document.getElementById('postForm').reset(); // 폼 리셋
        document.getElementById('filePreview').innerHTML = ''; // 미리보기 초기화
        document.getElementById('previewText').innerText = ''; // 내용 초기화
        document.getElementById('previewHashtags').innerHTML = ''; // 해시태그 초기화
      }
    }
  </script>
  
</body>
</html>
