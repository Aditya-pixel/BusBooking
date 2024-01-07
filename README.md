**Overview**

The Bus Booking System is a comprehensive project that facilitates the management of bus-related operations, catering to both administrators and users. The system is designed with distinct roles and functionalities for Admin and User, providing features for bus management, route creation, seat availability, booking, and cancellation.

### **User Roles:**

### Admin:

1. **Manage Bus Details:**
    - Add, update, and delete information related to buses.
    - Bus details include Bus Name, Total Number of Seats, Current Occupancy, and Available Days of Operation for each bus.
2. **Bus Route Creation:**
    - Create routes for buses, specifying the source and destination.

### User:

1. **Browse Available Buses:**
    - Retrieve information on the number of buses available between the source and destination.
    - Display the distance and Estimated Time of Arrival (ETA) for each bus.
2. **Check Seat Availability:**
    - View the number of seats available on a specific bus.
3. **Seat Booking:**
    - Reserve a seat on a selected bus.
    - Users are presented with buses located near them first.
4. **Cancel Seat Booking:**
    - Cancel a previously booked seat on the bus.

### **Constraints and UI Features:**

- **Seat Availability Color Codes:**
    - Green (60% Full or Less): Seats displayed in green when occupancy is 60% or less.
    - Yellow (60% - 90% Full): Seats displayed in yellow when occupancy is between 60% and 90%.
    - Red (90% - 100% Full): Seats displayed in red when occupancy is between 90% and 100%.
- **Concurrency Control:**
    - Implement measures to ensure that two users cannot concurrently book the same seat.

### **Implementation Components:**

1. **Database Design:**
    - Utilizes MySQL database to store information about buses, routes, seat bookings, and users.
2. **Java Entities and Repositories:**
    - Java entities (Bus, BusRoute, SeatBooking, User) mapped to database tables using Java Persistence API (JPA).
    - Repositories (BusRepository, BusRouteRepository, SeatBookingRepository, UserRepository) define database operations.
3. **Services:**
    - Service classes (BusService, BusRouteService, SeatBookingService, UserService) encapsulate business logic.
    - SeatBookingService handles seat cancellations and updates bus occupancy.
4. **Command Line UI:**
    - AdminCommandLineUI provides a command-line interface for admin operations (bus management, route creation).
    - UserCommandLineUI provides a command-line interface for user operations (browsing, booking, canceling seats).
5. **Security:**
    - User authentication with a username and password, where password input is masked.
6. **Color-Coded UI:**
    - Seat availability displayed with color-coded indicators for a quick assessment.
7. **BusRepository Configuration:**
    - A configuration class initializes the BusRepository bean.
8. **Concurrency Control:**
    - Measures implemented to prevent concurrent seat bookings.
9. **BusRoute Integration:**
    - BusRouteService integrated with existing code to handle bus route operations.

### **Running the Application:**

1. **Spring Boot Application:**
    - Main class: **`SpringDataBusBookingApplication`**
    - Run the application, which initializes the Spring Boot context.
2. **Roles Separation:**
    - Upon startup, prompt the user to choose between Admin and User roles.
    - Based on the selection, the respective command-line UI is initiated.
3. **Interactive UI:**
    - Users (Admin and User) interact with the system through the command-line interface, executing various functionalities.
4. **Database Interaction:**
    - Database operations performed through JPA repositories.
5. **Concurrency Handling:**
    - Ensure that concurrency measures are effective in preventing simultaneous seat bookings.

This Bus Booking System provides a robust solution for managing bus-related activities, offering a user-friendly interface for both administrators and users. The implemented features encompass all aspects of bus booking, ensuring a seamless and secure experience for users.

## Tech Stack Used

For the backend, the system leverages a powerful tech stack to ensure optimal performance and scalability. Key components include:

- **Java and Spring Boot**: Employed for backend development, providing a robust and scalable foundation.
- **MySQL Database**: Act as a SQL database wrapper using Spring Boot, facilitating efficient storage.

## Various features

### Authentication

Implementing robust user authentication is crucial to ensure secure access to the Bus Booking System. Below are key steps and considerations for implementing authentication in the project:

1. **User Entity and Repository:**
    - Created a **`User`** entity to represent user information (username, password, roles, etc.).
    - Implemented a **`UserRepository`** extending **`JpaRepository`** for database interactions.
2. **Role Based Access**

### Role Based Access

To ensure security and access control in the Bus Booking System, a separation between the User and Admin roles is implemented. Below are the key steps and considerations for role-based access control:

1. **User Entity and Repository:**
    - A `User` entity is created to represent user information, including username, password, and roles.
    - The `UserRepository` extends `JpaRepository` for database interactions.
2. **Authentication and Authorization:**
    - During system startup, users are prompted to choose between the Admin and User roles.
    - Based on the selection, the respective command-line UI is initiated.
3. **User Interface Differentiation:**
    - The command-line interface provides distinct functionalities and operations for each role.
    - Admins have access to administrative tasks such as bus management and route creation.
    - Users can browse available buses, check seat availability, book seats, and cancel bookings.
4. **Security Measures:**
    - User authentication is implemented using a username and password combination, with password input masked for security.
    - Access to sensitive functionalities and data is restricted based on user roles.

By separating the User and Admin roles and implementing appropriate authentication and authorization mechanisms, the Bus Booking System ensures secure access and controlled functionality based on user roles.

### Cost Estimation

### **Time Complexity Analysis:**

### 1. **BusRouteService - getETAandDistance:**

- Time Complexity: O(1) (constant time)
- The method retrieves the estimated time of arrival (ETA) and distance for a given bus route. Assuming this information is precomputed or obtained from an external API, the time complexity is constant.

### 2. **BusService - getAvailableBuses:**

- Time Complexity: O(n)
- The method retrieves a list of available buses between a source and destination. The complexity depends on the number of buses in the system.

### 3. **SeatBookingService - bookSeat:**

- Time Complexity: O(1) (constant time)
- Booking a seat involves updating the seat occupancy information. With proper indexing, this operation can be achieved in constant time.

### 4. **AdminCommandLineUI - addBus:**

- Time Complexity: O(1) (constant time)
- Adding a bus involves inserting a new record in the Bus table. Assuming the database insert operation is constant time, the overall complexity is constant.

### **Space Complexity Analysis:**

### 1. **BusRoute Entity:**

- Space Complexity: O(1) (constant space)
- Each bus route object stores a fixed set of attributes (source, destination, distance, ETA), resulting in constant space.

### 2. **Bus Entity:**

- Space Complexity: O(n) (linear space)
- The space required for storing bus information grows linearly with the number of buses in the system.

### 3. **SeatBooking Entity:**

- Space Complexity: O(n) (linear space)
- The space required for storing seat booking information grows linearly with the number of booked seats.

### 4. **User Entity:**

- Space Complexity: O(n) (linear space)
- The space required for storing user information grows linearly with the number of registered users.

### 

### **Object-Oriented Programming Language (OOPS)**

 Object-Oriented Programming (OOP) concepts are likely utilized to structure and design the code in a modular and maintainable manner. Here are some common OOP concepts that are applied:

development of the project.

1. **Encapsulation:**
    - **Example:** Using private access modifiers for class fields and methods to hide the internal details of classes.
2. **Abstraction:**
    - **Example:** Defining abstract classes or interfaces, such as the **`Bus`** and **`BusRoute`** classes, to represent common behaviors.
3. **Inheritance:**
    - **Example:** Extending classes to inherit properties and behaviors, like having a base class **`User`** with derived classes **`Admin`** and **`RegularUser`** for different user roles.
4. **Polymorphism:**
    - **Example:** Method overriding, where a method in a derived class (e.g., **`User`** class) provides a specific implementation of a method declared in its base class.
5. **Association:**
    - **Example:** Representing relationships between different entities, such as the association between **`Bus`** and **`BusRoute`** entities.
6. **Composition:**
    - **Example:** Composing complex objects from simpler ones, such as a **`Bus`** object composed of **`Seat`** objects.
7. **Interfaces:**
    - **Example:** Defining interfaces like **`IBusService`** to declare a contract for classes implementing specific functionalities.
8. **Dependency Injection:**
    - **Example:** Passing dependencies (like repositories or services) as parameters to class constructors, enhancing flexibility and testability.
9. **Encapsulation of State:**
    - **Example:** Using getter and setter methods to control access to the internal state of objects.
10. **Overloading:**
    - **Example:** Defining multiple methods with the same name but different parameter lists, such as having overloaded constructors in the **`Bus`** class.

### **Trade-offs in the System**

Since this was created in just less than a day there are bound to be some trade offs.

- **UI Choice**: A CLI-style UI was chosen over a full-fledged browser UI due to the simplicity of operations.
- **DataBase Selection**: Selected MySQL for the seamless integration with JPA, and to verify the generated queries.
- **Booking Seat:** Booking seat takes O(n) time, because first the available seat is searched, it also makes multiple calls to the db.

### **Error and Exception Handling**

- **Try-Catch Blocks**: Implemented at critical operations to handle potential issues.
- **Comprehensive Error Handling**: Covers parsing and various aspects to ensure robust application performance.

## **Backend Installation**

### **Prerequisites**

- Java Development Kit (JDK) 17 or higher
- Maven

### **Steps**

1. **Clone the repository:**

```bash
git clone <[https://github.com/PreyankM/FloorPlanManagementSystem.git](https://github.com/Aditya-pixel/BusBooking.git)>
cd BusBooking

```

1. **Build and Run the Backend:**

```bash
cd spring-data-bus-booking
mvn clean install
mvn spring-boot:run

```

The backend will run on **`http://localhost:8080`**.

## 

## DataBase Snapshot
1. Tables in the MySql DataBase

-![image](https://github.com/Aditya-pixel/BusBooking/assets/54276687/8647c54e-4c49-4f8b-9906-5713d09d8300)

2. Snapshot of Bus Table

-![image](https://github.com/Aditya-pixel/BusBooking/assets/54276687/3b269750-907a-4aaa-93fb-39a48ede7ff8)

3. Snapshot of Bus Route Table

-![image](https://github.com/Aditya-pixel/BusBooking/assets/54276687/ee6288bc-e462-4c87-a0e4-13cdeff947b9)


## Sample Output ScreenShots
1. Choose the Role (Admin/User)

   
-![image](https://github.com/Aditya-pixel/BusBooking/assets/54276687/f002c11d-dc93-4772-bfe6-f2ff6e9da448)

2. If Admin chosen, the admin panel is shown
   
-![image](https://github.com/Aditya-pixel/BusBooking/assets/54276687/0428f7ce-09ae-4708-a240-1e358264b7e2)


3. Starting with Add Bus:

-![image](https://github.com/Aditya-pixel/BusBooking/assets/54276687/cce0e430-8cbb-4510-90e0-90818e24b2b9)


4. Now updating the Bus Details

-![image](https://github.com/Aditya-pixel/BusBooking/assets/54276687/42fe7e3d-28d2-4c95-9b03-21769d7963be)


5. Deleting a Bus

-![image](https://github.com/Aditya-pixel/BusBooking/assets/54276687/f0fc0f1a-ac99-4b09-b4b2-5dc8b2b1e457)


6. Creating Bus Route

-![image](https://github.com/Aditya-pixel/BusBooking/assets/54276687/5ba264b4-18b5-4767-8576-0421e98e078d)


7. Registering a new user

-![image](https://github.com/Aditya-pixel/BusBooking/assets/54276687/f535e7d2-931d-408d-9f6e-d80013d04c53)


8. Show all buses from source to destination

-![image](https://github.com/Aditya-pixel/BusBooking/assets/54276687/311a8e6c-b8ec-4722-a6ce-36efbd00a432)


9. Checking availibility, along with the color coding

-![image](https://github.com/Aditya-pixel/BusBooking/assets/54276687/9f00e8d1-6879-4c3c-8579-5a369dbe8ce3)


10. Booking seat

-![image](https://github.com/Aditya-pixel/BusBooking/assets/54276687/07e8e2f1-2841-4c77-a825-253e644d1d07)



11. Cancelling the seat

-![image](https://github.com/Aditya-pixel/BusBooking/assets/54276687/bddb89f2-7b30-494a-a01f-2b793c2eebe3)
