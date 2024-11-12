<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../common/tags.jsp" %>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="../common/common.jsp" %>
  <title>주소록</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  <link rel="stylesheet" href="../../../resources/css/address-book.css">
</head>
<body>
<div id="divWrapper">
  <div id="divContents">
    <%@ include file="../common/header.jsp" %>
    <section class="verticalLayoutFixedSection">
      <%@ include file="../common/nav.jsp" %>
      <main>
        <div class="content">
          <section class="member-search-container">
            <h3 class="title1">주소록</h3>
            <!-- 검색 바 -->
            <div class="search-bar-wrapper">
              <div class="search-input-wrapper" style="display: flex; align-items: center;">
                <i class="fas fa-search icon" style="margin-right: 10px;"></i> <!-- 돋보기 아이콘 -->
                <select class="filter-select" style="margin-right: 10px;">
                  <option value="all">모두</option>
                  <option value="option1">옵션 1</option>
                  <option value="option2">옵션 2</option>
                </select>
                <input type="text" class="search-input" placeholder="회원명" />
              </div>
              <button class="search-button">
                <span class="button-text">검색</span>
              </button>
            </div>

            <!-- Testimonials 그리드 -->
            <div class="testimonial-grid">
              <!-- 각 testimonial 카드 -->
              <article class="testimonial-card">
                <div class="avatar-block">
                  <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/63e5c59a63559d036116f16a9815d063e5e1e04327cf0b6101b79823ad1ce768?placeholderIfAbsent=true&apiKey=0f6ff559058a4a6fb7ed50c3a9684c29" class="avatar-image" alt="Member avatar" />
                  <div class="info-block">
                    <h3 class="info-title">Title 1</h3>
                    <p class="info-description">Description 1</p>
                    <p class="contact-info">Email: example1@example.com<br>전화: 010-1234-5678</p>
                  </div>
                </div>
              </article>
              <article class="testimonial-card">
                <div class="avatar-block">
                  <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/63e5c59a63559d036116f16a9815d063e5e1e04327cf0b6101b79823ad1ce768?placeholderIfAbsent=true&apiKey=0f6ff559058a4a6fb7ed50c3a9684c29" class="avatar-image" alt="Member avatar" />
                  <div class="info-block">
                    <h3 class="info-title">Title 2</h3>
                    <p class="info-description">Description 2</p>
                    <p class="contact-info">Email: example2@example.com<br>전화: 010-2345-6789</p>
                  </div>
                </div>
              </article>
              <article class="testimonial-card">
                <div class="avatar-block">
                  <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/63e5c59a63559d036116f16a9815d063e5e1e04327cf0b6101b79823ad1ce768?placeholderIfAbsent=true&apiKey=0f6ff559058a4a6fb7ed50c3a9684c29" class="avatar-image" alt="Member avatar" />
                  <div class="info-block">
                    <h3 class="info-title">Title 3</h3>
                    <p class="info-description">Description 3</p>
                    <p class="contact-info">Email: example3@example.com<br>전화: 010-3456-7890</p>
                  </div>
                </div>
              </article>
              <article class="testimonial-card">
                <div class="avatar-block">
                  <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/63e5c59a63559d036116f16a9815d063e5e1e04327cf0b6101b79823ad1ce768?placeholderIfAbsent=true&apiKey=0f6ff559058a4a6fb7ed50c3a9684c29" class="avatar-image" alt="Member avatar" />
                  <div class="info-block">
                    <h3 class="info-title">Title 3</h3>
                    <p class="info-description">Description 3</p>
                    <p class="contact-info">Email: example3@example.com<br>전화: 010-3456-7890</p>
                  </div>
                </div>
              </article>
              <article class="testimonial-card">
                <div class="avatar-block">
                  <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/63e5c59a63559d036116f16a9815d063e5e1e04327cf0b6101b79823ad1ce768?placeholderIfAbsent=true&apiKey=0f6ff559058a4a6fb7ed50c3a9684c29" class="avatar-image" alt="Member avatar" />
                  <div class="info-block">
                    <h3 class="info-title">Title 3</h3>
                    <p class="info-description">Description 3</p>
                    <p class="contact-info">Email: example3@example.com<br>전화: 010-3456-7890</p>
                  </div>
                </div>
              </article>
              <article class="testimonial-card">
                <div class="avatar-block">
                  <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/63e5c59a63559d036116f16a9815d063e5e1e04327cf0b6101b79823ad1ce768?placeholderIfAbsent=true&apiKey=0f6ff559058a4a6fb7ed50c3a9684c29" class="avatar-image" alt="Member avatar" />
                  <div class="info-block">
                    <h3 class="info-title">Title 3</h3>
                    <p class="info-description">Description 3</p>
                    <p class="contact-info">Email: example3@example.com<br>전화: 010-3456-7890</p>
                  </div>
                </div>
              </article>
              <article class="testimonial-card">
                <div class="avatar-block">
                  <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/63e5c59a63559d036116f16a9815d063e5e1e04327cf0b6101b79823ad1ce768?placeholderIfAbsent=true&apiKey=0f6ff559058a4a6fb7ed50c3a9684c29" class="avatar-image" alt="Member avatar" />
                  <div class="info-block">
                    <h3 class="info-title">Title 3</h3>
                    <p class="info-description">Description 3</p>
                    <p class="contact-info">Email: example3@example.com<br>전화: 010-3456-7890</p>
                  </div>
                </div>
              </article>
              <article class="testimonial-card">
                <div class="avatar-block">
                  <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/63e5c59a63559d036116f16a9815d063e5e1e04327cf0b6101b79823ad1ce768?placeholderIfAbsent=true&apiKey=0f6ff559058a4a6fb7ed50c3a9684c29" class="avatar-image" alt="Member avatar" />
                  <div class="info-block">
                    <h3 class="info-title">Title 3</h3>
                    <p class="info-description">Description 3</p>
                    <p class="contact-info">Email: example3@example.com<br>전화: 010-3456-7890</p>
                  </div>
                </div>
              </article>
              <article class="testimonial-card">
                <div class="avatar-block">
                  <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/63e5c59a63559d036116f16a9815d063e5e1e04327cf0b6101b79823ad1ce768?placeholderIfAbsent=true&apiKey=0f6ff559058a4a6fb7ed50c3a9684c29" class="avatar-image" alt="Member avatar" />
                  <div class="info-block">
                    <h3 class="info-title">Title 3</h3>
                    <p class="info-description">Description 3</p>
                    <p class="contact-info">Email: example3@example.com<br>전화: 010-3456-7890</p>
                  </div>
                </div>
              </article>
              <article class="testimonial-card">
                <div class="avatar-block">
                  <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/63e5c59a63559d036116f16a9815d063e5e1e04327cf0b6101b79823ad1ce768?placeholderIfAbsent=true&apiKey=0f6ff559058a4a6fb7ed50c3a9684c29" class="avatar-image" alt="Member avatar" />
                  <div class="info-block">
                    <h3 class="info-title">Title 3</h3>
                    <p class="info-description">Description 3</p>
                    <p class="contact-info">Email: example3@example.com<br>전화: 010-3456-7890</p>
                  </div>
                </div>
              </article>
            </div>

            <div class="pagination">
              <button class="pagination-button">이전</button>
              <span class="pagination-numbers">
                <button class="pagination-number">1</button>
                <button class="pagination-number">2</button>
                <button class="pagination-number">3</button>
                <button class="pagination-number">4</button>
                <button class="pagination-number">5</button>
            </span>
              <button class="pagination-button">이후</button>
            </div>
          </section>
        </div>
      </main>
    </section>
  </div>
</div>
</body>
</html>