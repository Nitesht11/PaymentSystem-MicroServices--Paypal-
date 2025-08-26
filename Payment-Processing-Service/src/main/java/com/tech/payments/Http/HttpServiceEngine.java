package com.tech.payments.Http;

import com.tech.payments.Exception.PaypalProviderException;
import com.tech.payments.constants.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class HttpServiceEngine {

    private final RestClient restClient;

    public HttpServiceEngine(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }


    public ResponseEntity<String> makeHttpCall(HttpRequest httpRequest) {
        log.info("makehttpcall{}", httpRequest);
        try {
            ResponseEntity<String> responseEntity = restClient.method(httpRequest.getHttpMethod())
                    .uri(httpRequest.getUrl())
                    .headers(header -> header.addAll(httpRequest.getHeaders()))
                    .body(httpRequest.getRequestBody())
                    .retrieve()
                    .toEntity(String.class);

            log.info("responseEntity" + responseEntity);
            return responseEntity;

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error(" Http error occurred{}", e.getStatusCode(), e);
            // for valid error response clinet or server side 4xx or 5xx

            HttpStatusCode status = e.getStatusCode();

            // check for service un 503 or gateway timeout 504
            if (status == HttpStatus.SERVICE_UNAVAILABLE || status == HttpStatus.GATEWAY_TIMEOUT)

                throw  new RuntimeException(" service unavailable or gatway timeout",e);
//                throw new PaypalProviderException(
//                        ErrorCodeEnum.UNABLE_TO_CONNECT_PAYPAL.getCode(),
//                        ErrorCodeEnum.UNABLE_TO_CONNECT_PAYPAL.getMessage(),
//                        HttpStatus.valueOf(e.getStatusCode().value()));

            //  for other client/server  return response body
            String errorJson = e.getResponseBodyAsString();
            return ResponseEntity.status(status).body(errorJson);

        } catch (Exception e) {// generic error = time out/ no response frm papl  / error scenario
            log.error(" Exception occur while  making https call {}", e.getMessage());
            throw  new RuntimeException(" exception occured while making the call",e);
//            throw new PaypalProviderException(
//                    ErrorCodeEnum.UNABLE_TO_CONNECT_PAYPAL.getCode(),
//                    ErrorCodeEnum.UNABLE_TO_CONNECT_PAYPAL.getMessage(),
//                    HttpStatus.SERVICE_UNAVAILABLE);
        }

    }
}
