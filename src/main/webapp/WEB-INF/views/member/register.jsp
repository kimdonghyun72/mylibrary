<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>마을 도서관 - 회원가입</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;700&display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
    <style>
        /* Reset 및 기본 스타일 */
        * {
            box-sizing: border-box;
        }
        body {
            font-family: 'Noto Sans KR', sans-serif;
            background-color: #f0f4f8;
            margin: 0;
            padding: 0;
            color: #333;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        /* 헤더 */
        .header {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            text-align: center;
            padding: 20px 0;
            position: fixed;
            top: 0;
            left: 0;
            z-index: 1000;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .header h1 {
            margin: 0;
            font-size: 3em;
            letter-spacing: 2px;
        }
        .header h1 i {
            margin-right: 12px;
            color: #FFEB3B;
        }

        /* 메인 컨테이너 */
        .register-container {
            margin-top: 140px; /* 헤더 고정 높이 + 여유 */
            background: #fff;
            max-width: 480px;
            width: 90%;
            padding: 40px 40px 50px;
            border-radius: 12px;
            box-shadow: 0 6px 30px rgba(0,0,0,0.1);
            animation: fadeIn 0.8s ease forwards;
            text-align: center;
        }
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        .register-container h2 {
            color: #1565C0;
            font-weight: 700;
            margin-bottom: 30px;
        }
        /* 폼 그룹 */
        .form-group {
            margin-bottom: 18px;
            text-align: left;
        }
        .form-group label {
            display: block;
            margin-bottom: 6px;
            font-weight: 600;
            color: #444;
            cursor: pointer;
        }
        .form-group input[type="text"],
        .form-group input[type="password"],
        .form-group input[type="email"] {
            width: 100%;
            padding: 12px 14px;
            border: 1.8px solid #ccc;
            border-radius: 8px;
            font-size: 1em;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }
        .form-group input[type="text"]:focus,
        .form-group input[type="password"]:focus,
        .form-group input[type="email"]:focus {
            border-color: #4CAF50;
            outline: none;
            box-shadow: 0 0 6px rgba(76, 175, 80, 0.6);
        }

        /* 에러 메시지 */
        .error-message {
            color: #D32F2F;
            font-size: 0.9em;
            margin-bottom: 20px;
            font-weight: 600;
        }

        /* 제출 버튼 */
        .btn-register {
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 8px;
            padding: 15px 0;
            width: 100%;
            font-size: 1.15em;
            font-weight: 700;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .btn-register:hover,
        .btn-register:focus {
            background-color: #45a049;
            outline: none;
        }

        /* 로그인 링크 */
        .link-login {
            margin-top: 28px;
            font-size: 0.95em;
            color: #555;
        }
        .link-login a {
            color: #1565C0;
            font-weight: 600;
            text-decoration: none;
            transition: text-decoration 0.2s ease;
        }
        .link-login a:hover,
        .link-login a:focus {
            text-decoration: underline;
            outline: none;
        }

        /* 푸터 */
        .footer {
            margin-top: auto;
            padding: 20px 0;
            width: 100%;
            text-align: center;
            font-size: 0.9em;
            color: #777;
            background: #f9f9f9;
            box-shadow: inset 0 1px 0 #ddd;
        }

        /* 반응형 */
        @media (max-width: 480px) {
            .register-container {
                padding: 30px 20px 40px;
            }
            .header h1 {
                font-size: 2.4em;
            }
        }
    </style>
</head>
<body>
    <header class="header" role="banner">
        <h1><i class="fas fa-book-reader" aria-hidden="true"></i>마을 도서관</h1>
    </header>

    <main class="register-container" role="main">
        <h2><i class="fas fa-user-plus" aria-hidden="true"></i> 회원가입</h2>

        <c:if test="${not empty errorMessage}">
            <p class="error-message" role="alert">${errorMessage}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/member/register" method="post" novalidate>
            <div class="form-group">
                <label for="member_id">아이디 <span aria-hidden="true" style="color: red;">*</span></label>
                <input type="text" id="member_id" name="memberId" placeholder="아이디를 입력하세요" required autocomplete="username" />

            </div>

            <div class="form-group">
                <label for="password">비밀번호 <span aria-hidden="true" style="color: red;">*</span></label>
                <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" required autocomplete="new-password" />
            </div>

            <div class="form-group">
                <label for="passwordConfirm">비밀번호 확인 <span aria-hidden="true" style="color: red;">*</span></label>
                <input type="password" id="passwordConfirm" name="passwordConfirm" placeholder="비밀번호를 다시 입력하세요" required autocomplete="new-password" />
            </div>

            <div class="form-group">
                <label for="name">이름 <span aria-hidden="true" style="color: red;">*</span></label>
                <input type="text" id="name" name="name" placeholder="이름을 입력하세요" required autocomplete="name" />
            </div>

            <div class="form-group">
                <label for="email">이메일 <span aria-hidden="true" style="color: red;">*</span></label>
                <input type="email" id="email" name="email" placeholder="이메일 주소를 입력하세요" required autocomplete="email" />
            </div>

            <div class="form-group">
                <label for="phone">전화번호</label>
                <input type="text" id="phone" name="phone" placeholder="전화번호를 입력하세요 (선택 사항)" autocomplete="tel" />
            </div>

            <div class="form-group">
                <label for="address">주소</label>
                <input type="text" id="address" name="address" placeholder="주소를 입력하세요 (선택 사항)" autocomplete="street-address" />
            </div>

            <button type="submit" class="btn-register" aria-label="회원가입 제출">회원가입</button>
        </form>

        <p class="link-login">
            이미 계정이 있으신가요? <a href="${pageContext.request.contextPath}/member/login">로그인</a>
        </p>
    </main>

    <footer class="footer" role="contentinfo">
        <p>&copy; 2025 마을 도서관. All rights reserved.</p>
        <p>문의: contact@villagelibrary.org</p>
    </footer>
</body>
</html>
