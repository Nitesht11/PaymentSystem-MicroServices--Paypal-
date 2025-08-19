package com.tech.payments.Service.Helper;

import com.tech.payments.Exception.PaypalProviderException;
import com.tech.payments.Http.HttpRequest;
import com.tech.payments.Paypal.Link;
import com.tech.payments.Paypal.PayPalOrderResp;
import com.tech.payments.Pojo.Order;
import com.tech.payments.constants.Constants;
import com.tech.payments.constants.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import com.tech.payments.Utils.JsonUtils;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetOrderHelper {
    @Value("${paypal.getOrderUrl}")
    String getOrderUrl;

    private final JsonUtils jsonUtils;

    public HttpRequest prepareHttpRequestForGetOrder(String orderId, String accessToken) {
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String url= getOrderUrl;
        url= url.replace("{orderId}",orderId);

      // create http requst obj
        HttpRequest httpRequest=new HttpRequest();
        httpRequest.setHttpMethod(HttpMethod.GET);

        httpRequest.setUrl(url);
        httpRequest.setHeaders(httpHeaders);
        httpRequest.setRequestBody(Constants.EMPTY_STRING);

        log.info(" httprequest"+ httpRequest);

        return httpRequest;
    }

    public Order processGetOrderResponse(ResponseEntity<String> getOrderResponse) {

        String responseBody=getOrderResponse.getBody();
        log.info("responseBody:"+responseBody);

        if (getOrderResponse.getStatusCode()== HttpStatus.OK){
            PayPalOrderResp respObj= jsonUtils.fromJson(responseBody,PayPalOrderResp.class);
            log.info("respObj:{}", respObj);

            if(respObj!= null   // scenario while jackson transit4
                    && respObj.getId()!=null && respObj.getId().isEmpty()
                    && respObj.getStatus()!=null && !respObj.getStatus().isEmpty()){
                // sucees scenario

                Order orderRes = new Order();
                orderRes.setOrderId(respObj.getId());
                orderRes.setPaypalStatus(respObj.getStatus());
                Optional<String> opRedirectUrl= respObj.getLinks().stream()
                        .filter(link -> "payer_action".equalsIgnoreCase(link.getRel()))
                        .map(Link::getHref).findFirst();
                orderRes.setRedirecturl(opRedirectUrl.orElse(null));
                log.info("OrderRes:{}",orderRes);

                return orderRes;
            }
        }
        // Failed response
        if (getOrderResponse.getStatusCode().is4xxClientError()||
                getOrderResponse.getStatusCode().is5xxServerError()){

            log.error("Paypal error response: {}", responseBody);

            throw new PaypalProviderException(
                    ErrorCodeEnum.PAYPAL_ERROR.getCode(),
                    " ", //todo
                    HttpStatus.valueOf(getOrderResponse.getStatusCode().value()));
        }
//          for any error other than 4xx and 5xx genric handling
        log.error("Got unexpect error from paypal"+" returning generic response:{}",getOrderResponse);
        throw  new PaypalProviderException(
                ErrorCodeEnum.GENERIC_ERROR.getCode(),
                ErrorCodeEnum.GENERIC_ERROR.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
