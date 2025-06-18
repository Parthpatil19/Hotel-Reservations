package ui;

import java.util.*;
import api.HotelResource;
import model.*;
import service.*;

public final class TestData {

    public final static void populate() {

        // Initialize services
        CustomerService customerService = CustomerService.CustomerService();
        ReservationService reservationService = ReservationService.ReservationService();

        // Add customers with different dummy data
        customerService.addCustomer("john.doe@example.com", "John", "Doe");
        customerService.addCustomer("jane.smith@example.com", "Jane", "Smith");
        customerService.addCustomer("emily.johnson@example.com", "Emily", "Johnson");
        customerService.addCustomer("michael.brown@example.com", "Michael", "Brown");

        // Add rooms with different details
        List<IRoom> rooms = new ArrayList<>();
        rooms.add(new Room("201", 120.0, RoomType.SINGLE));
        rooms.add(new Room("202", 180.0, RoomType.Double));
        rooms.add(new Room("203", 220.0, RoomType.SINGLE));
        rooms.add(new Room("204", 270.0, RoomType.Double));
        rooms.add(new Room("205", 350.0, RoomType.SINGLE));

        rooms.forEach(reservationService::addRoom);

        // Set up reservation dates
        Date checkInDate1 = HotelResource.stringToDate("2025-11-01");
        Date checkOutDate1 = HotelResource.stringToDate("2025-11-04");
        Date checkInDate2 = HotelResource.stringToDate("2025-11-05");
        Date checkOutDate2 = HotelResource.stringToDate("2025-11-10");
        Date checkInDate3 = HotelResource.stringToDate("2025-11-15");
        Date checkOutDate3 = HotelResource.stringToDate("2025-11-20");

        // Fetch customers and reserve rooms
        Customer customer1 = customerService.getCustomer("john.doe@example.com");
        Customer customer2 = customerService.getCustomer("jane.smith@example.com");
        Customer customer3 = customerService.getCustomer("emily.johnson@example.com");

        if (customer1 != null) {
            reservationService.reserveARoom(customer1, rooms.get(0), checkInDate1, checkOutDate1);
        }
        if (customer2 != null) {
            reservationService.reserveARoom(customer2, rooms.get(1), checkInDate2, checkOutDate2);
        }
        if (customer3 != null) {
            reservationService.reserveARoom(customer3, rooms.get(2), checkInDate3, checkOutDate3);
        }

        System.out.println("Dummy Test Data added successfully");
        System.out.println("-------------------------------");
    }
}
