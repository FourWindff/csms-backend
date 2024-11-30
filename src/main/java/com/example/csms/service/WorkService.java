package com.example.csms.service;

import com.example.csms.entity.work.Work;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WorkService {
    //保存文件
    String saveFile(MultipartFile multipartFile);
    //返回所有作品集
    List<Work> selectWorkAll();
    //保存一个作品记录
    void saveOneWork(Work work);
    //返回所有未审核的作品
    List<Work> selectAllPendingWorks();
    //根据学生id与团队id更新作品记录
    void updateWorkInfoByTeamIdAndUserId(Work work);
    //根据团队id返回其一条作品记录，控制层记得循环取
    Work selectWorkByTeamId(String teamId);
}
