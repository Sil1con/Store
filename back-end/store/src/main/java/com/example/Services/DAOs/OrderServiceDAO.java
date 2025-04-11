package com.example.Services.DAOs;

import com.example.Entities.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderServiceDAO {
    void save(Order order) throws SQLException;
    void delete(int order_id) throws SQLException;
    int revenueByMonth(int month) throws SQLException;
    int soldProductsByMonth(int month) throws SQLException;
    int allCustomersByMonth(int month) throws SQLException;
    List<Integer> mostSoldProductsByMonth(int month) throws SQLException;
    int avrOrderValue() throws SQLException;
}
