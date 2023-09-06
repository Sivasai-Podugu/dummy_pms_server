package com.projects.payrollmanagementsystem.services;



import com.projects.payrollmanagementsystem.dto.*;
import com.projects.payrollmanagementsystem.empmapper.MapperImplementation;
import com.projects.payrollmanagementsystem.exception.CustomErrorResponse;
import com.projects.payrollmanagementsystem.models.Employee;
import com.projects.payrollmanagementsystem.repo.DeductionsRepository;
import com.projects.payrollmanagementsystem.repo.EarningsRepository;
import com.projects.payrollmanagementsystem.repo.EmployeeRepository;
import com.projects.payrollmanagementsystem.request.EmployeeRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class EmployeeService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MapperImplementation mapperImplementation;

    @Autowired
    private EarningsRepository earningsRepository;

    @Autowired
    private DeductionsRepository deductionsRepository;


    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Setter
    private BCryptPasswordEncoder bcryptEncoder;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Employee emp = employeeRepository.findByEmail(userName).get();
        return new User(emp.getEmail(), emp.getPassword(), Collections.singleton(new SimpleGrantedAuthority(emp.getRole())));

    }


    public ResponseEntity<?> getAllEmployees() {
        Optional<List<Employee>> employeeList = Optional.of(employeeRepository.findAll());

        if (employeeList.get().isEmpty()) {
            String errorMessage = "No employees found.";
            CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        return ResponseEntity.ok(mapperImplementation.map(employeeList.get()));
    }

    public ResponseEntity<?> getProfile(String username) {
        Optional<Employee> employee = (employeeRepository.findByEmail(username));
        if (!employee.isPresent()) {
            String errorMessage = "Employee not found with email: " + username;
            CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        employee.get().setEarning(null);
        employee.get().setDeduction(null);
        return ResponseEntity.ok(mapperImplementation.map(employee.get()));
    }




    public ResponseEntity<?> saveEmployee(EmployeeRequest employeeRequest) {
        try {
            Employee employee = mapperImplementation.map(employeeRequest);
            employee.setPassword(bcryptEncoder.encode(employee.getPassword()));

            return ResponseEntity.ok(employeeRepository.save(employee));
        } catch (Exception e) {
            String errorMessage = "An error occurred while saving employee: " + e.getMessage();
            CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    public ResponseEntity<?> addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = mapperImplementation.map(employeeRequest);

         
        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employeeRequest.getEmail());
        if (existingEmployee.isPresent()) {
            String errorMessage = "An employee with email " + employeeRequest.getEmail() + " already exists.";
            CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        employee.setPassword(bcryptEncoder.encode(employeeRequest.getPassword()));
        return ResponseEntity.ok(mapperImplementation.map(employeeRepository.save(employee)));
    }


    public ResponseEntity<?> deleteEmployee(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (!employee.isPresent()) {
            String errorMessage = "Employee not found with id: " + id;
            CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        employeeRepository.delete(employee.get());

        return ResponseEntity.ok(employeeRepository.findAll());
    }
    public ResponseEntity<?> updateEmployee(EmployeeRequest employeeRequest) {
        Employee employee = mapperImplementation.map(employeeRequest);
        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employeeRequest.getEmail());
        if(employeeRequest.getPassword().isEmpty()){
            employee.setPassword(existingEmployee.get().getPassword());
        }else{
            employee.setPassword(bcryptEncoder.encode(employee.getPassword()));
        }
        employee.setId(existingEmployee.get().getId());
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDTO savedEmployeeDTO = mapperImplementation.map(savedEmployee);
        return ResponseEntity.ok(savedEmployeeDTO);
    }


    public ResponseEntity<?> generatePayslip(Integer employeeId) {
        return ResponseEntity.ok(mapperImplementation.map(employeeRepository.findById(employeeId).get()));
    }



    public Authentication authenticate(String username, String password) {
        UserDetails userDetails;
        try {
            userDetails = loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Invalid username");
        }

        if (!bcryptEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }



}