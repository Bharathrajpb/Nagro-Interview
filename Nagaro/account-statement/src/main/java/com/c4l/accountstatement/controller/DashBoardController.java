package com.c4l.accountstatement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DashBoardController {
	
	@GetMapping("/login")
    public String login() {
        return "/login";
    }
	
	@GetMapping("/")
    public String root() {
        return "login";
    }

	@GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

	@GetMapping("/get-statement")
    public String getStatement(){
        return "get_statement";
    }

	@GetMapping("/get-statement-daterange")
    public String getStatementDaterange(){
        return "get_statement_daterange";
    }

	@GetMapping("/get-statement-amountrange")
    public String getStatementAmountrange(){
        return "get_statement_amountrange";
    }
}
