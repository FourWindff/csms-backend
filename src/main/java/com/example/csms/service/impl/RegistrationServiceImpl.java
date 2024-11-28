package com.example.csms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.csms.entity.member.Member;
import com.example.csms.entity.registration.Registration;
import com.example.csms.mapper.MemberMapper;
import com.example.csms.mapper.RegistrationMapper;
import com.example.csms.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationMapper registrationMapper;
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public int saveRegistration(Registration registrationChar) {
        return registrationMapper.insert(registrationChar);
    }

    //根据学生id获取属于它的所有报名信息
    @Override
    public List<Registration> selectRegistrationByStudentId(String studentId) {
        // 首先查询该学生的 team_id
        List<String> teamIds = memberMapper.selectList(
                        new QueryWrapper<Member>().eq("user_id", studentId)
                                .select("team_id") // 查询该学生的 team_id
                ).stream().map(Member::getTeamId)  // 假设 Member 类有 getTeamId() 方法
                .collect(Collectors.toList());

        // 如果 teamIds 为空，直接返回空列表
        if (teamIds.isEmpty()) {
            return new ArrayList<>();
        }

        // 然后根据 team_id 查询所有报名记录
        QueryWrapper<Registration> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("team_id", teamIds);  // 使用 in 查询来查找对应的 team_id

        return registrationMapper.selectList(queryWrapper);
    }



    @Override
    public List<Registration> selectRegistrationByTeacherId(String teacherId) {
        QueryWrapper<Registration> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", teacherId);
        return registrationMapper.selectList(queryWrapper);
    }

    @Override
    public void updateRegistrationById(Registration registration) throws Exception {

        QueryWrapper<Registration> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", registration.getId());

        // 先查询该ID对应的记录是否存在
        Registration oldregistration = registrationMapper.selectOne(queryWrapper);
        if (oldregistration == null) {
            throw new Exception("要更新的记录不存在 ");
        } else {
            // 如果存在，则执行更新操作(这里存入registration会自动提取它的主码进行传参)
            registrationMapper.updateById(registration);
        }

    }

    //返回未审核的报名表，记得传入参数是status=你要查询对应状态的报名表
    @Override
    public List<Registration> selectRegistrationByStatus(String status) {
        QueryWrapper<Registration> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status);
        return registrationMapper.selectList(queryWrapper);
    }

    @Override
    public List<Registration> selectRegistrationAll() {
        return registrationMapper.selectList(null);
    }


}