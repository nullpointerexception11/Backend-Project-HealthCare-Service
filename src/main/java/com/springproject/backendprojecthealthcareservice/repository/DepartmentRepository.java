package com.springproject.backendprojecthealthcareservice.repository;

import com.springproject.backendprojecthealthcareservice.model.Department;
import com.springproject.backendprojecthealthcareservice.model.enumeration.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(Departments name);
}
