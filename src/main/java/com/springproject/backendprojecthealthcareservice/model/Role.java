package com.springproject.backendprojecthealthcareservice.model;

import com.springproject.backendprojecthealthcareservice.model.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private UserRole name;

    @Override
    public String toString() {
        return  "{" + name + '}' ;
    }
}

