package com.example.Repositories.DAOs;

import com.example.Entities.Order;
import com.example.Entities.Product;

import java.util.List;

import java.sql.SQLException;

public interface OrderRepositoryDAO {
    void save(Order order) throws SQLException;
    void delete(int order_id) throws SQLException;
    int revenueByMonth(int month) throws SQLException;
    int soldProductsByMonth(int month) throws SQLException;
    int allCustomersByMonth(int month) throws SQLException;
    List<Integer> mostSoldProductsByMonth(int month) throws SQLException;
    int avrOrderValue() throws SQLException;
}
