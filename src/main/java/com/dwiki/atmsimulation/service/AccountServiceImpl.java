package com.dwiki.atmsimulation.service;

import java.util.List;

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
		if(Boolean.TRUE.equals(dataUtil.accountNumberValidationFormat(accountNumber))
				&& Boolean.TRUE.equals(dataUtil.pinValidationFormat(pin))){
			return accountRepository.findById(accountNumber).orElseThrow(() -> new EntityNotFoundException("Account number " + accountNumber + " not found"));
		}
		return null;
	}

	@Override
	public Account searchAccountByAccountNumber(String accountNumber) {
		if(Boolean.TRUE.equals(dataUtil.accountNumberValidationFormat(accountNumber))){
			return accountRepository.findById(accountNumber).orElseThrow(() -> new EntityNotFoundException("Account number " + accountNumber + " not found"));
		}
		return null;
	}
}