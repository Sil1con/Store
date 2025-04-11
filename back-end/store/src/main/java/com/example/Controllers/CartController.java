package com.example.Controllers;

import com.example.Entities.Product;
import com.example.Services.CartService;
import com.example.Builders.CommonResponse;
import com.example.Builders.CartResponseEntity;
import com.example.Services.UserService;
import com.example.Entities.Cart;
import com.example.Builders.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Objects;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService = new CartService();
    private final UserService userService = new UserService();

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestHeader(HttpHeaders.AUTHORIZATION) String api_key, @RequestBody String request) {
        ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        JsonNode input;
        try {
            int user_id = userService.findByKey(api_key);
            if (user_id == 0) {
                return UserResponseEntity.authorizationResponse(false, null);
            } else {
                input = mapper.readTree(request);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());
            return CommonResponse.internalErrorResponse();
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return CommonResponse.badRequestResponse("Malformed JSON");
        }
        try {
            JsonNode cartJson = input.get("cart_id");
            JsonNode productJson = input.get("product_id");
            JsonNode productAmountJson = input.get("product_amount");
            JsonNode userJson = input.get("user_id");
            if (Objects.isNull(cartJson) ||
                    Objects.isNull(productJson) ||
                    Objects.isNull(productAmountJson) ||
                    Objects.isNull(userJson))
            {
                return CommonResponse.badRequestResponse("All required fields are not provided");
            } else {
                int cart_id = cartJson.asInt();
                int product_id = productJson.asInt();
                int productAmount = productAmountJson.asInt();
                int user_id = userJson.asInt();
                cartService.add(cart_id, product_id, productAmount, user_id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());
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
        int user_id;
        try {
            user_id = userService.findByKey(api_key);
            if (user_id == 0) {
                return UserResponseEntity.authorizationResponse(false, null);
            } else {
                input = mapper.readTree(request);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());
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
                int product_id = idJson.asInt();
                cartService.delete(user_id, product_id); //user_id is identical to cart_id
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());
            return CommonResponse.internalErrorResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponse.internalErrorResponse();
        }
    }

    @PatchMapping("/clean")
    public ResponseEntity<Object> clean(@RequestHeader(HttpHeaders.AUTHORIZATION) String api_key, @RequestBody String request) {
        ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        JsonNode input;
        int user_id;
        try {
            user_id = userService.findByKey(api_key);
            if (user_id == 0) {
                return UserResponseEntity.authorizationResponse(false, null);
            } else {
                input = mapper.readTree(request);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());
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
                int cart_id = idJson.asInt();
                if (cart_id != user_id) {
                    return CommonResponse.shouldReturnAccess(false);
                } else {
                    cartService.clean(cart_id);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());
            return CommonResponse.internalErrorResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponse.internalErrorResponse();
        }
    }
    @GetMapping("/all")
    public ResponseEntity<Object> findAllCartProducts(@RequestHeader(HttpHeaders.AUTHORIZATION) String api_key) {
        int user_id;
        try {
            user_id = userService.findByKey(api_key);
            if (user_id == 0) {
                return UserResponseEntity.authorizationResponse(false, null);
            } else {
                List<Product> products = cartService.findAllProducts(user_id); //cart_id is equal to user_id
                Cart cart = new Cart(user_id, user_id, products);
                return CartResponseEntity.shouldReturnAllCartProducts(cart);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());
            return CommonResponse.internalErrorResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponse.internalErrorResponse();
        }
    }
}

