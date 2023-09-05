package com.projects.payrollmanagementsystem.empmapper;

import com.projects.payrollmanagementsystem.dto.*;
import com.projects.payrollmanagementsystem.models.Deduction;
import com.projects.payrollmanagementsystem.models.Earning;
import com.projects.payrollmanagementsystem.models.Employee;
import com.projects.payrollmanagementsystem.request.DeductionRequest;
import com.projects.payrollmanagementsystem.request.EarningRequest;
import com.projects.payrollmanagementsystem.request.EmployeeRequest;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Mapper(componentModel = "spring")
public interface MapperImplementation {

    EmployeeDTO map(Employee employee);

    Earning map(EarningRequest earningRequest);

    EarningsDTO map(Earning earnings);

    Deduction map(DeductionRequest deductionRequest);

    DeductionsDTO map(Deduction deduction);


    Employee map(EmployeeRequest employeeRequest);

   PayslipDTO map(Employee employee,Deduction deduction,Earning earning);

    List<EmployeeDTO> map(List<Employee> allEmployees, List<Deduction> allDeductions);
}
