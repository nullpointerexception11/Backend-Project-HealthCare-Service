package com.springproject.backendprojecthealthcareservice.dto;

import com.springproject.backendprojecthealthcareservice.model.Nurse;
import com.springproject.backendprojecthealthcareservice.model.enumeration.Departments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NurseDTO {

    private Long id;
    private UserDTO userId;
    private Departments department;

    public NurseDTO(Nurse nurse) {
        this.id = id;
        this.userId = userId;
        this.department = department;
    }
}
