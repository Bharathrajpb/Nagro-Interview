package com.c4l.accountstatement.service;


import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.c4l.accountstatement.dao.AccountDAO;
import com.c4l.accountstatement.model.Account;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock private AccountDAO accountDAO;
    @InjectMocks private AccountServiceImpl service;

    @Test
    void shouldFindAccountByAccountNumber() {
        // Given
        String accountNumber = "0012250016001";
        Account account = new Account(1L, accountNumber);

        when(accountDAO.findByAccountNumber(accountNumber)).thenReturn(account);

        // When
        Account actualAccount = service.findByAccountNumber(accountNumber);

        // Then
        assertSame(account, actualAccount);
    }
}