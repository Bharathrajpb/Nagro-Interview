package com.c4l.accountstatement.controller;


import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.security.InvalidParameterException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.c4l.accountstatement.model.Account;
import com.c4l.accountstatement.model.Statement;
import com.c4l.accountstatement.service.AccountService;
import com.c4l.accountstatement.service.StatementService;

@ExtendWith(MockitoExtension.class)
class StatementControllerTest {

    @Mock private AccountService accountService;
    @Mock private StatementService statementService;

    @InjectMocks private StatementController controller;

    @Mock private Authentication authentication;
    @Mock private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void shouldGetStatementByAccountNumberAndDateRange() {
        // Given
        long accountId = 1L;
        String accountNumber = "0012250016001";

        String fromDate = "2020-08-01";
        String toDate = "2020-08-04";

        Account account = new Account(accountId, accountNumber);

        List<Statement> statementList = asList(
                new Statement(1L, accountId, "03.03.2012", "373.950606558506"), 	
                new Statement(2L, accountId, "04.05.2012", "289.279082576802"), 	
                new Statement(3L, accountId, "04.07.2012", "113.405299633134") 	
        );

        when(accountService.findByAccountNumber(accountNumber)).thenReturn(account);
        when(statementService.findByFromDateAndToDate(accountId, fromDate, toDate)).thenReturn(statementList);

        // When
        ModelAndView response = controller.getStatement(accountNumber, fromDate, toDate);

        // Then
        assertEquals("*********6001", response.getModel().get("accountNumber"));
        assertEquals(statementList, response.getModel().get("statementList"));
        assertEquals("statement_list", response.getViewName());
    }

    @Test
    void shouldReturnErrorResponseWhenAccountNumberIsBlankForStatementByDateRange() {
        // When
        ModelAndView response = controller.getStatement(" ", "2020-06-01", "2020-08-01");

        // Then
        assertEquals("accountNumber cannot be blank", response.getModel().get("message"));
        assertEquals(InvalidParameterException.class, response.getModel().get("exception").getClass());
        assertEquals("custom_error", response.getViewName());
    }

    @Test
    void shouldGetStatementByAccountNumberAndAmountRange() {
        // Given
        long accountId = 2L;
        
        String accountNumber = "0012250016002";

        String fromAmount = "100.0";
        String toAmount = "200.0";

        Account account = new Account(accountId, accountNumber);

        List<Statement> statementList = asList(
                new Statement(1L, accountId, "19.01.2012", "166.470541608144"), 	
                new Statement(2L, accountId, "29.10.2012", "148.824332549236")
        );

        when(accountService.findByAccountNumber(accountNumber)).thenReturn(account);
        when(statementService.findByFromAmountAndToAmount(accountId, fromAmount, toAmount)).thenReturn(statementList);

        // When
        ModelAndView response = controller.getStatementByAmountRange(accountNumber, fromAmount, toAmount);

        // Then
        assertEquals("*********6002", response.getModel().get("accountNumber"));
        assertEquals(statementList, response.getModel().get("statementList"));
        assertEquals("statement_list", response.getViewName());
    }
    
    @Test
    void shouldReturnErrorResponseWhenAccountNumberLengthIsInvalidForStatementByAmountRange() {
        // When
        ModelAndView response = controller.getStatement("123456", "2020-06-01", "2020-08-01");

        // Then
        assertEquals("accountNumber length is invalid", response.getModel().get("message"));
        assertEquals(InvalidParameterException.class, response.getModel().get("exception").getClass());
        assertEquals("custom_error", response.getViewName());
    }

    @Test
    void shouldReturnErrorResponseWhenAccountNumberIsBlankForStatementByAmountRange() {
        // When
        ModelAndView response = controller.getStatementByAmountRange(" ", "100.00", "500.00");

        // Then
        assertEquals("accountNumber cannot be blank", response.getModel().get("message"));
        assertEquals(InvalidParameterException.class, response.getModel().get("exception").getClass());
        assertEquals("custom_error", response.getViewName());
    }

    @Test
    void shouldReturnErrorResponseWhenFromAmountIsBlankForStatementByAmountRange() {
        // When
        ModelAndView response = controller.getStatementByAmountRange("0012250016001", " ", "500.00");

        // Then
        assertEquals("fromAmount cannot be blank", response.getModel().get("message"));
        assertEquals(InvalidParameterException.class, response.getModel().get("exception").getClass());
        assertEquals("custom_error", response.getViewName());
    }

    @Test
    void shouldReturnErrorResponseWhenToAmountIsBlankForStatementByAmountRange() {
        // When
        ModelAndView response = controller.getStatementByAmountRange("0012250016002", "100.00", " ");

        // Then
        assertEquals("toAmount cannot be blank", response.getModel().get("message"));
        assertEquals(InvalidParameterException.class, response.getModel().get("exception").getClass());
        assertEquals("custom_error", response.getViewName());
    }
}