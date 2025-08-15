package com.tech.payments.Helper;

import com.tech.payments.Http.HttpRequest;
import com.tech.payments.Paypal.Request.*;
import com.tech.payments.Pojo.CreateOrderReq;
import com.tech.payments.Utils.JsonUtils;
import com.tech.payments.constants.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Collections;
@Component
@Slf4j
@RequiredArgsConstructor

public class CreateOrderHelper {

    private  final JsonUtils jsonUtils;

    @Value("${paypal.createOrderUrl}")
    private String createOrderUrl;

    public HttpRequest prepareHttpRequestForCreateOrder(CreateOrderReq req, String accessToken) {
        HttpHeaders headerObj = new HttpHeaders();
        headerObj.setBearerAuth(accessToken);
        headerObj.setContentType(MediaType.APPLICATION_JSON );
        // this gives the unique value//
        headerObj.add(Constants.PAYPAL_REQUEST_ID, req.getTxnref());


        // create Amount
        Amount amount= new Amount();
        amount.setCurrencyCode(req.getCurrency());
        amount.setValue(req.getAmount());

        // create purchase unit
        PurchaseUnits purchaseUnits= new PurchaseUnits();
        purchaseUnits.setAmount(amount);

        // create experience context
        ExperienceContext experienceContext= new ExperienceContext();
        experienceContext.setPaymentMethodPrefernece(Constants.PMP_IMMEDIATE_PAYMENT_REQUIRED);
        experienceContext.setLandingPage(Constants.LANDING_PAGE_LOGIN);
        experienceContext.setShippingPreference(Constants.SP_NO_SHIPPING);
        experienceContext.setUserAction(Constants.UA_PAY_NOW);
        experienceContext.setReturnUrl(req.getReturnUrl());
        experienceContext.setCancelUrl(req.getCancelUrl());

        // create Paypal Object
        PayPal paypal = new PayPal();
        paypal.setExperienceContext(experienceContext);

        // Create payment source

        PaymentSource paymentSource = new PaymentSource();
        paymentSource.setPayPal(paypal);

        // create PaymentRequest
        PaymentRequest paymentRequest= new PaymentRequest();
        paymentRequest.setIntent(Constants.CAPTURE);
        paymentRequest.setPurchaseUnit(Collections.singletonList(purchaseUnits));
        paymentRequest.setPaymentSource(paymentSource);

        //Using Jackson u convert the   req into JSON from the wrapper class made
        String requestBodyAsJson = jsonUtils.toJson(paymentRequest);
        log.info("requestBodyAsJson:" + requestBodyAsJson);

        if(requestBodyAsJson == null) {
            log.error("requestBodyAsJson is null");
            throw new RuntimeException("requestBodyAsJson is null");
        }

        HttpRequest httpRequest=new HttpRequest();
        httpRequest.setHttpMethod(HttpMethod.POST);
        httpRequest.setUrl(createOrderUrl);
        httpRequest.setHeaders(headerObj);
        httpRequest.setRequestBody(requestBodyAsJson);
        log.info("httpRequest:"+ httpRequest);
        return httpRequest;
    }
}
