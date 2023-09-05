package com.projects.payrollmanagementsystem.repo;

import com.projects.payrollmanagementsystem.dto.EmployeeDeductionsDTO;
import com.projects.payrollmanagementsystem.dto.EmployeeEarningsDTO;
import com.projects.payrollmanagementsystem.models.Deduction;
import com.projects.payrollmanagementsystem.models.Earning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DeductionsRepository extends JpaRepository<Deduction, Integer> {
    Optional<Deduction> findByEmployeeId(Integer employeeId);
}
