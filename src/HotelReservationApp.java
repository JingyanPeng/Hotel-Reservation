import java.util.Scanner;

public class HotelReservationApp {
    public static void main(String[] args){
        boolean keepRunning = true;
        try (Scanner scanner = new Scanner(System.in)){
            while (keepRunning){
                try{
                    MainMenu.displayMainMenuOptions();
                    int selection = Integer.parseInt(scanner.nextLine());
                    keepRunning = MainMenu.mainOptions(scanner, selection);
                    System.out.println();
                }catch (NumberFormatException ex){
                    //System.out.println(ex.getLocalizedMessage());
                    System.out.println("__Invalid input, Please enter a number__");
                }
            }
        }catch (Exception ex){
            System.out.println("\nError - Exiting program...\n");
        }
    }
}
