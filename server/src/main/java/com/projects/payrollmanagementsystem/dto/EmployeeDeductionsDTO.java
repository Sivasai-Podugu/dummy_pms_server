package com.projects.payrollmanagementsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDeductionsDTO {
    private Integer employeeId;
    private String name;
    private Integer deductionsId;
    private Double tax;
    private Double providentFund;
}
