package com.tech.payments.Service.Interface;

import com.tech.payments.Pojo.CreateOrderReq;

public interface PaymentService {

    public String createOrder(CreateOrderReq  req);
}
