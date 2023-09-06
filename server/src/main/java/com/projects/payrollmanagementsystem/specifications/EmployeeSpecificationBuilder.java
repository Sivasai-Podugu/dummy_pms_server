package com.projects.payrollmanagementsystem.specifications;




import com.projects.payrollmanagementsystem.models.Employee;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class EmployeeSpecificationBuilder extends BasicSpecificationBuilder{
    public EmployeeSpecificationBuilder(){
        super((new ArrayList<>()));
    }


    @Override
    public Specification<Employee> build() {
        if(getParams().isEmpty()){
            return null;
        }

        Specification<Employee> result = new EmployeeSpecification(getParams());
        return result;
    }
}
