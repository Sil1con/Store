package com.example.Repositories.DAOs;

import com.example.Entities.Product;

import java.sql.SQLException;
import java.util.List;
public interface CartRepositoryDAO {
    void add(int cart_id, int product_id, int product_amount, int user_id) throws SQLException;
    void delete(int cart_id,int product_id) throws SQLException;
    void clean(int cart_id) throws SQLException;
    List<Product> findAllProducts(int cart_id) throws SQLException;
}
