package com.tech.payments.paypalProvider;

import lombok.Data;

@Data
public class PPOrder {

    private String orderId;
    private String paypalStatus;
    private String redirecturl;
}
