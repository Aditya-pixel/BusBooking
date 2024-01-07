package com.springdatajpa.BusBooking;

import com.springdatajpa.BusBooking.Admin.AdminCommandLineUI;
import com.springdatajpa.BusBooking.Admin.UserCommandLineUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Scanner;

@SpringBootApplication
@EntityScan(basePackages = {"com.springdatajpa.BusBooking.entity"} )
@EnableJpaRepositories(basePackages = {"com.springdatajpa.BusBooking.repository"})
@ComponentScan(basePackages = {"com.springdatajpa.BusBooking"})
public class SpringDataBusBookingApplication implements CommandLineRunner {
	@Autowired
	private AdminCommandLineUI adminCommandLineUI;

	@Autowired
	private UserCommandLineUI userCommandLineUI;

	public static void main(String[] args) {SpringApplication.run(SpringDataBusBookingApplication.class, args);}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Choose a role to run:");
		System.out.println("1. Admin");
		System.out.println("2. User");
		System.out.print("Enter your choice (1 or 2): ");

		int choice = scanner.nextInt();
		scanner.nextLine(); // Consume the newline character

		switch (choice) {
			case 1:
				adminCommandLineUI.startAdminPanel();
				break;
			case 2:
				userCommandLineUI.startUserPanel();
				break;
			default:
				System.out.println("Invalid choice. Exiting.");
		}

		scanner.close();
	}

}
