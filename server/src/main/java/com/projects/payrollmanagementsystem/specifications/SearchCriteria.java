package com.projects.payrollmanagementsystem.specifications;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private SearchOperation op;
    private String value;
}
