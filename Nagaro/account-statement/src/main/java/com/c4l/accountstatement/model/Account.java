package com.c4l.accountstatement.model;


public class Account {

    private long id;
    private String accountNumber;
    
    
	public Account() { }
	
	
	public Account(long id, String accountNumber) {
		this.id = id;
		this.accountNumber = accountNumber;
	}
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
