package com.c4l.accountstatement.dao;

import java.util.List;

import com.c4l.accountstatement.model.Statement;



public interface StatementDAO {
	
	List<Statement> findByAccountNo(long accountId);

}
