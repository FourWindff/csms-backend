package com.example.csms.entity.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    private String userId;
    private String username;
    private String phone;
    private String role;
}
