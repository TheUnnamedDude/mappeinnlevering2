package no.kevin.innlevering1;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CarRentalManager {
    private final static String RENTED_FORMAT = "%s har leid %s.";
    private final static String RETURNED_FORMAT = "%s har levert inn %s.";
    private final static String NO_AVAILABLE_CAR_FORMAT = "%s prøvde å leie en bil, men det var ingen ledige.";
    private final ReentrantLock lock = new ReentrantLock();

    private Logger logger;
    private ArrayList<Car> carsForRent = new ArrayList<>();
    private HashMap<Car, Customer> leasingInfo = new HashMap<>();

    public CarRentalManager(Logger logger, Car... cars) {
        carsForRent.addAll(Arrays.asList(cars));
        this.logger = logger;
    }

    public Car lease(Customer customer) {
        lock.lock();
        try {
            Car car = findCar();

            if (car == null) {
                logger.info(String.format(NO_AVAILABLE_CAR_FORMAT, customer.getName()));
            } else {
                car.setRented(true);
                leasingInfo.put(car, customer);

                logger.info(String.format(RENTED_FORMAT, customer.getName(), car.getLicenseNumber()));
                printLeasingInfo();
            }

            return car;
        } finally {
            lock.unlock();
        }
    }

    public void turnIn(Car car) {
        lock.lock();
        try {
            car.setRented(false);
            Customer customer = leasingInfo.remove(car);

            logger.info(String.format(RETURNED_FORMAT, customer.getName(), car.getLicenseNumber()));
            printLeasingInfo();
        } finally {
            lock.unlock();
        }
    }

    private Car findCar() {
        return carsForRent.stream()
                .filter(car -> !car.isRented())
                .findFirst()
                .orElse(null);
    }

    private void printLeasingInfo() {
        logger.info("*********** Status for utleiebilene ***********");
        logger.info(generateLeasingInfoString());
        logger.info("***************** Info slutt. *****************");
    }

    private String generateLeasingInfoString() {
        return carsForRent.stream()
                .map(car -> {
                    if (car.isRented()) {
                        return car.getLicenseNumber() + " - utleid til " + leasingInfo.get(car).getName();
                    } else {
                        return car.getLicenseNumber() + " - ledig";
                    }
                })
                .collect(Collectors.joining(", "));
    }
}
