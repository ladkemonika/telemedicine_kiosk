package com.telemedicine.kiosk.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrescriptionDTO {
    private Long id;
    private Long appointmentId;
    private String medications;
    private String instructions;
    private String createdAt;
}
