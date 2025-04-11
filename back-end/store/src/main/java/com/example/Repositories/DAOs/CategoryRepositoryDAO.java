package com.example.Repositories.DAOs;

import com.example.Entities.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryRepositoryDAO {
    void save(String category) throws SQLException;
    void delete(int id) throws SQLException;
    Category findByName(String category) throws SQLException;
    List<Category> findAllCategories() throws SQLException;
}
