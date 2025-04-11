package com.example.Builders;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.Entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserResponseEntity {
    public static ResponseEntity<Object> shouldReturnThatUserWithTheEmailExists() {
        String message = "User with such email was already created";
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);

        return new ResponseEntity<>(response, httpStatus);
    }
    public static ResponseEntity<Object> authorizationResponse (boolean verified, String api_key) {
        String message;
        HttpStatus httpStatus;
        Map<String, Object> response = new HashMap<>();
        if (!verified) {
            message = "Unauthorized";
            httpStatus = HttpStatus.FORBIDDEN;
            response.put("verified", message);
        } else {
            message = "Authorized";
            httpStatus = HttpStatus.OK;
            response.put("verified", message);
            response.put("api_key", api_key);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    public static ResponseEntity<Object> shouldReturnUser(User user) {
        HttpStatus httpStatus = HttpStatus.OK;
        Map<String, Object> response = new HashMap<>();
        
        response.put("id", user.getId());
        response.put("Name", user.getName());
        response.put("email", user.getEmail());
        response.put("phone", user.getPhone());
        response.put("password", user.getPassword());
        
        return new ResponseEntity<>(response, httpStatus);
    }
    public static ResponseEntity<Object> shouldReturnListOfUsers(List<User> users) throws JsonProcessingException {

        Map<String, List<Object>> response = new HashMap<>();
        List<Object> listOfUsers = new ArrayList<>();
        Map<String, Object> jsonList;
        HttpStatus httpStatus = HttpStatus.OK;

        for (User obj : users) {
            jsonList = new HashMap<>();
            jsonList.put("id", obj.getId());
            jsonList.put("Name", obj.getName());
            jsonList.put("email", obj.getEmail());
            jsonList.put("phone", obj.getPhone());
            jsonList.put("password", obj.getPassword());
            listOfUsers.add(jsonList);
        }
        response.put("All Users", listOfUsers);
        return new ResponseEntity<>(response, httpStatus);
    }
}
