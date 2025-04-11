package com.example.Controllers;

import com.example.Builders.CategoryResponseEntity;
import com.example.Builders.CommonResponse;
import com.example.Builders.UserResponseEntity;
import com.example.Entities.Category;

import java.sql.SQLException;
import java.util.Objects;
import java.util.List;

import com.example.Role.Role;
import com.example.Services.CategoryService;
import com.example.Services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService = new CategoryService();
    private UserService userService = new UserService();
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestHeader(HttpHeaders.AUTHORIZATION) String api_key, @RequestBody String request){
        ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        JsonNode input;

        try {
            int user_id = userService.findByKey(api_key);
            if (user_id == 0) {
                return UserResponseEntity.authorizationResponse(false, null);
            } else {
                if (!userService.findById(user_id).getRole().equals(Role.SUPER_ADMIN)) {
                    return CommonResponse.shouldReturnAccess(false);
                } else
                    input = mapper.readTree(request);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return CommonResponse.internalErrorResponse();
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return CommonResponse.badRequestResponse("Malformed JSON");
        }
        try {
            JsonNode categoryJson = input.get("category");
            if (Objects.isNull(categoryJson)) {
                return CommonResponse.badRequestResponse("All required fields are not provided");
            } else {
                String category = categoryJson.asText();
                categoryService.save(category);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return CommonResponse.internalErrorResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponse.internalErrorResponse();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String api_key, @RequestBody String request) {
        ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        JsonNode input;

        try {
            int user_id = userService.findByKey(api_key);
            if (user_id == 0) {
                return UserResponseEntity.authorizationResponse(false, null);
            } else {
                if (!userService.findById(user_id).getRole().equals(Role.SUPER_ADMIN)) {
                    return CommonResponse.shouldReturnAccess(false);
                } else
                    input = mapper.readTree(request);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return CommonResponse.internalErrorResponse();
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return CommonResponse.badRequestResponse("Malformed JSON");
        }
        try {
            JsonNode idJson = input.get("id");
            if (Objects.isNull(idJson)) {
                return CommonResponse.badRequestResponse("All required fields are not provided");
            } else {
                int id = idJson.asInt();
                categoryService.delete(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return CommonResponse.internalErrorResponse();
        }  catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponse.internalErrorResponse();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllCategories(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String api_key) {

        try {
            if (api_key == null) {
                CommonResponse.badRequestResponse("Missing request header 'Authorization'");
            }
            int user_id = userService.findByKey(api_key);
            if (user_id == 0) {
                return UserResponseEntity.authorizationResponse(false, null);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return CommonResponse.internalErrorResponse();
        }
        try {
            List<Category> categories = categoryService.findAllCategories();
            if (categories == null) {
                return CommonResponse.internalErrorResponse();
            } else
                return CategoryResponseEntity.shouldReturnListOfCategories(categories);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return CommonResponse.internalErrorResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponse.internalErrorResponse();
        }
    }
}
