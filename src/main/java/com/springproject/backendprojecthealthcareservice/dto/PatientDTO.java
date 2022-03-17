package com.springproject.backendprojecthealthcareservice.dto;

import com.springproject.backendprojecthealthcareservice.model.FileDB;
import com.springproject.backendprojecthealthcareservice.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    private Long id;
    private UserDTO user;
    private String medicalHistories;
    private String diseases;
    private Set<String> medicalImaging;
    private Set<String> prescriptions;
    private Set<String> bloodTests;
    private String diagnosis;

    public PatientDTO(Patient patient) {
        this.id = id;
        this.user = user;
        this.medicalHistories = medicalHistories;
        this.diseases = diseases;
        this.medicalImaging = medicalImaging;
        this.prescriptions = prescriptions;
        this.bloodTests = bloodTests;
        this.diagnosis = diagnosis;
    }

    public Set<String> setFile(Set<FileDB> files) {
        Set<String> out = new HashSet<>();
        FileDB[] file = files.toArray(new FileDB[files.size()]);

        for (int i = 0; i < files.size(); i++)
            out.add(file[i].getId());

        return out;
    }
}
