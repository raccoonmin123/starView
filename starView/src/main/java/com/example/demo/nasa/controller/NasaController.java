package com.example.demo.nasa.controller;

import com.example.demo.nasa.model.ApodResponse;
import com.example.demo.nasa.service.NasaService;
import org.springframework.web.bind.annotation.*;

@RestController // RESTful 웹 서비스를 위한 컨트롤러
@RequestMapping("/api/nasa") // 기본 요청 경로 설정
@CrossOrigin(origins = "*") // CORS 설정 (개발 단계에서 모든 출처 허용)
public class NasaController {

    private final NasaService nasaService;

    // 생성자 주입을 통한 NasaService 의존성 주입
    public NasaController(NasaService nasaService) {
        this.nasaService = nasaService;
    }

    // GET 요청을 처리하여 APOD 데이터를 반환
    @GetMapping("/apod")
    public ApodResponse getApod(@RequestParam String date) {
        // NasaService를 호출하여 지정된 날짜의 APOD 데이터를 가져옵니다.
        return nasaService.getApodByDate(date);
    }
}