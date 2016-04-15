package no.kevin.innlevering3.controllers;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    private final Logger logger = LogManager.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(Throwable.class)
    public void handleException(Throwable t) {
        logger.catching(Level.ERROR, t);
    }
}
