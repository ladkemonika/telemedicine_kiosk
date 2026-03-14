package com.telemedicine.kiosk.service;

import com.telemedicine.kiosk.dto.SignupRequest;
import com.telemedicine.kiosk.dto.UserDTO;
import com.telemedicine.kiosk.entity.Doctor;
import com.telemedicine.kiosk.entity.Patient;
import com.telemedicine.kiosk.entity.Role;
import com.telemedicine.kiosk.entity.User;
import com.telemedicine.kiosk.exception.ResourceNotFoundException;
import com.telemedicine.kiosk.repository.DoctorRepository;
import com.telemedicine.kiosk.repository.PatientRepository;
import com.telemedicine.kiosk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Error: Email is already in use!");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(request.getRole())
                .build();

        user = userRepository.save(user);

        // Add additional profile info based on role
        if (request.getRole() == Role.DOCTOR) {
            Doctor doctor = Doctor.builder()
                    .user(user)
                    .specialization(request.getSpecialization())
                    .experienceYears(request.getExperienceYears())
                    .qualification(request.getQualification())
                    .department(request.getDepartment())
                    .build();
            doctorRepository.save(doctor);
        } else if (request.getRole() == Role.PATIENT) {
            Patient patient = Patient.builder()
                    .user(user)
                    .dateOfBirth(request.getDateOfBirth() != null ? LocalDate.parse(request.getDateOfBirth()) : LocalDate.now())
                    .gender(request.getGender())
                    .bloodGroup(request.getBloodGroup())
                    .address(request.getAddress())
                    .build();
            patientRepository.save(patient);
        }
        
        return user;
    }

    public UserDTO getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .build();
    }
}
