package com.tech.payments.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.payments.Http.HttpRequest;
import com.tech.payments.Http.HttpServiceEngine;
import com.tech.payments.Paypal.OAuthToken;
import com.tech.payments.constants.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    // construct injection
    private final HttpServiceEngine httpServiceEngine ;

    private  final ObjectMapper objectMapper;
// todo ed=nd to end token lifecycle  8 hrs exp
    private  static  String accessToken;

    @Value("${paypal.clientId}")
    private String ClientId;

    @Value("${paypal.clientSecrets}")
    private String ClientSecret;

    @Value("${paypal.oAuthUrl}")
    private String oAuth;


    public String getAccessToken() {
        log.info("getAccessToken called");
        if (accessToken != null) {
            log.info("getAccessToken returning|accessToken:" + accessToken);
            return accessToken;
        }

        log.info("Calling paypal  to generate another  new access token "+accessToken);

        HttpHeaders headerObj = new HttpHeaders();
        headerObj.setBasicAuth(ClientId,ClientSecret);
        headerObj.setContentType(MediaType.APPLICATION_FORM_URLENCODED );

        // prepare  form -urlencoded request bod
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add(Constants.GRANT_TYPE, Constants.CLIENT_CREDENTIALS);

        HttpRequest httpRequest=new HttpRequest();
        httpRequest.setHttpMethod(HttpMethod.POST);
        httpRequest.setUrl(oAuth);
        httpRequest.setHeaders(headerObj);
        httpRequest.setRequestBody(formParams);

        log.info("httpRequest:"+ httpRequest);
        ResponseEntity<String> oAuthResponse = httpServiceEngine.makeHttpCall(httpRequest);
         String responseBody= oAuthResponse.getBody();
         log.info("oAuthResponse:" +oAuthResponse);


//         String accessToken= null;
//  the responsebody frm paypl needs to be converted with jackson

        try {
            OAuthToken oAuthObj= objectMapper.readValue(responseBody, OAuthToken.class);
            accessToken=oAuthObj.getAccesstoken();
            log.info("accessToken:"+accessToken);
        } catch (JsonProcessingException e) {
            e.getStackTrace();
        }
        log.info("getAccessToken returning |accesToken");
        return  accessToken;

}
}