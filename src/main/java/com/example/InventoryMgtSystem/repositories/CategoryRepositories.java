package com.example.InventoryMgtSystem.repositories;

import com.example.InventoryMgtSystem.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepositories extends JpaRepository<Category, Long> {

}
