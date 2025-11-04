package com.example.InventoryMgtSystem.repositories;

import com.example.InventoryMgtSystem.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepositories extends JpaRepository<Supplier, Long> {

}
