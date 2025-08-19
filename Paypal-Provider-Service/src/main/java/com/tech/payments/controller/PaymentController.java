package com.tech.payments.controller;

import com.tech.payments.Exception.PaypalProviderException;
import com.tech.payments.Pojo.CreateOrderReq;
import com.tech.payments.Pojo.Order;
import com.tech.payments.Service.Interface.PaymentService;
import com.tech.payments.constants.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/paypal/order")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody CreateOrderReq createOrderReq){

        log.info("create order:{}", createOrderReq);

        Order response = paymentService.createOrder(createOrderReq);

        log.info("response:{}", response);

        return response;
    }

    @GetMapping("/{orderId}")
    public  Order getOrder(@PathVariable String orderId){

        log.info("getOrder OrderId{}:", orderId);

        if(orderId.contains("TEMP2")) {
            throw new PaypalProviderException(
                    ErrorCodeEnum.TEMP_02.getCode(),
                    ErrorCodeEnum.TEMP_02.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        Order response= paymentService.getOrder(orderId);

        log.info("Response:{}", response);

        return response;
    }
    @PostMapping("/{orderId}/capture")
    public Order captureOrder(@PathVariable String orderId){
        log.info("captureOrder order : {}", orderId);

        Order response= paymentService.captureOrder(orderId);

        log.info(" response:{}",response);
        return response;
    }
}
