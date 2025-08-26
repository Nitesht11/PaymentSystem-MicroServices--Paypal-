package com.tech.payments.Util.Converters;

import com.tech.payments.constants.PaymentMethodEnum;
import com.tech.payments.constants.PaymentTypeEnum;
import org.modelmapper.AbstractConverter;

public class PaymentTypeConverter extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
        return PaymentTypeEnum.fromId(source).getName();
    }
}

