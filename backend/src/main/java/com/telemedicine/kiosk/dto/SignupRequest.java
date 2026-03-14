package com.telemedicine.kiosk.dto;

import com.telemedicine.kiosk.entity.Role;
import lombok.Data;

@Data
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Role role; // ADMIN, DOCTOR, PATIENT

    // For Patients
    private String dateOfBirth; // e.g., "YYYY-MM-DD"
    private String gender;
    private String bloodGroup;
    private String address;

    // For Doctors
    private String specialization;
    private Integer experienceYears;
    private String qualification;
    private String department;
}
