package com.example.Controllers;

import com.example.Builders.CommonResponse;
import com.example.Builders.UserResponseEntity;
import com.example.Role.Role;
import com.example.Services.OrderService;
import com.example.Services.ProductService;
import com.example.Services.UserService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@RestController
@RequestMapping("/order")
public class OrderController {
    UserService userService = new UserService();
    OrderService orderService = new OrderService();
    ProductService productService = new ProductService();

    @PostMapping("/create")
    public ResponseEntity<Object> save(@RequestHeader(HttpHeaders.AUTHORIZATION) String api_key, @RequestBody String request) {
        ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        JsonNode input;

        try {
            int user_id = userService.findByKey(api_key);
            if (user_id == 0) {
                return UserResponseEntity.authorizationResponse(false, null);
            } else {
                if (userService.findById(user_id).getRole() != Role.SUPER_ADMIN) {
                    return CommonResponse.shouldReturnAccess(false);
                } else {
                    input = mapper.readTree(request);
                }
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
            JsonNode orderIdJson = input.get("id");
            JsonNode userIdJson = input.get("user_id");
            JsonNode fnameJson = input.get("fname");
            JsonNode lnameJson = input.get("lname");
            JsonNode phoneJson = input.get("phone");
            JsonNode countryJson = input.get("country");
            JsonNode cityJson = input.get("city");
            JsonNode addressJson = input.get("address");
            JsonNode apartmentJson = input.get("apartment");
            JsonNode postalCodeJson = input.get("postal_code");
            JsonNode companyJson = input.get("company");

            if (Objects.isNull(orderIdJson) ||
                    (Objects.isNull(userIdJson)) ||
                    (Objects.isNull(fnameJson)) ||
                    (Objects.isNull(lnameJson)) ||
                    (Objects.isNull(phoneJson)) ||
                    (Objects.isNull(countryJson)) ||
                    (Objects.isNull(cityJson)) ||
                    (Objects.isNull(addressJson)) ||
                    (Objects.isNull(apartmentJson)) ||
                    (Objects.isNull(postalCodeJson))
                )
            {
                return CommonResponse.badRequestResponse("All required fields are not provided");
            } else {
                int order_id = orderIdJson.asInt();
                int user_id = orderIdJson.asInt();
                String fname = fnameJson.asText();
                String lname = lnameJson.asText();
                String phone = phoneJson.asText();
                String country = countryJson.asText();
                String city = cityJson.asText();
                String address = addressJson.asText();
                String apartment = apartmentJson.asText();
                String postal_code = postalCodeJson.asText();
                String company = companyJson.asText();
            }
        } catch (IOException ex)

    }
}
