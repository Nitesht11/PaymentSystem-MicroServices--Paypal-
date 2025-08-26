package com.tech.payments.Util.Converters;

import com.tech.payments.constants.TxnStatusEnum;
import org.modelmapper.AbstractConverter;

public class TxnStatusConverter extends AbstractConverter<Integer,String> {
    @Override
    protected String convert(Integer source) {
        return TxnStatusEnum.fromId(source).getName();
    }
}
