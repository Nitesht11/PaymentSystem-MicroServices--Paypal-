package com.tech.payments.Service.Interface;

import com.tech.payments.Pojo.CreateOrderReq;
import com.tech.payments.Pojo.Order;

public interface PaymentService {

    public Order createOrder(CreateOrderReq  req);
}
