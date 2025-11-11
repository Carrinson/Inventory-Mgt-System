package com.example.InventoryMgtSystem.services;

import com.example.InventoryMgtSystem.dtos.CategoryDTO;
import com.example.InventoryMgtSystem.dtos.response.Response;

public interface CategoryService {
    Response createCategory(CategoryDTO categoryDTO);

    Response getAllCategory();

    Response getCategoryById(Long id);

    Response updateCategory(CategoryDTO categoryDTO);

    Response deleteCategory(Long id);
}
