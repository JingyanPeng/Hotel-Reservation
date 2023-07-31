import model.Customer;
import model.IRoom;
import model.Reservation;
import model.RoomType;
import api.AdminResource;
import api.HotelResource;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {

    public static void displayMainMenuOptions(){
        // To display the main menu for user to choose.
        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println("_____________________Main Menu______________________");
        System.out.println("1. Find and reserve a room");                               // done
        System.out.println("2. See my reservations");                                   // done
        System.out.println("3. Create an account");                                     // done
        System.out.println("4. Admin");                                                 // done
        System.out.println("5. Exit");                                                  // done
        System.out.println("____________________________________________________");
        System.out.println("Please select a number for the main menu option");
    }

    public static boolean mainOptions(Scanner scanner, Integer selection){
        // To control the main menu options.
        boolean keepRunning = true;
        switch (selection) {
            case 1 -> findAndReserveARoom(scanner);
            case 2 -> seeMyReservations(scanner);
            case 3 -> createAnAccount(scanner);
            case 4 -> runAdminMenu(scanner);
            case 5 -> {
                System.out.println("You have exited the system");
                keepRunning = false;
            }
            default -> System.out.println("__Please enter a number between 1 and 5__");
        }
        return keepRunning;
    }

    //only check the number format, not check whether it is the real date
    private static boolean checkDateFormat(String date){
        String dateRegex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[0-9])/([0-9]{4})$";
        Pattern pattern = Pattern.compile(dateRegex);
        return pattern.matcher(date).matches();
    }
    private static boolean checkEmailFormat(String email){
        String emailRegex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

//    private static void checkYesOrNoValid(String string){
//        if(!( string.equalsIgnoreCase("y") || string.equalsIgnoreCase("n") || string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("no"))){
//            throw new InputMismatchException("__Please enter y (Yes) or n (No)__");
//        }
//    }

    private static int[] convertStringToIntArray(String formatDate){
        String[] strArray = formatDate.split("/");
        int[] intArray = new int[strArray.length];
        for(int i = 0; i < strArray.length; i++){
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        return intArray;
    }


    private static void findAndReserveARoom(Scanner scanner){
        /*
         * 1)checkInDate -> RegEx check the date pattern
         * 2)checkOutDate
         * 3)bookARoom
         */
        //System.out.println("find and reserve a room");
        boolean validDates = false;
        Calendar checkInDate = Calendar.getInstance();
        Calendar checkOutDate = Calendar.getInstance();
        while(!validDates){
            System.out.println("Enter CheckIn Date mm/dd/yyyy example 02/01/2023");
            String checkInDateString = scanner.nextLine();
            System.out.println("Enter CheckOut Date mm/dd/yyyy example 02/21/2023");
            String checkOutDateString = scanner.nextLine();
            validDates = checkDateFormat(checkInDateString) && checkDateFormat(checkOutDateString);
            if(!validDates){
                System.out.println("__Please check the dates' format__");
            }else {
                int[] checkInDateInts = convertStringToIntArray(checkInDateString);
                checkInDate.set(checkInDateInts[2], checkInDateInts[0] - 1, checkInDateInts[1], 12, 0);
                int[] checkOutDateInts = convertStringToIntArray(checkOutDateString);
                checkOutDate.set(checkOutDateInts[2], checkOutDateInts[0] - 1, checkOutDateInts[1],11,59);
            }
            if(checkInDate.after(checkOutDate)){
                validDates = false;
                System.out.println("__CheckIn Date should be before CheckOut Date__");
            }
        }
        Date inDate = checkInDate.getTime();
        Date outDate = checkOutDate.getTime();
        boolean askingFree = false;
        boolean isFree = true;
        while (!askingFree){
            System.out.println("Do you want a free room or paid room? y/n");
            System.out.println("yes for free room, no for paid room");
            try{
                String yORn = scanner.nextLine();
                AdminMenu.checkYesOrNoValid(yORn);
                if (yORn.equalsIgnoreCase("y") || yORn.equalsIgnoreCase("yes")){
                    isFree = true;
                } else {
                    isFree = false;
                }
                askingFree = true;
            }catch (InputMismatchException ex){
                System.out.println(ex.getLocalizedMessage());
            }
        }

        Collection<IRoom> roomList = HotelResource.getInstance().findARoom(inDate, outDate, isFree);
        if (roomList.isEmpty()){
            System.out.println("There is no room available");
            System.out.println("Let's search for recommended rooms");
            System.out.println("It will add at most seven days to the original checkin and checkout dates to see the availabilities");
            Calendar checkIn = Calendar.getInstance();
            Calendar checkOut = Calendar.getInstance();
            checkIn.setTime(inDate);
            checkOut.setTime(outDate);
            int i = 0;
            while (i < 7 && roomList.isEmpty()){
                checkIn.add(Calendar.DAY_OF_MONTH, 1); // add 7 days to original date
                checkOut.add(Calendar.DAY_OF_MONTH, 1);
                inDate = checkIn.getTime();
                outDate = checkOut.getTime();
                roomList.addAll(HotelResource.getInstance().findARoom(inDate, outDate, isFree));
                i ++;
            }
            if(!roomList.isEmpty()){
                SimpleDateFormat myFormat = new SimpleDateFormat("E MMM dd yyyy");
                System.out.println("Recommended CheckIn Date: " + myFormat.format(inDate));
                System.out.println("Recommended CheckOut Date: " + myFormat.format(outDate));
            }
        }
        if(roomList.isEmpty()){
            System.out.println("There is no room available");
        }else {
            System.out.println("Rooms available:");
            for(IRoom room : roomList){
                System.out.println(room.toString());
            }
            boolean keepBooking = true;
            while (keepBooking){
                System.out.println("Do you have an account with us? y/n");
                try{
                    String yORn = scanner.nextLine();
                    AdminMenu.checkYesOrNoValid(yORn);
                    if (yORn.equalsIgnoreCase("y") || yORn.equalsIgnoreCase("yes")){
                        if(makeAReservation(scanner, roomList, inDate, outDate)){
                            keepBooking = false;
                        }
                    } else if (yORn.equalsIgnoreCase("n") || yORn.equalsIgnoreCase("no")) {
                        System.out.println("Please create an account with us first");
                        keepBooking = false;
                    } else {
                        keepBooking = true;
                    }
                }catch (InputMismatchException ex){
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        }
    }

    private static boolean makeAReservation(Scanner scanner, Collection<IRoom> roomList, Date checkInDate, Date checkOutDate){
        boolean validEmail = false;
        String email = "";
        while (!validEmail){
            System.out.println("Enter Email format: name@domain.com");
            email = scanner.nextLine();
            Customer customerExist = AdminResource.getInstance().getCustomer(email);
            if (!checkEmailFormat(email)){                       //email format
                System.out.println("__Check email format__");
            } else if (customerExist == null) {                //email exist (getCustomer != null)
                System.out.println("__That email doesn't exist__");
                return false;
            } else{
                validEmail = true;
            }
        }
        boolean validRoomNumber = false;
        String roomNumber = "";
        IRoom room;
        while (!validRoomNumber){
            System.out.println("What room number would you like to reserve?");
            roomNumber = scanner.nextLine();
            Iterator<IRoom> iterator = roomList.iterator();
            while(iterator.hasNext()){
                if (iterator.next().getRoomNumber().equals(roomNumber)){
                    validRoomNumber = true;
                    break;
                }
                if(!iterator.hasNext()){
                    System.out.println("__Please choose the available room__");
                }
            }
        }
        room = HotelResource.getInstance().getRoom(roomNumber);
        HotelResource.getInstance().BookARoom(email, room, checkInDate, checkOutDate);
        System.out.println("Thank you for your reservation");
        System.out.println("Your reservation details:");
        String customerFirstname = AdminResource.getInstance().getCustomer(email).getFirstName();
        String customerLastname = AdminResource.getInstance().getCustomer(email).getLastName();
        System.out.println(customerFirstname + " " +customerLastname);
        double roomPrice = room.getRoomPrice();
        RoomType roomType = room.getRoomType();
        System.out.println("Room: " + roomNumber + "-" + roomType);
        System.out.println("Price: $" + roomPrice + " per night");
        SimpleDateFormat myFormat = new SimpleDateFormat("E MMM dd yyyy");
        System.out.println("CheckIn Date: " + myFormat.format(checkInDate));
        System.out.println("CheckOut Date: " + myFormat.format(checkOutDate));
        return true;
    }

    private static void seeMyReservations(Scanner scanner){
        //email -> check format -> exist？
        boolean validEmail = false;
        String email;
        while(!validEmail){
            System.out.println("Enter Email format: name@domain.com");
            email = scanner.nextLine();
            Collection<Reservation> myReservations = HotelResource.getInstance().getCustomerReservations(email);
            Customer customerExist = AdminResource.getInstance().getCustomer(email);
            if (!checkEmailFormat(email)){                       //email format
                System.out.println("__Check email format__");
            } else if (customerExist == null) {                //email exist (getCustomer != null)
                System.out.println("__That email doesn't exist__");
                validEmail = true;
            } else{
                validEmail = true;
                for (Reservation myReservation : myReservations) {
                    System.out.println(myReservation);
                }
                if (myReservations.isEmpty()){
                    System.out.println("You don't have any reservations");
                }
            }
        }
    }

    private static void createAnAccount(Scanner scanner){
        /*
         * 1)email -> RegEx check the email pattern -> not exist
         * 2)firstname
         * 3)lastname
         */
        //System.out.println("create an account");
        boolean validEmail = false;
        String email;
        String firstname;
        String lastname;
        while (!validEmail){
            try{
                System.out.println("Enter email");
                email = scanner.nextLine();
                //exist or not
                Customer customerExist = AdminResource.getInstance().getCustomer(email);
                if (customerExist == null){
                    validEmail = true;
                    System.out.println("Enter firstname");
                    firstname = scanner.nextLine();
                    System.out.println("Enter lastname");
                    lastname = scanner.nextLine();
                    //Create an account
                    HotelResource.getInstance().createACustomer(email, firstname, lastname);
                }else {
                    System.out.println("__That email already exists please choose another one__");  //Already exists!
                }
            }catch (IllegalArgumentException ex){
                validEmail = false;
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }

    private static void runAdminMenu(Scanner scanner){
        boolean keepRunningAdmin = true;
        while (keepRunningAdmin){
            try{
                AdminMenu.displayAdminMenuOptions();
                int selectionAdmin = Integer.parseInt(scanner.nextLine());
                keepRunningAdmin = AdminMenu.adminOptions(scanner, selectionAdmin);
                System.out.println();
            }catch (NumberFormatException ex){
                //System.out.println(ex.getLocalizedMessage());
                System.out.println("__Invalid input, Please enter a number__");
            }
        }
    }
}
