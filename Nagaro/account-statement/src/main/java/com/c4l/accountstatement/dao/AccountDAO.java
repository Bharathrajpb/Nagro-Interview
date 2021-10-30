package com.c4l.accountstatement.dao;

import com.c4l.accountstatement.model.Account;

public interface AccountDAO {
	
	public Account findByAccountNumber(String accountNumber);
}
