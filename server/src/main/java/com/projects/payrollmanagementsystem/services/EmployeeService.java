package com.projects.payrollmanagementsystem.services;



import com.projects.payrollmanagementsystem.dto.*;
import com.projects.payrollmanagementsystem.empmapper.MapperImplementation;
import com.projects.payrollmanagementsystem.exception.CustomException;
import com.projects.payrollmanagementsystem.models.Deduction;
import com.projects.payrollmanagementsystem.models.Earning;
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


    public ResponseEntity<?> getAllEmps() {
        Optional<List<Employee>> employeeList = Optional.of(employeeRepository.findAll());

        if (employeeList.get().isEmpty()) {
            String errorMessage = "No employees found.";
            CustomException errorResponse = new CustomException(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        return ResponseEntity.ok(employeeList);
    }

    public ResponseEntity<?> getProfile(String username) {
        Optional<Employee> employee = (employeeRepository.findByEmail(username));
        if (!employee.isPresent()) {
            String errorMessage = "Employee not found with email: " + username;
            CustomException errorResponse = new CustomException(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(mapperImplementation.map(employee.get()));
    }


    public ResponseEntity<?> generatePayslip(Integer employeeId) {
        try {

            Optional<Employee> employee = employeeRepository.findById(employeeId);
            Optional<Earning> earning = earningsRepository.findByEmployeeId(employee.get().getId());
            Optional<Deduction> deduction = deductionsRepository.findByEmployeeId(employee.get().getId());


            if (employee.isPresent()) {

                return ResponseEntity.ok(mapperImplementation.map(employee.get(), deduction.get(), earning.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found or no pay slip details available.");

            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while generating the pay slip.");
        }
    }




    public ResponseEntity<?> saveEmployee(EmployeeRequest employeeRequest) {
        try {
            Employee employee = mapperImplementation.map(employeeRequest);
            employee.setPassword(bcryptEncoder.encode(employee.getPassword()));

            return ResponseEntity.ok(employeeRepository.save(employee));
        } catch (Exception e) {
            String errorMessage = "An error occurred while saving employee: " + e.getMessage();
            CustomException errorResponse = new CustomException(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    public ResponseEntity<?> addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = mapperImplementation.map(employeeRequest);

         
        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employeeRequest.getEmail());
        if (existingEmployee.isPresent()) {
            String errorMessage = "An employee with email " + employeeRequest.getEmail() + " already exists.";
            CustomException errorResponse = new CustomException(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        employee.setPassword(bcryptEncoder.encode(employeeRequest.getPassword()));
        return ResponseEntity.ok(mapperImplementation.map(employeeRepository.save(employee)));
    }


    public ResponseEntity<?> deleteEmployee(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (!employee.isPresent()) {
            String errorMessage = "Employee not found with id: " + id;
            CustomException errorResponse = new CustomException(errorMessage);
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