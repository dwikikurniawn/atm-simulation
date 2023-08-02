package com.dwiki.atmsimulation.service;

import com.dwiki.atmsimulation.LoginDto;
import com.dwiki.atmsimulation.model.Account;

import java.util.List;

public interface AccountService {
    Account searchAccountByAccountNumberAndPin(String accountNumber, String pin);

    Account getAccountByAccountNumberAndPin(LoginDto loginDto);

    Account searchAccountByAccountNumber(String accountNumber);

    List<Account> findAll();
}
