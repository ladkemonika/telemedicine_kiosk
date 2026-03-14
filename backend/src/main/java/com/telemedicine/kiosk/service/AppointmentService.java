package com.telemedicine.kiosk.service;

import com.telemedicine.kiosk.dto.AppointmentDTO;
import com.telemedicine.kiosk.dto.DoctorDTO;
import com.telemedicine.kiosk.dto.PatientDTO;
import com.telemedicine.kiosk.dto.UserDTO;
import com.telemedicine.kiosk.entity.Appointment;
import com.telemedicine.kiosk.entity.Doctor;
import com.telemedicine.kiosk.entity.Patient;
import com.telemedicine.kiosk.exception.ResourceNotFoundException;
import com.telemedicine.kiosk.repository.AppointmentRepository;
import com.telemedicine.kiosk.repository.DoctorRepository;
import com.telemedicine.kiosk.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentDTO bookAppointment(Long patientUserId, Long doctorId, LocalDateTime date, String notes) {
        Patient patient = patientRepository.findByUserId(patientUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for user: " + patientUserId));
        
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found: " + doctorId));
        
        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .appointmentDate(date)
                .status(Appointment.Status.SCHEDULED)
                .notes(notes)
                .build();
                
        appointment = appointmentRepository.save(appointment);
        return mapToDTO(appointment);
    }
    
    public List<AppointmentDTO> getPatientAppointments(Long patientUserId) {
        Patient patient = patientRepository.findByUserId(patientUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for user: " + patientUserId));
        
        return appointmentRepository.findByPatientId(patient.getId()).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getDoctorAppointments(Long doctorUserId) {
         Doctor doctor = doctorRepository.findByUserId(doctorUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found for user: " + doctorUserId));
                
        return appointmentRepository.findByDoctorId(doctor.getId()).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AppointmentDTO getAppointmentDetails(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found: " + id));
        return mapToDTO(appointment);
    }

    private AppointmentDTO mapToDTO(Appointment appointment) {
        UserDTO doctorUser = UserDTO.builder()
                .id(appointment.getDoctor().getUser().getId())
                .firstName(appointment.getDoctor().getUser().getFirstName())
                .lastName(appointment.getDoctor().getUser().getLastName())
                .email(appointment.getDoctor().getUser().getEmail())
                .role("DOCTOR")
                .build();
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(appointment.getDoctor().getId())
                .user(doctorUser)
                .specialization(appointment.getDoctor().getSpecialization())
                .build();

        UserDTO patientUser = UserDTO.builder()
                .id(appointment.getPatient().getUser().getId())
                .firstName(appointment.getPatient().getUser().getFirstName())
                .lastName(appointment.getPatient().getUser().getLastName())
                .email(appointment.getPatient().getUser().getEmail())
                .role("PATIENT")
                .build();
        PatientDTO patientDTO = PatientDTO.builder()
                .id(appointment.getPatient().getId())
                .user(patientUser)
                .build();

        return AppointmentDTO.builder()
                .id(appointment.getId())
                .doctor(doctorDTO)
                .patient(patientDTO)
                .appointmentDate(appointment.getAppointmentDate().toString())
                .status(appointment.getStatus().name())
                .notes(appointment.getNotes())
                .build();
    }
}
