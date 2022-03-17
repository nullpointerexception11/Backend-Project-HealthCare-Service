package com.springproject.backendprojecthealthcareservice.service;

import com.springproject.backendprojecthealthcareservice.dto.DoctorDTO;
import com.springproject.backendprojecthealthcareservice.dto.NurseDTO;
import com.springproject.backendprojecthealthcareservice.dto.SecretaryDTO;
import com.springproject.backendprojecthealthcareservice.exception.ResourceNotFoundException;
import com.springproject.backendprojecthealthcareservice.model.Department;
import com.springproject.backendprojecthealthcareservice.model.enumeration.Departments;
import com.springproject.backendprojecthealthcareservice.repository.DepartmentRepository;
import com.springproject.backendprojecthealthcareservice.repository.DoctorRepository;
import com.springproject.backendprojecthealthcareservice.repository.NurseRepository;
import com.springproject.backendprojecthealthcareservice.repository.SecretaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;
    private final SecretaryRepository secretaryRepository;

    public List<DoctorDTO> findDoctorsByDepartmentName(Departments departmentName){

        Department department = departmentRepository.findByName(departmentName).orElseThrow(()->
                new RuntimeException("Error: Department is not found"));
        return doctorRepository.findByDepartment(department);
    }
    public List<NurseDTO> findNursesByDepartmentName(Departments departmentName){
        Department department = departmentRepository.findByName(departmentName).orElseThrow(()->
                new RuntimeException("Error: Department is not found"));
        return nurseRepository.findByDepartment(department);
    }
    public List<SecretaryDTO> findSecretaryByDepartmentName(Departments departmentName){
        Department department = departmentRepository.findByName(departmentName).orElseThrow(()->
                new RuntimeException("Error: Department is not found"));
        return secretaryRepository.findByDepartment(department);
    }

    public Map<String, Object> findAllDepartmentStaff(Departments departmentName){
        Department department = departmentRepository.findByName(departmentName).orElseThrow(()->
                new RuntimeException("Error: Department is not found"));

        List<DoctorDTO> doctors = doctorRepository.findByDepartment(department);
        List<NurseDTO> nurses = nurseRepository.findByDepartment(department);
        List<SecretaryDTO> secretaries = secretaryRepository.findByDepartment(department);

        Map<String, Object> map = new HashMap<>();
        map.put("Doctors", doctors);
        map.put("Nurses", nurses);
        map.put("Secretaries", secretaries);

        return map;
    }




}
