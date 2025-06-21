package com.example.demo.controller; // 이 패키지 경로가 실제 파일 위치와 일치하는지 확인

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 이 어노테이션이 정확히 있는지 확인
public class WebPageController { // 파일 이름과 클래스 이름이 WebPageController.java로 일치하는지 확인

    // 웹 브라우저에서 접속하는 모든 가능성 있는 URL을 매핑합니다.
    // 예를 들어, http://localhost:8080/ 또는 http://localhost:8080/starView/ 등으로 접속할 경우
    @GetMapping({"/", "/starView", "/starView/", "/starView_starView", "/starView_starView/"})
    public String showStarView() {
        return "nasa"; // WEB-INF/views/nasa.jsp 파일을 찾아 렌더링
    }
}