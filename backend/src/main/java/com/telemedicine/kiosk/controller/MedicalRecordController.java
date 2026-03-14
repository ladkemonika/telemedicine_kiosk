package com.telemedicine.kiosk.controller;

import com.telemedicine.kiosk.dto.MedicalRecordDTO;
import com.telemedicine.kiosk.entity.User;
import com.telemedicine.kiosk.service.MedicalRecordService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<MedicalRecordDTO> uploadRecord(
            @AuthenticationPrincipal User user,
            @RequestBody RecordUploadRequest request) {
        return ResponseEntity.ok(medicalRecordService.uploadRecord(
                user.getId(), 
                request.getTitle(), 
                request.getDescription(), 
                request.getFileUrl()
        ));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
    public ResponseEntity<List<MedicalRecordDTO>> getPatientRecords(@PathVariable Long patientId) {
        return ResponseEntity.ok(medicalRecordService.getPatientRecords(patientId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
    public ResponseEntity<MedicalRecordDTO> getRecord(@PathVariable Long id) {
        return ResponseEntity.ok(medicalRecordService.getRecord(id));
    }
}

@Data
class RecordUploadRequest {
    private String title;
    private String description;
    private String fileUrl; // URL obtained from a cloud storage after client-side upload or base64 mock
}
