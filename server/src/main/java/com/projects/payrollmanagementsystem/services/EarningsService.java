package com.projects.payrollmanagementsystem.services;


import com.projects.payrollmanagementsystem.dto.EmployeeEarningsDTO;
import com.projects.payrollmanagementsystem.empmapper.MapperImplementation;
import com.projects.payrollmanagementsystem.exception.CustomException;
import com.projects.payrollmanagementsystem.models.Earning;
import com.projects.payrollmanagementsystem.models.Employee;
import com.projects.payrollmanagementsystem.repo.EarningsRepository;
import com.projects.payrollmanagementsystem.repo.EmployeeRepository;
import com.projects.payrollmanagementsystem.request.EarningRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EarningsService {
    @Autowired
    private EarningsRepository earningsRepository;

    @Autowired(required = true)
    private MapperImplementation mapperImplementation;

    @Autowired(required = true)
    private EmployeeRepository employeeRepository;


    public ResponseEntity<?> getAllEarnings() {
        Optional<EmployeeEarningsDTO> employeesWithEarnings = earningsRepository.getEmployeesWithEarnings();
        return ResponseEntity.ok(employeesWithEarnings);
//        if (!employeesWithEarnings) {
//            return ResponseEntity.ok(employeesWithEarnings.get());
//        } else {
//            String errorMessage = "No earnings found.";
//            CustomException errorResponse = new CustomException(errorMessage);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
//        }
    }
    public ResponseEntity<?> addEarnings(EarningRequest earningRequest) {

        Optional<Employee> employee = employeeRepository.findById(earningRequest.getEmployeeId());
        if (!employee.isPresent()) {
            String errorMessage = "Employee not found with ID: " + earningRequest.getEmployeeId();
            CustomException errorResponse = new CustomException(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        else{
            Earning earnings = mapperImplementation.map(earningRequest);
            return ResponseEntity.ok(mapperImplementation.map( earningsRepository.save(earnings)));
        }


    }
}
