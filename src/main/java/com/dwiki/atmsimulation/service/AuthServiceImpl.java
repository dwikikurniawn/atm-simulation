package com.dwiki.atmsimulation.service;

import java.util.List;
import java.util.Map;

import com.dwiki.atmsimulation.model.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{
	private final AccountService accountService;

//	public Account loginValidation(List<Account> accounts) {
//		Map<String, Object> loginScreenResult = screenServiceImpl.loginScreen();
//		Account account = accountService.searchAccountByAccountNumberAndPin((String) loginScreenResult.get("accountNumber"), (String) loginScreenResult.get("pin"));
//		while (Boolean.FALSE.equals(loginScreenResult.get("valid")) || account == null) {
//			if (account == null && Boolean.TRUE.equals(loginScreenResult.get("valid"))) {
//				System.out.println("Account is not valid, please log in with correct account and pin");
//			}
//			loginScreenResult = screenServiceImpl.loginScreen();
//			account = accountService.searchAccountByAccountNumberAndPin(
//					(String) loginScreenResult.get("accountNumber"), (String) loginScreenResult.get("pin"));
//		}
//		return account;
//	}

//	public void login(List<Account> accounts) {
//		ScreenServiceImpl screenServiceImpl = new ScreenServiceImpl();
//		Account account = loginValidation(accounts);
//		screenServiceImpl.transactionScreen(account, accounts);
//	}

    @Override
    public Account login(String accountNumber, String pin) {
        return accountService.searchAccountByAccountNumberAndPin(accountNumber,pin);
    }
}
