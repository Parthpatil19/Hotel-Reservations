package api;

import service.CustomerService;

public class Driver {
    public static void main(String[] args) {
        HotelResource hotelResource = new HotelResource();
        AdminResource adminResource = new AdminResource();
        System.out.println("Welcome to the hotel reservation application!");

        CustomerService customerService = CustomerService.CustomerService();
        customerService.addCustomer("john.doe@example.com", "Doe", "John");
        customerService.addCustomer("jane.smith@example.com", "Smith", "Jane");

        System.out.println(customerService.getCustomer("john.doe@example.com"));
        System.out.println(customerService.getCustomer("jane.smith@example.com"));
    }
}
