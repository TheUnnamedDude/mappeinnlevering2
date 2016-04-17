package no.kevin.innlevering3;

import no.kevin.innlevering3.math.PrimeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

/**
 * This is a class passed to the thymeleaf document so it can create a string thats more suited for displaying in a html
 * document.
 */
public class TextFormatter {
    @Autowired
    Environment env;
    @Value("${primeresult.textformat}")
    private String resultFormat;

    /**
     * Returns a formatted {@link String} for a {@link PrimeResult} and its passed number, the format can be configured
     * with the locale.properties file
     * @param number the number passed from the http client
     * @param result the key to look up the format from
     * @return a formatted {@link String} that can be displayed to the client
     */
    public String formatPrimeResult(String number, PrimeResult result) {
        return String.format(resultFormat, number, env.getProperty(result.getPropertyKey()));
    }
}
