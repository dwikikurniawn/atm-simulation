package com.dwiki.atmsimulation.controller;

import com.dwiki.atmsimulation.LoginDto;
import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @GetMapping(value = {"", "/", "/login"})
    public String loginPage(ModelMap model) {
        return "login-page";
    }

    @PostMapping("/login-process")
    String loginProcess(@RequestParam String accountNumber,
                        @RequestParam String pin, HttpSession session) {
        Account account = accountService.searchAccountByAccountNumberAndPin(accountNumber, pin);
        session.setAttribute("account", account);
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    String dashboard(ModelMap modelMap, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        Account updatedAccount = accountService.searchAccountByAccountNumber(account.getAccountNumber());
        session.setAttribute("account", updatedAccount);
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/api/login")
    public ResponseEntity<Account> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(accountService.getAccountByAccountNumberAndPin(loginDto));
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> findAllAccount() {
        return ResponseEntity.ok(accountService.findAll());
    }
}