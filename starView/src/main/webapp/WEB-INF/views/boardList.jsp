<%-- src/main/webapp/WEB-INF/views/board/boardList.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판 목록</title>
    <style>
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .header-links { margin-bottom: 20px; }
        .header-links a { margin-right: 15px; text-decoration: none; color: #007bff; }
        .header-links a:hover { text-decoration: underline; }
        .message { color: green; font-weight: bold; margin-top: 10px; }
    </style>
</head>
<body>
    <h1>게시판</h1>

    <div class="header-links">
        <%-- 로그인 상태에 따라 다른 링크 보여주기 --%>
        <c:choose>
            <c:when test="${not empty sessionScope.loggedInUserId}">
                <p>${sessionScope.loggedInUserName}님 환영합니다! </p>
                <a href="<c:url value='/board/writeForm' />">새 글 작성</a>
                <a href="<c:url value='/member/logout' />">로그아웃</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='/member/loginForm' />">로그인</a>
                <a href="<c:url value='/member/joinForm' />">회원가입</a> <%-- 추후 회원가입 폼 구현 시 사용 --%>
            </c:otherwise>
        </c:choose>
    </div>

    <%-- Controller에서 전달된 메시지 (로그인 성공/로그아웃 성공 등) --%>
    <c:if test="${not empty msg}">
        <p class="message">${msg}</p>
    </c:if>

    <table>
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
                <th>조회수</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="board" items="${boardList}">
                <tr>
                    <td>${board.postId}</td>
                    <td><a href="<c:url value='/board/view?postId=${board.postId}' />">${board.title}</a></td>
                    <td>${board.userName}</td> <%-- userName은 DB 조인으로 가져옴 --%>
                    <td>${board.regDate}</td>
                    <td>${board.viewCount}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty boardList}">
                <tr><td colspan="5">게시글이 없습니다.</td></tr>
            </c:if>
        </tbody>
    </table>
</body>
</html>