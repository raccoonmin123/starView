package com.example.demo.board.dto;

import java.time.LocalDateTime;

public class Board {
 private int postId;
 private String userId; // member 테이블의 userId와 매핑
 private String userName; // 조인 시 member.name을 담기 위함
 private String title;
 private String content;
 private LocalDateTime regDate;
 private LocalDateTime modDate;
 private int viewCount;

 // Getters and Setters
 public int getPostId() { return postId; }
 public void setPostId(int postId) { this.postId = postId; }
 public String getUserId() { return userId; }
 public void setUserId(String userId) { this.userId = userId; }
 public String getUserName() { return userName; }
 public void setUserName(String userName) { this.userName = userName; }
 public String getTitle() { return title; }
 public void setTitle(String title) { this.title = title; }
 public String getContent() { return content; }
 public void setContent(String content) { this.content = content; }
 public LocalDateTime getRegDate() { return regDate; }
 public void setRegDate(LocalDateTime regDate) { this.regDate = regDate; }
 public LocalDateTime getModDate() { return modDate; }
 public void setModDate(LocalDateTime modDate) { this.modDate = modDate; }
 public int getViewCount() { return viewCount; }
 public void setViewCount(int viewCount) { this.viewCount = viewCount; }
}