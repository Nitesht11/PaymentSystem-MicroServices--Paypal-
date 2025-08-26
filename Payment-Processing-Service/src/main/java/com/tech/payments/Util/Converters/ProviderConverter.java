package com.tech.payments.Util.Converters;

import com.tech.payments.constants.ProviderEnum;
import org.modelmapper.AbstractConverter;

public class ProviderConverter extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
        return ProviderEnum.fromId(source).getName();
    }
}

