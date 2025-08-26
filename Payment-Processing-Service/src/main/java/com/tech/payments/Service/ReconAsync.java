package com.tech.payments.Service;

import com.tech.payments.DTO.TransactionDTO;
import com.tech.payments.Entity.TransactionEntity;
import com.tech.payments.Service.Impl.PayPalProviderHandler;
import com.tech.payments.Service.Interface.ProviderHandler;
import com.tech.payments.constants.ProviderEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReconAsync {

    private final ApplicationContext applicationContext;

    @Async         // to call this method in diff thread thus using annotation
    public  void reconTransactionAsync(TransactionDTO txn){
        log.info("ReconAsync.reconTransactionAsync() called ", txn);

        ProviderHandler providerHandler = null;
        if (txn.getProvider().equals(ProviderEnum.PAYPAL.getName())){
            providerHandler=applicationContext.getBean(PayPalProviderHandler.class);
        }
        if (providerHandler== null){
            log.error("ReconAsync.reconTransactionAsync() - "+"providerhandler is null: {}",txn);
            return;
        }
        providerHandler.reconTransaction(txn);

    }


}


