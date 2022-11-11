package com.dwiki.atmsimulation;

import java.util.List;

import com.dwiki.atmsimulation.constant.Constant;
import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.service.AuthService;
import com.dwiki.atmsimulation.util.FileUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AtmsimulationApplication {
	public static void main(String[] args) {
		AuthService authService = new AuthService();
		FileUtil fileUtil = new FileUtil();
		List<Account> accounts = fileUtil.readAccountCsv(Constant.ACCOUNT_FILE_PATH);
		authService.login(accounts);
		SpringApplication.run(AtmsimulationApplication.class, args);
	}
}