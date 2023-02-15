package com.dwiki.atmsimulation.controller;

import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.model.Transaction;
import com.dwiki.atmsimulation.service.AccountService;
import com.dwiki.atmsimulation.service.TransactionService;
import com.dwiki.atmsimulation.util.DataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final DataUtil dataUtil;

    @GetMapping(value = { "/transfer-page" })
    public String transferPage(ModelMap modelMap, HttpSession session) {
        modelMap.put("account", session.getAttribute("account"));
        return "transfer-page";
    }

    @GetMapping("/transfer-confirmation")
    String transferConfirmation(@RequestParam String recipientAccountNumber, @RequestParam Integer amount, HttpSession session){
//        Account account = (Account) session.getAttribute("account");

        String referenceNumber = dataUtil.generateReferenceNumber().toString();
        session.setAttribute("amount", amount);
        session.setAttribute("recipientAccountNumber", recipientAccountNumber);
        session.setAttribute("referenceNumber", referenceNumber);
        return "transfer-confirmation-page";
    }

    @PostMapping("/transfer")
    String transfer(HttpSession session){
        Account account = (Account) session.getAttribute("account");
        String referenceNumber = (String) session.getAttribute("referenceNumber");
        String recipientAccountNumber = (String) session.getAttribute("recipientAccountNumber");
        Integer amount = (Integer) session.getAttribute("amount");
        transactionService.transferTransactionProcess(account.getAccountNumber(), recipientAccountNumber, amount, referenceNumber);
        account = accountService.searchAccountByAccountNumber(account.getAccountNumber());
        session.setAttribute("account", account);
        return "redirect:/dashboard";
    }

    @GetMapping(value = "/withdraw-page")
    public String withdrawPage(ModelMap modelMap, HttpSession session) {
        modelMap.put("account", session.getAttribute("account"));
        return "withdraw-page";
    }

    @GetMapping(value = { "/other-withdraw-page" })
    public String otherWithdrawPage(ModelMap modelMap, HttpSession session) {
        modelMap.put("account", session.getAttribute("account"));
        return "other-withdraw-page";
    }

    @PostMapping("/withdraw")
    String withdraw(@RequestParam Integer amount, HttpSession session){
        Account account = (Account) session.getAttribute("account");
        transactionService.withDrawTransactionProcess(amount,account.getAccountNumber());
        account = accountService.searchAccountByAccountNumber(account.getAccountNumber());
        session.setAttribute("account", account);
        return "redirect:/dashboard";
    }

    @GetMapping(value = "/history")
    public String viewTransactionHistory(ModelMap modelMap, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        List<Transaction> transactions= transactionService.lastTransaction(account.getAccountNumber());
        modelMap.put("transactions", transactions);
        return "transaction-history-page";
    }
}