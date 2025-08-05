package com.tech.payments.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@Slf4j
@RequestMapping("v1/paypal/order")
public class PaymentController {

    @PostMapping
    public String createOrder(){
        log.info("create ordwe:");
        return "create order";

    }


//
//    @GetMapping
//    public  String getorder{}
//
//
//    @PostMapping
}
