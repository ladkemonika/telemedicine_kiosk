package com.telemedicine.kiosk.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientDTO {
    private Long id;
    private UserDTO user;
    private String dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String address;
}
