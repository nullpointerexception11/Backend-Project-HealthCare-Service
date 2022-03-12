package com.springproject.backendprojecthealthcareservice.service;

import com.springproject.backendprojecthealthcareservice.dto.AdminDTO;
import com.springproject.backendprojecthealthcareservice.dto.UserDTO;
import com.springproject.backendprojecthealthcareservice.exception.BadRequestException;
import com.springproject.backendprojecthealthcareservice.exception.ConflictException;
import com.springproject.backendprojecthealthcareservice.exception.ResourceNotFoundException;
import com.springproject.backendprojecthealthcareservice.repository.ConfirmationTokenRepository;
import com.springproject.backendprojecthealthcareservice.repository.RoleRepository;
import com.springproject.backendprojecthealthcareservice.repository.UserRepository;
import com.springproject.backendprojecthealthcareservice.service.Email.EmailSender;
import com.springproject.backendprojecthealthcareservice.service.Email.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final EmailValidator emailValidator;
    private final PasswordEncoder passwordEncoder;


    private final static String USER_NOT_FOUND_MSG = "user with %id not found";

    public List<UserDTO> fetchAllUsers() {
        return userRepository.findAllBy();
    }

    public UserDTO findById(Long id) throws ResourceNotFoundException {
        return userRepository.findByIdOrderByFirstName(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));
    }

    public void register(AdminDTO adminDTO) throws BadRequestException {

        boolean isValidEmail = emailValidator.test(adminDTO.getEmail());

        if (!isValidEmail)
            throw new IllegalStateException("email not valid");
        if (userRepository.existsByEmail(adminDTO.getEmail()))
            throw new ConflictException("Error: Email is already in use!");
        if (adminDTO.getPassword() == null)
            throw new BadRequestException("Please enter new password");


    }


}
