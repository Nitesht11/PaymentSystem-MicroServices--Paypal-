package com.tech.payments.Http;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;


@Data
public class HttpRequest {

    private HttpMethod httpMethod;
    private String url;
    private HttpHeaders Headers;
    private  Object requestBody;
}



