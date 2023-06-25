package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    //provide a static reference
    private static HotelResource instance = new HotelResource();
    public static HotelResource getInstance(){
        return instance;
    }

    public Customer getCustomer(String email){
        return CustomerService.getInstance().getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName){
        CustomerService.getInstance().addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber){
        return ReservationService.getInstance().getARoom(roomNumber);
    }

    public void BookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        Customer customer = getCustomer(customerEmail);
        ReservationService.getInstance().reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomerReservations(String customerEmail){
        Customer customer = getCustomer(customerEmail);
        return ReservationService.getInstance().getCustomerReservation(customer);
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate){
        return ReservationService.getInstance().findRooms(checkInDate, checkOutDate);
    }
}
