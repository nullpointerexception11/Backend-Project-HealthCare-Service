package com.springproject.backendprojecthealthcareservice.service;

import com.springproject.backendprojecthealthcareservice.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
}
