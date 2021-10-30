package com.c4l.accountstatement.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c4l.accountstatement.dao.AccountDAO;
import com.c4l.accountstatement.model.Account;


@Service
public class AccountServiceImpl implements AccountService{
	
	private final AccountDAO accountDAO;

	@Autowired
	public AccountServiceImpl(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	
	
	@Override
	public Account findByAccountNumber(String accountNumber) {
		return accountDAO.findByAccountNumber(accountNumber);
	}
}
