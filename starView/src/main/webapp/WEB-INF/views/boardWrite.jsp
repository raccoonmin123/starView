<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 작성</title>
</head>
<body>
    <h1>새 글 작성</h1>
    <form action="<c:url value='/board/write' />" method="post">
        <label for="title">제목:</label><br>
        <input type="text" id="title" name="title" required><br><br>

        <label for="content">내용:</label><br>
        <textarea id="content" name="content" rows="10" cols="50" required></textarea><br><br>

        <%-- 실제 구현 시, 로그인된 사용자의 ID를 세션에서 가져와 자동으로 설정 --%>
        <%-- <input type="hidden" name="userId" value="${sessionScope.loginUser.userId}"> --%>

        <input type="submit" value="작성 완료">
        <button type="button" onclick="location.href='<c:url value='/board/list' />'">목록으로</button>
    </form>
</body>
</html>