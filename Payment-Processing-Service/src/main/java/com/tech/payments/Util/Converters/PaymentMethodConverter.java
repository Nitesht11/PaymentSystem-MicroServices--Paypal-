package com.tech.payments.Util.Converters;

import com.tech.payments.constants.PaymentMethodEnum;
import org.modelmapper.AbstractConverter;

public class PaymentMethodConverter  extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
      return   PaymentMethodEnum.fromId(source).getName();
    }
}
