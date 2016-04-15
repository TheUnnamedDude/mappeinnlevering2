package no.kevin.innlevering3.math;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApacheMathHelperTest {
    private ApacheMathHelper primeChecker = new ApacheMathHelper();

    @Test
    public void testObviousInValidNumber() throws Exception {
        assertFalse("\"bird\" should not be a valid number", primeChecker.isValidNumber("bird"));
    }

    @Test
    public void testValidNumber() throws Exception {
        assertTrue("\"987654321\" should be a valid number", primeChecker.isValidNumber("9876543210"));
    }

    @Test
    public void testNegativeNumber() throws Exception {
        assertTrue("\"-123\" should be a valid number", primeChecker.isValidNumber("-123"));
    }

    @Test
    public void testMixed() throws Exception {
        assertFalse("\"123f123\" should be a invalid number", primeChecker.isValidNumber("123f123"));
    }

    @Test(expected=NumberFormatException.class)
    public void testResultTooBigNumber() throws Exception {
        primeChecker.getResult("123456789123456789123456789");
    }

    @Test
    public void testPrimeReturnsPRIME() throws Exception {
        assertEquals(PrimeResult.PRIME, primeChecker.getResult("179426263"));
    }

    @Test
    public void testNonPrimeReturnsNON_PRIME() throws Exception {
        assertEquals(PrimeResult.NOT_PRIME, primeChecker.getResult("123456789"));
    }

    @Test
    public void testInvalidNumberReturnsINVALID_INPUT() throws Exception {
        assertEquals(PrimeResult.INVALID_INPUT, primeChecker.getResult("abcd"));
    }
}