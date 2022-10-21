package com.dwiki.atmsimulation.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.model.Transaction;

public class FileUtil {

	public static final String CSV_SEPARATOR = ",";

	public List<Transaction> readTransactionCsv(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))){
			return br.lines().map(line -> {
				String[] values = line.split(",");
				return transactionsFromCsv(values);
			}).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	private Transaction transactionsFromCsv(String[] values) {
		return new Transaction(values[0].replaceAll("^\"|\"$", ""), values[1],
				Integer.parseInt(values[2]), Instant.parse(values[3]), values[4].replaceAll("^\"|\"$", ""));
	}

	public List<Account> readAccountCsv(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			return br.lines().map(line -> {
				String[] values = line.split(",");
				return accountFromCsv(values);
			}).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	private Account accountFromCsv(String[] values) {
		return new Account(values[0].replaceAll("^\"|\"$", ""), values[1], values[2],
				Integer.parseInt(values[3].replaceAll("^\"|\"$", "")));
	}

	public void saveTransaction(String path, Transaction transaction) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true), "UTF-8"));
			StringBuilder oneLine = new StringBuilder();
			oneLine.append(transaction.getAccountNumber().trim().length() == 0 ? "" : transaction.getAccountNumber());
			oneLine.append(CSV_SEPARATOR);
			oneLine.append(transaction.getType().trim().length() == 0 ? "" : transaction.getType());
			oneLine.append(CSV_SEPARATOR);
			oneLine.append(transaction.getAmount());
			oneLine.append(CSV_SEPARATOR);
			oneLine.append(transaction.getTime());
			oneLine.append(CSV_SEPARATOR);
			oneLine.append(transaction.getRecepientAccountNumber());
			bw.write(oneLine.toString());
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
