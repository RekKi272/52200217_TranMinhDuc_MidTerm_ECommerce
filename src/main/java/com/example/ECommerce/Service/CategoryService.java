package com.example.ECommerce.Service;

import com.example.ECommerce.Model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category saveCategory(Category category);
    boolean deleteCategory(Long id);
    boolean existCategory(String name);
}
