package no.kevin.innlevering3.math;

public enum PrimeResult {
    PRIME("primeresult.prime"),
    NOT_PRIME("primeresult.not_prime"),
    INVALID_INPUT("primeresult.invalid"),
    NO_INPUT("primeresult.no_input");

    private String propertyKey;

    PrimeResult(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyKey() {
        return propertyKey;
    }
}
