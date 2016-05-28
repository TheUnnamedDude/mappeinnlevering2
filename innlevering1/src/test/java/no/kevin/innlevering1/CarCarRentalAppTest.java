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
        carRentalApp = new CarRentalApp();
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

        carRentalApp.start();

        verify(executorMock, atLeast(5)).execute(any(Customer.class));
    }

    @Test
    public void testReadCustomerInfo() throws IOException {
        when(readerMock.readLine()).thenReturn("name1");

        assertEquals("name1", carRentalApp.readCustomerInfo().getName());
    }
}
