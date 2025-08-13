package com.tech.payments.controller;

import com.tech.payments.Pojo.CreateOrderReq;
import com.tech.payments.Service.Interface.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/paypal/order")
public class PaymentController {

    private final PaymentService paymentService;

//    public PaymentController(PaymentService paymentService){
//        this.paymentService=paymentService;
//    }

    @PostMapping
    public String createOrder(@RequestBody CreateOrderReq createOrderReq){
        log.info("create order:{}", createOrderReq);

        String response = paymentService.createOrder(createOrderReq);
        return "create order"+ createOrderReq;

    }

    @GetMapping
    public  String getOrder(){
        return null;
    }


//    @PostMapping
}
