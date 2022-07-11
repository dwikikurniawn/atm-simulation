package com.dwiki.atmsimulation;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Account> accounts = new ArrayList<>();
		Account accountA = new Account("112233", "Jhon Doe", "111111", 200);
		Account accountB = new Account("112244", "Jane Doe", "111111", 150);
		accounts.add(accountA);
		accounts.add(accountB);

		System.out.println("Welcome to ATM Mitrais Bank");
		Account account = loginValidation(sc, accounts);

		transactionScreen(sc, account, accounts);
	}

	static Account loginValidation(Scanner sc, List<Account> accounts) {
		System.out.println("Enter Account Number: ");
		String inputtedAccountNumber = sc.nextLine();
		accountNumberValidationFormat(inputtedAccountNumber, sc);

		System.out.println("Enter PIN: ");
		String inputtedPin = sc.nextLine();
		pinValidationFormat(inputtedPin, sc);

		// account login validation
		Account account = searchAccountByAccountNumberAndPin(accounts, inputtedAccountNumber, inputtedPin);
		if (account == null) {
			System.out.println("Invalid Account Number/PIN");
			sc.close();
			throw new NullPointerException();
		}
		return account;
	}

	static boolean isNumeric(String str) {
		return str.chars().allMatch(Character::isDigit);
	}

	static Account searchAccountByAccountNumberAndPin(List<Account> accounts, String accountNumber, String pin) {
		for (Account account : accounts) {
			if (account.getAccountNumber().equalsIgnoreCase(accountNumber) && account.getPin().equalsIgnoreCase(pin)) {
				return account;
			}
		}
		return null;
	}

	static Integer generateReferenceNumber() {
		Random rnd = new Random();
		return 100000 + rnd.nextInt(900000);
	}

	static void balanceValidation(Integer amount, Account account, Scanner sc) {
		if (account.getBalance() < amount) {
			System.out.println("Insufficient balance $" + amount);
			sc.close();
			throw new NullPointerException();
		}
	}

	static void withDrawTransactionProcess(Integer amount, Account account) {
		account.setBalance(account.getBalance() - amount);
	}

	static void exitTransaction(Scanner sc) {
		sc.close();
		System.out.println("Thank you, have a good day!");
	}

	static void fundTransferTransactionScreen(Scanner sc, Account account, List<Account> accounts) {
		// Fund Transfer Screen
		System.out.println("Fund Transfer Screen");
		System.out.println(
				"Please enter destination account and press enter to continue or press cancel (Esc) to go back to Transaction: ");

		String destinationAccountNumber = sc.next();
		accountNumberValidationFormat(destinationAccountNumber, sc);

		System.out.println(
				"Please enter transfer amount and press enter to continue or press enter to go back to Transaction: ");
		Integer transferAmount = sc.nextInt();
		amountTransferValidation(transferAmount, sc);

		Integer referenceNumber = generateReferenceNumber();
		System.out.println("Reference Number: " + referenceNumber);
		System.out.println("Press any key to continue or cancel to go back to Transaction: ");
		String confirmationReferenceNumber = sc.next();
		if (confirmationReferenceNumber.equalsIgnoreCase("cancel")) {
			System.out.println("Okay, we are canceling your transfer");
			endTransactionScreen(sc, account, accounts);
		}

		transferConfirmationScreen(sc, account, accounts, destinationAccountNumber, transferAmount, referenceNumber);
	}

	static void transferConfirmationScreen(Scanner sc, Account account, List<Account> accounts,
			String destinationAccountNumber, Integer transferAmount, Integer referenceNumber) {
		// transfer Confirmation Screen
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
			processingTransferTransaction(sc, account, accounts, destinationAccountNumber, transferAmount,
					referenceNumber);
		} else if (transferConfirmationOption == 2) {
			System.out.println("Okay, we are canceling your transfer");
			endTransactionScreen(sc, account, accounts);
		}
	}

	static void processingTransferTransaction(Scanner sc, Account account, List<Account> accounts,
			String destinationAccountNumber, Integer transferAmount, Integer referenceNumber) {
		Account destinationAccount = searchAccountByAccountNumber(accounts, destinationAccountNumber);
		balanceValidation(account, transferAmount, sc);
		account.setBalance(account.getBalance() - transferAmount);
		destinationAccount.setBalance(destinationAccount.getBalance() + transferAmount);
		summaryTransfer(transferAmount, account, destinationAccountNumber, referenceNumber);
	}

	static void balanceValidation(Account account, Integer amount, Scanner sc) {
		if (account.getBalance() < amount) {
			System.out.println("Insufficient balance $" + amount);
			sc.close();
			throw new NullPointerException();
		}
	}

	static void withDrawTransactionScreen(Scanner sc, Account account) {
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
			balanceValidation(finalAmountWithDraw, account, sc);
			withDrawTransactionProcess(finalAmountWithDraw, account);
			summaryWithDraw(finalAmountWithDraw, account);
		} else if (withdrawOption == 2) {
			Integer finalAmountWithDraw = 50;
			balanceValidation(finalAmountWithDraw, account, sc);
			withDrawTransactionProcess(finalAmountWithDraw, account);
			summaryWithDraw(finalAmountWithDraw, account);
		} else if (withdrawOption == 3) {
			Integer finalAmountWithDraw = 100;
			balanceValidation(finalAmountWithDraw, account, sc);
			withDrawTransactionProcess(finalAmountWithDraw, account);
			summaryWithDraw(finalAmountWithDraw, account);
		} else if (withdrawOption == 4) {
			otherWithDrawTransaction(sc, account);
		}
	}

	static void endTransactionScreen(Scanner sc, Account account, List<Account> accounts) {
		System.out.println();
		System.out.println("1. Transaction");
		System.out.println("2. Exit");
		System.out.println("Choose option[2]:");
		Integer summaryOption = sc.nextInt();
		if (summaryOption == 1) {
			transactionScreen(sc, account, accounts);
		} else if (summaryOption == 2) {
			exitTransaction(sc);
		}
	}

	static void otherWithDrawTransaction(Scanner sc, Account account) {
		// other withdraw screen
		System.out.println("Other Withdraw Screen");
		System.out.println("Other Withdraw");
		System.out.println("Enter amount to withdraw");
		Integer amountWithdraw = sc.nextInt();

		amountWithDrawValidation(amountWithdraw, sc);

		withDrawTransactionProcess(amountWithdraw, account);
		Integer finalAmountWithDraw = amountWithdraw;
		summaryWithDraw(finalAmountWithDraw, account);

	}

	static void amountWithDrawValidation(Integer amountWithdraw, Scanner sc) {
		if (!isNumeric(Integer.toString(amountWithdraw)) || amountWithdraw % 10 != 0) {
			System.out.println("Invalid amount");
			sc.close();
			throw new NullPointerException();
		} else if (amountWithdraw > 1000) {
			System.out.println("Maximum amount to withdraw is $1000");
			sc.close();
			throw new NullPointerException();
		}
	}

	static void amountTransferValidation(Integer amountTransfer, Scanner sc) {
		if (!isNumeric(Integer.toString(amountTransfer))) {
			System.out.println("Invalid amount");
			sc.close();
			throw new NullPointerException();
		} else if (amountTransfer > 1000 || amountTransfer < 1) {
			System.out.println("Maximum amount to withdraw is $1000");
			sc.close();
			throw new NullPointerException();
		}
	}

	static void summaryWithDraw(Integer finalAmountWithDraw, Account account) {
		// summary withdraw screen
		System.out.println("Date: " + Instant.now());
		System.out.println("Withdraw: " + finalAmountWithDraw);
		System.out.println("Balance: " + account.getBalance());
	}

	static void summaryTransfer(Integer finalAmountTransfer, Account sourceAccount, String destinationAccountNumber,
			Integer referenceNumber) {
		// summary withdraw screen
		System.out.println("Fund Transfer Summary");
		System.out.println("Destination Account : " + destinationAccountNumber);
		System.out.println("Transfer amount : $" + finalAmountTransfer);
		System.out.println("Reference Number : " + referenceNumber);
		System.out.println("Balance : $" + sourceAccount.getBalance());
	}

	static Account searchAccountByAccountNumber(List<Account> accounts, String accountNumber) {
		for (Account account : accounts) {
			if (account.getAccountNumber().equalsIgnoreCase(accountNumber)) {
				return account;
			}
		}
		return null;
	}

	static void accountNumberValidationFormat(String accountNumber, Scanner sc) {
		if (!isNumeric(accountNumber)) {
			System.out.println("Account Number should only contains numbers");
			sc.close();
			throw new NullPointerException();
		} else if (accountNumber.length() != 6) {
			System.out.println("Account Number should have 6 digits length");
			sc.close();
			throw new NullPointerException();
		}
	}

	static void pinValidationFormat(String pin, Scanner sc) {
		if (!isNumeric(pin)) {
			System.out.println("PIN should only contains numbers");
			sc.close();
			throw new NullPointerException();
		} else if (pin.length() > 6) {
			System.out.println("PIN should have 6 digits length");
			sc.close();
			throw new NullPointerException();
		}
	}

	static void transactionScreen(Scanner sc, Account account, List<Account> accounts) {
		System.out.println();
		System.out.println("Transaction Screen");
		System.out.println("1. Withdraw");
		System.out.println("2. Fund Transfer");
		System.out.println("3. Exit");
		System.out.println("Please choose option[3]:");
		Integer transactionOption = sc.nextInt();
		if (transactionOption == 1) {
			withDrawTransactionScreen(sc, account);
			endTransactionScreen(sc, account, accounts);
		} else if (transactionOption == 2) {
			fundTransferTransactionScreen(sc, account, accounts);
			endTransactionScreen(sc, account, accounts);
		} else if (transactionOption == 3) {
			exitTransaction(sc);
		}
	}
}