<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main Page - 지도</title>
    <style>
        #map {
            width: 100%;
            height: 500px;
            border: 1px solid #ccc;
        }
    </style>

    <script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpKeyId=py9vptuy44"></script>
</head>
<body>

<h2>메인 페이지</h2>
<p>아래는 네이버 지도입니다.</p>

<div id="map"></div>

 <script type="text/javascript" src="/map.js"></script>

</body>
</html>
