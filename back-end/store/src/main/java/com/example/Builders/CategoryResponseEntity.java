package com.example.Builders;

import com.example.Entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CategoryResponseEntity {
    public static ResponseEntity<Object> shouldReturnListOfCategories(List<Category> categories) {
        HttpStatus httpStatus = HttpStatus.OK;
        Map<String, List<Object>> response = new HashMap<>();
        List<Object> list = new ArrayList<>();
        Map<String, Object> json;

        for (Category obj : categories) {
            json = new HashMap<>();
            json.put("id", obj.getId());
            json.put("category", obj.getCategory());
            list.add(json);
        }
        response.put("All categories", list);
        return new ResponseEntity<>(response, httpStatus);
    }
}
