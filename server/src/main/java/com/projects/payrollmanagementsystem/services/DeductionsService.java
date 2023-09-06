package com.projects.payrollmanagementsystem.services;

import com.projects.payrollmanagementsystem.empmapper.MapperImplementation;
import com.projects.payrollmanagementsystem.exception.CustomErrorResponse;
import com.projects.payrollmanagementsystem.models.Deduction;
import com.projects.payrollmanagementsystem.models.Employee;
import com.projects.payrollmanagementsystem.repo.DeductionsRepository;
import com.projects.payrollmanagementsystem.repo.EmployeeRepository;
import com.projects.payrollmanagementsystem.request.DeductionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeductionsService {
    @Autowired
    private MapperImplementation mapperImplementation;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DeductionsRepository deductionsRepository;



    public ResponseEntity<?> addDeductions(DeductionRequest deductionRequest) {
        try {
            Deduction deduction = mapperImplementation.map(deductionRequest);
            return ResponseEntity.ok(mapperImplementation.map(deductionsRepository.save(deduction)));
        } catch (Exception e) {
            String errorMessage = "An error occurred while adding deductions: " + e.getMessage();
            CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    public ResponseEntity<?> getAllDeductions() {
        List<Employee> employeeList = employeeRepository.findAll();
        for (Employee employee:employeeList ) {
            employee.setEarning(null);
        }
        return ResponseEntity.ok(mapperImplementation.map(employeeList));
    }
}
