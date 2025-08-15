package com.tech.payments.Pojo;

import lombok.Data;

@Data
public class CreateOrderReq
{
    private String txnref;
    private String amount;
    private String currency;
    private String returnUrl;
    private String cancelUrl;


}
