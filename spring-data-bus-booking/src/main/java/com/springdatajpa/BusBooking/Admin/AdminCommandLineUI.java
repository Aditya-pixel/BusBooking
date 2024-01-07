package com.springdatajpa.BusBooking.Admin;

import com.springdatajpa.BusBooking.entity.Bus;
import com.springdatajpa.BusBooking.entity.BusRoute;
import com.springdatajpa.BusBooking.entity.BusRouteService;
import com.springdatajpa.BusBooking.entity.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Console;
import java.util.Optional;
import java.util.Scanner;

@Component
public class AdminCommandLineUI {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123"; // Replace with a secure password in a real system;
    private final BusService busService;

    private final BusRouteService busRouteService;

    @Autowired
    public AdminCommandLineUI(BusService busService, BusRouteService busRouteService) {
        this.busService = busService;
        this.busRouteService = busRouteService;
    }

    public void startAdminPanel() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Admin Password: ");
        String password = hidePasswordInput();

        if (authenticateAdmin(username, password)) {
            System.out.println("Authentication successful. Welcome, Admin!");

            while (true) {
                System.out.println("\nAdmin Panel");
                System.out.println("1. Add Bus");
                System.out.println("2. Update Bus");
                System.out.println("3. Delete Bus");
                System.out.println("4. Create Bus Route");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        addBus();
                        break;
                    case 2:
                        updateBus(scanner);
                        break;
                    case 3:
                        deleteBus(scanner);
                        break;
                    case 4:
                        createBusRoute(scanner);
                        break;
                    case 5:
                        System.out.println("Exiting Admin Panel.");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        } else {
            System.out.println("Authentication failed. Exiting.");
        }
    }

    private String hidePasswordInput() {
        Console console = System.console();
        if (console != null) {
            char[] passwordChars = console.readPassword();
            return new String(passwordChars);
        } else {
            // Fallback for IDE environments
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        }
    }

    private boolean authenticateAdmin(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

    private void addBus() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Bus Name: ");
        String busName = scanner.nextLine();

        System.out.print("Enter Total Number of Seats: ");
        int totalSeats = scanner.nextInt();

        System.out.print("Enter Current Occupancy: ");
        int currentOccupancy = scanner.nextInt();

        System.out.print("Enter Available Days of Operation: ");
        String availableDays = scanner.next();

        // Assuming you have a BusService with a method addBuse
        Bus bus = new Bus(busName, totalSeats, currentOccupancy, availableDays);
        busService.addBus(bus);

        System.out.println("Bus added successfully!" + "\nThe Bus ID is: "+ bus.getBusId());
    }

    private void updateBus(Scanner scanner) {
        System.out.println("\nManage Bus Details:");
        System.out.print("Enter Bus ID to update: ");
        int busId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        Optional<Bus> busOptional = busService.getBusById(busId);
        if (busOptional.isPresent()) {
            // Collect updated bus details from the admin
            System.out.print("Enter new Occupancy: ");
            int newOccupancy = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            System.out.print("Enter new Total Seats: ");
            int newTotalSeats = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            System.out.print("Enter new Available Days: ");
            String newAvailableDays = scanner.nextLine();

            // Create an updated bus object
            Bus updatedBus = new Bus();
            updatedBus.setCurrentOccupancy(newOccupancy);
            updatedBus.setTotalSeats(newTotalSeats);
            updatedBus.setAvailableDays(newAvailableDays);

            // Update the bus using the BusService
            busService.updateBus(busId, updatedBus);
            System.out.println("Bus details updated successfully!");
        } else {
            System.out.println("Bus not found with ID: " + busId);
        }
    }

    private void deleteBus(Scanner scanner) {
        System.out.println("\nDelete Bus:");
        System.out.print("Enter Bus ID to delete: ");
        int busId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        Optional<Bus> busOptional = busService.getBusById(busId);
        if (busOptional.isPresent()) {
            busService.deleteBus(busId);
            System.out.println("Bus deleted successfully!");
        } else {
            System.out.println("Bus not found with ID: " + busId);
        }
    }
    private void createBusRoute(Scanner scanner) {
        System.out.println("\nCreate Bus Route:");

        // Collect details for the new BusRoute
        System.out.print("Enter Bus ID: ");
        int busId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        // Check if the provided busId exists
        if (busService.doesBusExist(busId)) {
            // Fetch the Bus object from the database
            Bus associatedBus = busService.getBusById(busId)
                    .orElseThrow(() -> new RuntimeException("Bus not found with ID: " + busId));

            // Collect other details for the new BusRoute
            System.out.print("Enter Source Location: ");
            String sourceLocation = scanner.nextLine();

            System.out.print("Enter Destination Location: ");
            String destinationLocation = scanner.nextLine();

            System.out.print("Enter Distance: ");
            float distance = scanner.nextFloat();
            scanner.nextLine(); // Consume the newline

            System.out.print("Enter ETA: ");
            int eta = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            // Create a new BusRoute object and associate it with the Bus
            BusRoute newBusRoute = new BusRoute();
            newBusRoute.setBus(associatedBus);
            newBusRoute.setSource(sourceLocation);
            newBusRoute.setDestination(destinationLocation);
            newBusRoute.setDistance(distance);
            newBusRoute.setEta(eta);

            // Save the new BusRoute using BusRouteService
            busRouteService.createBusRoute(newBusRoute);
            System.out.println("Bus Route created successfully!");
        } else {
            System.out.println("Bus not found with ID: " + busId);
        }
    }




    // Remaining methods for managing buses and routes...
}
