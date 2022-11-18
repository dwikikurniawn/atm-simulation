package com.dwiki.atmsimulation.service;

import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.model.Transaction;

import java.util.List;

public interface TransactionService {

    boolean balanceValidation(Account account, Integer amount);

    boolean amountWithDrawValidation(Integer amountWithdraw);

    boolean amountTransferValidation(Integer amountTransfer);

    void withDrawTransactionProcess(Integer amount, Account account);

    boolean transferTransactionProcess(Account sourceAccount,
                                       String destinationAccountNumber, Integer transferAmount);

    List<Transaction> lastTransaction(Account account);
}
