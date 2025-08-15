package com.tech.payments.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


// uses Jackson to convert onj into json or opp
@Component
@RequiredArgsConstructor

public class JsonUtils {
    private final ObjectMapper objectMapper;
                                            // toJSon is a wraper with input obj
   public String  toJson (Object obj){    //  whn try to conv onj to str reqbody any excp come rtn null
       String  requestBodyAsJson =null;
       try {
           requestBodyAsJson = objectMapper.writerWithDefaultPrettyPrinter(
           ).writeValueAsString(obj);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return requestBodyAsJson;
   }


    /**
     * Converts a JSON string to a Java object of the specified class.
     *
     * @param json  the JSON string to convert
     * @param clazz the class of the object to convert to
     * @return the converted object
     */

   public<T> T fromJson(String json, Class<T> clazz){
       T response = null;
       try {
           response =objectMapper.readValue(json,clazz);
       }catch (Exception e){
           e.printStackTrace();
       }
       return response;
   }
}

