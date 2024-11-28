package com.example.csms.service;

import com.example.csms.entity.registration.Registration;

import java.util.List;

public interface RegistrationService {

    int saveRegistration(Registration registrationChar);
    void updateRegistrationById(Registration registration) throws Exception;


    List<Registration> selectRegistrationByStudentId(String studentId);
    List<Registration> selectRegistrationByTeacherId(String teacherId);
    List<Registration> selectRegistrationByStatus(String status);
    List<Registration> selectRegistrationAll();


}