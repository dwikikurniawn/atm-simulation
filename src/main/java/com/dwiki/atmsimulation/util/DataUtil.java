package com.dwiki.atmsimulation.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataUtil {

	public boolean isNumeric(String str) {
		return str.chars().allMatch(Character::isDigit);
	}
	
	public boolean accountNumberValidationFormat(String accountNumber) {
		if (!isNumeric(accountNumber)) {
			throw new NumberFormatException("Account Number should only contains numbers");
		} else if (accountNumber.length() != 6) {
			throw new NumberFormatException("Account Number should have 6 digits length");
		}
		return Boolean.TRUE;
	}
	
	public boolean pinValidationFormat(String pin) {
		if (!isNumeric(pin)) {
			throw new NumberFormatException("PIN should only contains numbers");
		} else if (pin.length() > 6) {
			throw new NumberFormatException("PIN should have 6 digits length");
		}
		return Boolean.TRUE;
	}
	
	public Integer generateReferenceNumber() {
		Random rnd = new Random();
		return 100000 + rnd.nextInt(900000);
	}
}