package com.tech.payments.Service;

import com.tech.payments.DAO.TransactionDAO;
import com.tech.payments.DTO.TransactionDTO;
import com.tech.payments.Entity.TransactionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReconService {

    private final TransactionDAO transactionDAO;

    private  final ModelMapper modelMapper;

    private  final ReconAsync reconAsync;

    public  void reconTransaction() {
        log.info("reconService . recon Called");

// get List  of transaction entity from DB, for recon.....
        List<TransactionEntity> txnForRecon = transactionDAO.loadTransactionForRecon();
        log.info("ReconService.recon()-txnForRecon.size(): {}", txnForRecon.size());

        List<TransactionDTO>txnDTOlist = convertDTOtoList(txnForRecon);
        log.info("ReconService.recon() -txnDTOList.size(): {}",txnDTOlist.size());

        //going through each trxn & preform reconcilation
        txnDTOlist.forEach(txn -> {
            log.info("ReconService.recon()- txn", txn);
            reconAsync.reconTransactionAsync(txn);

        });
    }
    private List<TransactionDTO> convertDTOtoList(List<TransactionEntity> entityList){
        return entityList.stream()
                .map(entity->modelMapper.map(entity,TransactionDTO.class))
                .toList();


    }
}
