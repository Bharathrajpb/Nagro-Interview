package com.c4l.accountstatement.Exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class StatementExeptionHandler {

	 @ExceptionHandler(Exception.class) 
	    public ModelAndView reponseMyException(Exception e) {
	        return  createErrorResponse(e);
	    }
	 
	 private static ModelAndView createErrorResponse(Exception e) {
	        ModelAndView model = new ModelAndView();
	        model.addObject("message", e.getMessage());
	        model.addObject("exception", e);
	        model.setViewName("custom_error");
	        return model;
	    }
}
