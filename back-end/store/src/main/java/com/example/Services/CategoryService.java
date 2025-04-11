package com.example.Services;

import com.example.Entities.Category;
import com.example.Repositories.CategoryRepository;
import com.example.Services.DAOs.CategoryDAO;

import java.sql.SQLException;
import java.util.List;

public class CategoryService implements CategoryDAO {
    private CategoryRepository categoryRepository = new CategoryRepository();
    @Override
    public void save(String category) throws SQLException{
        categoryRepository.save(category);
    }
    public void delete(int category_id) throws SQLException{
        categoryRepository.delete(category_id);
    }
    @Override
    public Category findByName(String category_name) throws SQLException {
        if (categoryRepository.findByName(category_name) != null){
            return categoryRepository.findByName(category_name);
        } else
            return null;
    }
    @Override
    public List<Category> findAllCategories() throws SQLException {
        if (categoryRepository.findAllCategories() != null) {
            return categoryRepository.findAllCategories();
        } else
            return null;
    }
}
