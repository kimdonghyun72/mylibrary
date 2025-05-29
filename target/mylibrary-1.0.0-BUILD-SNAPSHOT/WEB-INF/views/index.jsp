<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>마을 도서관</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: 'Noto Sans KR', sans-serif;
            background-color: #f0f4f8; /* 은은한 푸른 계열 배경 */
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            color: #333;
            overflow-x: hidden; /* 가로 스크롤 방지 */
        }
        .header {
            width: 100%;
            padding: 20px 0;
            background-color: #4CAF50; /* 도서관 로고/이름 부분 배경색 */
            color: white;
            text-align: center;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .header h1 {
            margin: 0;
            font-size: 3em;
            letter-spacing: 2px;
        }
        .header h1 i {
            margin-right: 15px;
            color: #FFEB3B; /* 책 아이콘 색상 */
        }
        .main-content {
            background-color: #ffffff;
            padding: 50px 80px;
            border-radius: 12px;
            box-shadow: 0 6px 30px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 800px;
            width: 90%;
            margin: 40px auto;
            animation: fadeIn 1s ease-out;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
        .main-content p {
            font-size: 1.3em;
            line-height: 1.7;
            margin-bottom: 40px;
            color: #555;
            font-weight: 300;
        }
        .service-links {
            display: flex;
            justify-content: center;
            gap: 30px; /* 링크 간 간격 */
            flex-wrap: wrap; /* 작은 화면에서 줄바꿈 */
        }
        .service-links a {
            display: flex;
            flex-direction: column;
            align-items: center;
            text-decoration: none;
            color: #333;
            background-color: #e3f2fd; /* 링크 버튼 배경색 */
            padding: 25px 35px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            transition: all 0.3s ease;
            min-width: 150px;
            font-size: 1.1em;
            font-weight: 500;
        }
        .service-links a:hover {
            background-color: #bbdefb; /* 호버 시 색상 */
            transform: translateY(-5px);
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.15);
        }
        .service-links a i {
            font-size: 2.5em;
            margin-bottom: 15px;
            color: #1565C0; /* 아이콘 색상 */
        }
        .footer {
            margin-top: 50px;
            padding: 20px;
            color: #777;
            font-size: 0.95em;
            text-align: center;
        }

        /* 반응형 디자인 */
        @media (max-width: 768px) {
            .header h1 {
                font-size: 2.2em;
            }
            .main-content {
                padding: 30px 40px;
                margin: 20px auto;
            }
            .main-content p {
                font-size: 1.1em;
            }
            .service-links {
                flex-direction: column; /* 세로로 나열 */
                gap: 20px;
            }
            .service-links a {
                width: 80%; /* 폭 조절 */
            }
        }
    </style>
</head>
<body>
    <div class="header">
        <h1><i class="fas fa-book-reader"></i> 마을 도서관</h1>
    </div>

    <div class="main-content">
        <p>
            안녕하세요, 책과 함께하는 당신의 공간! 마을 도서관에 오신 것을 환영합니다.<br>
            지식과 영감을 주는 다양한 이야기들이 여러분을 기다리고 있습니다.
            지금 바로 도서관의 풍요로움을 경험해보세요.
        </p>
        <div class="service-links">
            <a href="#">
                <i class="fas fa-search"></i>
                <span>도서 검색</span>
            </a>
            <a href="#">
                <i class="fas fa-book-open"></i>
                <span>내 서재</span>
            </a>
            <a href="${pageContext.request.contextPath}/member/register">
                <i class="fas fa-user-plus"></i>
                <span>회원가입</span>
            </a>
            <a href="${pageContext.request.contextPath}/member/login">
                <i class="fas fa-sign-in-alt"></i>
                <span>로그인</span>
            </a>
        </div>
    </div>

    <div class="footer">
        <p>&copy; 2025 마을 도서관. All rights reserved.</p>
        <p>문의: contact@villagelibrary.org</p>
    </div>
</body>
</html>