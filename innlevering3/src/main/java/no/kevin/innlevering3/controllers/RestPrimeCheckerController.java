package no.kevin.innlevering3.controllers;

import no.kevin.innlevering3.math.MathHelper;
import no.kevin.innlevering3.math.PrimeResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RestPrimeCheckerController {
    private final Logger requestLogger = LogManager.getLogger(MvcPrimeCheckerController.class);

    @Autowired
    private MathHelper primeChecker;

    @RequestMapping(value="/primecheck/{number}")
    public PrimeResult checkPrimePathParam(@PathVariable String number, HttpServletRequest request) {
        requestLogger.info(String.format("[%s] %s: %s, number=\"%s\"", request.getRemoteAddr(), request.getMethod(), request.getRequestURL(), number));
        return primeChecker.getResult(number);
    }

    @RequestMapping(value="/primecheck")
    public PrimeResult checkPrimeRequestParam(@RequestParam(value="number", required=false) String number, HttpServletRequest request) {
        requestLogger.info(String.format("[%s] %s: %s, number=\"%s\"", request.getRemoteAddr(), request.getMethod(), request.getRequestURL(), number));
        return primeChecker.getResult(number);
    }
}
