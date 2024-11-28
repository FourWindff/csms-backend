package com.example.csms.service;

import com.example.csms.entity.Team;

public interface TeamService {
    void saveTeam(Team team);
    void updateTeam(Team team);
    void deleteTeamById(String id);
    Team getTeamById(String id);
}
