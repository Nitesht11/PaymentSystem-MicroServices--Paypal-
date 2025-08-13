package com.tech.payments.Pojo;

import lombok.Data;

@Data
public class CreateOrderReq
{
    private int amount;
    private String currency;
}
