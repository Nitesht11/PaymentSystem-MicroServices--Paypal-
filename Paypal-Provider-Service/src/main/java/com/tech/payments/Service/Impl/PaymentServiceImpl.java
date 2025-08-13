package com.tech.payments.Service.Impl;

import com.tech.payments.Pojo.CreateOrderReq;
import com.tech.payments.Service.Interface.PaymentService;
import com.tech.payments.Service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private TokenService tokenService;
    @Override
    public String createOrder(CreateOrderReq req) {



        tokenService.getAccessToken();
        return null;
    }
}
