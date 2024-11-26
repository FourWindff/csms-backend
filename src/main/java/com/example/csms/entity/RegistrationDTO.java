package com.example.csms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    private Registration registration;
    private String studentName;
    private String studentPhone;
    private String teacherName;
    private String teacherPhone;
    private String matchName;
}
