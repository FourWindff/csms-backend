package com.example.csms.entity.work;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("work")
public class Work {
    @TableId("team_id")
    private String teamId;

    private String userId;

    private String fileName;

    private String uploadDateTime;

    private String lastModifiedDateTime;

    private String status;
}
