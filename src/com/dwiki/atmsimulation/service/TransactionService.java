package com.dwiki.atmsimulation.service;

import java.time.Instant;
import java.util.List;

import com.dwiki.atmsimulation.constant.Constant;
import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.model.Transaction;
import com.dwiki.atmsimulation.util.DataUtil;
import com.dwiki.atmsimulation.util.FileUtil;

public class TransactionService {

	private final DataUtil dataUtil = new DataUtil();
	private final AccountService accountService = new AccountService();

	public boolean balanceValidation(Account account, Integer amount) {
		if (account.getBalance() < amount) {
			System.out.println("Insufficient balance $" + amount);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	public boolean amountWithDrawValidation(Integer amountWithdraw) {
		if (!dataUtil.isNumeric(Integer.toString(amountWithdraw)) || amountWithdraw % 10 != 0) {
			System.out.println("Invalid amount");
			return Boolean.FALSE;
		} else if (amountWithdraw > 1000) {
			System.out.println("Maximum amount to withdraw is $1000");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	public boolean amountTransferValidation(Integer amountTransfer) {
		if (!dataUtil.isNumeric(Integer.toString(amountTransfer))) {
			System.out.println("Invalid amount");
			return Boolean.FALSE;
		} else if (amountTransfer > 1000 || amountTransfer < 1) {
			System.out.println("Maximum amount to withdraw is $1000");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	public void withDrawTransactionProcess(Integer amount, Account account) {
		FileUtil fileUtil = new FileUtil();
		Transaction transaction = new Transaction();
		transaction.setAccountNumber(account.getAccountNumber());
		transaction.setAmount(amount);
		transaction.setType(Constant.TRANSACTION_TYPE_WITHDRAW);
		transaction.setTime(Instant.now());
		transaction.setRecepientAccountNumber("-");
		account.setBalance(account.getBalance() - amount);
		fileUtil.saveTransaction(Constant.TRANSACTION_FILE_PATH, transaction);
	}

	public Boolean transferTransactionProcess(Account sourceAccount, List<Account> accounts,
			String destinationAccountNumber, Integer transferAmount) {
		Account destinationAccount = accountService.searchAccountByAccountNumber(accounts, destinationAccountNumber);
		if (Boolean.TRUE.equals(
				transferValidation(sourceAccount, destinationAccountNumber, transferAmount, destinationAccount))) {
			FileUtil fileUtil = new FileUtil();
			sourceAccount.setBalance(sourceAccount.getBalance() - transferAmount);
			destinationAccount.setBalance(destinationAccount.getBalance() + transferAmount);

			Transaction transaction = new Transaction();
			transaction.setAccountNumber(sourceAccount.getAccountNumber());
			transaction.setAmount(transferAmount);
			transaction.setType(Constant.TRANSACTION_TYPE_TRANSFER);
			transaction.setTime(Instant.now());
			transaction.setRecepientAccountNumber(destinationAccountNumber);
			fileUtil.saveTransaction(Constant.TRANSACTION_FILE_PATH, transaction);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private Boolean transferValidation(Account sourceAccount, String destinationAccountNumber, Integer transferAmount,
			Account destinationAccount) {
		if (Boolean.FALSE.equals(balanceValidation(sourceAccount, transferAmount))) {
			return Boolean.FALSE;
		} else if (destinationAccount == null) {
			System.out.println("Transfer Failed!");
			System.out.println("Account with account number: " + destinationAccountNumber + " is not found");
			return Boolean.FALSE;
		} else if (sourceAccount.equals(destinationAccount)) {
			System.out.println("Transfer Failed!");
			System.out.println("Destination Account can't be same as Source Account");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	public void lastTransaction(Account account) {
		FileUtil fileUtil = new FileUtil();
		List<Transaction> transactions = fileUtil.readTransactionCsv(Constant.TRANSACTION_FILE_PATH);
		System.out.println();
		System.out.println("Here is your last 10 transaction: ");
		transactions.stream()
				.filter(transaction -> transaction.getAccountNumber().equalsIgnoreCase(account.getAccountNumber())
						|| transaction.getRecepientAccountNumber().equalsIgnoreCase(account.getAccountNumber()))
				.limit(10).forEach(transaction -> {
					System.out.println();
					System.out.println(" -Account Number: " + transaction.getAccountNumber() + "\n -Transaction Type: "
							+ transaction.getType() + "\n -Amount: " + transaction.getAmount() + "\n -Date: "
							+ transaction.getTime() + "\n -Recipient Account Number: "
							+ transaction.getRecepientAccountNumber());
				});
	}
}