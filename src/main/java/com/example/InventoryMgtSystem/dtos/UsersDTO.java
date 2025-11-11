package com.example.InventoryMgtSystem.dtos;

import com.example.InventoryMgtSystem.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersDTO {

    private long id;

    private String name;

    private String email;

    @JsonInclude
    private String password;

    private String phoneNumber;

    private UserRole role;

    private List<TransactionDTO> transactions;


    private LocalDateTime createdAt;

}
