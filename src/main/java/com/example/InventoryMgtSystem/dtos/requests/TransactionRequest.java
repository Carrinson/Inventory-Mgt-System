package com.example.InventoryMgtSystem.dtos.requests;

import com.example.InventoryMgtSystem.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRequest {

    @Positive(message = "product id is required")
    private long productid;

    @Positive(message = "quantity is required")
    private Integer quantity;

    @Positive(message = "product id is required")
    private long supplierid;

    private String description;

    private String note;

}
