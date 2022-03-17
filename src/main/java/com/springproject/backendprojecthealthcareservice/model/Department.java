package com.springproject.backendprojecthealthcareservice.model;

import com.springproject.backendprojecthealthcareservice.model.enumeration.Departments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private Departments name;

    public Department(Departments name) {
        this.name = name;
    }
}
