package com.runzo.common.response;

import com.runzo.common.constant.CommonConstant;
import lombok.Data;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T>{
    private boolean isSuccess;
    private String message;
    private T data;
    private int errorCode;
    private String RunzoCoId;
    private LocalDateTime timestamp;

    public ApiResponse(){
        this.RunzoCoId = MDC.get(CommonConstant.CORRELATION_ID);
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(T data, String message) {
        this.isSuccess = true;
        this.message = message;
        this.data = data;
        this.RunzoCoId = MDC.get(CommonConstant.CORRELATION_ID);
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(String message, int errorCode) {
        this.isSuccess = false;
        this.message = message;
        this.errorCode = errorCode;
        this.RunzoCoId = MDC.get(CommonConstant.CORRELATION_ID);
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(T data, String message, HttpStatus code) {
        this.data = data;
        this.isSuccess = false;
        this.message = message;
        this.errorCode = code.value();
        this.RunzoCoId = MDC.get(CommonConstant.CORRELATION_ID);
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message);
    }

    public static <T> ApiResponse<T> error(String message, int errorCode) {
        return new ApiResponse<>(message, errorCode);
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus httpStatus) {
        return new ApiResponse<>(message, httpStatus.value());
    }
    public static <T> ApiResponse<T> error(T data, String message, HttpStatus httpStatus) {
        return new ApiResponse<>(data, message, httpStatus);
    }
}
