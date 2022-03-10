package com.springproject.backendprojecthealthcareservice.security.service;

import com.springproject.backendprojecthealthcareservice.model.User;
import com.springproject.backendprojecthealthcareservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with email: " + email));

        return UserDetailsImpl.build(user);
    }
}
