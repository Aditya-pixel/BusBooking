package com.springdatajpa.BusBooking.Admin;

import com.springdatajpa.BusBooking.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class UserCommandLineUI {

    private final UserService userService;
    private final BusService busService;

    private final BusRouteService busRouteService;

    private final SeatBookingService seatBookingService;

    private int userId;

    @Autowired
    public UserCommandLineUI(UserService userService, BusService busService, BusRouteService busRouteService,
                             SeatBookingService seatBookingService) {
        this.userService = userService;
        this.busService = busService;
        this.busRouteService = busRouteService;
        this.seatBookingService = seatBookingService;
    }


    public void startUserPanel() {
        Scanner scanner = new Scanner(System.in);
            System.out.println("1. Register User");
            System.out.println("2. Authenticate User");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
        int flag = 1;
        while(flag == 1) {
            switch (choice) {
                case 1:
                    registerUser(scanner);
                    flag = 0;
                    break;
                case 2:
                    flag = authenticateUser(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    flag = 0;
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Browse Available Buses");
            System.out.println("2. Check Seat Availability");
            System.out.println("3. Seat Booking");
            System.out.println("4. Cancel Seat Booking");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    browseAvailableBuses(scanner);
                    break;
                case 2:
                    checkSeatAvailability(scanner);
                    break;
                case 3:
                    seatBooking(scanner);
                    break;
                case 4:
                    cancelSeatBooking(scanner);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            int userId = userService.registerUser(username, password);
            System.out.println("User registered successfully. User ID: " + userId);
            this.userId = userId;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int authenticateUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Optional<Integer> userIdOptional = userService.authenticateUser(username, password);

        if (userIdOptional.isPresent()) {
            int userId = userIdOptional.get();
            this.userId = userId;
            System.out.println("Authentication successful. User ID: " + userId);
            return 0;
        } else {
            System.out.println("Authentication failed.");
            return 1;
        }
    }
    private void browseAvailableBuses(Scanner scanner) {
        System.out.print("Enter Source: ");
        String source = scanner.nextLine();

        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine();

        List<BusRoute> matchingRoutes = busRouteService.getRoutesBySourceAndDestination(source, destination);

        if (matchingRoutes.isEmpty()) {
            System.out.println("No buses available for the specified source and destination.");
        } else {
            System.out.println("\nAvailable Buses for " + source + " to " + destination + ":");
            for (BusRoute route : matchingRoutes) {
                Optional<Bus> busOptional = busService.getBusById(route.getBus().getBusId());
                if (busOptional.isPresent()) {
                    Bus bus = busOptional.get();
                    System.out.println("Bus ID: " + bus.getBusId());
                    System.out.println("Bus Name: " + bus.getBusName());
                    System.out.println("Distance: " + route.getDistance() + " km");
                    System.out.println("ETA: " + route.getEta() + " hours");
                    System.out.println("------------------------");
                }
            }
        }
    }

    private void checkSeatAvailability(Scanner scanner) {
        System.out.println("\nCheck Seat Availability:");

        // Collect details for seat availability check
        System.out.print("Enter Bus ID: ");
        int busId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        // Check if the provided busId exists
        if (busService.doesBusExist(busId)) {
            // Fetch the Bus object from the database
            Bus bus = busService.getBusById(busId)
                    .orElseThrow(() -> new RuntimeException("Bus not found with ID: " + busId));

            // Get the number of available seats
            int availableSeats = busService.getAvailableSeats(bus);

            int percent = ((bus.getCurrentOccupancy()*100/bus.getTotalSeats()));
            String colorCode;
            if(percent <= 60) {
                colorCode = "GREEN";
            }
            else if(percent <= 90) {
                colorCode = "YELLOW";
            }
            else{
                colorCode = "RED";
            }

            System.out.print("\u001B[" + getColorCodeANSICode(colorCode) + "m"); // ANSI color code
            System.out.println(availableSeats);
            System.out.print("\u001B[0m");
        } else {
            System.out.println("Bus not found with ID: " + busId);
        }
    }

    private void seatBooking(Scanner scanner) {
        System.out.println("\nSeat Booking:");
        // Collect details for seat booking
        System.out.print("Enter Bus ID: ");
        int busId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        // Check if the provided busId exists
        if (busService.doesBusExist(busId)) {
            try {
                // Attempt to book the next available seat
                Bus bus = busService.getBusById(busId)
                        .orElseThrow(() -> new RuntimeException("Bus not found with ID: " + busId));
                if(busService.getAvailableSeats(bus) <= 0) {
                    System.out.println("No Seat Available!");
                    return;
                }
                int bookedSeatNumber = seatBookingService.bookNextAvailableSeat(busId, this.userId);
                int bookingId = seatBookingService.getBookingIdByUserIdBusIdAndSeatNumber(this.userId, busId, bookedSeatNumber);
                System.out.println("Seat booked successfully! Seat Number: " + bookedSeatNumber + " BookingId: " + bookingId);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Bus not found with ID: " + busId);
        }
    }

    private void cancelSeatBooking(Scanner scanner) {
        System.out.println("\nCancel Seat Booking:");


        System.out.print("Enter Booking ID: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        try {
            // Attempt to cancel the seat booking
            System.out.println("Cancelling seat for the booking: " + bookingId);
            seatBookingService.cancelSeatBookingByBookingId(bookingId);
            System.out.println("Cancelled seat successfully! ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String getColorCodeANSICode(String colorCode) {
        switch (colorCode) {
            case "GREEN":
                return "32"; // ANSI code for green
            case "YELLOW":
                return "33"; // ANSI code for yellow
            case "RED":
                return "31"; // ANSI code for red
            default:
                return "0";  // Default to reset color
        }
    }



}
