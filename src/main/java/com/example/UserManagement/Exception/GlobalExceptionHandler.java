package com.example.UserManagement.Exception;

import com.example.UserManagement.Exception.AccessDeniedException;
import com.example.UserManagement.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleException(Exception e) {

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError("Oops..Something went wrong!");
        apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleAccessDeniedException(AccessDeniedException e) {

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        apiResponse.setError("Unauthorized");

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

}
