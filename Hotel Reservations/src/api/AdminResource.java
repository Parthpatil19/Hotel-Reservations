package api;
import java.util.*;
import model.*;
import service.*;

public class AdminResource extends HotelResource{
    static AdminResource instance=null;

    static final ReservationService reservationService=ReservationService.ReservationService();
    static final CustomerService customerService=CustomerService.CustomerService();


    public static AdminResource AdminResource() {
        if(instance==null) {
            instance=new AdminResource();
        }
        return instance;

    }

    public void addRoom(List<IRoom> rooms) {
        for(IRoom room:rooms) {
            reservationService.addRoom(room);
        }
        System.out.println("Rooms added successfully");
        System.out.println("Added Rooms are:");
        for(IRoom room:rooms) {
            System.out.println(room);
        }

    }
    public Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();


    }
    public Customer getCustomer( String email) {
        return customerService.getCustomer(email);


    }
    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
    public Collection<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }
}
