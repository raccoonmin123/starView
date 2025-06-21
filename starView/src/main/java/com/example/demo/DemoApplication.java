package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder; // RestTemplateBuilder 임포트

import java.time.Duration; // Duration 임포트

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // RestTemplate Bean을 Spring 컨테이너에 등록합니다.
    // 이 RestTemplate 인스턴스가 NasaService에 주입됩니다.
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) { // RestTemplateBuilder를 주입받습니다.
        return builder
                .setConnectTimeout(Duration.ofSeconds(5)) // 연결 타임아웃 5초
                .setReadTimeout(Duration.ofSeconds(10))   // 읽기 타임아웃 10초
                // 필요한 경우 다른 설정들을 여기에 추가할 수 있습니다.
                // .additionalInterceptors(...) // 인터셉터 추가 (로깅, 인증 등)
                .build();
    }
}