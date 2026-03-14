package com.telemedicine.kiosk.service;

import com.telemedicine.kiosk.dto.PrescriptionDTO;
import com.telemedicine.kiosk.entity.Appointment;
import com.telemedicine.kiosk.entity.Prescription;
import com.telemedicine.kiosk.exception.ResourceNotFoundException;
import com.telemedicine.kiosk.repository.AppointmentRepository;
import com.telemedicine.kiosk.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;

    public PrescriptionDTO createPrescription(Long doctorUserId, Long appointmentId, String medications, String instructions) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found: " + appointmentId));

        if (!appointment.getDoctor().getUser().getId().equals(doctorUserId)) {
            throw new IllegalArgumentException("Doctor is not authorized for this appointment");
        }

        Prescription prescription = Prescription.builder()
                .appointment(appointment)
                .medications(medications)
                .instructions(instructions)
                .build();

        prescription = prescriptionRepository.save(prescription);
        
        // Update appointment status
        appointment.setStatus(Appointment.Status.COMPLETED);
        appointmentRepository.save(appointment);

        return mapToDTO(prescription);
    }
    
    public PrescriptionDTO getPrescriptionByAppointment(Long appointmentId) {
        Prescription prescription = prescriptionRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found for appointment: " + appointmentId));
        return mapToDTO(prescription);
    }
    
    public List<PrescriptionDTO> getPatientPrescriptions(Long patientId) {
        return prescriptionRepository.findByAppointmentPatientId(patientId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private PrescriptionDTO mapToDTO(Prescription prescription) {
        return PrescriptionDTO.builder()
                .id(prescription.getId())
                .appointmentId(prescription.getAppointment().getId())
                .medications(prescription.getMedications())
                .instructions(prescription.getInstructions())
                .createdAt(prescription.getCreatedAt().toString())
                .build();
    }
}
