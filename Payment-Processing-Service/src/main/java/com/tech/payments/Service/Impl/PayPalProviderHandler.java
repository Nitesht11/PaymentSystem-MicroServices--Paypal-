package com.tech.payments.Service.Impl;
import org.springframework.http.ResponseEntity;
import com.tech.payments.DTO.TransactionDTO;
import com.tech.payments.Http.HttpRequest;
import com.tech.payments.Http.HttpServiceEngine;
import com.tech.payments.Service.Interface.ProviderHandler;
import com.tech.payments.Util.JsonUtils;
import com.tech.payments.paypalProvider.PPOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayPalProviderHandler implements ProviderHandler {


    private final HttpServiceEngine httpServiceEngine;
    private final JsonUtils jsonUtils;



    @Override
    public void reconTransaction(TransactionDTO txn) {
        log.info("PayPalProviderHandler.reconTransaction() called txn:{}", txn);

        HttpRequest httpRequest = new HttpRequest();
        HttpHeaders httpheaders = new HttpHeaders();
        httpheaders.setContentType(MediaType.APPLICATION_JSON);

        httpRequest.setUrl(" ");
        httpRequest.setHttpMethod(HttpMethod.GET);
        httpRequest.setHeaders(httpheaders);
        httpRequest.setRequestBody(" ");

        ResponseEntity<String> response = httpServiceEngine.makeHttpCall(httpRequest);

        log.info("PayPalProviderHandler.reconTransaction() - " + "responseZ: {}", response);

        PPOrder suceesObj = processGetOrderResponse(response);
    }

    public PPOrder processGetOrderResponse(ResponseEntity<String> getOrderResponse) {
        String responseBody = getOrderResponse.getBody();
        log.info("responseBody:" + responseBody);

        if (getOrderResponse.getStatusCode()== HttpStatus.OK){
            PPOrder respObj= jsonUtils.fromJson(responseBody,PPOrder.class);
            log.info("respObj:{}", respObj);

            if (respObj != null
                    && respObj.getOrderId() != null && !respObj.getOrderId().isEmpty()
                    && respObj.getPaypalStatus() != null &&
                    !respObj.getPaypalStatus().isEmpty()){
                // sucees scenario


                log.info("OrderRes:{}",respObj);

                return respObj;
            }
        }
        // Failed response
        if (getOrderResponse.getStatusCode().is4xxClientError()||
                getOrderResponse.getStatusCode().is5xxServerError()){

            log.error("Paypal error response: {}", responseBody);
            throw  new RuntimeException(" ");

        }
//          for any error other than 4xx and 5xx genric handling
        log.error("Got unexpect error from paypal"+" returning generic response:{}",getOrderResponse);
        throw  new RuntimeException(" ");


    }

}


