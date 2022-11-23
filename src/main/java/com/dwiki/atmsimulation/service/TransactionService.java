package com.dwiki.atmsimulation.service;

import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.model.Transaction;

import java.util.List;

public interface TransactionService {

    void withDrawTransactionProcess(Integer amount, String accountNumber);

    boolean transferTransactionProcess(String sourceAccountNumber,
                                       String destinationAccountNumber, Integer transferAmount);

    List<Transaction> lastTransaction(String accountNumber);
}
