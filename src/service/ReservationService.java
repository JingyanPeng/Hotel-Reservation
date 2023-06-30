package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    // provide a static reference
    private static ReservationService instance = new ReservationService();
    public static ReservationService getInstance(){
        return instance;
    }

    private List<Reservation> reservations = new ArrayList<Reservation>();
    List<Reservation> getReservations(){
        return reservations;
    }
    private Set<IRoom> rooms = new HashSet<IRoom>();
    public Set<IRoom> getRooms(){
        return rooms;
    }

    String turnAlarmOn() {
        return "Turning the alarm on.";
    }

    public void addRoom(IRoom room){
        rooms.add(room);
    }

    public IRoom getARoom(String roomNumber){
        Iterator<IRoom> iterator = rooms.iterator();
        while(iterator.hasNext()){
            IRoom room = iterator.next();
            if(room.getRoomNumber().equals(roomNumber)){
                return room;
            }
        }
        return null;
    }

    public void reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        reservations.add(new Reservation(customer, room, checkInDate, checkOutDate));
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate,boolean isFree){
        Set<IRoom> roomList = new HashSet<IRoom>(rooms);
        Iterator<Reservation> iterator = reservations.iterator();
        while(iterator.hasNext()){
            Reservation res = iterator.next();
            if (res.getCheckInDate().compareTo(checkOutDate) < 0 && checkInDate.compareTo(res.getCheckOutDate()) < 0){
                roomList.remove(res.getRoom());
            }
        }
        Set<IRoom> allRoomList = new HashSet<IRoom>(rooms);
        if(isFree){ // free
            for(IRoom room : allRoomList){
                if(!room.isFree()){
                    roomList.remove(room);
                }
            }
        }else { // paid
            for(IRoom room : allRoomList){
                if(room.isFree()){
                    roomList.remove(room);
                }
            }
        }
        return roomList;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer){
        Collection<Reservation> CustomerReservation = new ArrayList<Reservation>();;
        Iterator<Reservation> iterator = reservations.iterator();
        while(iterator.hasNext()){
            Reservation res = iterator.next();
            if(res.getCustomer() == customer){
                CustomerReservation.add(res);
            }
        }
        return CustomerReservation;
    }

    public void printAllReservation(){
        int i = 0;
        Iterator<Reservation> iterator = reservations.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
            i ++;
        }
        if(i == 0){
            System.out.println("There is no reservation");
        }
    }

}
