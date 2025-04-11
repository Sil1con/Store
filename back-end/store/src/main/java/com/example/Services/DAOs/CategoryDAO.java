package com.example.Services.DAOs;

import com.example.Entities.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO {
    void save(String category) throws SQLException;
    void delete(int id) throws SQLException;
    Category findByName(String category_name) throws SQLException;
    List<Category> findAllCategories() throws SQLException;
}
