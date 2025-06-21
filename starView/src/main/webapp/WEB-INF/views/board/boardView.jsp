<%-- src/main/webapp/WEB-INF/views/board/boardView.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${board.title}</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { text-align: center; color: #333; margin-bottom: 30px; }
        .board-info { margin-bottom: 20px; border-bottom: 1px solid #eee; padding-bottom: 10px; }
        .board-info div { margin-bottom: 5px; }
        .board-info span { font-weight: bold; margin-right: 10px; color: #555; }
        .board-content { border: 1px solid #ddd; padding: 15px; min-height: 150px; margin-bottom: 20px; line-height: 1.6; white-space: pre-wrap; word-break: break-all; }
        .actions { text-align: right; margin-top: 20px; }
        .actions a, .actions button { display: inline-block; padding: 8px 15px; background-color: #007bff; color: white; text-decoration: none; border-radius: 4px; border: none; cursor: pointer; margin-left: 10px; }
        .actions a:hover, .actions button:hover { background-color: #0056b3; }
        .actions .delete-btn { background-color: #dc3545; }
        .actions .delete-btn:hover { background-color: #c82333; }
        .file-attachment { margin-top: 20px; padding: 10px; border: 1px dashed #ccc; background-color: #f9f9f9; }
        .file-attachment a { text-decoration: none; color: #007bff; }
        .file-attachment a:hover { text-decoration: underline; }
        .file-image { max-width: 100%; height: auto; display: block; margin-top: 10px; border: 1px solid #eee; }
    </style>
</head>
<body>
    <div class="container">
        <h1>${board.title}</h1>

        <div class="board-info">
            <div><span>작성자:</span> ${board.userName} (${board.userId})</div>
            <div><span>작성일:</span> ${board.regDate}</div>
            <div><span>조회수:</span> ${board.viewCount}</div>
        </div>

        <div class="board-content">
            ${board.content}
        </div>

        <c:if test="${not empty board.fileName}">
            <div class="file-attachment">
                <strong>첨부 파일:</strong>
                <a href="<c:url value='${board.filePath}' />" download="${board.fileName}">${board.fileName}</a>
                <c:set var="fileExtension" value="${fn:substringAfter(board.fileName, '.')}" />
                <c:if test="${fileExtension eq 'jpg' or fileExtension eq 'jpeg' or fileExtension eq 'png' or fileExtension eq 'gif'}">
                    <img src="<c:url value='${board.filePath}' />" alt="첨부 이미지" class="file-image">
                </c:if>
            </div>
        </c:if>

        <div class="actions">
            <a href="<c:url value='/board/editForm?postId=${board.postId}' />">수정</a>
            <a href="<c:url value='/board/delete?postId=${board.postId}' />" onclick="return confirm('정말로 삭제하시겠습니까?');" class="delete-btn">삭제</a>
            <a href="<c:url value='/board/list' />">목록으로</a>
        </div>
    </div>
</body>
</html>