package com.example.Builders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CommonResponse {
    public static ResponseEntity<Object> responseBuilder (
            String type,
            HttpStatus httpStatus,
            Object responseObject)
    {
        Map<String, Object> response = new HashMap<>();
        response.put(type, responseObject);
        return new ResponseEntity<>(response, httpStatus);
    }
    public static ResponseEntity<Object> shouldReturnAccess(boolean verified) {
        Map<String, String> response = new HashMap<>();
        HttpStatus httpStatus;
        if (!verified) {
            httpStatus = HttpStatus.FORBIDDEN;
            String message = "User does not have rights";
            response.put("message",message);
            return new ResponseEntity<>(response, httpStatus);
        } else {
            httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(httpStatus);
        }
    }
    public static ResponseEntity<Object> badRequestResponse (String message) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);

        return new ResponseEntity<>(response, httpStatus);
    }
    public static ResponseEntity<Object> internalErrorResponse () {
        String message = "Internal Server Error";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);

        return new ResponseEntity<>(response, httpStatus);
    }
}
