package com.runzo.common.exceptions.handler;

import com.runzo.common.constant.CommonConstant;
import com.runzo.common.exceptions.ex.ActivityNotFoundException;
import com.runzo.common.exceptions.ex.UserAlreadyExistException;
import com.runzo.common.exceptions.ex.UserNotFoundException;
import com.runzo.common.response.ApiResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException ex){
        ApiResponse<Object> response = ApiResponse.error(extractMessage(ex), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> userNotFoundException(UserNotFoundException ex){
        ApiResponse<Object> response = ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ActivityNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> activityNotFoundException(ActivityNotFoundException ex){
        ApiResponse<Object> response = ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiResponse<Object>> userAlreadyExist(UserAlreadyExistException ex){
        ApiResponse<Object> response = ApiResponse.error(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgValidation(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        for(FieldError error: ex.getBindingResult().getFieldErrors()){
            errors.put(error.getField(), error.getDefaultMessage());
        }

        HashMap<String, Object> response = new HashMap<>();
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(response, "Validation failed", HttpStatus.BAD_REQUEST));
    }




    private String extractMessage(RuntimeException ex){
        String message = ex.getMessage();
        if(message!=null && message.contains("duplicate key error")){
            if(message.contains("user_id") || message.contains("userId")){
                return "User ID already exists";
            }else if(message.contains("email")){
                return "Email already exist";
            }else if(message.contains("phone")){
                return "Phone number already exists";
            }

        }
        return message;
    }
}
