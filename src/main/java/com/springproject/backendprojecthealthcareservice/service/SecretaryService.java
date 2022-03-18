package com.springproject.backendprojecthealthcareservice.service;

import com.springproject.backendprojecthealthcareservice.dto.SecretaryDTO;
import com.springproject.backendprojecthealthcareservice.exception.BadRequestException;
import com.springproject.backendprojecthealthcareservice.exception.ResourceNotFoundException;
import com.springproject.backendprojecthealthcareservice.model.Department;
import com.springproject.backendprojecthealthcareservice.model.Secretary;
import com.springproject.backendprojecthealthcareservice.model.User;
import com.springproject.backendprojecthealthcareservice.repository.DepartmentRepository;
import com.springproject.backendprojecthealthcareservice.repository.SecretaryRepository;
import com.springproject.backendprojecthealthcareservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SecretaryService {

    private final SecretaryRepository secretaryRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";
    private final static String SECRETARY_NOT_FOUND_MSG = "secretary with id %d not found";
    private final static String SECRETARY_WITH_USER_ID_NOT_FOUND_MSG = "secretary with user id %d not found";

    public SecretaryDTO findByUserId(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        return secretaryRepository.findByUserIdOrderByUserId(user).orElseThrow(() ->
                new ResourceNotFoundException(String.format(SECRETARY_WITH_USER_ID_NOT_FOUND_MSG, userId)));
    }

    public SecretaryDTO findById(Long id) {
        return secretaryRepository.findByIdOrderById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(SECRETARY_NOT_FOUND_MSG, id)));
    }

    public List<SecretaryDTO> findAll() {
        return secretaryRepository.findAllBy();
    }

    public void addSecretary(Long userId, SecretaryDTO secretaryDTO) throws BadRequestException {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));
        Department department = departmentRepository.findByName(secretaryDTO.getDepartment()).orElseThrow(() ->
                new RuntimeException("Error: Department is not found"));

        Secretary secretary = new Secretary(user, department);
        secretaryRepository.save(secretary);
    }

    public void updateSecretary(Long userId, SecretaryDTO secretaryDTO) throws BadRequestException {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        Secretary secretary = secretaryRepository.findByUserId(user).orElseThrow(() ->
                new ResourceNotFoundException(String.format(SECRETARY_WITH_USER_ID_NOT_FOUND_MSG, userId)));

        Department department = departmentRepository.findByName(secretaryDTO.getDepartment()).orElseThrow(() ->
                new RuntimeException("Error: Department is not found"));

        secretary.setDepartment(department);
        secretaryRepository.save(secretary);
    }

    public void deleteById(Long id) throws BadRequestException {
        secretaryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(SECRETARY_NOT_FOUND_MSG, id)));

        secretaryRepository.deleteById(id);
    }
}
