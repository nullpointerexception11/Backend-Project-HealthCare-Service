package com.springproject.backendprojecthealthcareservice.repository;

import com.springproject.backendprojecthealthcareservice.dto.SecretaryDTO;
import com.springproject.backendprojecthealthcareservice.exception.ResourceNotFoundException;
import com.springproject.backendprojecthealthcareservice.model.Department;
import com.springproject.backendprojecthealthcareservice.model.Secretary;
import com.springproject.backendprojecthealthcareservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionScoped;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface SecretaryRepository extends JpaRepository<Secretary, Long> {

    Optional<SecretaryDTO> findByUserIdOrderByUserId(User userId) throws ResourceNotFoundException;

    Optional<Secretary> findByUserId(User userId) throws ResourceNotFoundException;

    Optional<SecretaryDTO> findByIdOrderById(Long id) throws ResourceNotFoundException;

    List<SecretaryDTO> findByDepartment(Department department);

    List<SecretaryDTO> findAllBy();
}
