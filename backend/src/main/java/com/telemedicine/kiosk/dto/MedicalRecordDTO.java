package com.telemedicine.kiosk.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicalRecordDTO {
    private Long id;
    private Long patientId;
    private String title;
    private String description;
    private String fileUrl;
    private String uploadedAt;
}
