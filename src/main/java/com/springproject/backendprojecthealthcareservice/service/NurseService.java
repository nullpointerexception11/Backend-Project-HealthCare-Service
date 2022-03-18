package com.springproject.backendprojecthealthcareservice.service;

import com.springproject.backendprojecthealthcareservice.dto.NurseDTO;
import com.springproject.backendprojecthealthcareservice.exception.BadRequestException;
import com.springproject.backendprojecthealthcareservice.exception.ResourceNotFoundException;
import com.springproject.backendprojecthealthcareservice.model.Department;
import com.springproject.backendprojecthealthcareservice.model.Nurse;
import com.springproject.backendprojecthealthcareservice.model.User;
import com.springproject.backendprojecthealthcareservice.repository.DepartmentRepository;
import com.springproject.backendprojecthealthcareservice.repository.NurseRepository;
import com.springproject.backendprojecthealthcareservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class NurseService {

    private final NurseRepository nurseRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";
    private final static String NURSE_NOT_FOUND_MSG = "nurse with id %d not found";
    private final static String NURSE_WITH_USER_ID_NOT_FOUND_MSG = "nurse with user id %d not found";

    public NurseDTO findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        return nurseRepository.findByUserIdOrderByUserId(user).orElseThrow(() ->
                new ResourceNotFoundException(String.format(NURSE_WITH_USER_ID_NOT_FOUND_MSG, userId)));
    }

    public NurseDTO findById(Long id) {
        return nurseRepository.findByIdOrderById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(NURSE_NOT_FOUND_MSG, id)));
    }

    public List<NurseDTO> findAll() {
        return nurseRepository.findAllBy();
    }

    public void addNurse(Long userId, NurseDTO nurseDTO) throws BadRequestException {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        Department departments = departmentRepository.findByName(nurseDTO.getDepartment()).orElseThrow(() ->
                new RuntimeException("Error: Department is not found"));

        Nurse nurse = new Nurse(user, departments);
        nurseRepository.save(nurse);
    }

    public void updateNurse(Long userId, NurseDTO nurseDTO) throws BadRequestException {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        Nurse nurse = nurseRepository.findByUserId(user).orElseThrow(() ->
                new ResourceNotFoundException(String.format(NURSE_WITH_USER_ID_NOT_FOUND_MSG, userId)));

        Department department = departmentRepository.findByName(nurseDTO.getDepartment()).orElseThrow(() ->
                new RuntimeException("Error: Department is not found"));

        nurse.setDepartment(department);
        nurseRepository.save(nurse);
    }

    public void deleteById(Long id) throws BadRequestException {
        nurseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(NURSE_NOT_FOUND_MSG, id)));

        nurseRepository.deleteById(id);
    }
}
