package com.tech.payments.constants;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    GENERIC_ERROR("40000","UNABLE TO PROCESS  REQUEST, TRU AGAIN LATER"),
          TEMP_01("40001","TEMP1 ERROR"),
          TEMP_02("40002","TEMP2 ERROR"),
          TEMP_03("40003","TEMP3 ERROR"),
    UNABLE_TO_CONNECT_PAYPAL("40005", "UNABLE TO CONNECT TO PAYPAL, TRY AGAIN LATER"),
     PAYPAL_ERROR("40006","<PREPARE DYNAMIC MESSAGE FROM PAYPAL>");


    private String code;
    private String message;

     ErrorCodeEnum(String code,String message){
        this .code=  code;
        this.message=message;
    }
}
