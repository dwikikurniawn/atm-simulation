package com.dwiki.atmsimulation.controller;

import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping(value = "/view-account")
    public String viewAccounts(ModelMap model) {
        List<Account> accounts= accountService.getAllAccount();
        model.addAttribute("accounts", accounts);
        log.info("viewAccounts()  accounts: {}" + accounts);
        log.info("model: {}", model );
        return "view-accounts";
    }
}
