package com.c4l.accountstatement.dao;


import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.c4l.accountstatement.model.Statement;
import com.c4l.accountstatement.model.StatementRowMapper;

@ExtendWith(MockitoExtension.class)
class StatementDAOImplTest {

    @Mock private JdbcTemplate jdbcTemplate;
    @InjectMocks private StatementDAOImpl dao;

    private static final String SELECT_BY_ACCOUNT_NO = "select * from statement where account_id=? ";

    @Test
    void shouldGetStatementByAccountIdAndDateRange() {
        // Given
        long accountId = 123L;

        List<Statement> statementList = asList(
                new Statement(1L, accountId, "10.06.2020", "100.00"),
                new Statement(2L, accountId, "20.07.2020", "200.00"),
                new Statement(3L, accountId, "11.08.2020", "300.00")
        );

        when(jdbcTemplate.query(eq(SELECT_BY_ACCOUNT_NO), any(StatementRowMapper.class), eq(accountId)))
                .thenReturn(statementList);

        // When
        List<Statement> actualStatementList = dao.findByAccountNo(accountId);

        // Then
        assertSame(statementList, actualStatementList);
    }

   
    @Test
    void shouldReturnNullWhenStatementByAccountIdAndDateRangeQueryThrowsException() {
        // Given
    	String message = "Wrong Parameters";
        when(jdbcTemplate.query(eq(SELECT_BY_ACCOUNT_NO), any(StatementRowMapper.class), any()))
                .thenThrow(new RuntimeException(message));
        
        try {
        // When
        List<Statement> actualStatementList = dao.findByAccountNo(123L);

        }
     // Then
        catch (Exception e) {
        	assertEquals(RuntimeException.class, e.getClass());
            assertEquals(message, e.getMessage());
        }
        
    }

}
