package no.kevin.innlevering3.controllers;

import no.kevin.innlevering3.TextFormatter;
import no.kevin.innlevering3.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Takes any requests from a normal browser and redirects it to a thymeleaf document
 */
@Controller
public class MvcPrimeCheckerController {
    private final Logger requestLogger = LogManager.getLogger(MvcPrimeCheckerController.class);
    @Autowired
    private MathHelper mathHelper;

    @Autowired
    private TextFormatter textFormatter;

    /**
     * Handles any http request from a normal browser requesting a html document
     * @param number A {@link String} representing the number passed by the browser
     * @param request Used for logging the ip address and other http related things in the access log
     * @return a {@link ModelAndView} object with information about the number, result and a text formatter to format
     *  the number passed, should probably be formatted in the controller instead.
     */
    @RequestMapping(value="/primecheck", produces="text/html")
    public ModelAndView checkPrimeNumber(@RequestParam(value="number",required=false) String number, HttpServletRequest request) {
        requestLogger.info(String.format("[%s] %s: %s, number=\"%s\"", request.getRemoteAddr(), request.getMethod(), request.getRequestURL(), number));
        ModelAndView model = new ModelAndView("primecheck");
        model.addObject("number", number);
        model.addObject("result", mathHelper.getResult(number));
        model.addObject("textFormatter", textFormatter);
        return model;
    }
}
