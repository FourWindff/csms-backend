package com.example.csms.service.impl;

import com.example.csms.entity.Team;
import com.example.csms.mapper.TeamMapper;
import com.example.csms.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamMapper teamMapper;

    public TeamServiceImpl(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    @Override
    public void saveTeam(Team team) {
        teamMapper.insert(team);
    }

    @Override
    public void updateTeam(Team team) {
        teamMapper.updateById(team);
    }

    @Override
    public void deleteTeamById(String id) {
        teamMapper.deleteById(id);
    }

    @Override
    public Team getTeamById(String id) {
        return teamMapper.selectById(id);
    }
}
