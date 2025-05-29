<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마을 도서관 - 내 손안의 도서관</title> <%-- 타이틀도 변경 --%>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700&display=swap" rel="stylesheet">
    <style>
        /* 기본 레이아웃 및 폰트 설정 */
        body {
            font-family: 'Noto Sans KR', 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f2f5; /* 밝은 회색 배경 */
            color: #333;
            line-height: 1.6;
            display: flex;
            flex-direction: column;
            min-height: 100vh; /* 뷰포트 전체 높이를 차지하도록 설정 */
        }
        a {
            text-decoration: none;
            color: inherit;
        }

        /* 색상 팔레트 */
        /* 이미지(mylib.jpg)의 책장 색상에서 추출한 계열 */
        :root {
            --primary-color: #8B2C3B; /* 깊은 와인색 (책장 색상에서 추출) */
            --secondary-color: #A03243; /* 좀 더 밝은 와인색 */
            --dark-background: #4A3C42; /* 어두운 갈색/회색 계열 (책장 그림자색) */
            --light-text-color: #ffffff;
            --dark-text-color: #333333;
            --hover-background: #BD4B5E; /* 호버 시 약간 밝게 */
            --border-color: #e0e0e0;
            --light-hover: #faeaea; /* 리스트 호버 등 연한 색상 */
        }


        /* 헤더 스타일 */
        header {
            background-color: var(--primary-color); /* 깊은 와인색 */
            color: var(--light-text-color);
            padding: 25px 0;
            text-align: center;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            position: relative;
            z-index: 10;
        }
        header h1 {
            margin: 0;
            font-size: 2.8em;
            letter-spacing: 1.5px;
            font-weight: 700; /* 더 굵은 글씨 */
            text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.2);
        }

        /* 메인 배너 이미지 스타일 */
        .main-banner {
            width: 100%;
            overflow: hidden; /* 이미지가 넘치는 것을 방지 */
            max-height: 300px; /* 최대 높이 설정하여 너무 커지는 것 방지 */
            display: flex; /* 이미지를 중앙에 배치하기 위함 */
            justify-content: center;
            align-items: center;
            background-color: #e2e6ea; /* 배너 배경색 */
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }
        .main-banner img {
            width: 100%;
            height: auto;
            display: block; /* 이미지 하단의 불필요한 여백 제거 */
            object-fit: cover; /* 이미지가 잘리더라도 영역을 채우도록 */
        }

        /* 내비게이션 바 스타일 */
        nav {
            background-color: var(--dark-background); /* 어두운 갈색/회색 */
            padding: 12px 0;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);
            display: flex; /* Flexbox로 메뉴 중앙 정렬 */
            justify-content: center;
            flex-wrap: wrap; /* 작은 화면에서 줄바꿈 */
        }
        nav a {
            color: var(--light-text-color);
            text-decoration: none;
            padding: 10px 22px; /* 패딩 조정 */
            font-weight: 500; /* 폰트 굵기 */
            font-size: 1.05em;
            transition: background-color 0.3s ease, color 0.3s ease;
            border-radius: 5px; /* 살짝 둥근 모서리 */
            margin: 0 10px; /* 메뉴 항목 간 간격 */
        }
        nav a:hover {
            background-color: var(--hover-background); /* 호버 시 약간 밝은 와인색 */
            color: var(--light-text-color); /* 텍스트 색상도 흰색 유지 */
            transform: translateY(-2px); /* 살짝 위로 올라가는 효과 */
            box-shadow: 0 3px 8px rgba(0, 0, 0, 0.2);
        }

        /* 메인 컨테이너 스타일 */
        .container {
            flex-grow: 1; /* 푸터를 하단에 고정하기 위해 남은 공간을 채움 */
            width: 90%;
            max-width: 1200px;
            margin: 40px auto; /* 상하 마진 증가 */
            background-color: white;
            padding: 35px; /* 패딩 증가 */
            border-radius: 12px; /* 더 둥근 모서리 */
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1); /* 더 깊은 그림자 */
            display: flex; /* 좌우 분할을 위해 Flexbox 사용 */
            gap: 30px; /* 섹션 간 간격 */
            flex-wrap: wrap; /* 작은 화면에서 줄바꿈 */
        }

        /* 좌우 분할 섹션 스타일 */
        .left-section, .right-section {
            flex: 1; /* 남은 공간을 동일하게 배분 */
            min-width: 300px; /* 최소 너비 설정 (반응형) */
        }

        .left-section {
            display: flex;
            flex-direction: column; /* 공지사항 섹션 내부 요소 세로 정렬 */
        }

        .right-section {
            display: flex;
            flex-direction: column; /* 신작/추천 도서 섹션 내부 요소 세로 정렬 */
            gap: 30px; /* 신작 도서와 추천 도서 섹션 간 간격 */
        }


        /* 섹션 제목 스타일 */
        .section-title {
            font-size: 2.2em; /* 폰트 크기 조정 */
            color: var(--primary-color); /* 와인색 */
            text-align: center;
            margin-top: 0; /* 상단 마진 제거 */
            margin-bottom: 25px; /* 하단 마진 증가 */
            padding-bottom: 12px; /* 패딩 조정 */
            border-bottom: 3px solid #eee;
            position: relative;
            font-weight: 700;
        }
        .section-title::after {
            content: '';
            display: block;
            width: 70px; /* 밑줄 길이 조정 */
            height: 4px; /* 밑줄 두께 증가 */
            background-color: var(--primary-color); /* 와인색 */
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            border-radius: 2px; /* 밑줄 모서리 둥글게 */
        }

        /* 공지사항 목록 스타일 */
        .notice-list {
            list-style: none;
            padding: 0;
            margin: 0;
            border: 1px solid var(--border-color);
            border-radius: 10px; /* 더 둥근 모서리 */
            overflow: hidden;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05); /* 은은한 그림자 */
            flex-grow: 1; /* 남은 공간 채우기 */
        }
        .notice-list li {
            padding: 16px 22px; /* 패딩 조정 */
            border-bottom: 1px solid #f0f0f0; /* 더 연한 구분선 */
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #fff;
            transition: background-color 0.3s ease;
        }
        .notice-list li:last-child {
            border-bottom: none;
        }
        .notice-list li:hover {
            background-color: var(--light-hover); /* 호버 시 연한 핑크/베이지 */
        }
        .notice-list li a {
            text-decoration: none;
            color: #007bff; /* 파란색 (링크는 표준색 유지) */
            font-weight: 600; /* 폰트 굵기 */
            flex-grow: 1;
            font-size: 1.1em;
            white-space: nowrap; /* 제목이 길어지면 한 줄로 */
            overflow: hidden; /* 넘치는 부분 숨김 */
            text-overflow: ellipsis; /* ...으로 표시 */
            transition: color 0.3s ease;
            padding-right: 15px; /* 날짜와의 간격 */
        }
        .notice-list li a:hover {
            color: #0056b3;
        }
        .notice-list li span {
            font-size: 0.9em;
            color: #888;
            flex-shrink: 0; /* 날짜가 줄어들지 않도록 */
        }

        /* 도서 그리드 스타일 */
        .book-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); /* 최소 크기 220px로 조정 */
            gap: 25px; /* 간격 증가 */
            margin-top: 0; /* 상단 마진 제거 */
        }
        .book-item {
            border: 1px solid var(--border-color); /* 연한 테두리 */
            border-radius: 12px; /* 더 둥근 모서리 */
            padding: 20px; /* 패딩 조정 */
            text-align: center;
            background-color: #fefefe;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08); /* 더 은은한 그림자 */
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .book-item:hover {
            transform: translateY(-7px); /* 더 크게 올라감 */
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.18); /* 더 강조된 그림자 */
        }
        .book-item img {
            max-width: 100%;
            height: 180px; /* 고정 높이 조정 */
            object-fit: contain;
            border-radius: 8px; /* 이미지 모서리 둥글게 */
            margin-bottom: 15px;
            border: 1px solid #f0f0f0;
            background-color: #fff; /* 이미지가 투명할 경우를 대비 */
        }
        .book-item h4 {
            margin-top: 0;
            margin-bottom: 8px;
            color: var(--dark-text-color);
            font-size: 1.25em; /* 폰트 크기 조정 */
            font-weight: 600;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .book-item p {
            margin: 4px 0;
            color: #666;
            font-size: 0.9em; /* 폰트 크기 조정 */
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .book-item .book-author, .book-item .book-publisher {
            margin-top: 6px;
            color: #777;
        }
        .book-item a.detail-button { /* 상세 보기 버튼 스타일 */
            display: inline-block;
            margin-top: 15px; /* 마진 조정 */
            padding: 9px 18px; /* 패딩 조정 */
            background-color: var(--secondary-color); /* 좀 더 밝은 와인색 */
            color: var(--light-text-color);
            text-decoration: none;
            border-radius: 7px;
            transition: background-color 0.3s ease, transform 0.2s ease;
            font-weight: 600;
            font-size: 0.95em; /* 폰트 크기 조정 */
        }
        .book-item a.detail-button:hover {
            background-color: var(--primary-color); /* 호버 시 기본 와인색 */
            transform: translateY(-2px);
        }

        /* 메시지 스타일 */
        .message, .error-message {
            padding: 18px; /* 패딩 증가 */
            margin-bottom: 25px; /* 마진 증가 */
            border-radius: 10px; /* 더 둥근 모서리 */
            text-align: center;
            font-weight: 600;
            font-size: 1.15em;
            animation: fadeIn 0.8s ease-out; /* 페이드인 애니메이션 */
        }
        .message {
            background-color: #d4edda; /* 밝은 초록색 */
            color: #155724; /* 진한 초록색 */
            border: 1px solid #c3e6cb;
        }
        .error-message {
            background-color: #f8d7da; /* 밝은 빨간색 */
            color: #721c24; /* 진한 빨간색 */
            border: 1px solid #f5c6cb;
        }
        .no-data-message {
            text-align: center;
            color: #888;
            font-style: italic;
            margin-top: 20px; /* 마진 조정 */
            padding: 15px; /* 패딩 조정 */
            background-color: #fcfcfc;
            border-radius: 10px;
            border: 1px dashed #d0d0d0;
            font-size: 1em; /* 폰트 크기 조정 */
        }

        /* 푸터 스타일 */
        footer {
            background-color: var(--dark-background); /* 어두운 갈색/회색 */
            color: #adb5bd; /* 밝은 회색 글씨 */
            text-align: center;
            padding: 25px; /* 패딩 증가 */
            margin-top: auto; /* 컨테이너 하단에 고정 */
            box-shadow: 0 -4px 10px rgba(0, 0, 0, 0.2);
            font-size: 0.95em;
        }
        footer p {
            margin: 5px 0;
        }

        /* 애니메이션 */
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        /* 반응형 디자인 */
        @media (max-width: 992px) {
            .container {
                flex-direction: column; /* 태블릿에서는 세로로 쌓이도록 */
                gap: 40px; /* 섹션 간 간격 증가 */
            }
            .left-section, .right-section {
                min-width: unset; /* 최소 너비 제한 해제 */
                width: 100%; /* 전체 너비 차지 */
            }
            .book-grid {
                grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                gap: 20px;
            }
            .book-item img {
                height: 160px;
            }
        }

        @media (max-width: 768px) {
            header h1 {
                font-size: 2.2em;
            }
            nav a {
                padding: 8px 15px;
                font-size: 1em;
                margin: 5px;
            }
            .main-banner {
                max-height: 200px; /* 배너 높이 줄이기 */
            }
            .section-title {
                font-size: 2em;
                margin-bottom: 20px;
            }
            .section-title::after {
                width: 60px;
                height: 3px;
            }
            .notice-list li {
                flex-direction: column; /* 공지사항 항목 세로 정렬 */
                align-items: flex-start;
                padding: 15px 20px;
            }
            .notice-list li span {
                margin-top: 5px;
                margin-left: 0;
            }
            .book-grid {
                grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
                gap: 15px;
            }
            .book-item {
                padding: 15px;
            }
            .book-item img {
                height: 140px;
            }
            .book-item h4 {
                font-size: 1.1em;
            }
            .book-item p {
                font-size: 0.85em;
            }
            .message, .error-message, .no-data-message {
                font-size: 1em;
                padding: 15px;
            }
        }

        @media (max-width: 576px) {
            nav {
                flex-direction: column;
                align-items: center;
            }
            nav a {
                width: 90%;
                text-align: center;
                margin-bottom: 8px;
            }
            .book-grid {
                grid-template-columns: 1fr; /* 한 줄에 하나씩 */
            }
            .book-item img {
                height: 180px; /* 다시 높이 키움 */
            }
        }
    </style>
</head>
<body>
    <header>
        <h1>마을 도서관</h1> <%-- MyLibrary -> 마을 도서관으로 변경 --%>
    </header>

    <div class="main-banner">
        <img src="/mylibrary/resources/images/mylib.jpg" alt="마을 도서관 메인 배너">
    </div>

    <nav>
        <a href="/mylibrary/">홈</a>
        <a href="/mylibrary/member/login">로그인</a>
        <a href="/mylibrary/member/join">회원가입</a>
        <a href="/mylibrary/member/mypage">마이 페이지</a>
        <c:if test="${sessionScope.loggedInMember != null}">
            <a href="/mylibrary/member/logout">로그아웃</a> <%-- 여기 오타 수정: 로그fs아웃 -> 로그아웃 --%>
        </c:if>
        <%-- TODO: 필요한 경우 여기에 추가 메뉴 항목을 더 넣을 수 있습니다. --%>
    </nav>

    <div class="container">
        <c:if test="${not empty message}">
            <p class="message">${message}</p>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <p class="error-message">${errorMessage}</p>
        </c:if>

        <div class="left-section">
            <h2 class="section-title">공지사항</h2>
            <c:choose>
                <c:when test="${not empty recentNotices}">
                    <ul class="notice-list">
                        <c:forEach var="notice" items="${recentNotices}">
                            <li>
                                <%-- TODO: 공지사항 상세 페이지 링크가 있다면 활성화 (예: /mylibrary/notice/detail?id=${notice.id}) --%>
                                <a href="#">${notice.title}</a>
                                <span><fmt:formatDate value="${notice.regDate}" pattern="yyyy-MM-dd"/></span>
                            </li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>
                    <p class="no-data-message">등록된 공지사항이 없습니다.</p>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="right-section">
            <h2 class="section-title">신작 도서</h2>
            <c:choose>
                <c:when test="${not empty newBooks}">
                    <div class="book-grid">
                        <c:forEach var="book" items="${newBooks}">
                            <div class="book-item">
                                <%-- TODO: 이미지 경로와 BookVO 필드에 'coverImage'가 있다면 주석 해제하여 사용 --%>
                                <img src="https://via.placeholder.com/180x220?text=Book+Cover" alt="기본 책 표지">
                                <h4>${book.title}</h4>
                                <p class="book-author">저자: ${book.author}</p>
                                <p class="book-publisher">출판사: ${book.publisher}</p>
                                <%-- TODO: 도서 상세 페이지 링크 추가 시 주석 해제 및 올바른 경로 설정 --%>
                                <a href="#" class="detail-button">상세 보기</a>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <p class="no-data-message">새로운 도서가 없습니다.</p>
                </c:otherwise>
            </c:choose>

            <h2 class="section-title">추천 도서</h2>
            <c:choose>
                <c:when test="${not empty recommendedBooks}">
                <div class="book-grid">
                    <c:forEach var="book" items="${recommendedBooks}">
                        <div class="book-item">
                            <%-- TODO: 이미지 경로와 BookVO 필드에 'coverImage'가 있다면 주석 해제하여 사용 --%>
                            <img src="https://via.placeholder.com/180x220?text=Book+Cover" alt="기본 책 표지">
                            <h4>${book.title}</h4>
                            <p class="book-author">저자: ${book.author}</p>
                            <p class="book-publisher">출판사: ${book.publisher}</p>
                            <%-- TODO: 도서 상세 페이지 링크 추가 시 주석 해제 및 올바른 경로 설정 --%>
                            <a href="#" class="detail-button">상세 보기</a>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <p class="no-data-message">추천 도서가 없습니다.</p>
            </c:otherwise>
        </c:choose>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 마을 도서관. All rights reserved.</p> <%-- 푸터 텍스트도 변경 --%>
    </footer>
</body>
</html>