package com.projects.payrollmanagementsystem.specifications;

import com.projects.payrollmanagementsystem.models.Employee;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmployeeSpecificationBuilder extends BasicSpecificationBuilder{
    public EmployeeSpecificationBuilder(String search){
        super((new ArrayList<>()),search);
    }

    @Override
    public Specification<Employee> build() {
        Pattern pattern = Pattern.compile("(\\w+?)([=<~\\[|>\\]])(\\*?)(\\w+?)(\\*?)([,])");
        Matcher matcher = pattern.matcher( this.getSearch());
        String operation = null;
        while (matcher.find()) {

         this.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }
        String[] strings=this.getSearch().split("operation=");
        if(strings[1].equals("or")){
            operation="or";
        }
        else if(strings[1].equals("and")){
            operation="and";
        }
        else{
            return (Specification<Employee>) new Exception("Invalid Operation");
        }

        if(getParams().isEmpty()){
            return null;
        }

        Specification<Employee> result = new EmployeeSpecification(getParams(), operation);
        return result;
    }
}
