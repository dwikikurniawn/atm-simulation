package com.dwiki.atmsimulation.service;

import com.dwiki.atmsimulation.model.Transaction;

import java.util.List;

public interface TransactionService {

    void withDrawTransactionProcess(Integer amount, String accountNumber);

    void
    transferTransactionProcess(String sourceAccountNumber,
                                       String destinationAccountNumber, Integer transferAmount, String referenceNumber);

    List<Transaction> lastTransaction(String accountNumber);

    void depositTransactionProcess(Integer amount, String accountNumber);
}
