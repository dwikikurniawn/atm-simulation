package com.dwiki.atmsimulation.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

@Controller
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ NumberFormatException.class })
    @GetMapping("/error=400")
    public ModelAndView handleIllegalInput(NumberFormatException e, HttpSession session) {
        ModelAndView model = new ModelAndView("error-page");
        model.addObject("code", "400");
        model.addObject("message", "Error: " + e.getMessage());
        return model;
    }

    @ExceptionHandler({
            EntityNotFoundException.class,
    })
    @GetMapping("/error=404")
    public ModelAndView handleEmptyDataResult(Exception e) {
        ModelAndView model = new ModelAndView("error-page");
        model.addObject("code", "404");
        model.addObject("message", "Error: " + e.getMessage());
        return model;
    }
}
