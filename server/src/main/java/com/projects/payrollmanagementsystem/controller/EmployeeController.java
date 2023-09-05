package com.projects.payrollmanagementsystem.controller;
import com.projects.payrollmanagementsystem.request.EmployeeRequest;
import com.projects.payrollmanagementsystem.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getProfile(Authentication authentication){

        return employeeService.getProfile(((User)authentication.getPrincipal()).getUsername());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(Authentication authentication){

        return employeeService.getAllEmps();
    }


    @GetMapping("/generate_payslip/{employee_id}")
    public ResponseEntity<?> generatePayslip(@PathVariable int employee_id){
        return employeeService.generatePayslip(employee_id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeRequest employeeRequest) throws Exception {
        return employeeService.saveEmployee(employeeRequest);
    }



    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeRequest employeeRequest){
        return employeeService.addEmployee(employeeRequest);
    }

    @PutMapping
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.updateEmployee(employeeRequest);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id){
        return employeeService.deleteEmployee(id);
    }


}
