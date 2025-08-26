package com.tech.payments.CONFIG;

import com.tech.payments.DTO.TransactionDTO;
import com.tech.payments.Entity.TransactionEntity;
import com.tech.payments.Util.Converters.PaymentMethodConverter;
import com.tech.payments.Util.Converters.PaymentTypeConverter;
import com.tech.payments.Util.Converters.ProviderConverter;
import com.tech.payments.Util.Converters.TxnStatusConverter;

import org.modelmapper.Converter;
import org.modelmapper.Converters;
import org.modelmapper.ModelMapper;

import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AppConfig {


    @Bean
    ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("Async-Task-");
        executor.initialize();

        return executor;
    }
    @Bean
    ModelMapper modelMapper(){

        ModelMapper modelMapper= new ModelMapper();
        modelMapper().getConfiguration().setMatchingStrategy
                (MatchingStrategies.STRICT);

        Converter<Integer,String> paymentMethodConverter= new PaymentMethodConverter();
        Converter<Integer,String> paymentTypeConverter= new PaymentTypeConverter();
        Converter<Integer,String> providerConverter= new ProviderConverter();
        Converter<Integer,String> txnStatusConverter= new TxnStatusConverter();

        modelMapper.addMappings(new PropertyMap<TransactionEntity,TransactionDTO >() {
// which field of which classs  convert to which  fileds of which class r defined here
            @Override
            protected void configure() {
                using(paymentMethodConverter).map(source.getPaymentMethodId(),destination.getPaymentMethod());
                using(paymentTypeConverter).map(source.getPaymentTypeId(),destination.getPaymentType());
                using(providerConverter).map(source.getProviderId(),destination.getProvider());
                using(txnStatusConverter).map(source.getTxnStatusId(),destination.getTxnStatus());

            }
        });

        return modelMapper();

    }
}
