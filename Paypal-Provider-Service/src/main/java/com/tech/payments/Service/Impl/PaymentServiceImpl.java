package com.tech.payments.Service.Impl;

import com.tech.payments.Helper.CreateOrderHelper;
import com.tech.payments.Http.HttpRequest;
import com.tech.payments.Http.HttpServiceEngine;
import com.tech.payments.Paypal.Link;
import com.tech.payments.Paypal.PayPalOrderResp;
import com.tech.payments.Pojo.CreateOrderReq;
import com.tech.payments.Pojo.Order;
import com.tech.payments.Service.Interface.PaymentService;
import com.tech.payments.Service.TokenService;
import com.tech.payments.Utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


    private final TokenService tokenService;

    private final CreateOrderHelper createOrderHelper;

    private final JsonUtils jsonUtils;

    private final HttpServiceEngine httpServiceEngine;


    @Override
    public Order createOrder(CreateOrderReq req) {

//        1.call oAuth api to get access token
//        2.call create order api from paypal with acces token
//        3.handle succes and error response
//        4.return response to controller

        String  accessToken = tokenService.getAccessToken();
        HttpRequest httpRequest =createOrderHelper.prepareHttpRequestForCreateOrder(req, accessToken);

        // make call to 3rd party api
        ResponseEntity<String> createOrderResponse = httpServiceEngine.makeHttpCall(httpRequest);
        String responseBody= createOrderResponse.getBody();

        PayPalOrderResp resObj=jsonUtils.fromJson(responseBody,PayPalOrderResp.class);
        log.info("resObj"+resObj);

        Order orderRes =new Order();
        orderRes.setOrderId(resObj.getId());
        orderRes.setPaypalStatus(resObj.getStatus());
        Optional<String>opRedirectUrl= resObj.getLinks().stream()
                .filter(link ->"payer_action".equalsIgnoreCase(link.getRel()))
                .map(Link::getHref).findFirst();

        orderRes.setRedirecturl(opRedirectUrl.orElse(null));

        log.info("OrderRes:{}",orderRes);

        return orderRes;
    }


}
