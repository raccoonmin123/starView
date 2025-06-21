package com.example.demo.nasa.service;

import com.example.demo.nasa.model.ApodResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service // 이 클래스가 Spring 서비스임을 나타내며, Bean으로 등록됩니다.
public class NasaService {

    private static final String NASA_APOD_API_BASE_URL = "https://api.nasa.gov/planetary/apod";

    // application.properties에서 NASA API 키를 주입받습니다.
    @Value("${nasa.api.key}")
    private String nasaApiKey;

    private final RestTemplate restTemplate;

    // RestTemplate은 Spring에 의해 자동으로 주입됩니다 (DemoApplication에서 Bean으로 등록했기 때문).
    public NasaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ApodResponse getApodByDate(String date) {
        // API 요청 URL을 안전하게 빌드합니다.
        String url = UriComponentsBuilder.fromUriString(NASA_APOD_API_BASE_URL)
                .queryParam("api_key", nasaApiKey)
                .queryParam("date", date)
                .toUriString();

        // RestTemplate을 사용하여 NASA API를 호출하고 ApodResponse 객체로 매핑합니다.
        ApodResponse response = restTemplate.getForObject(url, ApodResponse.class);

        // (선택적) NASA APOD가 가끔 HTTP URL을 반환할 수 있는데,
        // 이를 HTTPS로 변경하여 브라우저에서 혼합 콘텐츠 경고를 피할 수 있습니다.
        if (response != null && response.getMedia_type() != null && response.getMedia_type().equals("image") &&
            response.getUrl() != null && response.getUrl().startsWith("http://")) {
            response.setUrl(response.getUrl().replace("http://", "https://"));
        }

        return response;
    }
}