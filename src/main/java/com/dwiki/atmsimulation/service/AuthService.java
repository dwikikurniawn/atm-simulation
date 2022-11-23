package com.dwiki.atmsimulation.service;

import com.dwiki.atmsimulation.model.Account;

public interface AuthService {

    Account login(String accountNumber, String pin);
}
