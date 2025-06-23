package com.example.demo.mapper;

import com.example.demo.DTO.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {
    String loginCheck(MemberDTO dto);
}