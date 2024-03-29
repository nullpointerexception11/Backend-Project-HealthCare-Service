package com.springproject.backendprojecthealthcareservice.controller;

import com.springproject.backendprojecthealthcareservice.dto.AppointmentDTO;
import com.springproject.backendprojecthealthcareservice.model.Appointment;
import com.springproject.backendprojecthealthcareservice.model.Doctor;
import com.springproject.backendprojecthealthcareservice.model.User;
import com.springproject.backendprojecthealthcareservice.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<AppointmentDTO> getAppointmentById(HttpServletRequest request,
                                                             @RequestParam("appointmentId") Long appointmentId,
                                                             @RequestParam("patientId") Long patientId) {
        Long userId = (Long) request.getAttribute("id");
        AppointmentDTO appointment = appointmentService.findById(appointmentId, patientId, userId);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByPatientId(HttpServletRequest request,
                                                                           @PathVariable Long patientId) {
        Long userId = (Long) request.getAttribute("id");
        List<AppointmentDTO> appointment = appointmentService.findByPatientId(patientId, userId);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping("/all/appointmentsOfUser")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<List<AppointmentDTO>>> getAppointmentsByUserId(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        Set<List<AppointmentDTO>> appointment = appointmentService.findByUserId(userId);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<AppointmentDTO> getAppointmentByIdAuth(@PathVariable Long id) {
        AppointmentDTO appointment = appointmentService.findById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping("/{patientId}/patient")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByPatientIdAuth(@PathVariable Long patientId) {
        List<AppointmentDTO> appointment = appointmentService.findByPatientId(patientId);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping("/all/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<Set<List<AppointmentDTO>>> getAppointmentsByUserIdAuth(@PathVariable Long userId) {
        Set<List<AppointmentDTO>> appointments = appointmentService.findByUserId(userId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointment = appointmentService.findAll();
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Map<String, Object>> addAppointment(HttpServletRequest request,
                                                              @RequestParam("doctorId") Doctor doctorId,
                                                              @RequestParam("patientId") Long patientId,
                                                              @Valid @RequestBody AppointmentDTO appointment) {

        Long userId = (Long) request.getAttribute("id");
        appointmentService.addAppointment(userId, doctorId, patientId, appointment);
        Double appointmentFee = appointmentService.price(doctorId);

        Map<String, Object> map = new HashMap<>();
        map.put("appointment created successfully", true);
        map.put("appointment fee", appointmentFee);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Map<String, Object>> updateAppointment(HttpServletRequest request,
                                                                 @RequestParam("appointmentId") Long id,
                                                                 @RequestParam("doctorId") Doctor doctorId,
                                                                 @RequestParam("patientId") Long patientId,
                                                                 @Valid @RequestBody AppointmentDTO appointment) {
        Long userId = (Long) request.getAttribute("id");
        appointmentService.updateAppointment(id, userId, doctorId, patientId, appointment);
        Double appointmentFee = appointmentService.price(doctorId);

        Map<String, Object> map = new HashMap<>();
        map.put("appointment update successfully", true);
        map.put("appointment fee", appointmentFee);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/update/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<Map<String, Object>> updateAppointmentAuth(@RequestParam("appointmentId") Long id,
                                                                     @RequestParam("doctorId") Doctor doctorId,
                                                                     @RequestParam("patientId") Long patientId,
                                                                     @Valid @RequestBody AppointmentDTO appointment) {
        appointmentService.updateAppointmentAuth(id, doctorId, patientId, appointment);
        Double appointmentFee = appointmentService.price(doctorId);

        Map<String, Object> map = new HashMap<>();
        map.put("appointment update successfully", true);
        map.put("appointment fee", appointmentFee);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Map<String, Boolean>> deleteReservation(HttpServletRequest request,
                                                                  @RequestParam("appointmentId") Long appointmentId,
                                                                  @RequestParam("patientId") Long patientId) {
        Long userId = (Long) request.getAttribute("id");
        appointmentService.removeById(userId, appointmentId, patientId);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('NURSE') or hasRole('DOCTOR')")
    public ResponseEntity<Map<String, Boolean>> deleteReservationAuth(@PathVariable Long id) {
        appointmentService.removeById(id);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/availability")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY') or hasRole('NURSE') or hasRole('DOCTOR') or hasRole('PATIENT')")
    public ResponseEntity<Map<String, Object>> checkCarAvailability(@RequestParam(value = "doctorId") Doctor doctorId,
                                                                    @RequestParam(value = "appointmentTime")
                                                                    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss") LocalDateTime appointmentTime,
                                                                    @RequestParam(value = "AppointmentEndTime")
                                                                    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss") LocalDateTime appointmentEndTime) {

        boolean availability = appointmentService.reservationAvailability(doctorId, appointmentTime, appointmentEndTime);
        Double appointmentFee = appointmentService.price(doctorId);

        Map<String, Object> map = new HashMap<>();
        map.put("isAvailability", !availability);
        map.put("appointment fee", appointmentFee);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}