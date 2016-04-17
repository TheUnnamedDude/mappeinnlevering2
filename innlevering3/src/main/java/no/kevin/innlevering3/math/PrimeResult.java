package no.kevin.innlevering3.math;

/**
 * Represent the possible outcomes when parsing and checking a {@link String}
 */
public enum PrimeResult {
    /**
     * The number is a prime
     */
    PRIME("primeresult.prime"),
    /**
     * The number is not a prime
     */
    NOT_PRIME("primeresult.not_prime"),
    /**
     * The passed {@link String} is not a valid number
     */
    INVALID_INPUT("primeresult.invalid"),
    /**
     * No input
     */
    NO_INPUT("primeresult.no_input");

    private String propertyKey;

    PrimeResult(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    /**
     * Get the {@link String} used to get the format from the locale.properties file
     * @return a {@link String} representing the key for getting a value from the locale.properties file
     */
    public String getPropertyKey() {
        return propertyKey;
    }
}
