package com.springproject.backendprojecthealthcareservice.repository;

import com.springproject.backendprojecthealthcareservice.dto.DoctorDTO;
import com.springproject.backendprojecthealthcareservice.exception.ResourceNotFoundException;
import com.springproject.backendprojecthealthcareservice.model.Department;
import com.springproject.backendprojecthealthcareservice.model.Doctor;
import com.springproject.backendprojecthealthcareservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<DoctorDTO> findByUserIdOrderByUserId(User userId) throws ResourceNotFoundException;
    Optional<Doctor> findByUserId(User userId) throws ResourceNotFoundException;
    Optional<DoctorDTO> findByIdOrderById(Long id) throws ResourceNotFoundException;
    List<DoctorDTO> findByDepartment(Department department);
    List<DoctorDTO> findAllBy();
}
