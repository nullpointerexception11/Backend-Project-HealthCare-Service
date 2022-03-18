package com.springproject.backendprojecthealthcareservice.service;

import com.springproject.backendprojecthealthcareservice.dto.DoctorDTO;
import com.springproject.backendprojecthealthcareservice.exception.BadRequestException;
import com.springproject.backendprojecthealthcareservice.exception.ResourceNotFoundException;
import com.springproject.backendprojecthealthcareservice.model.*;
import com.springproject.backendprojecthealthcareservice.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final SecretaryRepository secretaryRepository;
    private final DepartmentRepository departmentRepository;
    private final FileDBRepository fileDBRepository;

    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";
    private final static String SECRETARY_NOT_FOUND_MSG = "secretary with id %d not found";
    private final static String DOCTOR_NOT_FOUND_MSG = "doctor with id %d not found";
    private final static String DOCTOR_WITH_USER_ID_NOT_FOUND_MSG = "doctor with user id with id %d not found";
    private final static String FILE_NOT_FOUND_MSG = "file with id %d not found";

    public DoctorDTO findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        return doctorRepository.findByUserIdOrderByUserId(user).orElseThrow(() ->
                new ResourceNotFoundException(String.format(DOCTOR_WITH_USER_ID_NOT_FOUND_MSG, userId)));
    }

    public DoctorDTO findById(Long id) {
        return doctorRepository.findByIdOrderById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(DOCTOR_NOT_FOUND_MSG, id)));
    }

    public List<DoctorDTO> findAll() {
        return doctorRepository.findAllBy();
    }

    public void addDoctor(Long userId, Long secretaryId, String fileId, DoctorDTO doctorDTO) throws BadRequestException {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));
        Secretary secretary = secretaryRepository.findById(secretaryId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(SECRETARY_NOT_FOUND_MSG, secretaryId)));
        FileDB fileDB = fileDBRepository.findById(fileId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(FILE_NOT_FOUND_MSG, fileId)));
        Department department = departmentRepository.findByName(doctorDTO.getDepartment()).orElseThrow(() ->
                new RuntimeException("Error: Department is not found"));
        Doctor doctor = new Doctor(user, secretary, doctorDTO.getProfession(), department,
                doctorDTO.getAppointmentFee(), (new FileDB()).setFile(fileDB));

        doctorRepository.save(doctor);
    }

    public void updateDoctor(Long userId, Long secretaryId, String fileId, DoctorDTO doctorDTO) throws BadRequestException {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));
        Secretary secretary = secretaryRepository.findById(secretaryId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(SECRETARY_NOT_FOUND_MSG, secretaryId)));
        FileDB fileDB = fileDBRepository.findById(fileId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(FILE_NOT_FOUND_MSG, fileId)));
        Department department = departmentRepository.findByName(doctorDTO.getDepartment()).orElseThrow(() ->
                new RuntimeException("Error: Department is not found"));
        Doctor doctor = doctorRepository.findByUserId(user).orElseThrow(() ->
                new ResourceNotFoundException(String.format(DOCTOR_NOT_FOUND_MSG, userId)));
        Doctor doctor1 = new Doctor(doctor.getId(), user, secretary, doctorDTO.getProfession(), department,
                doctorDTO.getAppointmentFee(), doctorDTO.getIsAvailable(), (new FileDB()).setFile(fileDB));

        doctorRepository.save(doctor1);
    }

    public void deleteById(Long id) throws BadRequestException {

        doctorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(DOCTOR_NOT_FOUND_MSG)));

        doctorRepository.deleteById(id);
    }
}
