package com.example.Repositories.DAOs;

import com.example.Entities.Product;

import java.sql.SQLException;
import java.util.List;
public interface ProductRepositoryDAO {
    String add(Product product) throws SQLException;
    String delete(int id) throws SQLException;
    String edit(Product product) throws SQLException;
    Product findById(int id) throws SQLException;
    Product findByName(String name) throws SQLException;
    List<Product> findAllByDescOrder() throws SQLException;
    List<Product> findAllByCategory(String category) throws SQLException;

}
