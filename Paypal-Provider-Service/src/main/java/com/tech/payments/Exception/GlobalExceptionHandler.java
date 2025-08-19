package com.tech.payments.Exception;

import com.tech.payments.Pojo.ErrorResponse;
import com.tech.payments.constants.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(PaypalProviderException.class)
    public ResponseEntity<ErrorResponse> handlePaypalException(PaypalProviderException ex){
        log.error("PaypalProviderException: {}",ex.getMessage(),ex);

        ErrorResponse errorResponse= new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        log.error("errorResponse: {}", errorResponse);
        return new ResponseEntity<>(errorResponse,
                ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>genericException(Exception ex){
        ErrorResponse errorResponse= new ErrorResponse(
                ErrorCodeEnum.GENERIC_ERROR.getCode(),
                ErrorCodeEnum.GENERIC_ERROR.getMessage());
        log.error("generic response: {}",ex.getMessage(),ex);
        return  new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


