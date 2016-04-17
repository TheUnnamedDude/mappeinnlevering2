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

/**
 * Handles all request that doesnt specify that it accepts a html result
 */
@RestController
public class RestPrimeCheckerController {
    private final Logger requestLogger = LogManager.getLogger(MvcPrimeCheckerController.class);

    @Autowired
    private MathHelper primeChecker;

    /**
     * A method that handles requests that doesnt specify that it accepts a html result
     * @param number a {@link String} that represents the number passed by the http client
     * @param request used to log ip address and other information used to log http requests to access.log
     * @return a {@link PrimeResult} that can be parsed into json/xml
     */
    @RequestMapping(value="/primecheck/{number}")
    public PrimeResult checkPrimePathParam(@PathVariable String number, HttpServletRequest request) {
        requestLogger.info(String.format("[%s] %s: %s, number=\"%s\"", request.getRemoteAddr(), request.getMethod(), request.getRequestURL(), number));
        return primeChecker.getResult(number);
    }

    /**
     * A method that handles requests that doesnt specify that it accepts a html result
     * @param number a {@link String} that represents the number passed by the http client
     * @param request used to log ip address and other information used to log http requests to access.log
     * @return a {@link PrimeResult} that can be parsed into json/xml
     */
    @RequestMapping(value="/primecheck")
    public PrimeResult checkPrimeRequestParam(@RequestParam(value="number", required=false) String number, HttpServletRequest request) {
        requestLogger.info(String.format("[%s] %s: %s, number=\"%s\"", request.getRemoteAddr(), request.getMethod(), request.getRequestURL(), number));
        return primeChecker.getResult(number);
    }
}
