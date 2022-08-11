package com.dwiki.atmsimulation.service;

import java.util.List;
import java.util.Map;

import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.util.DataUtil;

public class TransactionService {

	private final ScreenService screenService = new ScreenService();
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
		account.setBalance(account.getBalance() - amount);
	}

	public Boolean transferTransactionProcess(Account sourceAccount, List<Account> accounts,
			String destinationAccountNumber, Integer transferAmount) {
		Account destinationAccount = accountService.searchAccountByAccountNumber(accounts, destinationAccountNumber);
		if (Boolean.FALSE.equals(balanceValidation(sourceAccount, transferAmount))) {
			return Boolean.FALSE;
		} else if (destinationAccount == null) {
			System.out.println("Transfer Failed!");
			System.out.println("Account with account number: " + destinationAccountNumber + " is not found");
			return Boolean.FALSE;
		} else if(sourceAccount.equals(destinationAccount)) {
			System.out.println("Transfer Failed!");
			System.out.println("Destination Account can't be same as Source Account");
			return Boolean.FALSE;
		}
		else {
			sourceAccount.setBalance(sourceAccount.getBalance() - transferAmount);
			destinationAccount.setBalance(destinationAccount.getBalance() + transferAmount);
			return Boolean.TRUE;
		}
	}

	public Account login(List<Account> accounts) {
		Map<String, Object> loginScreenResult = screenService.loginScreen();
		Account account = accountService.searchAccountByAccountNumberAndPin(accounts,
				(String) loginScreenResult.get("accountNumber"), (String) loginScreenResult.get("pin"));
		while (Boolean.FALSE.equals(loginScreenResult.get("valid")) || account == null) {
			if (account == null && Boolean.TRUE.equals(loginScreenResult.get("valid"))) {
				System.out.println("Account is not valid, please log in with correct account and pin");
			}
			loginScreenResult = screenService.loginScreen();
			account = accountService.searchAccountByAccountNumberAndPin(accounts,
					(String) loginScreenResult.get("accountNumber"), (String) loginScreenResult.get("pin"));
		}
		return account;
	}

	public void mainApp(List<Account> accounts) {
		Account account = login(accounts);
		screenService.transactionScreen(account, accounts);
	}
}