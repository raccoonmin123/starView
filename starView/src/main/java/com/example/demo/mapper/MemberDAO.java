package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.example.demo.DTO.MemberDTO;

@Mapper
public interface MemberDAO {
    String loginCheck(MemberDTO dto);
}
