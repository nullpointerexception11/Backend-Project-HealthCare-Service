package com.springproject.backendprojecthealthcareservice.service;

import com.springproject.backendprojecthealthcareservice.model.ConfirmationToken;
import com.springproject.backendprojecthealthcareservice.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }
    public Optional<ConfirmationToken> getToken(String token){
        return confirmationTokenRepository.findByToken(token);
    }
    public int setConfirmedDate(String token){
        return confirmationTokenRepository.updateConfirmedDate(token, LocalDateTime.now());
    }
}
