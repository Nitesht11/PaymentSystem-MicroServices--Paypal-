package com.tech.payments.Pojo;

import lombok.Data;

@Data
public class Order {

    private String orderId;
    private String paypalStatus;
    private String redirecturl;
}
