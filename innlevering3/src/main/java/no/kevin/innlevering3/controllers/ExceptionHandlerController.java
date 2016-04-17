package no.kevin.innlevering3.controllers;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
/**
 * This class is supposed to catch all exceptions a controller might throw.
 */
public class ExceptionHandlerController {
    private final Logger logger = LogManager.getLogger(ExceptionHandlerController.class);

    /**
     * Handles exceptions caught by the different controllers, this one should catch all the uncaught ones
     * @param t The exception caught
     */
    @ExceptionHandler(Throwable.class)
    public void handleException(Throwable t) {
        logger.catching(Level.ERROR, t);
    }
}
