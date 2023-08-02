package com.dwiki.atmsimulation.service;

import com.dwiki.atmsimulation.LoginDto;
import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.repository.AccountRepository;
import com.dwiki.atmsimulation.util.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{

	private final AccountRepository accountRepository;
	private final DataUtil dataUtil;

	@Override
	public Account searchAccountByAccountNumberAndPin(String accountNumber, String pin) {
		if (isInvalidFormatPinOrAccount(accountNumber, pin))
			throw new RuntimeException("Invalid format for Account Number/ Pin");
		return accountRepository.findByAccountNumberAndPin(accountNumber, pin).orElseThrow(() ->
				new EntityNotFoundException("Account number " + accountNumber + " not found"));
	}

	@Override
	public Account getAccountByAccountNumberAndPin(LoginDto loginDto) {
		String accountNumber = loginDto.getAccountNumber();
		String pin = loginDto.getPin();
		if (isInvalidFormatPinOrAccount(accountNumber, pin))
			throw new RuntimeException("Invalid format for Account Number/ Pin");
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
			throw new RuntimeException("Invalid format for Account Number");
		return accountRepository.findById(accountNumber).orElseThrow(() ->
				new EntityNotFoundException("Account number " + accountNumber + " not found"));
		}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}
}