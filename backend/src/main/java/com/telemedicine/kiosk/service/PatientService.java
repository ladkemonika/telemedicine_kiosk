package com.telemedicine.kiosk.service;

import com.telemedicine.kiosk.dto.PatientDTO;
import com.telemedicine.kiosk.dto.UserDTO;
import com.telemedicine.kiosk.entity.Patient;
import com.telemedicine.kiosk.exception.ResourceNotFoundException;
import com.telemedicine.kiosk.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        return mapToDTO(patient);
    }

    public PatientDTO getPatientByUserId(Long userId) {
        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for user: " + userId));
        return mapToDTO(patient);
    }

    private PatientDTO mapToDTO(Patient patient) {
        UserDTO userDTO = UserDTO.builder()
                .id(patient.getUser().getId())
                .firstName(patient.getUser().getFirstName())
                .lastName(patient.getUser().getLastName())
                .email(patient.getUser().getEmail())
                .role(patient.getUser().getRole().name())
                .build();

        return PatientDTO.builder()
                .id(patient.getId())
                .user(userDTO)
                .dateOfBirth(patient.getDateOfBirth().toString())
                .gender(patient.getGender())
                .bloodGroup(patient.getBloodGroup())
                .address(patient.getAddress())
                .build();
    }
}
