package com.example.csms.entity.registration;

import com.example.csms.entity.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    private String matchId;
    private String teamId;
    private List<MemberDTO> memberDTOList;
}
