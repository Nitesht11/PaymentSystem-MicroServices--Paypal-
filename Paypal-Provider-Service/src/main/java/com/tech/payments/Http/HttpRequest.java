package com.tech.payments.Http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HttpRequest {

    private HttpMethod httpMethod;
    private String url;
    private HttpHeaders headers;
    private  Object requestBody;
}



