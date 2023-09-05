package com.projects.payrollmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeEarningsDTO {
    private Integer employeeId;
    private String name;
    private Integer earningsId;
    private Double anyAllowances;
    private Double bonus;

}

