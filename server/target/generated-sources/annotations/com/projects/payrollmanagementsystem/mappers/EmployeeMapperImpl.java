package com.projects.payrollmanagementsystem.mappers;

import com.projects.payrollmanagementsystem.dto.DeductionsDTO;
import com.projects.payrollmanagementsystem.dto.EarningsDTO;
import com.projects.payrollmanagementsystem.dto.EmployeeDTO;
import com.projects.payrollmanagementsystem.models.Deduction;
import com.projects.payrollmanagementsystem.models.Earning;
import com.projects.payrollmanagementsystem.models.Employee;
import com.projects.payrollmanagementsystem.request.DeductionRequest;
import com.projects.payrollmanagementsystem.request.EarningRequest;
import com.projects.payrollmanagementsystem.request.EmployeeRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-06T11:35:29+0530",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public Employee map(EmployeeRequest employeeRequest) {
        if ( employeeRequest == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setFirstName( employeeRequest.getFirstName() );
        employee.setLastName( employeeRequest.getLastName() );
        employee.setDateOfBirth( employeeRequest.getDateOfBirth() );
        employee.setGender( employeeRequest.getGender() );
        employee.setContactInformation( employeeRequest.getContactInformation() );
        employee.setPassword( employeeRequest.getPassword() );
        employee.setRole( employeeRequest.getRole() );
        employee.setBasicSalary( employeeRequest.getBasicSalary() );
        employee.setEmail( employeeRequest.getEmail() );
        employee.setDesignation( employeeRequest.getDesignation() );
        employee.setJoinDate( employeeRequest.getJoinDate() );

        return employee;
    }

    @Override
    public EmployeeDTO map(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId( employee.getId() );
        employeeDTO.setFirstName( employee.getFirstName() );
        employeeDTO.setLastName( employee.getLastName() );
        employeeDTO.setDateOfBirth( employee.getDateOfBirth() );
        employeeDTO.setJoinDate( employee.getJoinDate() );
        employeeDTO.setGender( employee.getGender() );
        employeeDTO.setContactInformation( employee.getContactInformation() );
        employeeDTO.setRole( employee.getRole() );
        employeeDTO.setBasicSalary( employee.getBasicSalary() );
        employeeDTO.setEmail( employee.getEmail() );
        employeeDTO.setDesignation( employee.getDesignation() );
        employeeDTO.setEarning( map( employee.getEarning() ) );
        employeeDTO.setDeduction( map( employee.getDeduction() ) );

        employeeDTO.setName( employee.getFirstName() + " " + employee.getLastName() );

        return employeeDTO;
    }

    @Override
    public List<EmployeeDTO> map(List<Employee> employeeList) {
        if ( employeeList == null ) {
            return null;
        }

        List<EmployeeDTO> list = new ArrayList<EmployeeDTO>( employeeList.size() );
        for ( Employee employee : employeeList ) {
            list.add( map( employee ) );
        }

        return list;
    }

    @Override
    public Earning map(EarningRequest earningRequest) {
        if ( earningRequest == null ) {
            return null;
        }

        Earning earning = new Earning();

        earning.setId( earningRequest.getId() );
        earning.setEmployeeId( earningRequest.getEmployeeId() );
        earning.setAnyAllowances( earningRequest.getAnyAllowances() );
        earning.setBonus( earningRequest.getBonus() );

        return earning;
    }

    @Override
    public EarningsDTO map(Earning earnings) {
        if ( earnings == null ) {
            return null;
        }

        EarningsDTO earningsDTO = new EarningsDTO();

        earningsDTO.setId( earnings.getId() );
        earningsDTO.setEmployeeId( earnings.getEmployeeId() );
        earningsDTO.setAnyAllowances( earnings.getAnyAllowances() );
        earningsDTO.setBonus( earnings.getBonus() );

        return earningsDTO;
    }

    @Override
    public Deduction map(DeductionRequest deductionRequest) {
        if ( deductionRequest == null ) {
            return null;
        }

        Deduction deduction = new Deduction();

        deduction.setId( deductionRequest.getId() );
        deduction.setEmployeeId( deductionRequest.getEmployeeId() );
        deduction.setProvidentFund( deductionRequest.getProvidentFund() );
        deduction.setTax( deductionRequest.getTax() );

        return deduction;
    }

    @Override
    public DeductionsDTO map(Deduction deduction) {
        if ( deduction == null ) {
            return null;
        }

        DeductionsDTO deductionsDTO = new DeductionsDTO();

        deductionsDTO.setId( deduction.getId() );
        deductionsDTO.setEmployeeId( deduction.getEmployeeId() );
        deductionsDTO.setProvidentFund( deduction.getProvidentFund() );
        deductionsDTO.setTax( deduction.getTax() );

        return deductionsDTO;
    }
}
