package com.example.csms.entity.registration;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("registration_char")
public class Registration {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    private String matchId;
    private String status;
    private String teamId;

    public Registration(String matchId, String status, String teamId) {
        this.matchId = matchId;
        this.status = status;
        this.teamId = teamId;
    }
}