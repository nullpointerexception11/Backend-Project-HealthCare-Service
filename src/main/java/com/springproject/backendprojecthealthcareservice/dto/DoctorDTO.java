package com.springproject.backendprojecthealthcareservice.dto;

import antlr.debug.DebuggingParser;
import com.springproject.backendprojecthealthcareservice.model.Doctor;
import com.springproject.backendprojecthealthcareservice.model.enumeration.Departments;
import com.springproject.backendprojecthealthcareservice.model.enumeration.DoctorProfession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {

    private Long id;
    private UserDTO userId;
    private SecretaryOfDoctorDTO secretaryId;
    private DoctorProfession profession;
    private Departments department;
    private Double appointmentFee;
    private Boolean isAvailable;
    private Set<String> certificates;

    public DoctorDTO(Doctor doctor) {
        this.id = id;
        this.userId = userId;
        this.secretaryId = secretaryId;
        this.profession = profession;
        this.department = department;
        this.appointmentFee = appointmentFee;
        this.isAvailable = isAvailable;
        this.certificates = certificates;
    }
}
