package com.dwiki.atmsimulation.repository;

import com.dwiki.atmsimulation.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByAccountNumberAndPin(String accountNumber, String pin);

    Optional<Account> findByAccountNumber(String accountNumber);
}
