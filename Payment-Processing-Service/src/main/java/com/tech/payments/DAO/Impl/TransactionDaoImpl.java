package com.tech.payments.DAO.Impl;

import com.tech.payments.DAO.TransactionDAO;
import com.tech.payments.Entity.TransactionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@RequiredArgsConstructor
public class TransactionDaoImpl implements TransactionDAO {

    private  final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<TransactionEntity> loadTransactionForRecon() {

        String sql=" Select * From payments.Transaction" +
                "WHERE txnStatusId IN(:status1, :status2) "+
                "AND reTryCount <= :reTryMAx";

        Map<String, Object> params = new HashMap<>();
        params.put("status1", 3);
        params.put("status2", 4);
        params.put("reTryMax", 3);



        List<TransactionEntity> txnForRecon = jdbcTemplate.query(sql, params,
                BeanPropertyRowMapper.newInstance(TransactionEntity.class));

        log.info("TransactionDOA. loadTransactionForRecon()"+
                   "txnRecon.size(): {}", txnForRecon.size());

        return txnForRecon;
    }
}
