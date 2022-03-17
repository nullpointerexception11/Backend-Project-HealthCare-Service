package com.springproject.backendprojecthealthcareservice.service;

import com.springproject.backendprojecthealthcareservice.dto.PatientDTO;
import com.springproject.backendprojecthealthcareservice.exception.ResourceNotFoundException;
import com.springproject.backendprojecthealthcareservice.model.FileDB;
import com.springproject.backendprojecthealthcareservice.model.Patient;
import com.springproject.backendprojecthealthcareservice.model.User;
import com.springproject.backendprojecthealthcareservice.repository.FileDBRepository;
import com.springproject.backendprojecthealthcareservice.repository.PatientRepository;
import com.springproject.backendprojecthealthcareservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final FileDBRepository fileDBRepository;
    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";
    private final static String PATIENT_NOT_FOUND_MSG = "patient with id %d not found";


    public PatientDTO findById(Long id, Long userId){
        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        return patientRepository.findByIdAndUserId(id, user).orElseThrow(()->
                new ResourceNotFoundException(String.format(PATIENT_NOT_FOUND_MSG, id)));
    }
    public List<PatientDTO> findByUserId(Long userId){

        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        return patientRepository.findByUserIdOrderById(user);
    }

    public PatientDTO findByIdAuth(Long id){
        return patientRepository.findByIdOrderById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(PATIENT_NOT_FOUND_MSG, id)));
    }
    public List<PatientDTO> findAll(){
        return patientRepository.findAllBy();
    }

    public void addPatient(Long userId, Patient patient){

        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        patient.setUserId(user);
        patientRepository.save(patient);
    }

    public void updatePatient(Long id, Long userId, Patient patient){

        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));
        Patient patient1 = patientRepository.findByIdAndUserIdOrderById(id, user).orElseThrow(()->
                new ResourceNotFoundException(String.format(PATIENT_NOT_FOUND_MSG, id)));

        patient1.setMedicalHistory(patient.getMedicalHistory());
        patient1.setDisease(patient.getDisease());

        patientRepository.save(patient1);
    }

    public void updatePatientAuth(Long id, String medicalImagingId, String prescriptionsId,
                                  String bloodTestsId, String diagnosis){

        Patient patient = patientRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(PATIENT_NOT_FOUND_MSG, id)));

        FileDB medicalImaging = fileDBRepository.findById(medicalImagingId).orElseThrow(()->
                new ResourceNotFoundException(String.format("medical image with id %d not found", id)));

        FileDB prescriptions = fileDBRepository.findById(prescriptionsId).orElseThrow(()->
                new ResourceNotFoundException(String.format("prescriptions with id %d not found", id)));

        FileDB bloodTests = fileDBRepository.findById(bloodTestsId).orElseThrow(()->
                new ResourceNotFoundException(String.format("blood tests with id %d not found", id)));

        FileDB file = new FileDB();
        patient.setMedicalImaging(file.setFile(medicalImaging));
        patient.setPrescriptions(file.setFile(prescriptions));
        patient.setBloodTests(file.setFile(bloodTests));
        patient.setDiagnosis(diagnosis);

        patientRepository.save(patient);
    }
}
