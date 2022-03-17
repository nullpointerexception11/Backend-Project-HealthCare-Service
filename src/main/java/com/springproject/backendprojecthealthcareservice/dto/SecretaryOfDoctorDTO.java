package com.springproject.backendprojecthealthcareservice.dto;

import com.springproject.backendprojecthealthcareservice.model.Secretary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecretaryOfDoctorDTO {

    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;

    public SecretaryOfDoctorDTO(Secretary secretary) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
