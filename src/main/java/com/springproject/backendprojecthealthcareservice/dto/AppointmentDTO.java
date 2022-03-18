package com.springproject.backendprojecthealthcareservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springproject.backendprojecthealthcareservice.model.Appointment;
import com.springproject.backendprojecthealthcareservice.model.enumeration.AppointmentStatus;
import com.springproject.backendprojecthealthcareservice.model.enumeration.Departments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private Long id;
    private DoctorOfAppointmentDTO doctorId;
    private PatientDTO patientId;
    private Departments department;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @NotNull(message = "Please enter the appointment time")
    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @NotNull(message = "Please enter the appointment time")
    @Column(nullable = false)
    private LocalDateTime appointmentEndTime;
    private AppointmentStatus status;

    public AppointmentDTO(Appointment appointment) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.department = department;
        this.appointmentTime = appointmentTime;
        this.appointmentEndTime = appointmentEndTime;
        this.status = status;
    }
}
