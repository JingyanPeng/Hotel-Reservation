# Hotel-Reservation
This is an Hotel Reservation Application built in Java. The hotel reservation application will allow customers to find and book a hotel room based on room availability. This project will demonstrate the abilities to design classes using OOP, organize and process data with collections, and use common Java types.

Main Components of the App
The major components of the Hotel Reservation Application will consist of the following:
	1. CLI for the User Interface. We'll use the Command Line Interface (or CLI for the user interface. For this, we'll need to have Java monitor the CLI for user input, so the user can enter commands to search for available rooms, book rooms, and so on.
	2. Java code. The second main component is the Java code itself—this is where we add our business logic for the app.
	3. Java collections. Finally, we'll use Java collections for in-memory storage of the data we need for the app, such as the users' names, room availability, and so on.

<img width="401" alt="image" src="https://github.com/JingyanPeng/Hotel-Reservation/assets/98308172/95d2d261-927e-4c8b-8d8f-ab8afdd0742b">


Application Architecture
Let's talk about the structure or architecture of the application. The app will be separated into the following layers:
	1. User interface (UI), including a main menu for the users who want to book a room, and an admin menu for ad
	2. ministrative functions.
	3. Resources will act as our Application Programming Interface (API) to our UI.
	4. Services will communicate with our resources, and each other, to build the business logic necessary to provide feedback to our UI.
	5. Data models will be used to represent the domain that we're using within the system (e.g., rooms, reservations, and customers).

<img width="397" alt="image" src="https://github.com/JingyanPeng/Hotel-Reservation/assets/98308172/853ab774-a562-4ec5-afa9-2225139519d9">


**User Scenarios**
The application provides four user scenarios:
  - Creating a customer account. The user needs to first create a customer account before they can create a reservation.
  - Searching for rooms. The app should allow the user to search for available rooms based on provided checkin and checkout dates. If the application has available rooms for the specified date range, a list of the corresponding rooms will be displayed to the user for choosing.
  - Booking a room. Once the user has chosen a room, the app will allow them to book the room and create a reservation.
  - Viewing reservations. After booking a room, the app allows customers to view a list of all their reservations.

**Admin Scenarios**
The application provides four administrative scenarios:
  - Displaying all customers accounts.
  - Viewing all of the rooms in the hotel.
  - Viewing all of the hotel reservations.
  - Adding a room to the hotel application.

**Reserving a Room – Requirements**
The application allows customers to reserve a room. Here are the specifics:
  - Avoid conflicting reservations. A single room may only be reserved by a single customer per a checkin and checkout date range.
  - Search for recommended rooms. If there are no available rooms for the customer's date range, a search will be performed that displays recommended rooms on alternative dates. The recommended room search will add seven days to the original checkin and checkout dates to see if the hotel has any availabilities, and then display the recommended rooms/dates to the customer.
    Example: If the customers date range search is 1/1/2020 – 1/5/2020 and all rooms are booked, the system will search again for recommended rooms using the date range 1/8/2020 - 1/12/2020. If there are no recommended rooms, the system will not return any rooms.

**Room Requirements**
  - Room cost. Rooms will contain a price per night. When displaying rooms, paid rooms will display the price per night and free rooms will display "Free" or have a $0 price.
  - Unique room numbers. Each room will have a unique room number, meaning that no two rooms can have the same room number.
  - Room type. Rooms can be either single occupant or double occupant (Enumeration: SINGLE, DOUBLE).

**Customer Requirements**
The application will have customer accounts. Each account has:
  - A unique email for the customer. RegEx is used to check that the email is in the correct format (i.e., name@domain.com(opens in a new tab)).
  - A first name and last name.

**Error Requirements**
The hotel reservation application handles all exceptions gracefully (user inputs included), meaning:
  - No crashing. The application does not crash based on user input.
  - No unhandled exceptions. The app has try and catch blocks that are used to capture exceptions and provide useful information to the user. There are no unhandled exceptions.
