package com.tech.payments.Service.Interface;

import com.tech.payments.DTO.TransactionDTO;

public interface ProviderHandler {
    void reconTransaction(TransactionDTO txn);
}
