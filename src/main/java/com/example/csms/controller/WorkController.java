package com.example.csms.controller;

import com.example.csms.entity.Result;
import com.example.csms.entity.member.Member;
import com.example.csms.entity.work.Work;
import com.example.csms.entity.work.WorkDTO;
import com.example.csms.service.MemberService;
import com.example.csms.service.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class WorkController {
    public final WorkService workService;
    public final MemberService memberService;

    @Value("${web.upload-path}")
    private String UPLOAD_PATH;

    public WorkController(WorkService workService, MemberService memberService) {
        this.workService = workService;
        this.memberService = memberService;
    }

    @PostMapping("/work/upload")
    public Result upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("teamId") String teamId,
                         @RequestParam("userId") String userId,
                         @RequestParam("status") String status) {
        Work work = new Work();
        //保存文件到本地服务器
        String filePath = workService.saveFile(file);
        work.setTeamId(teamId);
        work.setUserId(userId);
        work.setStatus(status);
        work.setFileName(filePath);
        //此处需要一个方法，参数为work，保存该记录  ok
        workService.saveOneWork(work);
        return Result.success("成功提交作品");
    }

    @GetMapping("/work/team/{userId}")
    public Result getTeamInfo(@PathVariable("userId") String userId) {
        List<Member> listMember = memberService.getMembersByUserId(userId);
        log.info(listMember.toString());
        // 使用 Stream 来处理 listMember 并收集对应的 work 记录
        List<Work> listWork = listMember.stream()
                .map(member -> workService.selectWorkByTeamId(member.getTeamId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());  // 收集结果为 List<Work>
                //返回listWork OK
        return Result.success("获取成功", listWork);
    }


    @GetMapping("/work/all")
    public Result getAllWork() {
        List<Work> listWork ;
        //需要一个无参方法，将work表中状态status==PENDING的所有记录筛选，返回集合list OK
        listWork = workService.selectAllPendingWorks();
        return Result.success("成功提交作品", listWork);
    }
    //管理员用于修改状态
    //学生用于修改作品提交时间
    @PostMapping("/work/update")
    public Result updateWork(WorkDTO workDTO,@RequestParam(value = "file",required = false) MultipartFile file) {
        Work work = new Work();
        if("root".equals(workDTO.getUserId())){
            work.setTeamId(workDTO.getTeamId());
            work.setStatus(workDTO.getStatus());
        }else{
            String filePath = workService.saveFile(file);

            work.setFileName(filePath);
            work.setTeamId(workDTO.getTeamId());
            work.setUserId(workDTO.getUserId());
            work.setStatus(workDTO.getStatus());
            work.setLastModifiedDateTime(String.valueOf(LocalDateTime.now()));
        }
        //需要一个以work为参数的更新方法，通过teamId和userId一起筛选出对应记录，使用work覆盖 ok
        workService.updateWorkInfoByTeamIdAndUserId(work);
        return Result.success("提交成功");
    }


    @GetMapping("/work/file/{fileName}")
    public ResponseEntity<Resource> getWork(@PathVariable("fileName") String fileName) throws UnsupportedEncodingException {
        try {
            // 获取文件路径
            Path filePath = Paths.get(UPLOAD_PATH + fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

                // 使用 filename* 属性来支持 UTF-8 编码的文件名
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''" + encodedFileName)
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

