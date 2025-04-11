package com.example.Controllers;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.Builders.KeyBuilder;
import com.example.Builders.CommonResponse;
import com.example.Builders.UserResponseEntity;
import com.example.Services.UserService;
import com.example.Entities.User;
import com.example.Role.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/shop")
public class UserController {
    UserService userService = new UserService();
    @PostMapping("/register")
    public ResponseEntity<Object> create (@RequestBody String request) {
        ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        JsonNode input;
        String api_key;
        User user;
        try {
            input = mapper.readTree(request);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return CommonResponse.badRequestResponse("Malformed Json");
        }
        try {
            JsonNode nameJsonNode = input.get("name");
            JsonNode emailJsonNode = input.get("email");
            JsonNode phoneJsonNode = input.get("phone");
            JsonNode passwordJsonNode = input.get("password");
            if (Objects.isNull(nameJsonNode) || Objects.isNull(emailJsonNode) || Objects.isNull(phoneJsonNode) || Objects.isNull(passwordJsonNode)) {
                return CommonResponse.badRequestResponse("All required fields are not provided");
            } if (!Objects.isNull(userService.findByEmail(emailJsonNode.asText()))) {
                return UserResponseEntity.shouldReturnThatUserWithTheEmailExists();
            } else {
                String name = nameJsonNode.asText();
                String fname = name.split(" ")[0];
                String lname = name.split(" ")[1];
                String email = emailJsonNode.asText();
                String phone = phoneJsonNode.asText();
                String salt = BCrypt.gensalt();
                String password = BCrypt.hashpw(passwordJsonNode.asText(), salt);
                api_key = KeyBuilder.generateKey();

                user = new User(fname, lname, email, phone, password, salt);
                userService.save(user);
                int user_id = userService.findByEmail(user.getEmail()).getId();
                userService.saveKey(user_id, api_key);
                return UserResponseEntity.authorizationResponse(true, api_key);
            }
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

    @GetMapping("/login")
    public ResponseEntity<Object> login (@RequestBody String request) {
        ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        JsonNode input;
        String api_key;
        User user;
        try {
            input = mapper.readTree(request);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return CommonResponse.badRequestResponse("Malformed JSON");
        }
        try {
            JsonNode emailJsonNode = input.get("email");
            JsonNode passwordJsonNode = input.get("password");
            if (Objects.isNull(emailJsonNode) || Objects.isNull(passwordJsonNode)) {
                return CommonResponse.badRequestResponse("All required fields are not provided");
            } else {
                String password = passwordJsonNode.asText();
                String email = emailJsonNode.asText();

                if(Objects.isNull(userService.findByEmail(email))) {
                    return UserResponseEntity.authorizationResponse(false, null);
                } else {
                    user = userService.findByEmail(email);
                    password = BCrypt.hashpw(password, user.getSalt());

                    if(!user.getPassword().equals(password)) {
                        return UserResponseEntity.authorizationResponse(false, null);
                    } else {
                        int id = user.getId();
                        api_key = KeyBuilder.generateKey();
                        userService.saveKey(id, api_key);
                        return UserResponseEntity.authorizationResponse(true, api_key);
                    }
                }
            }
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

    @PatchMapping("/update")
    public ResponseEntity<Object> update (@RequestHeader(HttpHeaders.AUTHORIZATION) String api_key, @RequestBody String request) {
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
            JsonNode fnameJson = input.get("fname");
            JsonNode lnameJson = input.get("lname");
            JsonNode emailJson = input.get("email");
            JsonNode phoneJson = input.get("phone");
            JsonNode passwordJson = input.get("password");
            if (Objects.isNull(idJson) ||
                    Objects.isNull(fnameJson) ||
                    Objects.isNull(lnameJson) ||
                    Objects.isNull(emailJson) ||
                    Objects.isNull(phoneJson) ||
                    Objects.isNull(passwordJson))
            {
                return CommonResponse.badRequestResponse("All required fields are not provided");
            } else {
                int id = idJson.asInt();
                String fname = fnameJson.asText();
                String lname = lnameJson.asText();
                String email = emailJson.asText();
                String phone = phoneJson.asText();
                String salt = BCrypt.gensalt();
                String password = BCrypt.hashpw(passwordJson.asText(), salt);

                User user = new User(id, fname, lname, email, phone, password, salt);
                userService.update(user);
                return new ResponseEntity<>(HttpStatus.OK);
            }
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

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody String request) {
        ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        JsonNode input;

        try {
            input = mapper.readTree(request);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return CommonResponse.badRequestResponse("Malformed JSON");
        }
        try {
            JsonNode roleJsonNode = input.get("role");
            JsonNode idJsonNode = input.get("id");
            if (Objects.isNull(idJsonNode) || Objects.isNull(roleJsonNode)) {
                return CommonResponse.badRequestResponse("All required fields are not provided");
            } else {
                if (!roleJsonNode.asText().equalsIgnoreCase(Role.SUPER_ADMIN.name())) {
                    return UserResponseEntity.authorizationResponse(false, null);
                } else {

                    int id = idJsonNode.asInt();

                    if (Objects.isNull(userService.findById(id))) {
                        return CommonResponse.badRequestResponse("User with such id does not exist");
                    } else {
                        userService.delete(id);
                        return new ResponseEntity<>(HttpStatus.OK);
                    }
                }
            }
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
    @GetMapping("/findByEmail")
    public ResponseEntity<Object> findByEmail(@RequestHeader(HttpHeaders.AUTHORIZATION) String api_key, @RequestBody String request) {
        ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        JsonNode input;

        try {
            int user_id = userService.findByKey(api_key);
            if (user_id == 0) {
                return UserResponseEntity.authorizationResponse(false, null);
            } else //Check user's role
                if (!userService.findById(user_id).getRole().equals(Role.SUPER_ADMIN)) {
                    return CommonResponse.shouldReturnAccess(false);
                } else
                    input = mapper.readTree(request);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return CommonResponse.badRequestResponse("Malformed JSON");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return CommonResponse.internalErrorResponse();
        }
        try {
            JsonNode emailJsonNode = input.get("email");
            if (Objects.isNull(emailJsonNode)) {
                return CommonResponse.badRequestResponse("All required fields are not provided");
            } else {
                String email = emailJsonNode.asText();
                User user = userService.findByEmail(email);
                if (user == null) {
                    return CommonResponse.badRequestResponse("User with such email does not exist");
                }
                return UserResponseEntity.shouldReturnUser(user);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return CommonResponse.internalErrorResponse();
        }
    }
    @GetMapping("/users/all")
    public ResponseEntity<Object> findAllUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String api_key) {
        List<User> users;
        try {
            int user_id = userService.findByKey(api_key);
            if (user_id == 0) {
                return UserResponseEntity.authorizationResponse(false, null);
            } else {
                Role role = userService.findById(user_id).getRole();
                if (!role.equals(Role.SUPER_ADMIN)) {
                    return CommonResponse.shouldReturnAccess(false);
                } else {
                    users = userService.findAllUsers();
                    return UserResponseEntity.shouldReturnListOfUsers(users);
                }
            }
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
