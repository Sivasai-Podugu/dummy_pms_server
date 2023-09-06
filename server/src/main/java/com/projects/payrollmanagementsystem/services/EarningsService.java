package com.projects.payrollmanagementsystem.services;


import com.projects.payrollmanagementsystem.mappers.EarningMapper;
import com.projects.payrollmanagementsystem.mappers.EmployeeMapper;
import com.projects.payrollmanagementsystem.exception.CustomErrorResponse;
import com.projects.payrollmanagementsystem.models.Earning;
import com.projects.payrollmanagementsystem.models.Employee;
import com.projects.payrollmanagementsystem.repositories.EarningsRepository;
import com.projects.payrollmanagementsystem.repositories.EmployeeRepository;
import com.projects.payrollmanagementsystem.request.EarningRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EarningsService {
    @Autowired
    private EarningsRepository earningsRepository;

    @Autowired
    private EarningMapper earningMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeRepository employeeRepository;



    public ResponseEntity<?> addEarnings(EarningRequest earningRequest) {
        Optional<Employee> employee = employeeRepository.findById(earningRequest.getEmployeeId());

        if (!employee.isPresent()) {
            String errorMessage = "Employee not found with ID: " + earningRequest.getEmployeeId();
            CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            Earning earnings = employeeMapper.map(earningRequest);
            return ResponseEntity.ok(employeeMapper.map(earningsRepository.save(earnings)));
        }
    }


    public ResponseEntity<?> getAllEarnings() {
        List<Employee> employeeList = employeeRepository.findAll();

        return ResponseEntity.ok(earningMapper.map(employeeList));
    }
}
