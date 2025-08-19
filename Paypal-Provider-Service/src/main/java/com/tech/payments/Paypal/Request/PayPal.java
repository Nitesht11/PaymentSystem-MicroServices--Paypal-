package com.tech.payments.Paypal.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class PayPal {

    @JsonProperty("experiencee_context")

    private ExperienceContext experienceContext;
}
