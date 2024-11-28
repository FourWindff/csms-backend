package com.example.csms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.csms.entity.Login;
import com.example.csms.mapper.LoginMapper;
import com.example.csms.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    public Login getLoginByUserId(String userId) {
        return loginMapper.selectOne(new QueryWrapper<Login>().eq("user_id", userId));
    }

    @Override
    public boolean registerUser(Login login)
    {
        int insert = loginMapper.insert(login);
        return insert > 0;
    }

    @Override
    public boolean isTeacher(String userId) {
        QueryWrapper<Login> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        Login user=loginMapper.selectById(userId);
        if(user==null) return false;
        return "teacher".equals(user.getRole());

    }

    @Override
    public boolean isStudent(String userId) {
        QueryWrapper<Login> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        Login user=loginMapper.selectById(userId);
        if(user==null) return false;
        return "student".equals(user.getRole());
    }
}