package com.dwiki.atmsimulation.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.util.DataUtil;

public class ScreenService {

	private final TransactionService transactionService = new TransactionService();
	private final AuthService authService = new AuthService();
	private final Scanner sc = new Scanner(System.in);
	private final DataUtil dataUtil = new DataUtil();

	public Map<String, Object> loginScreen() {
		System.out.println();
		System.out.println("Welcome to ATM Mitrais Bank");
		System.out.println("Enter Account Number: ");
		Map<String, Object> result = new HashMap<String, Object>();
		String inputtedAccountNumber = sc.nextLine();
		if (Boolean.FALSE.equals(dataUtil.accountNumberValidationFormat(inputtedAccountNumber))) {
			result.put("accountNumber", inputtedAccountNumber);
			result.put("pin", "");
			result.put("valid", Boolean.FALSE);
			return result;
		}
		System.out.println("Enter PIN: ");
		String inputtedPin = sc.nextLine();
		if (Boolean.FALSE.equals(dataUtil.pinValidationFormat(inputtedPin))) {
			result.put("valid", Boolean.FALSE);
		} else {
			result.put("valid", Boolean.TRUE);
		}
		result.put("accountNumber", inputtedAccountNumber);
		result.put("pin", inputtedPin);
		return result;
	}

	public void summaryTransferScreen(Integer finalAmountTransfer, Account sourceAccount,
			String destinationAccountNumber, Integer referenceNumber) {
		System.out.println("Fund Transfer Summary");
		System.out.println("Destination Account : " + destinationAccountNumber);
		System.out.println("Transfer amount : $" + finalAmountTransfer);
		System.out.println("Reference Number : " + referenceNumber);
		System.out.println("Balance : $" + sourceAccount.getBalance());
	}

	public void summaryWithDrawScreen(Integer finalAmountWithDraw, Account account) {
		System.out.println("Date: " + Instant.now());
		System.out.println("Withdraw: " + finalAmountWithDraw);
		System.out.println("Balance: " + account.getBalance());
	}

	public void otherWithDrawTransaction(Account account) {
		System.out.println("Other Withdraw Screen");
		System.out.println("Other Withdraw");
		System.out.println("Enter amount to withdraw");
		Integer amountWithdraw = sc.nextInt();
		Boolean amountValidation = transactionService.amountWithDrawValidation(amountWithdraw);
		while (Boolean.FALSE.equals(amountValidation)) {
			System.out.println("Other Withdraw");
			System.out.println("Enter amount to withdraw");
			amountWithdraw = sc.nextInt();
			amountValidation = transactionService.amountWithDrawValidation(amountWithdraw);
		}
		finalWithdrawProcess(account, transactionService, amountWithdraw);
	}

	public void exitTransactionScreen() {
		System.out.println("Thank you, have a good day!");
	}

	public void endTransactionScreen(Account account, List<Account> accounts) {
		System.out.println();
		System.out.println("1. Transaction");
		System.out.println("2. Exit");
		System.out.println("Choose option[2]:");
		Integer summaryOption = sc.nextInt();
		if (summaryOption == 1) {
			transactionScreen(account, accounts);
		} else if (summaryOption == 2) {
			exitTransactionScreen();
			authService.login(accounts);
		} else {
			System.out.println("Please choose option 1 or 2");
			endTransactionScreen(account, accounts);
		}
	}

	void transferConfirmationScreen(Account account, List<Account> accounts, String destinationAccountNumber,
			Integer transferAmount, Integer referenceNumber) {
		System.out.println();
		System.out.println();
		System.out.println("Transfer Confirmation");
		System.out.println("Destination Account: " + destinationAccountNumber);
		System.out.println("Transfer Amount:" + transferAmount);
		System.out.println("Reference Number:" + referenceNumber);
		System.out.println();
		System.out.println("1. Confirm Trx");
		System.out.println("2. Cancel Trx");
		System.out.println("Choose option[2]: ");
		Integer transferConfirmationOption = sc.nextInt();
		if (transferConfirmationOption == 1) {
			if (Boolean.TRUE.equals(transactionService.transferTransactionProcess(account, accounts,
					destinationAccountNumber, transferAmount))) {
				summaryTransferScreen(transferAmount, account, destinationAccountNumber, referenceNumber);
			}
		} else if (transferConfirmationOption == 2) {
			System.out.println("Okay, we are canceling your transfer");
			endTransactionScreen(account, accounts);
		}
	}

	void fundTransferTransactionScreen(Account account, List<Account> accounts) {
		System.out.println("Fund Transfer Screen");
		System.out.println(
				"Please enter destination account and press enter to continue or press cancel (Esc) to go back to Transaction: ");

		String destinationAccountNumber = sc.next();
		Boolean accountNumberValidation = dataUtil.accountNumberValidationFormat(destinationAccountNumber);
		while (Boolean.FALSE.equals(accountNumberValidation)) {
			System.out.println(
					"Please enter destination account and press enter to continue or press cancel (Esc) to go back to Transaction: ");
			destinationAccountNumber = sc.next();
			accountNumberValidation = dataUtil.accountNumberValidationFormat(destinationAccountNumber);
		}

		System.out.println(
				"Please enter transfer amount and press enter to continue or press enter to go back to Transaction: ");
		Integer transferAmount = sc.nextInt();
		Boolean transferAmountValidaiton = transactionService.amountTransferValidation(transferAmount);
		while (Boolean.FALSE.equals(transferAmountValidaiton)) {
			System.out.println(
					"Please enter transfer amount and press enter to continue or press enter to go back to Transaction: ");
			transferAmount = sc.nextInt();
			transferAmountValidaiton = transactionService.amountTransferValidation(transferAmount);
		}

		Integer referenceNumber = dataUtil.generateReferenceNumber();
		System.out.println("Reference Number: " + referenceNumber);
		System.out.println("Press any key to continue or cancel to go back to Transaction: ");
		String confirmationReferenceNumber = sc.next();
		if (confirmationReferenceNumber.equalsIgnoreCase("cancel")) {
			System.out.println("Okay, we are canceling your transfer");
			endTransactionScreen(account, accounts);
		}
		transferConfirmationScreen(account, accounts, destinationAccountNumber, transferAmount, referenceNumber);
	}

	public void withDrawTransactionScreen(Account account, List<Account> accounts) {
		System.out.println();
		System.out.println("Withdraw Screen");
		System.out.println("1. $10");
		System.out.println("2. $50");
		System.out.println("3. $100");
		System.out.println("4. Other");
		System.out.println("5. Back");
		System.out.println("Please choose option[5]:");
		Integer withdrawOption = sc.nextInt();

		if (withdrawOption == 1) {
			Integer finalAmountWithDraw = 10;
			finalWithdrawProcess(account, transactionService, finalAmountWithDraw);
		} else if (withdrawOption == 2) {
			Integer finalAmountWithDraw = 50;
			finalWithdrawProcess(account, transactionService, finalAmountWithDraw);
		} else if (withdrawOption == 3) {
			Integer finalAmountWithDraw = 100;
			finalWithdrawProcess(account, transactionService, finalAmountWithDraw);
		} else if (withdrawOption == 4) {
			otherWithDrawTransaction(account);
		} else if (withdrawOption == 5) {
			transactionScreen(account, accounts);
		} else {
			System.out.println("Please choose between option 1, 2, 3, 4, or 5");
			withDrawTransactionScreen(account, accounts);
		}
	}

	public void finalWithdrawProcess(Account account, TransactionService transactionService,
			Integer finalAmountWithDraw) {
		if (Boolean.TRUE.equals(transactionService.balanceValidation(account, finalAmountWithDraw))) {
			transactionService.withDrawTransactionProcess(finalAmountWithDraw, account);
			summaryWithDrawScreen(finalAmountWithDraw, account);
		}
	}

	public void transactionScreen(Account account, List<Account> accounts) {
		System.out.println();
		System.out.println("Transaction Screen");
		System.out.println("1. Withdraw");
		System.out.println("2. Fund Transfer");
		System.out.println("3. Last Trasantion");
		System.out.println("4. Exit");
		System.out.println("Please choose option[3]:");
		Integer transactionOption = sc.nextInt();
		if (transactionOption == 1) {
			withDrawTransactionScreen(account, accounts);
			endTransactionScreen(account, accounts);
		} else if (transactionOption == 2) {
			fundTransferTransactionScreen(account, accounts);
			endTransactionScreen(account, accounts);
		} else if (transactionOption == 3) {
			transactionService.lastTransaction(account);
			endTransactionScreen(account, accounts);
		} else if (transactionOption == 4) {
			exitTransactionScreen();
			authService.login(accounts);
		} else {
			System.out.println("Please choose between option 1, 2, 3, or 4");
			transactionScreen(account, accounts);
		}
	}
}