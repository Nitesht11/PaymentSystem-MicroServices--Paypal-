package com.tech.payments.Http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class HttpServiceEngine {

    private final RestClient restClient;
    public HttpServiceEngine(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }
     public ResponseEntity<String> makeHttpCall(HttpRequest httpRequest){


          ResponseEntity<String>responseEntity=restClient.method(httpRequest.getHttpMethod())
               .uri(httpRequest.getUrl())
               .headers(  header->header.addAll(httpRequest.getHeaders())  )
               .body(httpRequest.getRequestBody())
               .retrieve()
               .toEntity(String.class);

          log.info("responseEntity"+responseEntity);
          return  responseEntity;
     }





}
