package com.example.Controllers;

import com.example.Builders.CommonResponse;
import com.example.Builders.ProductResponseEntity;
import com.example.Builders.UserResponseEntity;
import com.example.Entities.Category;
import com.example.Entities.Product;
import com.example.Role.Role;
import com.example.Services.ProductService;
import com.example.Services.UserService;
import com.example.Audit.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;


@RestController
@RequestMapping("/product")
public class ProductController {
    UserService userService = new UserService();
    ProductService productService = new ProductService();

    @PostMapping("/create")
    public ResponseEntity<Object> add(@RequestHeader(HttpHeaders.AUTHORIZATION) String api_key, @RequestBody String request) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode input;
        Role userRole;
        try {
            int user_id = userService.findByKey(api_key);
            if (user_id == 0) {
                return UserResponseEntity.authorizationResponse(false, null);
            } else {
                userRole = userService.findById(user_id).getRole();
                if (!userRole.equals(Role.SUPER_ADMIN) || !userRole.equals(Role.ADMIN)) {
                    return CommonResponse.shouldReturnAccess(false);
                } else
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
            JsonNode titleJson = input.get("title");
            JsonNode nameJson = input.get("name");
            JsonNode detailsJson = input.get("details");
            JsonNode descrJson = input.get("description");
            JsonNode readmeJson = input.get("readme");
            JsonNode amountJson = input.get("amount");
            JsonNode priceJson = input.get("price");
            JsonNode imageJson = input.get("image");
            JsonNode categoriesJson = input.get("categories");

            if (Objects.isNull(titleJson) ||
                    Objects.isNull(nameJson) ||
                    Objects.isNull(detailsJson) ||
                    Objects.isNull(descrJson) ||
                    Objects.isNull(readmeJson) ||
                    Objects.isNull(amountJson) ||
                    Objects.isNull(priceJson) ||
                    Objects.isNull(imageJson) ||
                    Objects.isNull(categoriesJson)
            ) {
                return CommonResponse.badRequestResponse("All required fields are not provided");
            } else {
                String title = titleJson.asText();
                String name = nameJson.asText();
                String details = detailsJson.asText();
                String descr = descrJson.asText();
                String readme = readmeJson.asText();
                int amount = amountJson.asInt();
                float price = Float.parseFloat(priceJson.asText());
                String image = imageJson.asText();
                String categoriesStr = categoriesJson.asText();
                ArrayList<Category> categories = new ArrayList<>();

                //I have possibility to avoid new Audit method, just using Audit.categoryAudit
                //Audit returns mismatched categories
                if (!Objects.isNull(Audit.categoryJsonAudit(categoriesStr))) {
                    return CommonResponse.badRequestResponse("Categories were not properly provided");
                } else {
                    for (Category category : Audit.categoryJsonConverter(categoriesStr)) {
                        categories.add(category);
                    }
                    Product product = new Product(
                            title, name, details, descr,
                            readme, amount, price, image, categories
                    );
                    productService.add(product);
                    int product_id = productService.findByName(name).getId();
                    return CommonResponse.responseBuilder("id", HttpStatus.OK, product_id);
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return CommonResponse.internalErrorResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponse.badRequestResponse("Malformed JSON");
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<Object> edit(@RequestHeader(HttpHeaders.AUTHORIZATION) String api_key, @RequestBody String request) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode input;
        Role userRole;
        try {
            int user_id = userService.findByKey(api_key);
            if (user_id == 0) {
                return UserResponseEntity.authorizationResponse(false, null);
            } else {
                userRole = userService.findById(user_id).getRole();
                if (!userRole.equals(Role.SUPER_ADMIN) || !userRole.equals(Role.ADMIN)) {
                    return CommonResponse.shouldReturnAccess(false);
                } else
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
            JsonNode titleJson = input.get("title");
            JsonNode nameJson = input.get("name");
            JsonNode detailsJson = input.get("details");
            JsonNode descrJson = input.get("description");
            JsonNode readmeJson = input.get("readme");
            JsonNode amountJson = input.get("amount");
            JsonNode priceJson = input.get("price");
            JsonNode imageJson = input.get("image");
            JsonNode categoriesJson = input.get("categories");

            if (Objects.isNull(idJson) ||
                    Objects.isNull(titleJson) ||
                    Objects.isNull(nameJson) ||
                    Objects.isNull(detailsJson) ||
                    Objects.isNull(descrJson) ||
                    Objects.isNull(readmeJson) ||
                    Objects.isNull(amountJson) ||
                    Objects.isNull(priceJson) ||
                    Objects.isNull(imageJson) ||
                    Objects.isNull(categoriesJson)
            ) {
                return CommonResponse.badRequestResponse("All required fields are not provided");
            } else {
                int id = idJson.asInt();
                String title = titleJson.asText();
                String name = nameJson.asText();
                String details = detailsJson.asText();
                String descr = descrJson.asText();
                String readme = readmeJson.asText();
                int amount = amountJson.asInt();
                float price = Float.parseFloat(priceJson.asText());
                String image = imageJson.asText();
                String categoriesStr = categoriesJson.asText();
                ArrayList<Category> categories = new ArrayList<>();

                //Audit returns mismatched categories
                if (!Objects.isNull(Audit.categoryJsonAudit(categoriesStr))) {
                    return CommonResponse.badRequestResponse("Categories were not properly provided");
                } else {
                    for (Category category : Audit.categoryJsonConverter(categoriesStr)) {
                        categories.add(category);
                    }
                    Product product = new Product(
                            title, name, details, descr,
                            readme, amount, price, image, categories
                    );
                    Product oldProduct = productService.findByProductId(id);
                    Product updatedProduct = Audit.productAudit(oldProduct, product);
                    productService.edit(updatedProduct);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return CommonResponse.internalErrorResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponse.badRequestResponse("Malformed JSON");
        }
    }
//    @DeleteMapping("/delete")
//    public ResponseEntity<Object> delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String api_key, @RequestBody String request) {
//        ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
//        JsonNode input;
//
//        try {
//            int user_id = userService.findByKey(api_key);
//            if (user_id == 0) {
//                return UserResponseEntity.authorizationResponse(false, null);
//            } else {
//                Role userRole = userService.findById(user_id).getRole();
//                if (!userRole.equals(Role.SUPER_ADMIN) || !userRole.equals(Role.ADMIN)) {
//                    return CommonResponse.shouldReturnAccess(false);
//                } else
//                    input = mapper.readTree(request);
//            }
//        } catch (SQLException ex) {
//            System.out.println("SQLException: " + ex.getMessage());
//            System.out.println("SQLState: " + ex.getSQLState());
//            System.out.println("VendorError:" + ex.getErrorCode());
//            return CommonResponse.internalErrorResponse();
//        } catch (JsonProcessingException ex) {
//            ex.printStackTrace();
//            return CommonResponse.badRequestResponse("Malformed JSON");
//        }
//        try {
//            JsonNode idJson = input.get("id");
//            if (Objects.isNull(idJson)) {
//                return CommonResponse.badRequestResponse("All required fields were not provided");
//            } else {
//                int product_id = idJson.asInt();
//                if (productService.findByProductId(product_id) == null) {
//                    return CommonResponse.badRequestResponse("Product with such id does not exist");
//                } else {
//                    productService.delete(product_id);
//                    return new ResponseEntity<>(HttpStatus.OK);
//                }
//            }
//        } catch (SQLException ex) {
//            System.out.println("SQLException: " + ex.getMessage());
//            System.out.println("SQLState: " + ex.getSQLState());
//            System.out.println("VendorError:" + ex.getErrorCode());
//            return CommonResponse.internalErrorResponse();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return CommonResponse.internalErrorResponse();
//        }
//    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String api_key,
            @RequestBody String request)
    {
        ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        JsonNode input;

        try {
            int user_id = userService.findByKey(api_key);
            if (user_id == 0) {
                return UserResponseEntity.authorizationResponse(false, null);
            } else {
                Role userRole = userService.findById(user_id).getRole();
                if (!userRole.equals(Role.SUPER_ADMIN) || !userRole.equals(Role.ADMIN)) {
                    return CommonResponse.shouldReturnAccess(false);
                } else {
                    input = mapper.readTree(request);
                    JsonNode idJson = input.get("id");
                    if (Objects.isNull(idJson)) {
                        return CommonResponse.badRequestResponse("All required fields were not provided");
                    } else {
                        int product_id = idJson.asInt();
                        if (productService.findByProductId(product_id) == null) {
                            return CommonResponse.badRequestResponse("Product with such id does not exist");
                        } else {
                            productService.delete(product_id);
                            return new ResponseEntity<>(HttpStatus.OK);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());
            return CommonResponse.internalErrorResponse();
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return CommonResponse.badRequestResponse("Malformed JSON");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponse.internalErrorResponse();
        }
    }
    @GetMapping("/all")
    public ResponseEntity<Object> findAllByDescOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String api_key) {
        try {
            int user_id = userService.findByKey(api_key);
            if (user_id == 0) {
                return UserResponseEntity.authorizationResponse(false, null);
            } else {
                Role userRole = userService.findById(user_id).getRole();
                if (!userRole.equals(Role.SUPER_ADMIN) || !userRole.equals(Role.ADMIN)) {
                    return CommonResponse.shouldReturnAccess(false);
                } else {
                    List<Product> products = productService.findAllByDescOrder();
                    return ProductResponseEntity.findAllProducts(products);
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
    @GetMapping("/all/category")
    public ResponseEntity<Object> findAllByCategory (
            @RequestHeader(HttpHeaders.AUTHORIZATION) String api_key,
            @RequestHeader(name = "Category") String category)
    {
        try {
            int user_id = userService.findByKey(api_key);
            if (user_id == 0) {
                return UserResponseEntity.authorizationResponse(false, null);
            } else {
                Role userRole = userService.findById(user_id).getRole();
                if (!userRole.equals(Role.SUPER_ADMIN) || !userRole.equals(Role.ADMIN)) {
                    return CommonResponse.shouldReturnAccess(false);
                } else {
                    List<Product> products = productService.findAllByCategory(category);
                    return ProductResponseEntity.findAllProducts(products);
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
}
