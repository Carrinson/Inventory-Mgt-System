package com.example.InventoryMgtSystem.services.implimentation;


import com.example.InventoryMgtSystem.dtos.CategoryDTO;
import com.example.InventoryMgtSystem.dtos.response.Response;
import com.example.InventoryMgtSystem.models.Category;
import com.example.InventoryMgtSystem.repositories.CategoryRepository;
import com.example.InventoryMgtSystem.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpli implements CategoryService {

    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;



    @Override
    public Response createCategory(CategoryDTO categoryDTO) {

        Category categoryToSave = modelMapper.map(categoryDTO, Category.class);

        categoryRepository.save(categoryToSave);

        return Response.builder()
                .status(200)
                .message("Category saved Successfully")
                .build();


    }

    @Override
    public Response getAllCategory() {
        List<Category> categories = categoryRepository.findAll((Sort.by(Sort.Direction.DESC,"id")));

        categories.forEach(category -> category.setProducts(null));
        List<CategoryDTO> categoryDTOList = modelMapper.map(categories, new TypeToken<CategoryDTO>()
        {}.getType());

        return Response.builder()
                .status(200)
                .message("Success")
                .categories(categoryDTOList)
                .build();
    }

    @Override
    public Response getCategoryById(Long id) {
        return null;
    }

    @Override
    public Response updateCategory(CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public Response deleteCategory(Long id) {
        return null;
    }
}
