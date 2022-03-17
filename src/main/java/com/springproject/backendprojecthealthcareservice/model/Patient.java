package com.springproject.backendprojecthealthcareservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.File;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @Size(max = 300, message = "Size exceeded")
    @Column(length = 300)
    private String medicalHistory;

    @Size(max = 300, message = "Size exceeded")
    @Column(length = 300)
    private String disease;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "medical_imaging_id",
    joinColumns = @JoinColumn(name = "patient_id"),
    inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<FileDB> medicalImaging;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "patient_precriptions",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<FileDB> prescriptions;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "patient_blood_tests",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<FileDB> bloodTests;

    @Size(max = 300, message = "Size exceeded")
    @Column(length = 300)
    private String diagnosis;
}
