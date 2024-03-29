package com.springproject.backendprojecthealthcareservice.model;

import com.springproject.backendprojecthealthcareservice.model.enumeration.UserRole;
import io.dropwizard.validation.OneOf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 15)
    @NotNull(message = "Please enter your first name")
    @Column(nullable = false, length = 15)
    private String firstName;

    @Size(max = 15)
    @NotNull(message = "Please enter your last name")
    @Column(nullable = false, length = 15)
    private String lastName;

    @Size(min = 4, max = 60, message = "Please enter min 4 characters")
    @NotNull(message = "Please enter your password")
    @Column(nullable = false, length = 120)
    private String password;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter valid phone number")
    @Size(max = 14, min = 14, message = "Phone number should be exact 10 characters")
    @NotNull(message = "Please enter your phone number")
    @Column(nullable = false, length = 14)
    private String phoneNumber;

    @Email(message = "Please enter valid email")
    @Size(min = 5, max = 100)
    @NotNull(message = "Please enter your email")
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Size(max = 250)
    @NotNull(message = "Please your enter your address")
    @Column(nullable = false, length = 250)
    private String address;

    @Size(max = 15)
    @NotNull(message = "Please enter your zip code")
    @Column(nullable = false, length = 15)
    private String zipCode;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @NotNull(message = "Please enter your age")
    @Column(nullable = false)
    private Integer age;

    @Size(max = 6)
    @NotNull(message = "Please enter your gender that should be Female or Male")
    @OneOf(value = {"Female", "Male"}, ignoreCase = true, ignoreWhitespace = true)
    @Column(nullable = false, length = 6)
    private String gender;

    @Column(nullable = false)
    private Boolean builtIn = false;

    @Column(nullable = false)
    private Boolean locked = false;

    @Column(nullable = false)
    private Boolean enabled = false;

    public User(String firstName, String lastName, String password, String phoneNumber, String email, String address,
                String zipCode, Set<Role> roles, Integer age, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.zipCode = zipCode;
        this.roles = roles;
        this.age = age;
        this.gender = gender;
    }

    public User(Long id, String firstName, String lastName, String password, String phoneNumber, String email,
                String address, String zipCode, Set<Role> roles, Integer age, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.zipCode = zipCode;
        this.roles = roles;
        this.age = age;
        this.gender = gender;
    }

    public Set<Role> getRole() {
        return roles;
    }

    public Set<String> getRoles(){

        Set<String> roles1 = new HashSet<>();
        Role[] role = this.roles.toArray(new Role[this.roles.size()]);

        for (int i = 0; i < roles.size(); i++){
            if (role[i].getName().equals(UserRole.ROLE_ADMIN))
                roles1.add("Administrator");
            else if (role[i].getName().equals(UserRole.ROLE_DOCTOR))
                roles1.add("Doctor");
            else if (role[i].getName().equals(UserRole.ROLE_NURSE))
                roles1.add("Nurse");
            else if (role[i].getName().equals(UserRole.ROLE_SECRETARY))
                roles1.add("Secretary");
            else
                roles1.add("Patient");
        }
        return roles1;
    }
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }
}
