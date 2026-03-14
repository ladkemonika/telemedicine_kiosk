package com.telemedicine.kiosk.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppointmentDTO {
    private Long id;
    private DoctorDTO doctor;
    private PatientDTO patient;
    private String appointmentDate; // ISO string format
    private String status;
    private String notes;
}
