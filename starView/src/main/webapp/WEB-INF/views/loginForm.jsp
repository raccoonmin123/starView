<%-- src/main/webapp/WEB-INF/views/member/loginForm.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <style>
        body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; background-color: #f4f4f4; }
        .login-container { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); width: 300px; text-align: center; }
        h1 { color: #333; margin-bottom: 20px; }
        input[type="text"], input[type="password"] { width: calc(100% - 20px); padding: 10px; margin-bottom: 15px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        input[type="submit"] { width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        input[type="submit"]:hover { background-color: #0056b3; }
        .message { color: red; margin-bottom: 15px; }
        .links { margin-top: 20px; }
        .links a { color: #007bff; text-decoration: none; margin: 0 10px; }
        .links a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="login-container">
        <h1>로그인</h1>

        <%-- Controller에서 전달된 메시지 (로그인 실패 시) --%>
        <c:if test="${not empty msg}">
            <p class="message">${msg}</p>
        </c:if>

        <form action="<c:url value='/member/login' />" method="post">
            <input type="text" id="userid" name="userid" placeholder="아이디" required><br>
            <input type="password" id="passwd" name="passwd" placeholder="비밀번호" required><br>
            <input type="submit" value="로그인">
        </form>

        <div class="links">
            <a href="<c:url value='/member/joinForm' />">회원가입</a> <%-- 추후 회원가입 폼 구현 시 사용 --%>
            <a href="<c:url value='/board/list' />">게시판으로 돌아가기</a>
        </div>
    </div>
</body>
</html>