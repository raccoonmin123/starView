package com.example.demo.member.dao; // ⭐ 패키지 선언 변경됨

import com.example.demo.member.dto.Member; // ⭐ import 경로 변경됨
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MemberDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // userid로 회원 정보를 조회하는 메서드
    public Member selectMemberByUserid(String userid) {
        String sql = "SELECT userid, passwd, name, email, join_date FROM member WHERE userid = ?";
        try {
            // queryForObject는 결과가 없으면 EmptyResultDataAccessException 발생
            return jdbcTemplate.queryForObject(sql, new RowMapper<Member>() {
                @Override
                public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Member member = new Member();
                    member.setUserid(rs.getString("userid"));
                    member.setPasswd(rs.getString("passwd"));
                    member.setName(rs.getString("name"));
                    member.setEmail(rs.getString("email"));
                    // SQL DATE를 Java 8 LocalDate로 매핑
                    member.setJoinDate(rs.getDate("join_date").toLocalDate());
                    return member;
                }
            }, userid);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            // 조회 결과가 없을 경우 null 반환
            return null;
        }
    }

    // TODO: 회원가입 (insertMember), 회원 정보 수정 (updateMember) 등 추가 가능
    public void insertMember(Member member) {
        String sql = "INSERT INTO member (userid, passwd, name, email, join_date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, member.getUserid(), member.getPasswd(), member.getName(), member.getEmail(), member.getJoinDate());
    }
}