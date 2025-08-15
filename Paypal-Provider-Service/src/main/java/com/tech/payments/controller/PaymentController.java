package com.tech.payments.controller;

import com.tech.payments.Pojo.CreateOrderReq;
import com.tech.payments.Pojo.Order;
import com.tech.payments.Service.Interface.PaymentService;
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

    @GetMapping
    public  String getOrder(){
        return null;
    }


//    @PostMapping
}
