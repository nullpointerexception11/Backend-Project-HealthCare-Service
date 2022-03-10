package com.springproject.backendprojecthealthcareservice.repository;

import com.springproject.backendprojecthealthcareservice.exception.ResourceNotFoundException;
import com.springproject.backendprojecthealthcareservice.model.Role;
import com.springproject.backendprojecthealthcareservice.model.enumeration.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(UserRole userRole) throws ResourceNotFoundException;
}
