package com.c4l.accountstatement.controller;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DashBoardControllerTest {

    private DashBoardController controller = new DashBoardController();

    @Test
    void testShouldReturnCorrectViews() {
        assertEquals("/login", controller.login());
        assertEquals("login", controller.root());
        assertEquals("dashboard", controller.dashboard());
        assertEquals("get_statement", controller.getStatement());
        assertEquals("get_statement_daterange", controller.getStatementDaterange());
        assertEquals("get_statement_amountrange", controller.getStatementAmountrange());
    }
}