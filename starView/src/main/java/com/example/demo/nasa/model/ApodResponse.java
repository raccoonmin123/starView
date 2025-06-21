package com.example.demo.nasa.model;

//JSON 직렬화/역직렬화를 위한 DTO (Data Transfer Object)
public class ApodResponse {
 private String title;
 private String url;
 private String explanation;
 private String media_type; // 'image' 또는 'video' 등
 // 필요한 경우 더 많은 필드를 추가할 수 있습니다.

 // 기본 생성자는 JSON 라이브러리(Jackson)가 객체를 생성할 때 필요합니다.
 public ApodResponse() {
 }

 // 모든 필드를 포함하는 생성자 (선택적이지만 유용)
 public ApodResponse(String title, String url, String explanation, String media_type) {
     this.title = title;
     this.url = url;
     this.explanation = explanation;
     this.media_type = media_type;
 }

 // Getter와 Setter (필수)
 public String getTitle() {
     return title;
 }

 public void setTitle(String title) {
     this.title = title;
 }

 public String getUrl() {
     return url;
 }

 public void setUrl(String url) {
     this.url = url;
 }

 public String getExplanation() {
     return explanation;
 }

 public void setExplanation(String explanation) {
     this.explanation = explanation;
 }

 public String getMedia_type() {
     return media_type;
 }

 public void setMedia_type(String media_type) {
     this.media_type = media_type;
 }

 @Override
 public String toString() {
     return "ApodResponse{" +
            "title='" + title + '\'' +
            ", url='" + url + '\'' +
            ", explanation='" + explanation + '\'' +
            ", media_type='" + media_type + '\'' +
            '}';
 }
}
