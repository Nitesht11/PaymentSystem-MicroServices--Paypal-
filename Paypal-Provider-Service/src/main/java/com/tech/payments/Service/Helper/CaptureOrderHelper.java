package com.tech.payments.Service.Helper;

import com.tech.payments.Http.HttpRequest;
import com.tech.payments.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CaptureOrderHelper { 
     @Value("${paypal.captureOrderUrl}")
     private String captureOrderUrl;
    public HttpRequest prepareHttpRequestForCaptureOrder(String orderId, String accessToken) {

        HttpHeaders httpHeaders =new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String url=captureOrderUrl;
      url= url.replace("{orderId}",orderId);


  //      create http-req obj

       HttpRequest httpRequest =new HttpRequest();
       httpRequest.setHttpMethod(HttpMethod.POST);
       httpRequest.setHeaders(httpHeaders);
       httpRequest.setUrl(url);
       httpRequest.setRequestBody(Constants.EMPTY_STRING);

       log.info("httprequest"+ httpRequest);
       return  httpRequest;

    }
}
