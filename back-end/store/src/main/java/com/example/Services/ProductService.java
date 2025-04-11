package com.example.Services;

import com.example.Entities.Product;
import com.example.Repositories.ProductRepository;
import com.example.Services.DAOs.ProductDAO;

import java.sql.SQLException;
import java.util.List;

public class ProductService implements ProductDAO {
    private ProductRepository productRepository = new ProductRepository();
    @Override
    public void add(Product product) throws SQLException{
        productRepository.add(product);
    }

    @Override
    public void edit(Product product) throws SQLException {
        productRepository.edit(product);
    }

    @Override
    public void delete(int id) throws SQLException {
        productRepository.delete(id);
    }
    @Override
    public Product findByName(String name) throws SQLException {
        if (productRepository.findByName(name) != null){
            return productRepository.findByName(name);
        } else
            return null;
    }
    @Override
    public Product findByProductId(int id) throws SQLException {
        if (productRepository.findById(id) != null){
            return productRepository.findById(id);
        } else
            return null;
    }

    @Override
    public List<Product> findAllByCategory(String category) throws SQLException {
        if (productRepository.findAllByCategory(category) != null) {
            return productRepository.findAllByCategory(category);
        } else
            return null;
    }

    @Override
    public List<Product> findAllByDescOrder() throws SQLException {
        if (productRepository.findAllByDescOrder() != null) {
            return productRepository.findAllByDescOrder();
        } else
            return null;
    }

    @Override
    public List<Product> findAllDiscountedProducts() throws SQLException {
        return null;
    }
}
