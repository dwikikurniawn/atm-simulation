package com.dwiki.atmsimulation;

import java.util.ArrayList;
import java.util.List;

import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.service.TransactionService;

public class Main {
	public static void main(String[] args) {
		TransactionService transactionService= new TransactionService();
		
		//initiate Bank Account
		List<Account> accounts = new ArrayList<>();
		Account accountA = new Account("112233", "Jhon Doe", "111111", 200);
		Account accountB = new Account("112244", "Jane Doe", "111111", 150);
		accounts.add(accountA);
		accounts.add(accountB);

		transactionService.mainApp(accounts);
	}
}