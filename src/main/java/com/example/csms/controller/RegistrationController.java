package com.example.csms.controller;

import com.example.csms.entity.*;
import com.example.csms.service.MatchService;
import com.example.csms.service.RegistrationService;
import com.example.csms.service.StudentService;
import com.example.csms.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);
    private final RegistrationService registrationService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final MatchService matchService;

    public RegistrationController(RegistrationService registrationService, StudentService studentService, TeacherService teacherService, MatchService matchService) {
        this.registrationService = registrationService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.matchService = matchService;
    }

    /*报名操作开始*/
    //报名竞赛，初始为未审核PENDING
    @PostMapping("/registration/saveRegistration")
    public Result saveRegistration(Registration registration) {
        String studentId = registration.getStudentId();
        String teacherId = registration.getTeacherId();

        if (!studentService.isIdExistInStudentTable(studentId)) {
            return Result.error("学生ID不存在");
        }
        if (teacherId != null && !teacherService.isIdExistInTeacherTable(teacherId)) {
            return Result.error("教师ID不存在");
        }

        registration.setStatus("PENDING");
        int num = registrationService.saveRegistration(registration);
        if (num > 0) {
            return Result.success();
        } else {
            return Result.error("报名失败");
        }
    }

    //获取报名表（管理员审核）
    @PostMapping("/registration/getAllRegistration")
    public Result getAllRegistration(Registration registration) {
        if (registration.getStatus() == null) {
            List<Registration> result = registrationService.selectRegistrationAll();
            log.info("获取所有报名表成功，共{}条记录", result.size());
            return Result.success(result.stream().map(this::toDTO).collect(Collectors.toList()));
        } else {
            List<Registration> result = registrationService.selectRegistrationByStatus(registration.getStatus());
            log.info("获取{}报名表成功，共{}条记录", registration.getStatus(), result.size());
            return Result.success(result.stream().map(this::toDTO).collect(Collectors.toList()));
        }

    }


    //学生获取报名表
    @GetMapping("/registration/student/{userId}")
    public Result getAllRegistration(@PathVariable String userId) {
        List<Registration> result = registrationService.selectRegistrationByStudentId(userId);
        if (result == null) return Result.error("学生当前没有报名信息");
        return Result.success(result.stream().map(this::toDTO).collect(Collectors.toList()));
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

    private RegistrationDTO toDTO(Registration registration) {
        // 获取学生、教师和比赛的详细信息
        Student student = studentService.selectStudentByStudentId(registration.getStudentId());
        Teacher teacher = teacherService.selectTeacherById(registration.getTeacherId());
        Match match = matchService.selectByMatchId(registration.getMatchId());

        // 构建 DTO
        RegistrationDTO dto = new RegistrationDTO();
        dto.setRegistration(registration);
        dto.setStudentName(student != null ? student.getUsername() : null);
        dto.setStudentPhone(student != null ? student.getPhone() : null);
        dto.setTeacherName(teacher != null ? teacher.getUsername() : null);
        dto.setTeacherPhone(teacher != null ? teacher.getPhone() : null);
        dto.setMatchName(match != null ? match.getName() : null);

        return dto;
    }
}