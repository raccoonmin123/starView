package com.example.demo.member.service; // ⭐ 패키지 선언 변경됨

import com.example.demo.member.dao.MemberDAO;    // ⭐ import 경로 변경됨
import com.example.demo.member.dto.Member;      // ⭐ import 경로 변경됨
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberDAO memberDAO;

    /**
     * 사용자 로그인 처리
     * @param userid 사용자 ID
     * @param passwd 비밀번호
     * @return 로그인 성공 시 Member 객체, 실패 시 null
     */
    public Member login(String userid, String passwd) {
        Member member = memberDAO.selectMemberByUserid(userid);

        // 사용자가 존재하지 않거나 비밀번호가 일치하지 않으면 로그인 실패
        // 실제 비밀번호는 반드시 암호화된 상태로 비교해야 합니다 (예: BCryptPasswordEncoder)
        if (member != null && member.getPasswd().equals(passwd)) {
            return member; // 로그인 성공
        }
        return null; // 로그인 실패
    }

    // TODO: 회원가입, 아이디 중복 확인 등 추가 가능
}