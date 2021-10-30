package com.c4l.accountstatement.service;

import java.util.List;

import com.c4l.accountstatement.model.Statement;



public interface StatementService {
	
	
	public List<Statement> findByFromDateAndToDate(long accountId,String fromDate,String toDate);
	
	
	public List<Statement> findByFromAmountAndToAmount(long accountId,String fromAmount,String toAmount);
}
