package com.projects.payrollmanagementsystem.repo;

import com.projects.payrollmanagementsystem.dto.EmployeeEarningsDTO;
import com.projects.payrollmanagementsystem.models.Earning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EarningsRepository extends JpaRepository<Earning, Integer> {
    public Optional<Earning> findByEmployeeId(Integer employeeId);

    @Query("SELECT NEW com.projects.payrollmanagementsystem.dto.EmployeeEarningsDTO(employee.id, CONCAT(employee.firstName, ' ', employee.lastName), earning.id, earning.anyAllowances, earning.bonus) " +
            "FROM Earning earning " +
            "LEFT JOIN earning.employee employee")
    Optional<EmployeeEarningsDTO> getEmployeesWithEarnings();
}
