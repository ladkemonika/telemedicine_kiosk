package com.telemedicine.kiosk.controller;

import com.telemedicine.kiosk.dto.PrescriptionDTO;
import com.telemedicine.kiosk.entity.User;
import com.telemedicine.kiosk.service.PatientService;
import com.telemedicine.kiosk.service.PrescriptionService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final PatientService patientService;

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<PrescriptionDTO> createPrescription(
            @AuthenticationPrincipal User user,
            @RequestBody PrescriptionRequest request) {
        return ResponseEntity.ok(prescriptionService.createPrescription(
                user.getId(), 
                request.getAppointmentId(), 
                request.getMedications(), 
                request.getInstructions()
        ));
    }

    @GetMapping("/appointment/{id}")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
    public ResponseEntity<PrescriptionDTO> getByAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionByAppointment(id));
    }

    @GetMapping("/patient")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<PrescriptionDTO>> getMyPrescriptions(@AuthenticationPrincipal User user) {
        Long patientId = patientService.getPatientByUserId(user.getId()).getId();
        return ResponseEntity.ok(prescriptionService.getPatientPrescriptions(patientId));
    }
}

@Data
class PrescriptionRequest {
    private Long appointmentId;
    private String medications;
    private String instructions;
}
