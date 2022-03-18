package com.springproject.backendprojecthealthcareservice.dto;

import com.springproject.backendprojecthealthcareservice.model.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorOfAppointmentDTO {

    private String doctorFullName;
    private String contactPhoneNumber;
    private String contactEmail;
    private Double appointmentFee;

    public DoctorOfAppointmentDTO(Doctor doctor) {
        this.doctorFullName = doctorFullName;
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactEmail = contactEmail;
        this.appointmentFee = appointmentFee;
    }
}
