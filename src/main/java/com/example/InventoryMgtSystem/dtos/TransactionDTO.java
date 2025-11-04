package com.example.InventoryMgtSystem.dtos;

import com.example.InventoryMgtSystem.enums.TransactionStatus;
import com.example.InventoryMgtSystem.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTO {

    private long id;

    private Integer totalProducts;

    private BigDecimal totalPrice;

    private TransactionType transactionType; //purchase, sale, return

    private TransactionStatus status; //pending, completed, processing

    private String description;

    private String note;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;


    private ProductDTO product;

    private UsersDTO user;

    private SupplierDTO supplier;


}
