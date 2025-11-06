package com.example.InventoryMgtSystem.specification;


import com.example.InventoryMgtSystem.models.Transaction;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class TransactionFilter {

    public static Specification<Transaction> byFilter(String searchValues){

        return ((root,query, criteriaBuilder) -> {

            if (searchValues == null || searchValues.isEmpty()){
                return criteriaBuilder.conjunction();
            }

            String searchPattern = "%" +searchValues.toLowerCase()+ "%";

//            create a list to hold predicates
            List<Predicate> predicates = new ArrayList<>();

//            search within transaction fields
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchPattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("note")), searchPattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("status").as(String.class)), searchPattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("transactionType").as(String.class)), searchPattern));
//safety join to check user fields
            if (root.getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("user"))){
                root.join("user", JoinType.LEFT);
            }
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("user",JoinType.LEFT).get("name")), searchPattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("user",JoinType.LEFT).get("email")), searchPattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("user",JoinType.LEFT).get("phoneNumber")), searchPattern));
//safety join to check supplier fields
            if (root.getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("supplier"))){
                root.join("supplier", JoinType.LEFT);
            }
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("supplier",JoinType.LEFT).get("name")), searchPattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("supplier",JoinType.LEFT).get("contactInfo")), searchPattern));

            //safety join to check product fields
            if (root.getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("product"))){
                root.join("product", JoinType.LEFT);
            }
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("product",JoinType.LEFT).get("name")), searchPattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("product",JoinType.LEFT).get("sku")), searchPattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("product",JoinType.LEFT).get("description")), searchPattern));

            //safety join to check category fields
            if (root.getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("products")) &&
            root.join("product").getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("category"))){
                root.join("product", JoinType.LEFT).join("category", JoinType.LEFT);
            }
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("product",JoinType.LEFT).join("category",JoinType.LEFT).get("name")), searchPattern));

        })
    }

}
