package com.dwiki.atmsimulation.util;

import com.dwiki.atmsimulation.model.Transaction;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

@Component
public class FileUtil {

	public static final String CSV_SEPARATOR = ",";

	public void saveTransaction(String path, Transaction transaction) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true), "UTF-8"));
			StringBuilder oneLine = new StringBuilder();
			oneLine.append(transaction.getSourceAccountNumber().trim().length() == 0 ? "" : transaction.getSourceAccountNumber());
			oneLine.append(CSV_SEPARATOR);
			oneLine.append(transaction.getType().trim().length() == 0 ? "" : transaction.getType());
			oneLine.append(CSV_SEPARATOR);
			oneLine.append(transaction.getAmount());
			oneLine.append(CSV_SEPARATOR);
			oneLine.append(transaction.getTime());
			oneLine.append(CSV_SEPARATOR);
			oneLine.append(transaction.getRecipientAccountNumber());
			oneLine.append(CSV_SEPARATOR);
			oneLine.append(transaction.getReferenceNumber());
			bw.write(oneLine.toString());
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
