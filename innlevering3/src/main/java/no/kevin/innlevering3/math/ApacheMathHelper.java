package no.kevin.innlevering3.math;

import org.apache.commons.math3.primes.Primes;

/**
 * This is the Apache commons math implementation of the MathHelper, it uses the class {@link Primes} to check if the
 * passed number is a prime or not.
 */
public class ApacheMathHelper implements MathHelper {
    public boolean isPrime(int number) {
        return Primes.isPrime(number);
    }
}
