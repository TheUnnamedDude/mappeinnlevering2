package no.kevin.innlevering1;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CustomerTest {
    //TODO: Add leaseCar test

    @Test
    public void testFixedDelay() throws Exception {
        Customer customer = new Customer("name1", null, 1234);
        assertEquals(1234, customer.getRandomInterval(0));
    }

    @Test
    public void testMaxInterval() throws Exception {
        Customer customer = new Customer("name1", null, 0);
        assertTrue(customer.getRandomInterval(1) <= 1000);
        assertTrue(customer.getRandomInterval(1) <= 1000);
        assertTrue(customer.getRandomInterval(1) <= 1000);
        assertTrue(customer.getRandomInterval(1) <= 1000);
    }

    @Test
    public void testLease() throws Exception {
        CarRentalManager manager = mock(CarRentalManager.class);
        Customer customer = spy(new Customer("name1", manager, 1));
        customer.leaseCar();
        verify(manager, times(1)).lease(customer);
        verify(customer, times(1)).randomWaitTime(anyInt());
    }

    @Test
    public void testTurnIn() {
        CarRentalManager manager = mock(CarRentalManager.class);
        Customer customer = spy(new Customer("name1", manager, 1));
        when(manager.lease(any())).thenReturn(new Car("AB12345"));
        customer.leaseCar();
        verify(customer, times(1)).randomWaitTime(anyInt());
        customer.turnIn();

        verify(customer, times(2)).randomWaitTime(anyInt());

        verify(manager, times(1)).turnIn(any());
    }
}
