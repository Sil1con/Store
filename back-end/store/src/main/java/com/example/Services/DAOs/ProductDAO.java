package com.example.Services.DAOs;

import com.example.Entities.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    void add(Product product) throws SQLException;
    void edit(Product product) throws SQLException;
    void delete(int id) throws SQLException;
    Product findByProductId(int id) throws SQLException;
    Product findByName(String name) throws SQLException;
    List<Product> findAllByCategory(String category) throws SQLException;
    List<Product> findAllByDescOrder() throws SQLException;
    List<Product> findAllDiscountedProducts() throws SQLException;
}
