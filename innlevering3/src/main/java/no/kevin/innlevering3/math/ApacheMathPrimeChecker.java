package no.kevin.innlevering3.math;

import org.apache.commons.math3.primes.Primes;

public class ApacheMathPrimeChecker implements PrimeChecker {
    public boolean isPrime(int number) {
        return Primes.isPrime(number);
    }
}
