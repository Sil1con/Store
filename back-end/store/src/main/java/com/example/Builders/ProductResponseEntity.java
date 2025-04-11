package com.example.Builders;

import com.example.Entities.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.Entities.Product;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class ProductResponseEntity {
    public static ResponseEntity<Object> findAllProducts(List<Product> products) {
        HttpStatus httpStatus = HttpStatus.OK;
        Map<String, List<Object>> response = new HashMap<>();
        List<Object> productsListJson = new ArrayList<>();
        Map<String, Object> productJson;
        List<Object> categories;
        Map<String, Object> category;

        for (Product loopProduct : products) {
            productJson = new HashMap<>();
            categories = new ArrayList<>();
            for (Category loopCategory : loopProduct.getCategories()) {
                category = new HashMap<>();
                category.put("name", loopCategory.getCategory());
                categories.add(category);
            }
            productJson.put("id", loopProduct.getId());
            productJson.put("title", loopProduct.getTitle());
            productJson.put("name", loopProduct.getName());
            productJson.put("details", loopProduct.getDetails());
            productJson.put("description", loopProduct.getDescription());
            productJson.put("readme", loopProduct.getReadme());
            productJson.put("amount", loopProduct.getAmount());
            productJson.put("price", loopProduct.getPrice());
            productJson.put("image", loopProduct.getImage());
            productJson.put("categories", categories);
            productsListJson.add(productJson);
        }
        response.put("All products", productsListJson);
        return new ResponseEntity<>(response, httpStatus);
    }
}
