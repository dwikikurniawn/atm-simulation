package com.dwiki.atmsimulation.service;

import java.util.List;

import com.dwiki.atmsimulation.constant.Constant;
import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.model.Transaction;
import com.dwiki.atmsimulation.repository.AccountRepository;
import com.dwiki.atmsimulation.repository.TransactionRepository;
import com.dwiki.atmsimulation.util.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService{

	private final DataUtil dataUtil;
	private final AccountService accountService;
	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;

	private void balanceValidation(Account account, Integer amount) {
		if (account.getBalance() < amount) {
			throw new NumberFormatException("Insufficient balance $" + amount);
		}
	}

	private void amountWithDrawValidation(Integer amountWithdraw) {
		if (!dataUtil.isNumeric(Integer.toString(amountWithdraw)) || amountWithdraw % 10 != 0) {
			throw new NumberFormatException("Invalid amount");
		} else if (amountWithdraw > 1000) {
			throw new NumberFormatException("Maximum amount to withdraw is $1000");
		}
	}

	private void amountTransferValidation(Integer amountTransfer) {
		if (!dataUtil.isNumeric(Integer.toString(amountTransfer))) {
			throw new NumberFormatException("Invalid amount");
		} else if (amountTransfer > 1000 || amountTransfer < 1) {
			throw new NumberFormatException("Maximum amount to withdraw is $1000");
		}
	}

	@Override
	public void withDrawTransactionProcess(Integer amount, String sourceAccountNumber) {

		Transaction transaction = new Transaction();
		Account account = accountService.searchAccountByAccountNumber(sourceAccountNumber);
		amountWithDrawValidation(amount);
		balanceValidation(account, amount);

		transaction.setSourceAccountNumber(account.getAccountNumber());
		transaction.setAmount(amount);
		transaction.setType(Constant.TRANSACTION_TYPE_WITHDRAW);
		transaction.setRecipientAccountNumber("-");
		account.setBalance(account.getBalance() - amount);

		accountRepository.save(account);
		transactionRepository.save(transaction);
	}

	@Override
	public void transferTransactionProcess(String sourceAccountNumber,
			String destinationAccountNumber, Integer transferAmount) {
		Account sourceAccount = accountService.searchAccountByAccountNumber(sourceAccountNumber);
		Account destinationAccount = accountService.searchAccountByAccountNumber(destinationAccountNumber);
		amountTransferValidation(transferAmount);
		balanceValidation(sourceAccount, transferAmount);
		transferValidation(sourceAccount, destinationAccountNumber, transferAmount, destinationAccount);

		sourceAccount.setBalance(sourceAccount.getBalance() - transferAmount);
		destinationAccount.setBalance(destinationAccount.getBalance() + transferAmount);

		Transaction transaction = new Transaction();
		transaction.setSourceAccountNumber(sourceAccount.getAccountNumber());
		transaction.setAmount(transferAmount);
		transaction.setType(Constant.TRANSACTION_TYPE_TRANSFER);
		transaction.setRecipientAccountNumber(destinationAccountNumber);

		transactionRepository.save(transaction);
		accountRepository.save(sourceAccount);
		accountRepository.save(destinationAccount);
	}

	private void transferValidation(Account sourceAccount, String destinationAccountNumber, Integer transferAmount,
			Account destinationAccount) {
		 if (sourceAccount.equals(destinationAccount)) {
			 throw new NumberFormatException("Transfer Failed, Destination Account can't be same as Source Account");
		}
	}

	@Override
	public List<Transaction> lastTransaction(String accountNumber) {
		log.info("lastTransaction() accountNumber: {}", accountNumber);
		Account account = accountService.searchAccountByAccountNumber(accountNumber);
		return transactionRepository.findTop10BySourceAccountNumberOrRecipientAccountNumberOrderByTimeDesc(account.getAccountNumber(), account.getAccountNumber());
	}
}