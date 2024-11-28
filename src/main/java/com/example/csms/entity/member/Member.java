package com.example.csms.entity.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("member")

public class Member {
    @TableId
    private String userId;
    @TableField()
    private String teamId;

    private String role;

    public Member(MemberDTO memberDTO, String teamId) {
        this.userId = memberDTO.getUserId();
        this.teamId = teamId;
        this.role = memberDTO.getRole();
    }
}
