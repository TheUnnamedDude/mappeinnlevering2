package no.kevin.innlevering3.math;

import org.apache.commons.math3.primes.Primes;

public class ApacheMathHelper implements MathHelper {
    public boolean isPrime(int number) {
        return Primes.isPrime(number);
    }
}
