package com.example.csms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.csms.entity.member.Member;
import com.example.csms.entity.member.MemberDTO;
import com.example.csms.entity.registration.Registration;
import com.example.csms.mapper.MemberMapper;
import com.example.csms.mapper.RegistrationMapper;
import com.example.csms.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final RegistrationMapper registrationMapper;

    public MemberServiceImpl(MemberMapper memberMapper, RegistrationMapper registrationMapper) {
        this.memberMapper = memberMapper;
        this.registrationMapper = registrationMapper;
    }

    @Override
    public void saveMember(Member member) {
        memberMapper.insert(member);
    }

    @Override
    public void saveMember(MemberDTO memberDTO, String teamId) {
        memberMapper.insert(new Member(memberDTO, teamId));
    }

    @Override
    public void saveMembers(List<MemberDTO> memberDTOList, String teamId) {
        List<Member> memberList = memberDTOList.stream().map(memberDTO -> new Member(memberDTO, teamId)).toList();
        memberMapper.insert(memberList);
    }

    @Override
    public void updateMember(Member member) {
        memberMapper.updateById(member);
    }

    @Override
    public void deleteMemberById(String userId) {
        memberMapper.deleteById(userId);
    }

    @Override
    public List<Member> getMembersByUserId(String userId) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return memberMapper.selectList(queryWrapper);
    }

    @Override
    public List<Member> getMembersByTeamId(String teamId) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", teamId);
        return memberMapper.selectList(queryWrapper);
    }

    @Override
    public boolean isExist(String userId, String teamId) {
        QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("team_id",teamId);
        return memberMapper.exists(queryWrapper);
    }

    @Override
    public boolean isUserRegisteredForMatch(String userId, String matchId) {
        // 获取 userId 对应的 teamId 列表
        List<String> teamIds = memberMapper.selectList(
                        new QueryWrapper<Member>().eq("user_id", userId))
                .stream()
                .map(Member::getTeamId)
                .toList();
        if(teamIds.isEmpty()){
            return false;
        }

        // 检查是否有 teamId 报名了 matchId
        return registrationMapper.selectCount(
                new QueryWrapper<Registration>()
                        .in("team_id", teamIds)
                        .eq("match_id", matchId)) > 0;
    }

}
