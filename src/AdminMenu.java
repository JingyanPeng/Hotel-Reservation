import model.*;
import api.AdminResource;
import api.HotelResource;

import java.util.Calendar;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminMenu {

    /**
     *      To display the admin menu for user to choose.
     */
    public static void displayAdminMenuOptions(){
        System.out.println("_____________________Admin Menu_____________________");
        System.out.println("1. See all customers");                                     // done
        System.out.println("2. See all rooms");                                         // done
        System.out.println("3. See all reservations");                                  // done
        System.out.println("4. Add a room");                                            // done
        System.out.println("5. Back to Main Menu");                                     // done
        System.out.println("6. Add Test Data");                                         // done
        System.out.println("____________________________________________________");
        System.out.println("Please select a number for the admin menu option");
    }


    /**
     *      To control the admin menu options.
     */
    public static boolean adminOptions(Scanner scanner, Integer selection){
        boolean keepRunningAdmin = true;
        switch (selection) {
            case 1 -> seeAllCustomers();
            case 2 -> seeAllRooms();
            case 3 -> seeAllReservations();
            case 4 -> addRooms(scanner);
            case 5 -> keepRunningAdmin = false;
            case 6 -> addTestData();
            default -> System.out.println("__Please enter a number between 1 and 6__");
        }
        return keepRunningAdmin;
    }

    /**
     * 1.   See All Customers
     */
    private static void seeAllCustomers(){
        //System.out.println("see all customers");
        Collection<Customer> allCustomers = AdminResource.getInstance().getAllCustomers();
        if (allCustomers.isEmpty()){
            System.out.println("There is no customer");
        }else {
            for(Customer customer : allCustomers){
                System.out.println(customer.toString());
            }
        }
        System.out.println();
    }

    /**
     * 3.   See All Reservations
     */
    private static void seeAllReservations(){
        //System.out.println("see all reservations");
        AdminResource.getInstance().displayAllReservations();
    }

    /**
     * 2.   See All Rooms
     */
    private static void seeAllRooms(){
        //System.out.println("see all rooms");
        Collection<IRoom> allRooms = AdminResource.getInstance().getAllRooms();
        if (allRooms.isEmpty()){
            System.out.println("There is no room");
        }else {
            for(IRoom room : allRooms){
                System.out.println(room.toString());
            }
        }
        System.out.println();
    }

    //Check the valid input
    //default can be accessed within the package
    static void checkYesOrNoValid(String string){
        if(!( string.equalsIgnoreCase("y") || string.equalsIgnoreCase("n") || string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("no"))){
            throw new InputMismatchException("__Please enter y (Yes) or n (No)__");
        }
    }

    private static void check3DigitNumber(String number){
        int number3Digit = Integer.parseInt(number);
        if (number3Digit < 0 || number3Digit > 999){
            throw new InputMismatchException("__Please enter a 3 digits number as the room's ID__");
        }
    }
    // or Using RegEx for checking 3-digit-number


    /**
     * 4.   Add Rooms
     *      add a room + keep adding?
     */
    private static void addRooms(Scanner scanner){
        boolean keepAddingRooms = true;
        addRoom(scanner);
        while (keepAddingRooms){
            System.out.println("Would you like add another room? y/n");
            try{
                String yORn = scanner.nextLine();
                checkYesOrNoValid(yORn);
                if (yORn.equalsIgnoreCase("y") || yORn.equalsIgnoreCase("yes")){
                    addRoom(scanner);
                } else if (yORn.equalsIgnoreCase("n") || yORn.equalsIgnoreCase("no")) {
                    keepAddingRooms = false;
                }
            }catch (InputMismatchException ex){
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }


    private static void addRoom(Scanner scanner){
        /*
         * 1)roomNumber -> 3-digit-number -> not exist
         * 2)roomPrice -> positive
         * 3)roomType -> 1 or 2
         */
        //System.out.println("add a room");
        //get room number
        boolean validRoomNumber = false;
        String roomNumber = null;
        while (!validRoomNumber){
            try{
                System.out.println("Enter room number");
                roomNumber = scanner.nextLine();
                //3-digit-number or not
                check3DigitNumber(roomNumber);
                //exist or not
                IRoom roomExist = HotelResource.getInstance().getRoom(roomNumber);
                if (roomExist == null){
                    validRoomNumber = true;
                }else {
                    System.out.println("__That room number already exists please choose another one__");  //Enter y/n to update it or not
                }
            }catch (NumberFormatException ex1){
                System.out.println("__Please enter a 3 digits NUMBER as the room's ID__");
            }catch (InputMismatchException ex2){
                System.out.println(ex2.getLocalizedMessage());
            }
        }
        //get room price
        boolean validPrice = false;
        double roomPrice = 0.00;
        while (!validPrice){
            try{
                System.out.println("Enter price per night");
                roomPrice = Double.parseDouble(scanner.nextLine());
                if (roomPrice >= 0){
                    validPrice = true;
                }else {
                    System.out.println("__The price cannot be negative__");
                }
            }catch (NumberFormatException ex){
                System.out.println("__Please enter a valid price for the room__");
            }
        }

        //get room type
        boolean validType = false;
        RoomType roomType = null;
        while (!validType){
            try {
                System.out.println("Enter room type (1 for single bed, 2 for double bed)");
                int roomTypeNumber = Integer.parseInt(scanner.nextLine());
                if (roomTypeNumber == 1){
                    roomType = RoomType.SINGLE;
                    validType = true;
                } else if (roomTypeNumber == 2) {
                    roomType = RoomType.DOUBLE;
                    validType = true;
                } else {
                    System.out.println("__Please enter 1 or 2__");
                }
            }catch (Exception ex){
                System.out.println("__Please enter 1 or 2__");
            }
        }

        if(roomPrice!=0){
            //Create room
            Room newRoom1 = new Room(roomNumber, roomPrice, roomType);
            AdminResource.getInstance().addRoom(newRoom1);
        }else {
            Room newRoom2 = new FreeRoom(roomNumber, roomPrice, roomType);
            AdminResource.getInstance().addRoom(newRoom2);
        }

    }

    private static boolean addTestData() {
        if (AdminResource.getInstance().getCustomer("j@gmail.com") != null){
            System.out.println("You have added test data. Please do not add records again.");
            return false;
        }
        AdminResource.getInstance().addRoom(new Room("100", 135.0, RoomType.SINGLE));
        AdminResource.getInstance().addRoom(new Room("200", 125.0, RoomType.DOUBLE));
        AdminResource.getInstance().addRoom(new FreeRoom("300", 150.0, RoomType.SINGLE));
        AdminResource.getInstance().addRoom(new FreeRoom("400", 125.0, RoomType.DOUBLE));

        HotelResource.getInstance().createACustomer("j@gmail.com", "jenny", "floyd");
        HotelResource.getInstance().createACustomer("k@gmail.com", "katrina", "kai");
        HotelResource.getInstance().createACustomer("h@gmail.com", "hana", "potter");

        Calendar checkInDate = Calendar.getInstance();
        Calendar checkOutDate = Calendar.getInstance();
        checkInDate.set(2023, 1, 20, 12, 0);
        checkOutDate.set(2023, 1, 25, 11, 59);
        IRoom room1 = HotelResource.getInstance().getRoom("100");
        HotelResource.getInstance().BookARoom("k@gmail.com", room1, checkInDate.getTime(), checkOutDate.getTime());


        checkInDate.set(2023, 0, 20, 12, 0);
        checkOutDate.set(2023, 0, 25, 11, 59);
        HotelResource.getInstance().BookARoom("h@gmail.com", room1, checkInDate.getTime(), checkOutDate.getTime());


        checkInDate.set(2023, 1, 1, 12, 0);
        checkOutDate.set(2023, 2, 25, 11, 59);
        IRoom room2 = HotelResource.getInstance().getRoom("200");
        HotelResource.getInstance().BookARoom("h@gmail.com", room2, checkInDate.getTime(), checkOutDate.getTime());

        System.out.println("You have added some customers, rooms and reservations records.");
        return true;
    }


}
