package com.example.csms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.csms.entity.member.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}
