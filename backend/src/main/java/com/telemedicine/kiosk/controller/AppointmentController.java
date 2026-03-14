package com.telemedicine.kiosk.controller;

import com.telemedicine.kiosk.dto.AppointmentDTO;
import com.telemedicine.kiosk.entity.User;
import com.telemedicine.kiosk.service.AppointmentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<AppointmentDTO> bookAppointment(
            @AuthenticationPrincipal User user,
            @RequestBody BookingRequest request) {
        return ResponseEntity.ok(appointmentService.bookAppointment(
                user.getId(), 
                request.getDoctorId(), 
                LocalDateTime.parse(request.getDate()), 
                request.getNotes()
        ));
    }

    @GetMapping("/patient")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<AppointmentDTO>> getMyPatientAppointments(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(appointmentService.getPatientAppointments(user.getId()));
    }

    @GetMapping("/doctor")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<AppointmentDTO>> getMyDoctorAppointments(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(appointmentService.getDoctorAppointments(user.getId()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'ADMIN')")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentDetails(id));
    }
}

@Data
class BookingRequest {
    private Long doctorId;
    private String date; // ISO "yyyy-MM-dd'T'HH:mm:ss"
    private String notes;
}
