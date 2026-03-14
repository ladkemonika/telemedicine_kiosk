package com.telemedicine.kiosk.service;

import com.telemedicine.kiosk.dto.MedicalRecordDTO;
import com.telemedicine.kiosk.entity.MedicalRecord;
import com.telemedicine.kiosk.entity.Patient;
import com.telemedicine.kiosk.exception.ResourceNotFoundException;
import com.telemedicine.kiosk.repository.MedicalRecordRepository;
import com.telemedicine.kiosk.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final MedicalRecordRepository recordRepository;
    private final PatientRepository patientRepository;

    public MedicalRecordDTO uploadRecord(Long patientUserId, String title, String description, String fileUrl) {
        Patient patient = patientRepository.findByUserId(patientUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for user: " + patientUserId));

        MedicalRecord record = MedicalRecord.builder()
                .patient(patient)
                .title(title)
                .description(description)
                .fileUrl(fileUrl)
                .build();
                
        record = recordRepository.save(record);
        return mapToDTO(record);
    }

    public List<MedicalRecordDTO> getPatientRecords(Long patientId) {
        return recordRepository.findByPatientId(patientId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public MedicalRecordDTO getRecord(Long id) {
        MedicalRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found: " + id));
        return mapToDTO(record);
    }

    private MedicalRecordDTO mapToDTO(MedicalRecord record) {
        return MedicalRecordDTO.builder()
                .id(record.getId())
                .patientId(record.getPatient().getId())
                .title(record.getTitle())
                .description(record.getDescription())
                .fileUrl(record.getFileUrl())
                .uploadedAt(record.getUploadedAt().toString())
                .build();
    }
}
