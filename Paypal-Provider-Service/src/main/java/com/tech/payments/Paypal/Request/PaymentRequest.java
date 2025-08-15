package com.tech.payments.Paypal.Request;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentRequest {

    private String intent;

    @JsonProperty("purchase_units")
    private List<PurchaseUnits> purchaseUnit;

    @JsonProperty("payment_source")
    private PaymentSource paymentSource;
}
