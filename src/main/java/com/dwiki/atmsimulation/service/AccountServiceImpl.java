package com.dwiki.atmsimulation.service;

import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.repository.AccountRepository;
import com.dwiki.atmsimulation.util.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{

	private final AccountRepository accountRepository;
	private final DataUtil dataUtil;

	@Override
	public Account searchAccountByAccountNumberAndPin(String accountNumber, String pin) {
		if (isInvalidFormatPinOrAccount(accountNumber, pin))
			throw new RuntimeException("Unhandled exception");
		return accountRepository.findByAccountNumberAndPin(accountNumber, pin).orElseThrow(() ->
				new EntityNotFoundException("Account number " + accountNumber + " not found"));
	}

	private boolean isInvalidFormatPinOrAccount(String accountNumber, String pin) {
		return !dataUtil.accountNumberValidationFormat(accountNumber)
				|| !dataUtil.pinValidationFormat(pin);
	}

	private boolean isInvalidFormatAccountNumber(String accountNumber) {
		return !dataUtil.accountNumberValidationFormat(accountNumber);
	}

	@Override
	public Account searchAccountByAccountNumber(String accountNumber) {
		if(isInvalidFormatAccountNumber(accountNumber))
			throw new RuntimeException("Unhandled exception");
		return accountRepository.findById(accountNumber).orElseThrow(() ->
				new EntityNotFoundException("Account number " + accountNumber + " not found"));
		}
}