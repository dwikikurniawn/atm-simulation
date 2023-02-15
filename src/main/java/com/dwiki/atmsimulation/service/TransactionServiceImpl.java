package com.dwiki.atmsimulation.service;

import com.dwiki.atmsimulation.constant.Constant;
import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.model.Transaction;
import com.dwiki.atmsimulation.repository.AccountRepository;
import com.dwiki.atmsimulation.repository.TransactionRepository;
import com.dwiki.atmsimulation.util.DataUtil;
import com.dwiki.atmsimulation.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService{
	private final AccountService accountService;
	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;
	private final FileUtil fileUtil;

	private final DataUtil dataUtil;

	@Override
	public void withDrawTransactionProcess(Integer amount, String sourceAccountNumber) {
		Account account = accountService.searchAccountByAccountNumber(sourceAccountNumber);
		transactionAmountValidation(amount);
		balanceValidation(account, amount);

		Transaction transaction = new Transaction(sourceAccountNumber, Constant.TRANSACTION_TYPE_WITHDRAW,
				amount, "-", "-");
		decreaseBalance(amount, account);

		fileUtil.saveTransaction(Constant.TRANSACTION_FILE_PATH, transaction);
		accountRepository.save(account);
		transactionRepository.save(transaction);
	}

	@Override
	public void transferTransactionProcess(String sourceAccountNumber,
			String destinationAccountNumber, Integer transferAmount, String referenceNumber) {
		Account sourceAccount = accountService.searchAccountByAccountNumber(sourceAccountNumber);
		Account destinationAccount = accountService.searchAccountByAccountNumber(destinationAccountNumber);
		transactionAmountValidation(transferAmount);
		balanceValidation(sourceAccount, transferAmount);
		transferValidation(sourceAccount, destinationAccount);

		decreaseBalance(transferAmount, sourceAccount);
		increaseBalance(transferAmount, destinationAccount);

		Transaction transaction = new Transaction(sourceAccountNumber, Constant.TRANSACTION_TYPE_TRANSFER,
				transferAmount, destinationAccountNumber, referenceNumber);

		Transaction result =  transactionRepository.save(transaction);
		fileUtil.saveTransaction(Constant.TRANSACTION_FILE_PATH, result);
		accountRepository.save(sourceAccount);
		accountRepository.save(destinationAccount);
	}

	@Override
	public List<Transaction> lastTransaction(String accountNumber) {
		log.info("lastTransaction() accountNumber: {}", accountNumber);
		Account account = accountService.searchAccountByAccountNumber(accountNumber);
		return transactionRepository.findTop10BySourceAccountNumberOrRecipientAccountNumberOrderByTimeDesc
				(account.getAccountNumber(), account.getAccountNumber());
	}

	private void balanceValidation(Account account, Integer amount) {
		if (account.getBalance() < amount) {
			throw new NumberFormatException("Insufficient balance $" + amount);
		}
	}

	private void transactionAmountValidation(Integer amountWithdraw) {
		if (amountWithdraw > 1000 || amountWithdraw < 1) {
			throw new NumberFormatException("Maximum amount for the transaction is $1000 and minimum is 1");
		}
	}

	private void increaseBalance(Integer amount, Account destinationAccount) {
		destinationAccount.setBalance(destinationAccount.getBalance() + amount);
	}

	private void decreaseBalance(Integer amount, Account sourceAccount) {
		sourceAccount.setBalance(sourceAccount.getBalance() - amount);
	}

	private void transferValidation(Account sourceAccount, Account destinationAccount) {
		 if (sourceAccount.equals(destinationAccount)) {
			 throw new NumberFormatException("Transfer Failed, Destination Account can't be same as Source Account");
		}
	}
}