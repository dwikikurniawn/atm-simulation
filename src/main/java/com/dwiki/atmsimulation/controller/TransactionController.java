package com.dwiki.atmsimulation.controller;

import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.model.Transaction;
import com.dwiki.atmsimulation.service.AccountService;
import com.dwiki.atmsimulation.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;

    @GetMapping(value = { "/transfer-page/{accountNumber}" })
    public String transferPage(ModelMap model, @PathVariable String accountNumber) {
        model.addAttribute("accountNumber", accountNumber);
        return "transfer-page";
    }

    @PostMapping("/transfer/{accountNumber}")
    String transfer(ModelMap modelMap, @RequestParam String recipientAccountNumber, @RequestParam Integer amount, @PathVariable String accountNumber){
        transactionService.transferTransactionProcess(accountNumber, recipientAccountNumber, amount);
        Account account = accountService.searchAccountByAccountNumber(accountNumber);
        modelMap.put("name", account.getName());
        modelMap.put("balance", account.getBalance());
        modelMap.put("accountNumber", account.getAccountNumber());
        return "dashboard";
    }

    @GetMapping(value = { "/withdraw-page/{accountNumber}" })
    public String withdrawPage(ModelMap modelMap, @PathVariable String accountNumber) {
        Account account = accountService.searchAccountByAccountNumber(accountNumber);
        modelMap.put("name", account.getName());
        modelMap.put("balance", account.getBalance());
        modelMap.put("accountNumber", account.getAccountNumber());
        return "withdraw-page";
    }

    @GetMapping(value = { "/other-withdraw-page/{accountNumber}" })
    public String otherWithdrawPage(ModelMap modelMap, @PathVariable String accountNumber) {
        Account account = accountService.searchAccountByAccountNumber(accountNumber);
        modelMap.put("name", account.getName());
        modelMap.put("balance", account.getBalance());
        modelMap.put("accountNumber", account.getAccountNumber());
        return "other-withdraw-page";
    }

    @PostMapping("/withdraw/{accountNumber}")
    String withdraw(ModelMap modelMap, @PathVariable String accountNumber, @RequestParam Integer amount){
        transactionService.withDrawTransactionProcess(amount,accountNumber);
        Account account = accountService.searchAccountByAccountNumber(accountNumber);
        modelMap.put("name", account.getName());
        modelMap.put("balance", account.getBalance());
        modelMap.put("accountNumber", account.getAccountNumber());
        return "dashboard";
    }

    @GetMapping(value = "/history/{accountNumber}")
    public String viewTransactionHistory(ModelMap modelMap, @PathVariable String accountNumber) {
        List<Transaction> transactions= transactionService.lastTransaction(accountNumber);
        modelMap.put("transactions", transactions);
        modelMap.put("accountNumber", accountNumber);
        return "transaction-history-page";
    }
}
