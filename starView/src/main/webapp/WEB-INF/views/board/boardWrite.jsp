<%-- src/main/webapp/WEB-INF/views/board/boardWrite.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>새 글 작성</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { text-align: center; color: #333; margin-bottom: 30px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }
        input[type="text"], textarea { width: calc(100% - 22px); padding: 10px; margin-bottom: 15px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        textarea { resize: vertical; min-height: 150px; }
        input[type="file"] { margin-bottom: 15px; }
        input[type="submit"] { background-color: #28a745; color: white; padding: 12px 20px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; float: right; }
        input[type="submit"]:hover { background-color: #218838; }
        .cancel-btn { background-color: #6c757d; color: white; padding: 12px 20px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; margin-right: 10px; float: right; }
        .cancel-btn:hover { background-color: #5a6268; }
        .message { color: red; margin-bottom: 15px; }
        .clear-fix::after { content: ""; display: table; clear: both; }
    </style>
</head>
<body>
    <div class="container">
        <h1>새 글 작성</h1>

        <c:if test="${not empty msg}">
            <p class="message">${msg}</p>
        </c:if>

        <form action="<c:url value='/board/write' />" method="post" enctype="multipart/form-data">
            <label for="title">제목:</label>
            <input type="text" id="title" name="title" required><br>

            <label for="content">내용:</label>
            <textarea id="content" name="content" required></textarea><br>

            <label for="file">파일 첨부:</label>
            <input type="file" id="file" name="file"><br>

            <div class="clear-fix">
                <input type="submit" value="작성">
                <button type="button" class="cancel-btn" onclick="location.href='<c:url value="/board/list" />'">취소</button>
            </div>
        </form>
    </div>
</body>
</html>