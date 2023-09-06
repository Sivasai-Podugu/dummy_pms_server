package com.projects.payrollmanagementsystem.repositories;

import com.projects.payrollmanagementsystem.models.Employee;
import com.projects.payrollmanagementsystem.specifications.EmployeeSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee>, PagingAndSortingRepository<Employee,Integer> {
    public Optional<Employee> findByEmail(String email);

}
