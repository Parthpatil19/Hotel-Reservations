package api;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import model.*;
import service.*;
import java.util.*;

public class HotelResource {

    public static HotelResource instance=null;
    static final CustomerService customerService=CustomerService.CustomerService();
    static final ReservationService reservationService=ReservationService.ReservationService();

    public static HotelResource HotelResource() {
        if(instance==null) {
            instance=new HotelResource();
        }
        return instance;
    }

    public void createACustomer(String email,String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);

    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);

    }
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);

    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer=customerService.getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);

    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return reservationService.findRooms(checkIn, checkOut);

    }
    public Collection<Reservation> getCustomersReservations(String customerEmail){
        Customer customer=customerService.getCustomer(customerEmail);
        return reservationService.getCustomersReservation(customer);

    }

    public static final Date stringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


}
