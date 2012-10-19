package com.netkiller.util;

public enum AccountType {
	PAID, FREE;

	@Override
	public String toString() {
		switch (this) {
		case PAID:
			return "paid";
		case FREE:
			return "free";
		default:
			return null;
		}
	}

	public static AccountType get(String accountTypeStr) {
		AccountType accountType = null;
		for (AccountType accountTypeVal : values()) {
			if (accountTypeVal.toString().equals(accountTypeStr)) {
				accountType = accountTypeVal;
			}
		}
		return accountType;
	}

}
