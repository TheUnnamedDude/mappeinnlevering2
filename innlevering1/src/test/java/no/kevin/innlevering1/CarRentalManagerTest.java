package no.kevin.innlevering1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarRentalManagerTest {
    @Mock
    private Logger logger;
    private Car defaultCar = new Car("HJ11111");
    @Mock
    private Customer customer;

    @Test
    public void testConstructor() {
        CarRentalManager carRentalManager = new CarRentalManager(logger, defaultCar);
        assertEquals(defaultCar, carRentalManager.lease(customer));
    }

    @Test
    public void testReturnNullIfNoAvailableCars() {
        CarRentalManager carRentalManager = new CarRentalManager(logger, defaultCar);
        assertEquals(defaultCar, carRentalManager.lease(customer));
        assertNull(carRentalManager.lease(customer));
    }

    @Test
    public void testMultipleCars() {
        Car[] cars = new Car[] { defaultCar, new Car("HJ22222"),
                new Car("HJ333333"), new Car("HJ444444"), new Car("HJ55555") };
        Customer customer = mock(Customer.class);
        CarRentalManager carRentalManager = new CarRentalManager(logger, cars);
        assertNotNull(carRentalManager.lease(customer));
        assertNotNull(carRentalManager.lease(customer));
        assertNotNull(carRentalManager.lease(customer));
        assertNotNull(carRentalManager.lease(customer));
        assertNotNull(carRentalManager.lease(customer));
        assertNull(carRentalManager.lease(customer));
    }

    @Test
    public void testTurningIn() {
        CarRentalManager carRentalManager = new CarRentalManager(logger, defaultCar);
        carRentalManager.lease(customer);
        carRentalManager.turnIn(defaultCar);
        assertEquals(defaultCar, carRentalManager.lease(customer));
    }

    @Test
    public void testSetRentedAfterLeasing() {
        CarRentalManager carRentalManager = new CarRentalManager(logger, defaultCar);
        carRentalManager.lease(customer);
        assertTrue(defaultCar.isRented());
        carRentalManager.turnIn(defaultCar);
        assertFalse(defaultCar.isRented());
    }
}
