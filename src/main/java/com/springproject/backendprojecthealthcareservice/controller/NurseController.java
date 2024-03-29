package com.springproject.backendprojecthealthcareservice.controller;

import com.springproject.backendprojecthealthcareservice.dto.NurseDTO;
import com.springproject.backendprojecthealthcareservice.service.NurseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
@RestController
@RequestMapping("/nurse")
public class NurseController {

    private final NurseService nurseService;

    @GetMapping("")
    @PreAuthorize("hasRole('NURSE')")
    public ResponseEntity<NurseDTO> getNurseByUserId(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        NurseDTO nurse = nurseService.findByUserId(userId);
        return new ResponseEntity<>(nurse, HttpStatus.OK);
    }

    @GetMapping("/{id}/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<NurseDTO> getNurseById(@PathVariable Long id) {
        NurseDTO nurse = nurseService.findById(id);
        return new ResponseEntity<>(nurse, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<NurseDTO> getNurseByUserIdAuth(@PathVariable Long userId) {
        NurseDTO nurse = nurseService.findByUserId(userId);
        return new ResponseEntity<>(nurse, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<List<NurseDTO>> getAllNurse() {
        List<NurseDTO> nurses = nurseService.findAll();
        return new ResponseEntity<>(nurses, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('NURSE')")
    public ResponseEntity<Map<String, Boolean>> addNurse(HttpServletRequest request,
                                                         @Valid @RequestBody NurseDTO nurse) {

        Long userId = (Long) request.getAttribute("id");
        nurseService.addNurse(userId, nurse);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<Map<String, Boolean>> addNurseAuth(@PathVariable Long userId,
                                                             @Valid @RequestBody NurseDTO nurse) {

        nurseService.addNurse(userId, nurse);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PutMapping("")
    @PreAuthorize("hasRole('NURSE')")
    public ResponseEntity<Map<String, Boolean>> updateNurse(HttpServletRequest request,
                                                            @Valid @RequestBody NurseDTO nurse) {
        Long userId = (Long) request.getAttribute("id");
        nurseService.updateNurse(userId, nurse);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<Map<String, Boolean>> updateNurseAuth(@PathVariable Long userId,
                                                                @Valid @RequestBody NurseDTO nurse) {
        nurseService.updateNurse(userId, nurse);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> deleteNurse(@PathVariable Long id) {
        nurseService.deleteById(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
