package com.tech.payments.Exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class PaypalProviderException extends RuntimeException{

    private final String errorCode;
    private final String errorMessage;
    private  final HttpStatus httpStatus;

    public PaypalProviderException (String errorCode,String errorMessage,HttpStatus httpStatus){

        this.errorCode= errorCode;
        this.errorMessage=errorMessage;
        this.httpStatus=httpStatus;
    }
}
