package service;

import java.util.*;
import model.*;

public class ReservationService {

    public static ReservationService instance=null;
    static  HashMap<String, IRoom> rooms;
    static List<Reservation> reservations;





    public static ReservationService ReservationService() {
        if(instance==null) {
            instance=new ReservationService();
            rooms=new HashMap<>();
            reservations=new ArrayList<>();
        }
        return instance;
    }



    public IRoom getARoom(String roomId) {
        if(rooms.containsKey(roomId)) {
            return rooms.get(roomId);
        }
        else{
            return null;
        }
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);

    }

    public final Reservation reserveARoom(Customer customer, IRoom room, Date  checkInDate, Date checkOutDate) {
        Reservation reserve=new Reservation(customer,room,checkInDate,checkOutDate);
        reservations.add(reserve);
        return reserve;
    }


    public final Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms=new ArrayList<>();
        for(IRoom room:rooms.values()) {
            if(isAvailable(room,checkInDate,checkOutDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }



    public final Collection<Reservation> getCustomersReservation(Customer customer){

        Collection<Reservation> customerReservations=new ArrayList<>();
        for(Reservation reservation: reservations) {
            if(reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;

    }
    private boolean isAvailable(IRoom room,Date checkIn, Date checkOut) {
        for(Reservation reservation:reservations) {
            if(reservation.getRoom().getRoomNumber().equals(room.getRoomNumber())) {
                if(isOverlap(reservation.getCheckInDate(),reservation.getCheckOutDate(),checkIn,checkOut)) {
                    return false;
                }
            }
        }
        return true;
    }

    public final void printAllReservation() {
        if(reservations.isEmpty()) {
            System.out.println("No reservations yet");
        }
        else {
            reservations.stream().forEach(System.out::println);
        }

    }

    public final Collection<IRoom> getAllRooms(){
        return rooms.values();
    }


    //code adapted from https://codereview.stackexchange.com/questions/206710/checking-if-two-time-intervals-overlap
    private final boolean isOverlap(Date start1,Date end1, Date start2, Date end2) {
        return !(end2.before(start1) || end1.before(start2));
    }


}
