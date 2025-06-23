package com.example.demo.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import jakarta.inject.Inject;
import com.example.demo.DTO.MemberDTO;
import com.example.demo.mapper.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService {

 @Inject
 MemberDAO memberDao;

 @Override
 public String loginCheck(MemberDTO dto, HttpSession session) {
     String name = memberDao.loginCheck(dto);
     if (name != null) {
         session.setAttribute("userid", dto.getUserid());
         session.setAttribute("name", name);
     }
     return name;
 }

 @Override
 public void logout(HttpSession session) {
     session.invalidate();
 }
}