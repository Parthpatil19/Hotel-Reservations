package ui;


import java.util.*;
import api.*;
import model.*;
import service.*;


public class AdminMenu {
    private static Scanner sc=new Scanner(System.in);
    static AdminResource adminResource=AdminResource.AdminResource();

    public static void menu() {
        int menuOption = 0;

        while (true) {
            System.out.println("""
                           Admin Menu
                           --------------------------------------
                           1. See all Customers;
                           2. See all Rooms;
                           3. See all Reservations;
                           4. Add Rooms;
                           5. Populate with test data;
                           6. Back to Main Menu.
                            --------------------------------------
                            """);
            while (true) {
                try {
                    menuOption = Integer.parseInt(sc.nextLine().trim());
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter a valid number (1-6) for the menu option");
                }
            }
            switch (menuOption) {
                case 1 -> displayAllCustomers();
                case 2 -> displayAllRooms();
                case 3 -> displayAllReservations();
                case 4 -> inputRooms();
                case 5 -> TestData.populate();
                case 6 -> {
                    return; // exit to main menu
                }

            }
        }
    }

    private static void displayAllCustomers() {

        if(adminResource.getAllCustomers().size()==0) {
            System.out.println("No customers found.");
        }
        else {
            System.out.println("Customers:");
            adminResource.getAllCustomers().stream().forEach(System.out::println);
        }
        System.out.println("-------------------------------");

    }

    private static void displayAllRooms() {
        if(adminResource.getAllRooms().size()==0) {
            System.out.println("No rooms found.");
        }
        else {
            System.out.println("Rooms:");
            adminResource.getAllRooms().stream().forEach(System.out::println);
        }
        System.out.println("-------------------------------");


    }

    private static void displayAllReservations() {
        adminResource.displayAllReservations();
        System.out.println("-------------------------------");


    }

    private static void inputRooms() {
        ArrayList<IRoom> rooms=new ArrayList<>();
        while(true) {
            String roomNumber="";
            System.out.println("Enter a room number");
            while(true) {

                roomNumber=sc.nextLine();
                String finalRoomNumber=roomNumber;
                if(adminResource.getRoom(roomNumber)!=null || rooms.stream().anyMatch(room->room.getRoomNumber().equals(finalRoomNumber))) {
                    System.out.println("Room already exists. Enter a new room number");
                }
                else if(!roomNumber.matches("^[0-9]+$")) {
                    System.out.println("Enter a valid room number");
                }
                else {
                    break;
                }
            }

            Double price=0.0;
            while(true) {
                System.out.println("Enter price per night");
                try {
                    price=Double.parseDouble(sc.nextLine().trim());
                }
                catch(Exception e) {
                    System.out.println("Enter a valid price");
                }
                if(price<=0) {
                    System.out.println("Enter a valid price");
                }
                else {
                    break;
                }
            }
            int type=0;
            RoomType roomType;

            while(true) {
                System.out.println("Enter room type: 1 for single bed, 2 for double bed");
                try {
                    type=Integer.parseInt(sc.nextLine().trim());
                }
                catch(Exception e) {
                    System.out.println("Enter 1 for single bed, 2 for double bed");
                }
                if(type!=1 && type !=2) {
                    System.out.println("Enter valid number for room type");
                }
                else {
                    roomType=type==1?RoomType.SINGLE:RoomType.Double;
                    break;
                }

            }

            IRoom room=new Room(roomNumber, price, roomType);
            rooms.add(room);
            System.out.println("Would you like to add another room y/n");
            if(MainMenu.yesOrNo()) {
                continue;
            }
            else {
                break;
            }

        }
        adminResource.addRoom(rooms);
        System.out.println("The rooms added are:");
        rooms.stream().forEach(System.out::println);
        System.out.println("-------------------------------");



    }

}

