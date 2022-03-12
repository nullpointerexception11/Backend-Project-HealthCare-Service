package com.springproject.backendprojecthealthcareservice.repository;

import com.springproject.backendprojecthealthcareservice.exception.ResourceNotFoundException;
import com.springproject.backendprojecthealthcareservice.model.ConfirmationToken;
import com.springproject.backendprojecthealthcareservice.model.User;
import net.bytebuddy.description.NamedElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationTokenRepository, Long> {

    Optional<ConfirmationToken> findByToken(String token) throws ResourceNotFoundException;

    Optional<ConfirmationToken> findByUser(User user) throws ResourceNotFoundException;

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c SET c.confirmedDate = ?2 WHERE c.token = ?1")
    int updateConfirmedDate(String token, LocalDateTime confirmedDate);
}
