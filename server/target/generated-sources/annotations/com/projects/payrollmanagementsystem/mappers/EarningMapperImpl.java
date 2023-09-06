package com.projects.payrollmanagementsystem.mappers;

import com.projects.payrollmanagementsystem.dto.EarningsDTO;
import com.projects.payrollmanagementsystem.dto.EmployeeDTO;
import com.projects.payrollmanagementsystem.models.Earning;
import com.projects.payrollmanagementsystem.models.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-06T16:16:10+0530",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class EarningMapperImpl implements EarningMapper {

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
        employeeDTO.setEarning( earningToEarningsDTO( employee.getEarning() ) );

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

    protected EarningsDTO earningToEarningsDTO(Earning earning) {
        if ( earning == null ) {
            return null;
        }

        EarningsDTO earningsDTO = new EarningsDTO();

        earningsDTO.setId( earning.getId() );
        earningsDTO.setEmployeeId( earning.getEmployeeId() );
        earningsDTO.setAnyAllowances( earning.getAnyAllowances() );
        earningsDTO.setBonus( earning.getBonus() );

        return earningsDTO;
    }
}
