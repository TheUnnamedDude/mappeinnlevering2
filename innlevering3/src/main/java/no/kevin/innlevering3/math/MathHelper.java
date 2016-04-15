package no.kevin.innlevering3.math;

public interface MathHelper {
    /**
     * Checks if the passed number is a prime
     * @param number the number to check
     * @return if the number is a prime or not
     */
    boolean isPrime(int number);

    /**
     * This checks if the passed string can be parsed as a number,
     * however it does not take into consideration that the number
     * might be too big to fit in a int.
     * @param input the input {@link String} to test
     * @return a boolean representing if the input was valid or not
     */
    default boolean isValidNumber(String input) {
        if (input == null || input.length() <= 0)
            return false;
        char[] chars = input.toCharArray();
        for (int index = chars[0] == '-' ? 1 : 0; index < chars.length; index++) {
            char c = chars[index];
            if (c < '0' || c > '9')
                return false;
        }
        return true;
    }

    /**
     * This will return a result based on the number provided as string, it checks
     * if the {@link String} is a valid number, but it might throw a exception if its too big...
     * @param number a string representing the number to check if its a prime
     * @return a {@link PrimeResult} that represents if the number is valid, a prime or
     * not a prime, it also returns NO_INPUT if the number was null
     */
    default PrimeResult getResult(String number) {
        if (number == null) {
            return PrimeResult.NO_INPUT;
        } else if (!isValidNumber(number)) {
            return PrimeResult.INVALID_INPUT;
        } else if (isPrime(Integer.parseInt(number))) {
            // Should check if the int is too big, but this is a nice way to test
            // the exception handling
            return PrimeResult.PRIME;
        } else {
            return PrimeResult.NOT_PRIME;
        }

        /*try {
            if (mathHelper.isPrime(Integer.parseInt(number))) {
                return PrimeResult.PRIME;
            } else {
                return PrimeResult.NOT_PRIME;
            }
        } catch (NumberFormatException e) {
            // Fall back to catching exceptions to catch too big numbers
            return PrimeResult.INVALID_INPUT;
        }*/
    }
}
