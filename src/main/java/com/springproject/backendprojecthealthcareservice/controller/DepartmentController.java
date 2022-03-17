package com.springproject.backendprojecthealthcareservice.controller;

import com.springproject.backendprojecthealthcareservice.dto.DoctorDTO;
import com.springproject.backendprojecthealthcareservice.dto.NurseDTO;
import com.springproject.backendprojecthealthcareservice.dto.SecretaryDTO;
import com.springproject.backendprojecthealthcareservice.model.enumeration.Departments;
import com.springproject.backendprojecthealthcareservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RestController
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService service;


    @GetMapping("/doctors")
    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('SECRETARY') " +
            "or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<List<DoctorDTO>> getDepartmentDoctors(@RequestParam(value = "departmentName") Departments name) {
        List<DoctorDTO> doctors = service.findDoctorsByDepartmentName(name);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/nurses")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<List<NurseDTO>> getDepartmentNurses(@RequestParam(value = "departmentName") Departments name) {
        List<NurseDTO> nurses = service.findNursesByDepartmentName(name);
        return new ResponseEntity<>(nurses, HttpStatus.OK);
    }

    @GetMapping("/secretaries")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<List<SecretaryDTO>> getDepartmentSecretaries(@RequestParam(value = "departmentName") Departments name) {
        List<SecretaryDTO> secretaries = service.findSecretaryByDepartmentName(name);
        return new ResponseEntity<>(secretaries, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<Map<String, Object>> getDepartmentStaff(@RequestParam(value = "departmentName") Departments name) {
        Map<String, Object> list = service.findAllDepartmentStaff(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
