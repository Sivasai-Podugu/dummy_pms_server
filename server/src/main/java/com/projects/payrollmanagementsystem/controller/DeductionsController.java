package com.projects.payrollmanagementsystem.controller;

import com.projects.payrollmanagementsystem.request.DeductionRequest;
import com.projects.payrollmanagementsystem.services.DeductionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/deduction")
public class DeductionsController {
    @Autowired
    DeductionsService deductionsService;

    @PostMapping
    public ResponseEntity<?> addDeductions(@RequestBody DeductionRequest deductionRequest){
        return deductionsService.addDeductions(deductionRequest);
    }

    @GetMapping
    public ResponseEntity<?> getAllDeductions(){
        return deductionsService.getAllDeductions();
    }
}
