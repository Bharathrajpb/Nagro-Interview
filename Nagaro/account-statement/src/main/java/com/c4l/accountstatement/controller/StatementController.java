package com.c4l.accountstatement.controller;

import static com.c4l.accountstatement.util.ApplicationUtils.maskAccountNumber;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.c4l.accountstatement.model.Account;
import com.c4l.accountstatement.model.Statement;
import com.c4l.accountstatement.service.AccountService;
import com.c4l.accountstatement.service.StatementService;



@Controller
@RequestMapping("/statement")
@Validated
public class StatementController {

    public static final Logger logger = LoggerFactory.getLogger(StatementController.class);
    

    private final AccountService accountService;
    private final StatementService statementService;

    @Autowired
    public StatementController(AccountService accountService, StatementService statementService) {
        this.accountService = accountService;
        this.statementService = statementService;
    }

    /**
     * To fetch the Account Statement for the given Date range
     *
     * @param accountNumber 
     * @param fromDate      
     * @param toDate        
     * @return statement
     */
    @GetMapping("/statement-report")
    
    public ModelAndView getStatement(
            @RequestParam(name = "accountNumber") @NotBlank @Size(min=13,max=13) String accountNumber,
            @RequestParam(name = "fromDate", required = false) String fromDate,
            @RequestParam(name = "toDate", required = false) String toDate) {

		try {
			String maskedAccountNumber = maskAccountNumber(accountNumber);
            logger.info("Statement requested by user: {} for account: {} from date: {} to date: {}", getUsername(), maskedAccountNumber, fromDate, toDate);
            Account account = accountService.findByAccountNumber(accountNumber);
			List<Statement> statementList = statementService.findByFromDateAndToDate(account.getId(), fromDate, toDate);

            return createStatementResponse(account, statementList);
        } catch (Exception e) {
            logger.error("Error while fetching statement", e);
            return createErrorResponse(e);
        }
    }

    /**
     * To fetch the Account Statement for the given Amount range
     *
     * @param accountNumber 
     * @param fromAmount    
     * @param toAmount      
     * @return statement
     */
    @GetMapping("/statement-report-amountrange")
    public ModelAndView getStatementByAmountRange(
            @RequestParam(name = "accountNumber") @NotBlank @Size(min=13,max=13) String accountNumber,
            @RequestParam(name = "fromAmount") @NotBlank String fromAmount,
            @RequestParam(name = "toAmount") @NotBlank String toAmount) {

        try {
        	String maskedAccountNumber = maskAccountNumber(accountNumber);
            logger.info("Statement requested by user: {} for account: {} from amount: {} to amount: {}", getUsername(),maskedAccountNumber, fromAmount, toAmount);

            Account account = accountService.findByAccountNumber(accountNumber);
			List<Statement> statementList = statementService.findByFromAmountAndToAmount(account.getId(), fromAmount, toAmount);

            return createStatementResponse(account, statementList);
        } catch (Exception e) {
            logger.error("Error while fetching statement", e);
            return createErrorResponse(e);
        }
    }
    
    
    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    
    
    private static ModelAndView createStatementResponse(Account account, List<Statement> statementList) {
        ModelAndView model = new ModelAndView();
        model.addObject("accountNumber", account.getAccountNumber());
        model.addObject("statementList", statementList.size()>0?statementList:null);
        model.setViewName("statement_list");
        return model;
    }
    
    /**
     * This method is used to prepare model and view for Error case.
     *
     * @param exception
     * @return model
     */
    private static ModelAndView createErrorResponse(Exception e) {
        ModelAndView model = new ModelAndView();
        model.addObject("message", e.getMessage());
        model.addObject("exception", e);
        model.setViewName("custom_error");
        return model;
    }
}
