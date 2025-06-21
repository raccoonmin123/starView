package com.example.demo.service;

import jakarta.servlet.http.HttpSession;
import com.example.demo.DTO.MemberDTO;

public interface MemberService {
 public String loginCheck(MemberDTO dto, HttpSession session);
 public void logout(HttpSession session);
}