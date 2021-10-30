package com.c4l.accountstatement.service;

import com.c4l.accountstatement.model.Account;

public interface AccountService {
	

	public Account findByAccountNumber(String accountNumber);
}
