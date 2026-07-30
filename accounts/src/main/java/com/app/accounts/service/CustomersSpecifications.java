package com.app.accounts.service;

import com.app.accounts.dto.CustomerAccountDto;
import com.app.accounts.dto.SearchCustomerDto;
import com.app.accounts.entity.CustomersEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import jakarta.persistence.Column;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

public class CustomersSpecifications {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Specification<CustomersEntity> findCustomers(SearchCustomerDto searchCustomers) {

        return (root, query, criteriaBuilder) -> {

            var predicates = criteriaBuilder.conjunction();

            if (searchCustomers.getFirstName() != null && !searchCustomers.getFirstName().isEmpty())
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(root.get("firstName"), "%" + searchCustomers.getFirstName() + "%"));

            if (searchCustomers.getLastName() != null && !searchCustomers.getLastName().isEmpty())
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(root.get("lastName"), "%" + searchCustomers.getLastName() + "%"));

            if (searchCustomers.getStreetAddress() != null && !searchCustomers.getStreetAddress().isEmpty())
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(root.get("streetAddress"), "%" + searchCustomers.getStreetAddress() + "%"));

            if (searchCustomers.getCity() != null && !searchCustomers.getCity().isEmpty())
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(root.get("city"), "%" + searchCustomers.getCity() + "%"));

            if (searchCustomers.getState() != null && !searchCustomers.getState().isEmpty())
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("state"), searchCustomers.getState()));

            if (searchCustomers.getZipCode() != null && !searchCustomers.getZipCode().isEmpty())
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("zipCode"), searchCustomers.getZipCode()));

            return predicates;
        };
    }

}
