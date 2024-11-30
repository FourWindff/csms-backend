package com.example.csms.service;

import com.example.csms.entity.member.Member;
import com.example.csms.entity.member.MemberDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberService {
    void saveMember(Member member);
    void saveMember(MemberDTO memberDTO,String teamId);
    void saveMembers(List<MemberDTO> memberDTOList, String teamId);
    void updateMember(Member member);
    void deleteMemberById(String id);
    List<Member> getMembersByUserId(String id);
    List<Member> getMembersByTeamId(String teamId);
    boolean isExist(String userId,String teamId);
    boolean isUserRegisteredForMatch(String userId, String matchId);
}
