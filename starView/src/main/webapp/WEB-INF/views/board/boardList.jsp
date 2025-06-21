<%-- src/main/webapp/WEB-INF/views/board/boardList.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- fn 태그 사용을 위해 추가 --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판 목록</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        .container { max-width: 900px; margin: 0 auto; background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { text-align: center; color: #333; margin-bottom: 30px; }
        .action-buttons { text-align: right; margin-bottom: 15px; }
        .action-buttons a { background-color: #007bff; color: white; padding: 8px 15px; text-decoration: none; border-radius: 4px; display: inline-block; }
        .action-buttons a:hover { background-color: #0056b3; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #f2f2f2; }
        .message { color: green; font-weight: bold; text-align: center; margin-bottom: 15px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>게시판</h1>

        <c:if test="${not empty msg}">
            <p class="message">${msg}</p>
        </c:if>

        <div class="action-buttons">
            <a href="<c:url value='/board/writeForm' />">새 글 작성</a>
        </div>

        <table>
            <thead>
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>조회수</th>
                    <th>파일</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="board" items="${boardList}">
                    <tr>
                        <td>${board.postId}</td>
                        <td><a href="<c:url value='/board/view?postId=${board.postId}' />">${board.title}</a></td>
                        <td>${board.userName}</td>
                        <td>${fn:substring(board.regDate, 0, 10)}</td> <%-- 날짜만 표시 --%>
                        <td>${board.viewCount}</td>
                        <td>
                            <c:if test="${not empty board.fileName}">
                                <a href="<c:url value='${board.filePath}' />" download="${board.fileName}">첨부</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty boardList}">
                    <tr><td colspan="6" style="text-align: center;">게시글이 없습니다.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>