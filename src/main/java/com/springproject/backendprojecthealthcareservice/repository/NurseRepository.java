package com.springproject.backendprojecthealthcareservice.repository;

import com.springproject.backendprojecthealthcareservice.dto.NurseDTO;
import com.springproject.backendprojecthealthcareservice.exception.ResourceNotFoundException;
import com.springproject.backendprojecthealthcareservice.model.Department;
import com.springproject.backendprojecthealthcareservice.model.Nurse;
import com.springproject.backendprojecthealthcareservice.model.User;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {

    Optional<NurseDTO> findByUserIdOrderByUserId(User userId) throws ResourceNotFoundException;
    Optional<Nurse> findByUserId(User userId) throws ResourceNotFoundException;
    Optional<NurseDTO> findByIdOrderById(Long id) throws ResourceNotFoundException;
    List<NurseDTO> findByDepartment(Department department);
    List<NurseDTO> findAllBy();
}
