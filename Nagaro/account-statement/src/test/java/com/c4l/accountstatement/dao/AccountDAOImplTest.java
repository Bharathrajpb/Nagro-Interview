package com.c4l.accountstatement.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.c4l.accountstatement.model.Account;



@ExtendWith(MockitoExtension.class)
class AccountDAOImplTest {

    private static final String SQL = "select * from account where account_number=?";

    @Mock private JdbcTemplate jdbcTemplate;

    @InjectMocks private AccountDAOImpl dao;

    @Test
    void shouldFindAccountByNumber() {
        // Given
        String accountNumber = "0012250016001";
        Account account = new Account(123L, accountNumber);

        when(jdbcTemplate.queryForObject(eq(SQL), any(BeanPropertyRowMapper.class), eq(accountNumber))).thenReturn(account);

        // When
        Account actualResult = dao.findByAccountNumber(accountNumber);

        // Then
        assertSame(account, actualResult);
    }

    @Test
    void shouldReturnEmptyAccountWhenExceptionOccurs() {
        // Given
        String accountNumber = "123456789";
        String message = "accountNumber not found";
        try {
	        when(jdbcTemplate.queryForObject(eq(SQL), any(BeanPropertyRowMapper.class), eq(accountNumber)))
	                .thenThrow(new NoSuchElementException(message));
	
	        // When
	        Account actualResult = dao.findByAccountNumber(accountNumber);
        }catch (Exception e) {
        	assertEquals(NoSuchElementException.class, e.getClass());
            assertEquals(message, e.getMessage());
        }
        // Then
        //assertNull(actualResult.getAccountType());
        //assertNull(actualResult.getAccountNumber());
    }
}