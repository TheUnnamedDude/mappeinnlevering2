package no.kevin.innlevering1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CarCarRentalAppTest
{
    private CarRentalApp carRentalApp;
    @Mock
    private BufferedReader readerMock;
    @Mock
    private ExecutorService executorMock;
    @Mock
    private Logger loggerMock;

    @Before
    public void setup() {
        carRentalApp = spy(new CarRentalApp());
        inject("reader", carRentalApp, readerMock);
        inject("executor", carRentalApp, executorMock);
        inject("logger", carRentalApp, loggerMock);
    }

    private void inject(String fieldName, Object obj, Object injected) {
        try {
            // Inject two mocks for testing
            Field field = CarRentalApp.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, injected);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStart() throws IOException {
        when(readerMock.readLine()).thenReturn("name1", "name2", "name3", "name4", "name5");

        carRentalApp.waitForUsers();

        verify(executorMock, atLeast(5)).execute(any(Customer.class));
    }

    @Test
    public void testReadCustomerInfo() throws IOException {
        when(readerMock.readLine()).thenReturn("name1");

        assertEquals("name1", carRentalApp.readCustomerInfo().getName());
    }

    @Test
    public void testParseDelay() throws Exception {
        carRentalApp.parseArgs("--delay", "1000");
        when(readerMock.readLine()).thenReturn("name1");
        Customer customer = carRentalApp.readCustomerInfo();
        assertEquals(1000, customer.getRandomInterval(0));
    }

    @Test
    public void testReturnsFalseWithFailedDelayParsing() throws Exception {
        assertFalse(carRentalApp.parseArgs("--delay"));
    }

    @Test
    public void testFileReaderParsing() throws Exception {
        carRentalApp.parseArgs("--file", getClass().getResource("/users.txt").getFile());
        Customer customer = carRentalApp.readCustomerInfo();
        assertEquals("name1", customer.getName());
    }

    @Test
    public void testReturnsFalseWithFailedFileParsing() throws Exception {
        assertFalse(carRentalApp.parseArgs("--file"));
    }

    @Test
    public void testHelpShouldReturnFalse() throws Exception {
        assertFalse(carRentalApp.parseArgs("--help"));
    }

    @Test
    public void testPrintHelpOnFailedArgs() throws Exception {
        doNothing().when(carRentalApp).printHelp();
        carRentalApp.start("--help");
        verify(carRentalApp, times(1)).printHelp();
    }

    @Test
    public void testApplicationInitialized() throws Exception {
        doNothing().when(carRentalApp).waitForUsers();
        carRentalApp.start();
        verify(carRentalApp, times(1)).waitForUsers();
    }

    @Test
    public void testLoggerFormat() throws Exception {
        CarRentalApp.ConsoleFormatter consoleFormatter = new CarRentalApp.ConsoleFormatter();
        assertTrue(consoleFormatter.format(new LogRecord(Level.INFO, "test")).contains("test"));
    }
}
