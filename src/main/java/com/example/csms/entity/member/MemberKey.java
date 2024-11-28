package com.example.csms.entity.member;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberKey implements Serializable {
    private String userId;
    private String teamId;
}
