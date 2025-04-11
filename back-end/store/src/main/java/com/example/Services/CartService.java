package com.example.Services;

import com.example.Entities.Product;
import com.example.Services.DAOs.CartDAO;
import com.example.Repositories.CartRepository;

import java.sql.SQLException;
import java.util.List;

public class CartService implements CartDAO {
    CartRepository cartRepository = new CartRepository();
    @Override
    public void add(int cart_id, int product_id, int product_amount, int user_id) throws SQLException {
        cartRepository.add(cart_id, product_id, product_amount, user_id);
    }

    @Override
    public void delete(int cart_id, int product_id) throws SQLException {
        cartRepository.delete(cart_id,product_id);
    }

    @Override
    public void clean(int cart_id) throws SQLException {
        cartRepository.clean(cart_id);
    }

    @Override
    public List<Product> findAllProducts(int cart_id) throws SQLException {
        if (cartRepository.findAllProducts(cart_id) != null) {
            return cartRepository.findAllProducts(cart_id);
        } else
            return null;
    }
}
