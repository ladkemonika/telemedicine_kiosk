package com.telemedicine.kiosk.service;

import com.telemedicine.kiosk.dto.DoctorDTO;
import com.telemedicine.kiosk.dto.UserDTO;
import com.telemedicine.kiosk.entity.Doctor;
import com.telemedicine.kiosk.exception.ResourceNotFoundException;
import com.telemedicine.kiosk.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        return mapToDTO(doctor);
    }
    
    public DoctorDTO getDoctorByUserId(Long userId) {
        Doctor doctor = doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found for user: " + userId));
        return mapToDTO(doctor);
    }

    private DoctorDTO mapToDTO(Doctor doctor) {
        UserDTO userDTO = UserDTO.builder()
                .id(doctor.getUser().getId())
                .firstName(doctor.getUser().getFirstName())
                .lastName(doctor.getUser().getLastName())
                .email(doctor.getUser().getEmail())
                .role(doctor.getUser().getRole().name())
                .build();

        return DoctorDTO.builder()
                .id(doctor.getId())
                .user(userDTO)
                .specialization(doctor.getSpecialization())
                .experienceYears(doctor.getExperienceYears())
                .qualification(doctor.getQualification())
                .department(doctor.getDepartment())
                .availabilitySchedule(doctor.getAvailabilitySchedule())
                .build();
    }
}
