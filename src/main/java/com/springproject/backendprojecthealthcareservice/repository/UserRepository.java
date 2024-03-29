package com.springproject.backendprojecthealthcareservice.repository;

import com.springproject.backendprojecthealthcareservice.dto.UserDTO;
import com.springproject.backendprojecthealthcareservice.exception.ConflictException;
import com.springproject.backendprojecthealthcareservice.exception.ResourceNotFoundException;
import com.springproject.backendprojecthealthcareservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<UserDTO> findAllBy();

    Optional<UserDTO> findByIdOrderByFirstName(Long id) throws ResourceNotFoundException;

    boolean existsByEmail(String email) throws ConflictException;

    Optional<User> findByEmail(String email) throws ResourceNotFoundException;

    @Modifying
    @Query("UPDATE User u SET u.enabled = TRUE WHERE u.email = ?1")
    void enableAppUser(String email);

}
