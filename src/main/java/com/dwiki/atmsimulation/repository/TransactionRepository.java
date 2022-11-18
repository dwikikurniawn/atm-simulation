package com.dwiki.atmsimulation.repository;

import com.dwiki.atmsimulation.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findTop10BySourceAccountNumberOrderByTimeDesc(String sourceAccountNumber);
}
