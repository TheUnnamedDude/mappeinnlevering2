package no.kevin.innlevering1;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;

public class CarRentalApp
{
    private static Logger logger = Logger.getLogger("Rental_service");
    private int fixedDelay = 0;
    private BufferedReader reader;
    private ExecutorService executor = Executors.newCachedThreadPool();
    CarRentalManager carRentalManager = new CarRentalManager(logger,
            new Car("HJ11111"),
            new Car("HJ22222"),
            new Car("HJ33333"),
            new Car("HJ44444"),
            new Car("HJ55555"));

    public static void main(String[] args) {
        CarRentalApp rentalService = new CarRentalApp();
        try {
            if (!rentalService.parseArgs(args)) {
                printHelp();
                return;
            }
        } catch (FileNotFoundException e) {
            logger.info("Failed to find the provided file.");
            printHelp();
            return;
        }
        rentalService.start();
    }

    public CarRentalApp() {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new ConsoleFormatter());
        logger.addHandler(consoleHandler);
    }

    public boolean parseArgs(String... args) throws FileNotFoundException {
        for (int i = 0; i < args.length; i++) { // The reason for not using streams is because I want to be able to skip values.
            final String arg = args[i].toLowerCase();
            if ("--delay".equals(arg)) {
                if (args.length <= i + 1)
                    return false;
                fixedDelay = Integer.parseInt(args[++i]);
            } else if ("--file".equals(arg)) {
                if (args.length <= i + 1)
                    return false;
                reader = new BufferedReader(new FileReader(args[++i]));
            } else if ("--help".equals(arg)) {
                return false;
            }
        }
        return true;
    }

    public void start() {
        if (reader == null) {
            logger.fine("No input provided, defaulting to System.in");
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        try {
            // Could use Arrays.stream(readCustomerInfo(10)).forEach(executor::execute); but I dont see a point.
            for (Customer customer : readCustomerInfo(5)) { // This will read 5 customers, then iterate through them and execute the thread.
                executor.execute(customer); // That sounds horrible!
            }
            for (int i = 0; i < 5; i++) { // After reading the initial customers read 5 more, but not in a blocking way.
                executor.execute(readCustomerInfo());
            }
        } catch (IOException e) {
            logger.info("Failed to read the provided customer info.");
            e.printStackTrace();
        }
    }

    public Customer[] readCustomerInfo(int numberOfCustomers) throws IOException {
        Customer[] customers = new Customer[numberOfCustomers];
        for (int i = 0; i < numberOfCustomers; i++) {
            customers[i] = readCustomerInfo();
        }
        return customers;

    }

    public Customer readCustomerInfo() throws IOException {
        String name = reader.readLine();
        return new Customer(name, carRentalManager, fixedDelay);
    }

    public static void printHelp() {
        System.out.println("java -jar <jarfile> [--help] [--delay <delay in seconds>] [--file <file>]");
    }

    private class ConsoleFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            return "[" + record.getLevel() + "] " +  record.getMessage() + "\n";
        }
    }
}
