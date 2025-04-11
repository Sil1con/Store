package com.example.Services;

import com.example.Entities.Order;
import com.example.Repositories.DAOs.OrderRepositoryDAO;
import com.example.Repositories.OrderRepository;

import java.sql.SQLException;
import java.util.List;

public class OrderService implements OrderRepositoryDAO {
    OrderRepository orderRepository = new OrderRepository();
    @Override
    public void save(Order order) throws SQLException {
        orderRepository.save(order);
    }

    @Override
    public void delete(int order_id) throws SQLException {
        orderRepository.delete(order_id);
    }

    @Override
    public int revenueByMonth(int month) throws SQLException {
        return orderRepository.revenueByMonth(month);
    }
    @Override
    public int soldProductsByMonth(int month) throws SQLException {
        return orderRepository.soldProductsByMonth(month);
    }
    @Override
    public List<Integer> mostSoldProductsByMonth(int month) throws SQLException {
        return orderRepository.mostSoldProductsByMonth(month);
    }

    @Override
    public int avrOrderValue() throws SQLException {
        return orderRepository.avrOrderValue();
    }
    @Override
    public int allCustomersByMonth(int month) throws SQLException {
        return orderRepository.allCustomersByMonth(month);
    }
}
