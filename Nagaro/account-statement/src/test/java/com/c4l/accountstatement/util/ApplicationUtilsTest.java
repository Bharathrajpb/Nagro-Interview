package com.c4l.accountstatement.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.InvalidParameterException;

import org.junit.jupiter.api.Test;

public class ApplicationUtilsTest {

	
	@Test
    void shouldMaskAccountNumber() {
        assertEquals(null,ApplicationUtils.maskAccountNumber(null));
        assertEquals("",ApplicationUtils.maskAccountNumber(""));
        assertEquals("1234",ApplicationUtils.maskAccountNumber("1234"));
        assertEquals("*****6789",ApplicationUtils.maskAccountNumber("123456789"));
    }
}
