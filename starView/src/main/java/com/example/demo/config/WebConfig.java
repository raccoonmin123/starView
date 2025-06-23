package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 이 클래스가 Spring의 설정 클래스임을 알립니다.
public class WebConfig implements WebMvcConfigurer {

    // application.properties에서 file.upload.dir 값을 주입받습니다.
    @Value("${file.upload.dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /upload/** 경로로 요청이 오면 file.upload.dir (예: C:/img)에서 파일을 찾도록 설정
        // Windows 경로의 경우 'file:///' 뒤에 C:/ 처럼 드라이브 문자가 오므로 슬래시 3개가 맞습니다.
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///" + uploadDir + "/");

        // 만약 /js/map.js 등의 static 파일 접근이 계속 문제되면 이 부분도 명시적으로 추가할 수 있습니다.
        // 현재 application.properties의 spring.web.resources.static-locations 설정과
        // Spring Boot의 기본 동작으로 인해 일반적으로는 필요 없지만, 상황에 따라 추가할 수 있습니다.
        // registry.addResourceHandler("/js/**")
        //         .addResourceLocations("classpath:/static/js/");
    }
}