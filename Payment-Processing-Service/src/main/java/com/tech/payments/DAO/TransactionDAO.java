package com.tech.payments.DAO;

import com.tech.payments.Entity.TransactionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDAO {

    public List<TransactionEntity> loadTransactionForRecon();
}
