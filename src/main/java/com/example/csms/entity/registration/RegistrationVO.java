package com.example.csms.entity.registration;

import com.example.csms.entity.Match;
import com.example.csms.entity.member.MemberDTO;
import com.example.csms.entity.member.MemberVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationVO {
    private Registration registration;
    private Match match;
    private List<MemberVO> memberVOList;
}
