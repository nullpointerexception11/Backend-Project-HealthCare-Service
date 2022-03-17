package com.springproject.backendprojecthealthcareservice.repository;

import com.springproject.backendprojecthealthcareservice.dto.PatientDTO;
import com.springproject.backendprojecthealthcareservice.exception.ResourceNotFoundException;
import com.springproject.backendprojecthealthcareservice.model.Patient;
import com.springproject.backendprojecthealthcareservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<PatientDTO> findByIdAndUserId(Long id, User userId) throws ResourceNotFoundException;

    Optional<Patient> findByIdAndUserIdOrderById (Long id, User userId) throws ResourceNotFoundException;

    List<Patient> findByUserId(User user);

    List<PatientDTO> findByUserIdOrderById(User userId) throws ResourceNotFoundException;

    Optional<PatientDTO> findByIdOrderById(Long id) throws ResourceNotFoundException;

    List<PatientDTO> findAllBy();
}
