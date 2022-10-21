package com.dwiki.atmsimulation.service;

import java.util.List;

import com.dwiki.atmsimulation.model.Account;

public class AccountService {

	public Account searchAccountByAccountNumberAndPin(List<Account> accounts, String accountNumber, String pin) {
		return accounts.stream()
				.filter(account -> accountNumber.equalsIgnoreCase(account.getAccountNumber()))
				.filter(account -> pin.equalsIgnoreCase(account.getPin()))
				.findAny().orElse(null);
	}

	public Account searchAccountByAccountNumber(List<Account> accounts, String accountNumber) {
		return accounts.stream().filter(account -> accountNumber.equalsIgnoreCase(account.getAccountNumber()))
				.findAny().orElse(null);
	}
}