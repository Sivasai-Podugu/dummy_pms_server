package com.projects.payrollmanagementsystem.dto;


import lombok.Data;

@Data
public class PayslipDTO {
    private EmployeeDTO employeeDTO;
    private EarningsDTO earningsDTO;
    private DeductionsDTO deductionsDTO;
}
