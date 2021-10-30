package com.c4l.accountstatement.service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c4l.accountstatement.dao.StatementDAO;
import com.c4l.accountstatement.dao.StatementDAOImpl;
import com.c4l.accountstatement.model.Statement;


@Service
public class StatementServiceImpl implements StatementService{

    public static final Logger logger = LoggerFactory.getLogger(StatementDAOImpl.class);

    private final StatementDAO statementDAO;
    private static final int DEFAULT_STATEMENT_MONTHS = -10;
    private static final SimpleDateFormat dateToBeConverted = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat dateFromDB = new SimpleDateFormat("dd.MM.yyyy");


	@Autowired
	public StatementServiceImpl(StatementDAO statementDAO) {
		this.statementDAO = statementDAO;
	}
	
	/**
	 * To get the Account statement on given fromDate and toDate
     * @param accountId 
     * @param fromDate  
     * @param toDate    
     * @return statement 
     */
	@Override
	public List<Statement> findByFromDateAndToDate(long accountId, String fromDate, String toDate) {
		List<Statement> statementList = new ArrayList<Statement>();
		logger.info("Call DAO to get retrive all the records");
		List<Statement> tempStatementList = statementDAO.findByAccountNo(accountId);
		try {
			if (fromDate == null || toDate == null) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
				Date date = cal.getTime();
				toDate = dateFormat.format(date); 
				cal.add(Calendar.MONTH, DEFAULT_STATEMENT_MONTHS);
				date = cal.getTime();
				fromDate = dateFormat.format(date); 
			}
			for (Statement statement : tempStatementList) {
				if (dateToBeConverted.parse(fromDate).compareTo(dateFromDB.parse(statement.getDate())) < 0
						&& dateToBeConverted.parse(toDate).compareTo(dateFromDB.parse(statement.getDate())) > 0) {
					statementList.add(statement);
				}
			}
		} catch (Exception e) {
			logger.error("Parsing Exception" + e.getMessage());
		}
		logger.info("{} records Found", statementList.size());
		return statementList;
	}
	
	/**
	 * TO get get the Account statement for the given amount Range
     * @param accountId     
     * @param fromAmount    
     * @param toAmount      
     * @return statement list
     */
	@Override
	public List<Statement> findByFromAmountAndToAmount(long accountId, String fromAmount, String toAmount) {
		List<Statement> statementList = new ArrayList<Statement>();

			logger.info("Call DAO to get retrive all the records");
			List<Statement> tempStatementList = statementDAO.findByAccountNo(accountId);

			try {
				for (Statement statement : tempStatementList) {
					if (Double.valueOf(fromAmount) <= Double.valueOf(statement.getAmount())
							&& Double.valueOf(toAmount) >= Double.valueOf(statement.getAmount())) {
						statementList.add(statement);
					}
				}
			} catch (Exception e) {
				logger.error("Parsing Exception" + e.getMessage());
			}

			logger.info("{} records Found", statementList.size());
			return statementList;
	}
}
