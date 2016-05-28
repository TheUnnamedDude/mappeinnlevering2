package no.kevin.innlevering1;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class Customer implements Runnable {
    private static final Random RNG = new Random();

    @Getter
    private final String name;
    private final CarRentalManager carRentalService;
    private final int fixedSleepInterval;

    private Car car;

    @Override
    public void run() {
        while (true) {
            leaseCar();
            turnIn();
        }
    }

    public void leaseCar() {
        randomWaitTime(10);
        car = carRentalService.lease(this);
    }

    public void turnIn() {
        if (car != null) {
            randomWaitTime(3);
            carRentalService.turnIn(car);
        }
    }

    public void randomWaitTime(int maxWait) {
        try {
            Thread.sleep(fixedSleepInterval == 0 ? (RNG.nextInt(maxWait) + 1) * 1000 : fixedSleepInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
