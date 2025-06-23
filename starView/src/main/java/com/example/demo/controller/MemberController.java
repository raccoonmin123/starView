package com.example.demo.controller;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.DTO.MemberDTO;
import com.example.demo.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Inject
    private MemberService memberService;

    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/member/login.do";
    }

    @GetMapping("login.do")
    public String login() {
        return "member/login";
    }

    
    
    @PostMapping("login_check.do")
    public ModelAndView login_check(@ModelAttribute MemberDTO dto, HttpSession session) {
        String name = memberService.loginCheck(dto , session);
        ModelAndView mav = new ModelAndView();
        if (name != null) {
            mav.setViewName("redirect:/member/mainpage");
        } else {
            mav.setViewName("member/login");
            mav.addObject("message", "error");
        }
        return mav;
    }
    @GetMapping("/mainpage")
    public String mainPage() {
        return "member/mainpage";
    }

    
    
    @GetMapping("logout.do")
    public ModelAndView logout(HttpSession session) {
        memberService.logout(session);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("member/login");
        mav.addObject("message", "logout");
        return mav;
    }
}