<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>NASA Astronomy Picture of the Day</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
            color: #333;
            min-width: 320px;
            padding: 20px;
            box-sizing: border-box;
        }
        h1 { color: #2c3e50; text-align: center; margin-bottom: 30px; }

        .controls {
            text-align: center;
            margin-bottom: 20px;
        }

        input[type="date"], button {
            padding: 10px 15px;
            border-radius: 5px;
            border: 1px solid #ccc;
            margin-right: 10px;
            font-size: 1em;
        }
        button {
            background-color: #3498db;
            color: white;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        button:hover { background-color: #2980b9; }

        #result {
            margin: 30px auto;
            max-width: 900px;
            width: 90%;
            padding: 25px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            min-height: 200px;
            display: flex;
            flex-direction: column;
            align-items: center;
            box-sizing: border-box;
        }
        #result h3 {
            color: #2c3e50;
            margin-top: 0;
            margin-bottom: 20px;
            font-size: 1.8em;
            text-align: center;
            line-height: 1.3;
        }
        #result img, #result iframe {
            max-width: 100%;
            height: auto;
            margin-top: 0;
            margin-bottom: 20px;
            border-radius: 4px;
            display: block;
            box-shadow: 0 2px 8px rgba(0,0,0,0.2);
        }
        #result iframe {
            width: 100%;
            height: 500px;
        }
        #result p {
            line-height: 1.8;
            margin-top: 10px;
            font-size: 1.1em;
            text-align: justify;
            padding: 0 10px;
        }
        .loader {
            border: 4px solid #f3f3f3; border-top: 4px solid #3498db; border-radius: 50%;
            width: 30px; height: 30px; animation: spin 2s linear infinite; display: none; margin: 20px auto;
        }
        @keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }
    </style>
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const today = new Date();
            const year = today.getFullYear();
            const month = String(today.getMonth() + 1).padStart(2, '0');
            const day = String(today.getDate()).padStart(2, '0');
            document.getElementById('dateInput').value = `${year}-${month}-${day}`;
            getApod(); // 페이지 로드 시 자동 조회
        });

        function getApod() {
            const date = document.getElementById('dateInput').value;
            const resultDiv = document.getElementById('result');
            const loader = document.getElementById('loader');

            if (!date) {
                alert("날짜를 선택해주세요.");
                return;
            }

            resultDiv.innerHTML = '';
            loader.style.display = 'block';

            const apiUrl = `/starView/api/nasa/apod?date=${date}`;

            console.log("API 요청 URL: ", apiUrl);

            fetch(apiUrl)
                .then(res => { // <<<<<<<<<< 여기를 res로 받습니다.
                    if (!res.ok) { // HTTP 상태 코드가 200 OK가 아니면 에러 처리
                        return res.json().then(errorData => { // 에러 메시지가 JSON으로 올 수도 있으므로 파싱 시도
                            throw new Error(errorData.msg || `API 요청 실패: ${res.status} ${res.statusText}`);
                        }).catch(() => { // JSON 파싱 실패 시 일반 에러 메시지
                            throw new Error(`API 요청 실패 (HTTP ${res.status}): ${res.statusText}`);
                        });
                    }
                    return res.json(); // <<<<<<<<<< 여기서 JSON 데이터를 파싱하여 다음 .then()으로 전달
                })
                .then(data => { // <<<<<<<<<< 이제 여기의 data는 실제 JSON 객체입니다!
                    loader.style.display = 'none';

                    console.log("★★★★ API 응답 Raw 데이터 (파싱 완료):", data); // 이제 여기에 실제 JSON 데이터가 찍힙니다.
                    console.log("★★★★ data.title:", data.title);
                    console.log("★★★★ data.url:", data.url);
                    console.log("★★★★ data.explanation:", data.explanation);
                    console.log("★★★★ data.media_type:", data.media_type);

                    if (!data || !data.title || !data.explanation || !data.media_type) {
                        resultDiv.innerHTML = `<p style="color: red;">API 응답 데이터가 불완전합니다. (필수 필드 누락)</p>`;
                        console.error("API 응답 데이터 불완전:", data);
                        return;
                    }

                    // --- HTML 요소 직접 생성 및 추가 방식으로 유지 (이전 답변에서 제공했던 방식) ---

                    const titleElement = document.createElement('h3');
                    titleElement.textContent = data.title;
                    resultDiv.appendChild(titleElement);

                    if (data.media_type === 'image') {
                        if (data.url) {
                            const imgElement = document.createElement('img');
                            imgElement.src = data.url; // URL 직접 할당
                            imgElement.alt = data.title; // alt 텍스트 할당
                            resultDiv.appendChild(imgElement);
                            console.log("★★★★ 이미지 요소 생성 및 추가 완료. src:", imgElement.src);
                        } else {
                            const p = document.createElement('p');
                            p.style.color = 'orange';
                            p.textContent = '이미지 타입이지만 URL을 찾을 수 없습니다.';
                            resultDiv.appendChild(p);
                            console.warn("이미지 타입이지만 URL이 없음:", data);
                        }
                    } else if (data.media_type === 'video') {
                        if (data.url) {
                            const iframeElement = document.createElement('iframe');
                            let videoUrl = data.url;
                            if (videoUrl.includes("youtube.com/watch?v=")) {
                                videoUrl = videoUrl.replace("watch?v=", "embed/");
                            }
                            if (videoUrl.startsWith("http://")) {
                                videoUrl = videoUrl.replace("http://", "https://");
                            }
                            iframeElement.src = videoUrl; // URL 직접 할당
                            iframeElement.frameBorder = "0";
                            iframeElement.allowFullscreen = true;
                            resultDiv.appendChild(iframeElement);
                            console.log("★★★★ 비디오 요소 생성 및 추가 완료. src:", iframeElement.src);
                        } else {
                            const p = document.createElement('p');
                            p.style.color = 'orange';
                            p.textContent = '비디오 타입이지만 URL을 찾을 수 없습니다.';
                            resultDiv.appendChild(p);
                            console.warn("비디오 타입이지만 URL이 없음:", data);
                        }
                    } else {
                        const p = document.createElement('p');
                        p.textContent = `지원되지 않는 미디어 타입입니다: ${data.media_type}`;
                        resultDiv.appendChild(p);
                        console.warn("지원되지 않는 미디어 타입:", data.media_type, data);
                    }

                    const explanationElement = document.createElement('p');
                    explanationElement.textContent = data.explanation;
                    resultDiv.appendChild(explanationElement);

                    console.log("★★★★ 모든 요소 resultDiv에 설정 완료. Elements 탭 확인 필요.");
                    // --- 변경된 부분 끝 ---

                })
                .catch(error => {
                    loader.style.display = 'none';
                    resultDiv.innerHTML = `<p style="color: red;">데이터 불러오기 실패: ${error.message}</p>`;
                    console.error('Fetch error (네트워크 또는 서버):', error);
                });
        }
    </script>
</head>
<body>
    <h1>NASA Astronomy Picture of the Day</h1>
    <div class="controls">
        <input type="date" id="dateInput" />
        <button onclick="getApod()">조회하기</button>
    </div>
    <div class="loader" id="loader"></div>
    <div id="result"></div>
</body>
</html>