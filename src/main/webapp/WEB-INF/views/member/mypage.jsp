<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%-- <<< 이 부분을 jakarta.tags.core 로 변경했습니다. --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko"> <%-- lang 속성 추가 --%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> <%-- 반응형 디자인을 위한 뷰포트 메타 태그 --%>
    <title>${member.name}님의 마이 페이지</title>
    <%-- 외부 CSS 파일이 있다면 여기에 링크 --%>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>">
    <style>
        /* 모든 페이지에 공통적으로 적용되는 기본 바디 스타일 */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex; /* flexbox를 사용하여 전체 레이아웃 제어 */
            flex-direction: column; /* 세로로 아이템 배치 (헤더, 콘텐츠, 푸터) */
            min-height: 100vh; /* 최소 화면 높이를 뷰포트 높이로 설정하여 푸터를 하단에 고정 */
            margin: 0;
            padding: 0; /* 기본 브라우저 여백 제거 */
        }

        /* 메인 콘텐츠를 감싸는 래퍼, 남은 공간을 차지하여 푸터를 아래로 밀어냄 */
        .content-wrapper {
            flex-grow: 1; /* 남은 공간을 모두 차지 */
            display: flex;
            flex-direction: column;
            align-items: center; /* 자식 요소 (mypage-container, loan-list-container)를 수평 중앙 정렬 */
            padding: 20px 0; /* 상하 패딩 */
        }

        /* 마이페이지 컨테이너 및 대출 목록 컨테이너 스타일 */
        .mypage-container, .loan-list-container {
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 800px;
            max-width: 95%; /* 반응형: 작은 화면에서 너무 커지지 않도록 */
            margin-bottom: 20px;
            text-align: center;
        }
        h2 { margin-bottom: 20px; color: #333; }
        .info-group { margin-bottom: 15px; text-align: left; }
        .info-group label { display: block; margin-bottom: 5px; color: #555; font-weight: bold; }
        .info-group p { background-color: #e9e9e9; padding: 10px; border-radius: 4px; color: #333; margin-top: 0; }
        .btn-group { margin-top: 30px; }
        .btn-group a {
            display: inline-block; padding: 10px 20px; margin: 0 10px;
            background-color: #007bff; color: white; border: none;
            border-radius: 4px; cursor: pointer; font-size: 16px; text-decoration: none;
        }
        .btn-group a:hover { background-color: #0056b3; }
        .btn-group a.btn-logout { background-color: #dc3545; }
        .btn-group a.btn-logout:hover { background-color: #c82333; }

        /* 대출 목록 테이블 스타일 */
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #f2f2f2; }
        .loan-status-ongoing { color: #007bff; font-weight: bold; }
        .loan-status-returned { color: #28a745; font-weight: bold; }

        /* 반응형 스타일 */
        @media (max-width: 768px) {
            .mypage-container, .loan-list-container {
                padding: 20px;
            }
            .btn-group a {
                margin: 5px;
                padding: 8px 15px;
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
    <%-- 상단 메뉴 포함 (경로 확인) --%>
    <%@ include file="/WEB-INF/views/includes/header.jsp" %>

    <div class="content-wrapper"> <%-- 메인 콘텐츠 래퍼 --%>
        <div class="mypage-container">
            <h2>${member.name}님의 마이 페이지</h2>

            <c:if test="${not empty message}">
                <p style="color: green;">${message}</p>
            </c:if>

            <div class="info-group">
                <label>아이디:</label>
                <p>${member.memberId}</p>
            </div>
            <div class="info-group">
                <label>이름:</label>
                <p>${member.name}</p>
            </div>
            <div class="info-group">
                <label>이메일:</label>
                <p>${member.email}</p>
            </div>
            <div class="info-group">
                <label>전화번호:</label>
                <p>${member.phone}</p>
            </div>
            <div class="info-group">
                <label>역할:</label>
                <p>${member.role}</p>
            </div>
            <div class="info-group">
                <label>주소:</label>
                <p>${member.address}</p>
            </div>
            <div class="info-group">
                <label>상태:</label>
                <p>${member.status}</p>
            </div>
            <div class="info-group">
                <label>가입일:</label>
            <p><fmt:formatDate value="${member.regDate}" pattern="yyyy-MM-dd HH:mm"/></p>
            </div>

            <div class="btn-group">
                <a href="${pageContext.request.contextPath}/member/edit">정보 수정</a>
                <a href="${pageContext.request.contextPath}/member/logout" class="btn-logout">로그아웃</a>
                <a href="${pageContext.request.contextPath}/">메인으로</a>
            </div>
        </div>

        <div class="loan-list-container">
            <h3>나의 대출 내역</h3>
            <c:choose>
                <c:when test="${not empty loanList}">
                    <table>
                        <thead>
                            <tr>
                                <th>도서 제목</th>
                                <th>저자</th>
                                <th>대출일</th>
                                <th>반납 예정일</th>
                                <th>반납일</th>
                                <th>상태</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="loan" items="${loanList}">
                                <tr>
                                    <td>${loan.bookTitle}</td>
                                    <td>${loan.author}</td>
                                    <td><fmt:formatDate value="${loan.loanDate}" pattern="yyyy-MM-dd"/></td>
                                    <td><fmt:formatDate value="${loan.dueDate}" pattern="yyyy-MM-dd"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty loan.returnDate}">
                                                <fmt:formatDate value="${loan.returnDate}" pattern="yyyy-MM-dd"/>
                                            </c:when>
                                            <c:otherwise>
                                                -
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty loan.returnDate}">
                                                <span class="loan-status-returned">반납 완료</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="loan-status-ongoing">대출 중</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p>대출 내역이 없습니다.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div> <%-- content-wrapper 끝 --%>

    <%-- 푸터 포함 --%>
    <%@ include file="/WEB-INF/views/includes/footer.jsp" %>
</body>
</html>