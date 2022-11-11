package com.dwiki.atmsimulation.service;

import java.util.List;
import java.util.Map;

import com.dwiki.atmsimulation.model.Account;

public class AuthService {

	private final AccountService accountService = new AccountService();

	public Account loginValidation(List<Account> accounts) {
		ScreenService screenService = new ScreenService();
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

	public void login(List<Account> accounts) {
		ScreenService screenService = new ScreenService();
		Account account = loginValidation(accounts);
		screenService.transactionScreen(account, accounts);
	}
}
