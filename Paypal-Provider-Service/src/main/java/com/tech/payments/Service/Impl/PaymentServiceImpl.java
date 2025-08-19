package com.tech.payments.Service.Impl;
import com.tech.payments.Exception.PaypalProviderException;
import com.tech.payments.Service.Helper.CaptureOrderHelper;
import com.tech.payments.Service.Helper.CreateOrderHelper;
import com.tech.payments.Service.Helper.GetOrderHelper;
import com.tech.payments.Http.HttpRequest;
import com.tech.payments.Http.HttpServiceEngine;
import com.tech.payments.Paypal.Link;
import com.tech.payments.Paypal.PayPalOrderResp;
import com.tech.payments.Pojo.CreateOrderReq;
import com.tech.payments.Pojo.Order;
import com.tech.payments.Service.Interface.PaymentService;
import com.tech.payments.Service.TokenService;
import com.tech.payments.Utils.JsonUtils;
import com.tech.payments.constants.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final TokenService tokenService;
    private final CreateOrderHelper createOrderHelper;
    private final GetOrderHelper getOrderHelper;
    private final CaptureOrderHelper captureOrderHelper;
    private final JsonUtils jsonUtils;
    private final HttpServiceEngine httpServiceEngine;


    @Override
    public Order createOrder(CreateOrderReq req) {

//        1.call oAuth api to get access token
//        2.call create order api from paypal with acces token
//        3.handle succes and error response
//        4.return response to controller

        String accessToken = tokenService.getAccessToken();
        HttpRequest httpRequest = createOrderHelper.prepareHttpRequestForCreateOrder(req, accessToken);

        // make call to 3rd party api
        ResponseEntity<String> createOrderResponse = httpServiceEngine.makeHttpCall(httpRequest);
        String responseBody = createOrderResponse.getBody();

        PayPalOrderResp resObj = jsonUtils.fromJson(responseBody, PayPalOrderResp.class);
        log.info("resObj" + resObj);

        Order orderRes = new Order();
        orderRes.setOrderId(resObj.getId());
        orderRes.setPaypalStatus(resObj.getStatus());
        Optional<String> opRedirectUrl = resObj.getLinks().stream()
                .filter(link -> "payer_action".equalsIgnoreCase(link.getRel()))
                .map(Link::getHref).findFirst();

        orderRes.setRedirecturl(opRedirectUrl.orElse(null));

        log.info("OrderRes:{}", orderRes);

        return orderRes;
    }

    @Override
    public Order getOrder(String orderId) {
        if (orderId.contains("TEMP1")) {
            throw new PaypalProviderException(
                    ErrorCodeEnum.TEMP_01.getCode(),
                    ErrorCodeEnum.TEMP_01.getMessage(),
                    HttpStatus.BAD_REQUEST);
            // 400 BAD REQUEST
        }

        if (orderId.contains("TEMP3")) {
            throw new PaypalProviderException(
                    ErrorCodeEnum.TEMP_03.getCode(),
                    ErrorCodeEnum.TEMP_03.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        String accessToken = tokenService.getAccessToken();

        HttpRequest httpRequest = getOrderHelper.prepareHttpRequestForGetOrder(orderId, accessToken);

        ResponseEntity<String> getOrderResponse = httpServiceEngine.makeHttpCall(httpRequest);

        Order response = getOrderHelper.processGetOrderResponse(getOrderResponse);

      return response;
    }

    @Override
    public Order captureOrder(String orderId) {
        String accessToken = tokenService.getAccessToken();
        HttpRequest httpRequest = captureOrderHelper.prepareHttpRequestForCaptureOrder(orderId, accessToken);
        ResponseEntity<String> captureOrderResponse = httpServiceEngine.makeHttpCall(httpRequest);
        String responseBody = captureOrderResponse.getBody();

        PayPalOrderResp respObj = jsonUtils.fromJson(responseBody, PayPalOrderResp.class);

        Order orderRes = new Order();
        orderRes.setOrderId(respObj.getId());
        orderRes.setPaypalStatus(respObj.getStatus());
        Optional<String> opRedirectUrl = respObj.getLinks().stream()
                .filter(link -> "payer_action".equalsIgnoreCase(link.getRel()))
                .map(Link::getHref).findFirst();
        orderRes.setRedirecturl(opRedirectUrl.orElse(null));

        log.info("OrderRes:{}", orderRes);
        return orderRes;


    }
}
