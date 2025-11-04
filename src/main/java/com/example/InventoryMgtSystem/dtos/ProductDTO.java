package com.example.InventoryMgtSystem.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class ProductDTO {

    private long id;

    private long categoryId;

    private long productId;

    private long supplierId;

    private String name;

    private String sku;

    private BigDecimal price;

    private Integer stockQuantity;

    private String description;

    private LocalDateTime expiryDate;

    private String imageUrl;

    private LocalDateTime createdAt;


}
