package com.dwiki.atmsimulation.controller;

import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AccountService accountService;

    @PostMapping("/login-process")
    String loginProcess(@RequestParam String accountNumber,
                 @RequestParam String pin, ModelMap modelMap){
        log.info("loginProcess() accountNumber: {}, pin: {}", accountNumber,pin);
        Account account = accountService.searchAccountByAccountNumberAndPin(accountNumber, pin);
        modelMap.put("name", account.getName());
        modelMap.put("balance", account.getBalance());
        modelMap.put("accountNumber", account.getAccountNumber());
        return "dashboard";
    }

    @GetMapping("/dashboard/{accountNumber}")
    String dashboard(@PathVariable String accountNumber,
                         ModelMap modelMap){
        log.info("dashboard() accountNumber: {}", accountNumber);
        Account account = accountService.searchAccountByAccountNumber(accountNumber);
        modelMap.put("name", account.getName());
        modelMap.put("balance", account.getBalance());
        modelMap.put("accountNumber", account.getAccountNumber());
        return "dashboard";
    }

    @GetMapping(value = { "", "/", "/login" })
    public String loginPage(ModelMap model) {
        return "login-page";
    }
}
