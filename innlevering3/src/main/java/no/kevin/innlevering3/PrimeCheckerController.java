package no.kevin.innlevering3;

import no.kevin.innlevering3.math.PrimeChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrimeCheckerController {
    @Autowired
    private PrimeChecker primeChecker;

    @RequestMapping("/rest/checkprime/{number}")
    public boolean checkPrime(@PathVariable int number) {
        return primeChecker.isPrime(number);
    }
}
