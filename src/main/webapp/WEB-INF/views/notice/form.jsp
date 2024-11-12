<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판 글쓰기</title>
    <link href="../../../resources/css/notic.css" rel="stylesheet"/>
    <style>
        /* 기본 스타일 */
        main {
            width: 100%;
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }

        form.write {
            display: flex;
            flex-direction: column;
        }

        form.write input {
            font-size: 1.5rem;
            padding: 15px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 8px;
            width: 100%;
        }

        .button {
            text-align: center;
        }

        .button p {
            font-size: 1.5rem;
            margin: 0;
        }

        .button a {
            font-size: 1.5rem;
            padding: 12px 25px;
            margin: 0 10px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        .button a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <main>
        <form class="write">
            <input type="text" placeholder="공지사항을 입력해주세요." required>
            <input type="text" placeholder="오늘의 공지는?" class="text" required>
            <div class="button">
                <p><a href="인서트jsp 실행">공지등록</a><a href="게시글목록jsp넣기">취소</a></p>
            </div>
        </form>
    </main>
</body>
</html>
