package com.example.csms.entity.work;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDTO {
    private String teamId;
    private String userId;
    private String status;
}
