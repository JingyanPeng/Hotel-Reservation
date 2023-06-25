package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;

public class AdminResource {

    //provide a static reference
    private static AdminResource instance = new AdminResource();
    public static AdminResource getInstance(){
        return instance;
    }

    public Customer getCustomer(String email){
        return CustomerService.getInstance().getCustomer(email);
    }

    public void addRoom(IRoom room){
        ReservationService.getInstance().addRoom(room);
    }

    public Collection<IRoom> getAllRooms(){
        return ReservationService.getInstance().getRooms();
    }

    public Collection<Customer> getAllCustomers(){
        return CustomerService.getInstance().getAllCustomers();
    }

    public void displayAllReservations(){
        ReservationService.getInstance().printAllReservation();
    }

}
