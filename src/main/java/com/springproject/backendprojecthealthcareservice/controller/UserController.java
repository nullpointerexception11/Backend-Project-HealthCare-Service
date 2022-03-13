package com.springproject.backendprojecthealthcareservice.controller;

import com.springproject.backendprojecthealthcareservice.dto.AdminDTO;
import com.springproject.backendprojecthealthcareservice.dto.UserDTO;
import com.springproject.backendprojecthealthcareservice.security.jwt.JwtUtils;
import com.springproject.backendprojecthealthcareservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping()
@RestController
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @GetMapping("/user/auth/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUser() {
        List<UserDTO> users = userService.fetchAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserByIdAdmin(@PathVariable Long id) {
        UserDTO user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('DOCTOR')" +
            " or hasRole('NURSE') or hasRole('SECRETARY')")
    public ResponseEntity<UserDTO> getUserById(HttpServletRequest request) {
        Long id = (Long) request.getAttribute("id");
        UserDTO userDTO = userService.findById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Boolean>> registerUser(@Valid @RequestBody AdminDTO adminDTO) {
        userService.register(adminDTO);

        Map<String, Boolean> map = new HashMap<>();
        map.put("Registered successfully! Please confirm your email", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping(path = "/user/confirm")
    public String confirm(@RequestParam("token") String token) {
        return userService.confirmToken(token);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody Map<String, Object> userMap) {

        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

        userService.login(email, password);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/user/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> updateUserAuth(@PathVariable Long id,
                                                               @Valid @RequestBody AdminDTO adminDTO) {

        userService.updateUserAuth(id, adminDTO);
        Map<String, Boolean> map = new HashMap<>();
        map.put("Success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PatchMapping("/user/password")
    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('DOCTOR')" +
            " or hasRole('NURSE') or hasRole('SECRETARY')")
    public ResponseEntity<Map<String, Boolean>> updatePassword(HttpServletRequest request,
                                                               @RequestBody Map<String, Object> userMap) {
        Long id = (Long) request.getAttribute("id");
        String newPassword = (String) userMap.get("newPassword");
        String oldPassword = (String) userMap.get("oldPassword");
        userService.updatePassword(id, newPassword, oldPassword);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK)
    }

    @DeleteMapping("/user/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
