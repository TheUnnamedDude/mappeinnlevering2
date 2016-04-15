package no.kevin.innlevering3;

import no.kevin.innlevering3.math.PrimeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

public class TextFormatter {
    @Autowired
    Environment env;
    @Value("${primeresult.textformat}")
    private String resultFormat;

    public String formatPrimeResult(String number, PrimeResult result) {
        return String.format(resultFormat, number, env.getProperty(result.getPropertyKey()));
    }
}
