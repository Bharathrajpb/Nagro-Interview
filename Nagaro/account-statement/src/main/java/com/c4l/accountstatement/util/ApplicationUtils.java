package com.c4l.accountstatement.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationUtils {

	 public static final Logger logger = LoggerFactory.getLogger(ApplicationUtils.class);
	

	  public static String maskAccountNumber(String accountNumber) {
	        return StringUtils.isEmpty(accountNumber)
					? accountNumber
					: accountNumber.replaceAll(".(?=.{4})", "*");
	    }
}
