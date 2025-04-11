package com.example.Builders;

import com.example.Entities.Category;
import com.example.Entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.Entities.Cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class CartResponseEntity {
    public static ResponseEntity<Object> shouldReturnAllCartProducts(Cart cart) {
        HttpStatus httpStatus = HttpStatus.OK;
        Map<String, List<Object>> response = new HashMap<>();
        List<Object> products = new ArrayList<>();
        Map<String, Object> product;
        List<Object> categories;
        Map<String, String> category;

        for (Product loopProduct : cart.getProducts()) {
            product = new HashMap<>();
            categories = new ArrayList<>();
            for (Category loopCategory : loopProduct.getCategories()) {
                category = new HashMap<>();
                category.put("name", loopCategory.getCategory());
                categories.add(category);
            }
            product.put("id", loopProduct.getId());
            product.put("title", loopProduct.getTitle());
            product.put("description", loopProduct.getDescription());
            product.put("amount", loopProduct.getAmount());
            product.put("price", loopProduct.getPrice());
            product.put("image", loopProduct.getImage());
            product.put("categories", categories);
            products.add(product);
        }
        response.put("All products", products);
        return new ResponseEntity<>(response, httpStatus);
    }
}
