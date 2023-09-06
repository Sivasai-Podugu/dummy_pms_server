package com.projects.payrollmanagementsystem.specifications;


import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public abstract class BasicSpecification<M> implements Specification<M> {
    private List<SearchCriteria> searchCriteriaList;

    public BasicSpecification(final List<SearchCriteria> searchCriteriaList) {
        super();
        this.searchCriteriaList = searchCriteriaList;
    }
    @Override
    public Predicate toPredicate(Root<M> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicateList = new ArrayList<>();

        for ( SearchCriteria searchCriteria: searchCriteriaList) {

            Object value = getEnumValueIfEnum(searchCriteria.getKey(), searchCriteria.getValue(), searchCriteria.getOp());

            switch (searchCriteria.getOp()) {
                case EQUALITY:
                    predicateList.add(builder.equal(root.get(searchCriteria.getKey()), value));
                    break;


                case GREATER_THAN:
                    predicateList.add(builder.greaterThan(root.get(searchCriteria.getKey()),Integer.parseInt((String)value)));
                    break;



                case LESS_THAN:
                    predicateList.add( builder.lessThan(root.get(searchCriteria.getKey()), Integer.parseInt((String)value)));
                    break;



                case STARTS_WITH:
                    predicateList.add(builder.like(builder.lower(root.get(searchCriteria.getKey())), value.toString().toLowerCase() + "%"));
                    break;


                case ENDS_WITH:
                    predicateList.add( builder.like(builder.lower(root.get(searchCriteria.getKey())), "%" +value.toString().toLowerCase()));
                    break;


                case CONTAINS:
                    predicateList.add( builder.like(builder.lower(root.get(searchCriteria.getKey())), "%" + value.toString().toLowerCase() + "%"));
                    break;
                default:
                    return null;

            }




        }

        return builder.and(predicateList.toArray(new Predicate[0]));
    }

    protected abstract Object getEnumValueIfEnum(String object, String value, SearchOperation op);
}