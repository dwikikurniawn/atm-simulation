package com.dwiki.atmsimulation.service;

import com.dwiki.atmsimulation.model.Account;

public interface AccountService {
    Account searchAccountByAccountNumberAndPin(String accountNumber, String pin);

    Account searchAccountByAccountNumber(String accountNumber);

}
