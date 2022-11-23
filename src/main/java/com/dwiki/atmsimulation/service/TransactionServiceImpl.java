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

@Service
@AllArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService{

	private final DataUtil dataUtil;
	private final AccountService accountService;
	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;

	private boolean balanceValidation(Account account, Integer amount) {
		if (account.getBalance() < amount) {
			log.info("Insufficient balance $" + amount);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	private boolean amountWithDrawValidation(Integer amountWithdraw) {
		if (!dataUtil.isNumeric(Integer.toString(amountWithdraw)) || amountWithdraw % 10 != 0) {
			log.info("Invalid amount");
			return Boolean.FALSE;
		} else if (amountWithdraw > 1000) {
			log.info("Maximum amount to withdraw is $1000");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	private boolean amountTransferValidation(Integer amountTransfer) {
		if (!dataUtil.isNumeric(Integer.toString(amountTransfer))) {
			log.info("Invalid amount");
			return Boolean.FALSE;
		} else if (amountTransfer > 1000 || amountTransfer < 1) {
			log.info("Maximum amount to withdraw is $1000");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@Override
	public void withDrawTransactionProcess(Integer amount, String sourceAccountNumber) {

		Transaction transaction = new Transaction();
		Account account = accountRepository.findById(sourceAccountNumber).orElse(null);
		transaction.setSourceAccountNumber(account.getAccountNumber());
		transaction.setAmount(amount);
		transaction.setType(Constant.TRANSACTION_TYPE_WITHDRAW);
		transaction.setRecipientAccountNumber("-");
		account.setBalance(account.getBalance() - amount);

		accountRepository.save(account);
		transactionRepository.save(transaction);
	}

	@Override
	public boolean transferTransactionProcess(String sourceAccountNumber,
			String destinationAccountNumber, Integer transferAmount) {
		Account sourceAccount = accountRepository.findById(sourceAccountNumber).orElse(null);
		Account destinationAccount = accountService.searchAccountByAccountNumber(destinationAccountNumber);
		if (Boolean.TRUE.equals(
				transferValidation(sourceAccount, destinationAccountNumber, transferAmount, destinationAccount))) {
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

			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private boolean transferValidation(Account sourceAccount, String destinationAccountNumber, Integer transferAmount,
			Account destinationAccount) {
		if(Boolean.FALSE.equals(amountTransferValidation(transferAmount))){
			return Boolean.FALSE;
		}
		else if (Boolean.FALSE.equals(balanceValidation(sourceAccount, transferAmount))) {
			return Boolean.FALSE;
		} else if (destinationAccount == null) {
			log.info("Transfer Failed!");
			log.info("Account with account number: " + destinationAccountNumber + " is not found");
			return Boolean.FALSE;
		} else if (sourceAccount.equals(destinationAccount)) {
			log.info("Transfer Failed!");
			log.info("Destination Account can't be same as Source Account");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@Override
	public List<Transaction> lastTransaction(String accountNumber) {
		log.info("lastTransaction() accountNumber: {}", accountNumber);
		Account account = accountRepository.findById(accountNumber).orElse(null);
		return transactionRepository.findTop10BySourceAccountNumberOrRecipientAccountNumberOrderByTimeDesc(account.getAccountNumber(), account.getAccountNumber());
	}
}