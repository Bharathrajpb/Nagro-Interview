package com.c4l.accountstatement.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.c4l.accountstatement.dao.StatementDAO;
import com.c4l.accountstatement.model.Statement;

@ExtendWith(MockitoExtension.class)
class StatementServiceImplTest {

    @Mock private StatementDAO statementDAO;
    @InjectMocks StatementServiceImpl service;

    @Test
    void shouldGetStatementByAccountIdAndDateRange() {
        // Given
        long accountId = 123L;
        String fromDate = "2020-06-01";
        String toDateDate = "2020-08-01";

        List<Statement> statementList =new ArrayList<>();
        statementList.add(new Statement(1L, accountId, "05.06.2020", "150.00"));
        statementList.add(new Statement(2L, accountId, "15.06.2020", "300.00"));
        statementList.add(new Statement(3L, accountId, "15.07.2020", "350.00"));

        when(statementDAO.findByAccountNo(accountId)).thenReturn(statementList);

        // When
        List<Statement> actualList = service.findByFromDateAndToDate(accountId, fromDate, toDateDate);

        // Then
        assertEquals(statementList, actualList);
    }

    @Test
    void shouldGetStatementByAccountIdAndAmountRange() {
        // Given
        long accountId = 123L;
        String fromAmount = "100.00";
        String toAmount = "400.00";

        List<Statement> statementList = Arrays.asList(
                new Statement(1L, accountId, "05.06.2020", "150.00"),
                new Statement(2L, accountId, "15.06.2020", "300.00"),
                new Statement(3L, accountId, "15.07.2020", "350.00")
        );

        when(statementDAO.findByAccountNo(accountId)).thenReturn(statementList);

        // When
        List<Statement> actualList = service.findByFromAmountAndToAmount(accountId, fromAmount, toAmount);

        // Then
        assertEquals(statementList, actualList);
    }
}