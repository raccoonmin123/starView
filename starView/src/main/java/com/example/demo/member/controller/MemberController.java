package com.example.demo.member.controller; // ⭐ 패키지 선언 변경됨

import com.example.demo.member.dto.Member;       // ⭐ import 경로 변경됨
import com.example.demo.member.service.MemberService; // ⭐ import 경로 변경됨
import jakarta.servlet.http.HttpSession; // Jakarta EE 9+ (Spring Boot 3.x)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 로그인 폼 페이지를 보여주는 메서드
    // URL: http://localhost:8080/starView/member/loginForm
    @GetMapping("/loginForm")
    public String loginForm() {
        return "member/loginForm"; // src/main/webapp/WEB-INF/views/member/loginForm.jsp
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam("userid") String userid,
                        @RequestParam("passwd") String passwd,
                        HttpSession session, // 세션 객체를 주입받음
                        RedirectAttributes rttr) { // 리다이렉트 시 메시지 전달용

        Member loggedInMember = memberService.login(userid, passwd);

        if (loggedInMember != null) {
            // 로그인 성공 시 세션에 사용자 정보 저장
            session.setAttribute("loggedInUser", loggedInMember); // Member 객체 통째로
            session.setAttribute("loggedInUserId", loggedInMember.getUserid()); // 사용자 ID만 별도로
            session.setAttribute("loggedInUserName", loggedInMember.getName()); // 사용자 이름도 저장
            rttr.addFlashAttribute("msg", loggedInMember.getName() + "님, 환영합니다!");
            return "redirect:/board/list"; // 로그인 성공 후 게시글 목록으로 리다이렉트
        } else {
            // 로그인 실패 시 메시지 전달 및 로그인 폼으로 돌아감
            rttr.addFlashAttribute("msg", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "redirect:/member/loginForm"; // 로그인 폼으로 다시 리다이렉트
        }
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes rttr) {
        session.invalidate(); // 세션 무효화
        rttr.addFlashAttribute("msg", "로그아웃 되었습니다.");
        return "redirect:/board/list"; // 로그아웃 후 게시글 목록으로 리다이렉트
    }

    // TODO: 회원가입 폼, 회원가입 처리 등 추가 필요
}