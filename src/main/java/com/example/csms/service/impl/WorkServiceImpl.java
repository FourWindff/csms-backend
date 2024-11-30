package com.example.csms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.csms.entity.work.Work;
import com.example.csms.mapper.WorkMapper;
import com.example.csms.service.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class WorkServiceImpl implements WorkService {

    private final WorkMapper workMapper;
    @Value("${web.upload-path}")
    private String UPLOAD_PATH;

    public WorkServiceImpl(WorkMapper workMapper) {
        this.workMapper = workMapper;
    }

    @Override
    public String saveFile(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        if (fileName != null) {
            // 删除文件名中的所有下划线
            fileName = fileName.replace("_", "");
        }
        UUID uuid = UUID.randomUUID();
        String uuidFileName = uuid +"_"+fileName;

        String baseUrl=UPLOAD_PATH;
        String filePath = baseUrl+ File.separator+uuidFileName;
        File file = new File(filePath);

        log.info("存储路径{}",file);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            // 创建父目录，如果父目录的上级目录不存在也一并创建
            parentDir.mkdirs();
        }
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uuidFileName;
    }

    @Override
    public List<Work> selectWorkAll(){
        return workMapper.selectList(null);
    }

    @Override
    public void saveOneWork(Work work){
        work.setUploadDateTime(String.valueOf(LocalDateTime.now()));
        work.setLastModifiedDateTime(String.valueOf(LocalDateTime.now()));
        workMapper.insert(work);
    }

    @Override
    public List<Work> selectAllPendingWorks(){
        QueryWrapper<Work> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "PENDING");
        return workMapper.selectList(queryWrapper);
    }

    @Override
    public void updateWorkInfoByTeamIdAndUserId(Work work){
        QueryWrapper<Work> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", work.getTeamId());
        workMapper.update(work, queryWrapper);
    }

    @Override
    public Work selectWorkByTeamId(String teamId){
        QueryWrapper<Work> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", teamId);
        return workMapper.selectOne(queryWrapper);
    }

}
