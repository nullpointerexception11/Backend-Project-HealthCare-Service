package com.springproject.backendprojecthealthcareservice.dto;

import com.springproject.backendprojecthealthcareservice.model.Secretary;
import com.springproject.backendprojecthealthcareservice.model.enumeration.Departments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecretaryDTO {

    private Long id;
    private UserDTO userId;
    private Departments department;

    public SecretaryDTO(Secretary secretary) {
        this.id = id;
        this.userId = userId;
        this.department = department;
    }
}
