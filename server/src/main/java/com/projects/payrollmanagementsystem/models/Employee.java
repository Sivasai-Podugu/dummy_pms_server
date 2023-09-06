package com.projects.payrollmanagementsystem.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employees")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private String contactInformation;
    private String password;
    private String role;
    private double basicSalary;
    private String email;
    private String designation;
    private Date joinDate;

    @OneToOne(mappedBy = "employee")
    @Fetch(value = FetchMode.SELECT)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Earning earning;

    @OneToOne(mappedBy = "employee")
    @Fetch(value = FetchMode.SELECT)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Deduction deduction;

}
