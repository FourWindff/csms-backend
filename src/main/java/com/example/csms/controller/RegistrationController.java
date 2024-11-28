package com.example.csms.controller;

import com.example.csms.entity.*;
import com.example.csms.entity.member.Member;
import com.example.csms.entity.member.MemberDTO;
import com.example.csms.entity.member.MemberVO;
import com.example.csms.entity.registration.Registration;
import com.example.csms.entity.registration.RegistrationDTO;
import com.example.csms.entity.registration.RegistrationVO;
import com.example.csms.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);
    private final RegistrationService registrationService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final MatchService matchService;
    private final TeamService teamService;
    private final MemberService memberService;
    private final LoginService loginService;

    public RegistrationController(RegistrationService registrationService, StudentService studentService, TeacherService teacherService, MatchService matchService, TeamService teamService, MemberService memberService, LoginService loginService) {
        this.registrationService = registrationService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.matchService = matchService;
        this.teamService = teamService;
        this.memberService = memberService;
        this.loginService = loginService;
    }

    /*报名操作开始*/
    //报名竞赛，初始为未审核PENDING
    @PostMapping("/registration/saveRegistration")
    public Result saveRegistration(@RequestBody RegistrationDTO registrationDTO) {
        String matchId = registrationDTO.getMatchId();
        String teamId = registrationDTO.getTeamId();
        List<MemberDTO> memberDTOList = registrationDTO.getMemberDTOList();
        StringBuilder errorMessage = new StringBuilder();

        memberDTOList.forEach(memberDTO -> {
            if ("student".equals(memberDTO.getRole()) && memberService.isUserRegisteredForMatch(memberDTO.getUserId(), matchId)) {
                errorMessage.append(memberDTO.getUserId() + "已经报名过该比赛");
            }
        });
        memberDTOList.forEach(memberDTO -> {
            if ("student".equals(memberDTO.getRole()) && !loginService.isStudent(memberDTO.getUserId())) {
                errorMessage.append(memberDTO.getUserId() + "不是学生");
            }
        });
        memberDTOList.forEach(memberDTO -> {
            if ("teacher".equals(memberDTO.getRole()) && !loginService.isTeacher(memberDTO.getUserId())) {
                errorMessage.append(memberDTO.getUserId() + "不是教师");
            }
        });
        log.info(matchId);
        log.info(teamId);
        log.info(memberDTOList.toString());
        if (!errorMessage.isEmpty()) {
            log.info(errorMessage.toString());
            return Result.error(errorMessage.toString());
        }
        Integer memberCount = memberDTOList.size();


        Registration newRegistration = new Registration(matchId, "PENDING", teamId);
        Team team = new Team(teamId, memberCount);
        teamService.saveTeam(team);

        registrationService.saveRegistration(newRegistration);
        memberService.saveMembers(memberDTOList, teamId);

        return Result.success("报名成功");


    }

    //获取报名表（管理员审核）
    @PostMapping("/registration/getAllRegistration")
    public Result getAllRegistration(Registration registration) {
        if (registration.getStatus() == null) {
            List<Registration> result = registrationService.selectRegistrationAll();
            log.info("获取所有报名表成功，共{}条记录", result.size());
            return Result.success(result.stream().map(this::toVO).collect(Collectors.toList()));
        } else {
            List<Registration> result = registrationService.selectRegistrationByStatus(registration.getStatus());
            log.info("获取{}报名表成功，共{}条记录", registration.getStatus(), result.size());
            return Result.success(result.stream().map(this::toVO).collect(Collectors.toList()));
        }

    }


    //学生获取报名表
    @GetMapping("/registration/student/{userId}")
    public Result getAllRegistration(@PathVariable String userId) {
        List<Registration> result = registrationService.selectRegistrationByStudentId(userId);
        if (result == null) return Result.error("学生当前没有报名信息");
        log.info(result.toString());
        return Result.success(result.stream().map(this::toVO).collect(Collectors.toList()));
    }

    //修改报名表
    @PutMapping("/registration/updateRegistration")
    public Result updateRegistration(Registration registration) {
        //提供一个更新报名表的方法，该方法以编号id为参数,将参数registration记录覆盖旧记录 ok
        try {
            registrationService.updateRegistrationById(registration);
        } catch (Exception e) {
            log.info(e.getMessage());
            return Result.error("更新失败");

        }
        return Result.success("更新成功", registration.getId());
    }

    private RegistrationVO toVO(Registration registration) {
        RegistrationVO registrationVO = new RegistrationVO();
        Match match = matchService.selectByMatchId(registration.getMatchId());
        List<Member> memberList = memberService.getMembersByTeamId(registration.getTeamId());

        List<MemberVO> memberVOList = memberList
                .stream()
                .map(member -> {
                    String userId = member.getUserId();
                    String role = member.getRole();
                    if ("student".equals(role)) {
                        Student student = studentService.selectStudentByStudentId(userId);
                        return new MemberVO(userId, student.getUsername(), student.getPhone(), role);
                    }
                    if ("teacher".equals(role)) {
                        Teacher teacher = teacherService.selectTeacherById(userId);
                        return new MemberVO(userId, teacher.getUsername(), teacher.getPhone(), role);
                    }
                    return new MemberVO(userId, "", "", role);

                })
                .toList();

        registrationVO.setRegistration(registration);
        registrationVO.setMatch(match);
        registrationVO.setMemberVOList(memberVOList);
        return registrationVO;
    }
}