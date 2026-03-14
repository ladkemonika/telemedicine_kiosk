package com.telemedicine.kiosk.repository;

import com.telemedicine.kiosk.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUserId(Long userId);
    List<Doctor> findByDepartment(String department);
    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);
}
