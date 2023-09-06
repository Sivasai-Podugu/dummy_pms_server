package com.projects.payrollmanagementsystem.services;

import com.projects.payrollmanagementsystem.mappers.DeductionMapper;
import com.projects.payrollmanagementsystem.mappers.EarningMapper;
import com.projects.payrollmanagementsystem.mappers.EmployeeMapper;
import com.projects.payrollmanagementsystem.exception.CustomErrorResponse;
import com.projects.payrollmanagementsystem.models.Deduction;
import com.projects.payrollmanagementsystem.models.Employee;
import com.projects.payrollmanagementsystem.repositories.DeductionsRepository;
import com.projects.payrollmanagementsystem.repositories.EmployeeRepository;
import com.projects.payrollmanagementsystem.request.DeductionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeductionsService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private DeductionMapper deductionMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DeductionsRepository deductionsRepository;



    public ResponseEntity<?> addDeductions(DeductionRequest deductionRequest) {
        try {
            Deduction deduction = employeeMapper.map(deductionRequest);
            return ResponseEntity.ok(employeeMapper.map(deductionsRepository.save(deduction)));
        } catch (Exception e) {
            String errorMessage = "An error occurred while adding deductions: " + e.getMessage();
            CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    public ResponseEntity<?> getAllDeductions() {
        List<Employee> employeeList = employeeRepository.findAll();
        return ResponseEntity.ok(deductionMapper.map(employeeList));
    }
}
