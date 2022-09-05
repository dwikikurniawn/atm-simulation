package com.dwiki.atmsimulation;

import java.util.List;

import com.dwiki.atmsimulation.constant.Constant;
import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.service.TransactionService;
import com.dwiki.atmsimulation.util.FileUtil;

public class Main {
	public static void main(String[] args) {
		TransactionService transactionService= new TransactionService();
		FileUtil fileUtil = new FileUtil();
		List<Account> accounts = fileUtil.readAccountCsv(Constant.ACCOUNT_FILE_PATH);

		transactionService.mainApp(accounts);
	}
}