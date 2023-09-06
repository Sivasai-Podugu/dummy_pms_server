package com.projects.payrollmanagementsystem.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;

@Entity
@Table(name = "deductions")
@Data
public class Deduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "employee_id")
    private Integer employeeId;
    @Column
    private double providentFund;
    @Column
    private double tax;


    @OneToOne
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Employee employee;



}
