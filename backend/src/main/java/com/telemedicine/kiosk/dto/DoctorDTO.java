package com.telemedicine.kiosk.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorDTO {
    private Long id;
    private UserDTO user;
    private String specialization;
    private Integer experienceYears;
    private String qualification;
    private String department;
    private String availabilitySchedule;
}
