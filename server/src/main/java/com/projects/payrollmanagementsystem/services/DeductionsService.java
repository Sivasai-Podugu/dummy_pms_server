package com.projects.payrollmanagementsystem.services;

import com.projects.payrollmanagementsystem.dto.EmployeeDTO;
import com.projects.payrollmanagementsystem.dto.EmployeeDeductionsDTO;
import com.projects.payrollmanagementsystem.empmapper.MapperImplementation;
import com.projects.payrollmanagementsystem.exception.CustomException;
import com.projects.payrollmanagementsystem.models.Deduction;
import com.projects.payrollmanagementsystem.models.Earning;
import com.projects.payrollmanagementsystem.models.Employee;
import com.projects.payrollmanagementsystem.repo.DeductionsRepository;
import com.projects.payrollmanagementsystem.repo.EmployeeRepository;
import com.projects.payrollmanagementsystem.request.DeductionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DeductionsService {
    @Autowired(required = true)
    private MapperImplementation mapperImplementation;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DeductionsRepository deductionsRepository;


    public ResponseEntity<?> getAllDeductions() {
        List<Employee> allEmployees = employeeRepository.findAll();
        List<Deduction> allDeductions = deductionsRepository.findAll();
        List<EmployeeDTO> employeesWithDeductions = mapperImplementation.map(allEmployees,allDeductions);
        return ResponseEntity.ok(employeesWithDeductions);
//        if(employeesWithDeductions.isPresent()){
//            return ResponseEntity.ok(employeesWithDeductions);
//        }
//        if (!employeesWithDeductions.isPresent()) {
//            return ResponseEntity.ok(employeesWithDeductions.get());
//        } else {
//            String errorMessage = "No Deductions found.";
//            CustomException errorResponse = new CustomException(errorMessage);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
//        }
    }



    public ResponseEntity<?> addDeductions(DeductionRequest deductionRequest) {
        try {
            Deduction deduction = mapperImplementation.map(deductionRequest);
            return ResponseEntity.ok(deductionsRepository.save(deduction));
        } catch (Exception e) {
            String errorMessage = "An error occurred while adding deductions: " + e.getMessage();
            CustomException errorResponse = new CustomException(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
