// src/main/java/com/example/demo/board/dto/Board.java
package com.example.demo.board.dto;

import lombok.Data; // Lombok 사용 시
import java.time.LocalDateTime;

@Data // Lombok @Data 어노테이션 사용 (Getter, Setter, toString 등 자동 생성)
public class Board {
    private int postId;
    private String title;
    private String content;
    private String userId; // 작성자 ID
    private String userName; // 작성자 이름
    private LocalDateTime regDate;
    private int viewCount;

    // ⭐ 파일 업로드 관련 필드 추가
    private String fileName; // 원본 파일명
    private String filePath; // 서버에 저장된 파일의 경로 (URL 접근 경로)
}