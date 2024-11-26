package com.example.csms.service;

import com.example.csms.entity.Match;

import java.util.List;

public interface MatchService {
    List<Match> selectAll();
    List<Match> selectByMatchType(String matchType);
    List<Match> selectByMatchStartTime(String matchStartTime);
    List<Match> selectByMatchEndTime(String matchEndTime);
    List<Match> selectByRegistrationStartTime(String registrationStartTime);
    List<Match> selectByRegistrationEndTime(String registrationEndTime);

    Match selectByMatchId(String matchId);

    int saveMatch(Match match);

    void updateMatchById(Match match) throws Exception;

    boolean deleteMatchRecordById(String id);
}
