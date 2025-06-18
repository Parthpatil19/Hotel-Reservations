package ui;


import api.*;
import java.util.*;
import java.util.regex.Pattern;
import model.*;



public class MainMenu {
    static Scanner sc=new Scanner(System.in);
    static AdminResource adminResource=AdminResource.AdminResource();



    public static void mainMenu() {
        int menuOption = 0;
        while (true) {
            System.out.println("""
                Main Menu
                ------------------------------
                1. Find and reserve a room;
                2. See my reservations;
                3. Create an account;
                4. Admin;
                5. Exit.
                -------------------------------
                Please select a number for the menu option""");
            while (true) {
                try {
                    menuOption = Integer.parseInt(sc.nextLine().trim());
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter a valid number (1-5) for the menu option");
                }
            }
            switch (menuOption) {
                case 1 -> findAndReserveARoom();
                case 2 -> checkMyReservations();
                case 3 -> createAnAccount();
                case 4 -> AdminMenu.menu();
                case 5 -> {
                    System.out.println("Thank you!");
                    return;
                }
                default -> System.out.println("Please enter a valid number (1-5) for the menu");
            }
        }
    }



    private static void findAndReserveARoom() {
        int DEFAULT_DAYS=7;
        String email=getEmail();

        Customer customer=adminResource.getCustomer(email);
        if(customer==null) {
            System.out.println("Customer does not exist. Do you want to create an account? y/n");
            if(yesOrNo()) {
                createAnAccount();
            }
            else {
                return;
            }

        }
        else {
            System.out.println("Enter your check in date. format: (yyyy-mm-dd)");
            Date checkInDate;
            while(true) {
                String checkInStr=sc.nextLine();
                if(!checkInStr.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$")) {
                    System.out.println("Please enter correct date format");
                    continue;
                }
                try {
                    checkInDate=HotelResource.stringToDate(checkInStr);
                }catch(Exception e) {
                    System.out.println("Enter correct date format");
                    continue;
                }
                if(checkInDate!=null) {
                    if(checkInDate.after(new Date())) {
                        System.out.println("check in date: "+ Reservation.dateToString(checkInDate));
                        break;
                    }
                    else {
                        System.out.println("The date you entered is in past. Please enter correct date");

                    }
                }
            }

            System.out.println("Enter your check out date. format: (yyyy-mm-dd)");
            Date checkOutDate;
            while(true) {
                String checkOutStr=sc.nextLine();
                if(!checkOutStr.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$")) {
                    System.out.println("Please enter correct date format");
                    continue;
                }
                try {
                    checkOutDate=HotelResource.stringToDate(checkOutStr);
                }catch(Exception e) {
                    System.out.println("Enter correct date format");
                    continue;
                }
                if(checkOutDate!=null) {
                    if(checkOutDate.after(new Date())) {
                        if(checkOutDate.after(checkInDate)) {
                            System.out.println("check out date: "+ Reservation.dateToString(checkOutDate));
                            break;
                        }
                        else {
                            System.out.println("The date you entered is in past of check in date. Please enter correct date");
                        }

                    }
                    else {
                        System.out.println("The date you entered is in past. Please enter correct date");

                    }
                }
            }

            Collection<IRoom> rooms=adminResource.findARoom(checkInDate, checkOutDate);
            int numOfRooms=rooms.size();
            if(numOfRooms==0) {
                System.out.println("Sorry! No rooms available at these dates. Do you want to use our reccomendation service? y/n");
                if(yesOrNo()) {
                    checkInDate=addToDate(checkInDate,DEFAULT_DAYS);
                    checkOutDate=addToDate(checkOutDate,DEFAULT_DAYS);
                    System.out.println("Finding rooms from check in date: "+Reservation.dateToString(checkInDate)+" check out date: "+Reservation.dateToString(checkOutDate));
                    rooms=adminResource.findARoom(checkInDate, checkOutDate);
                    if(rooms.size()==0) {
                        System.out.println("Sorry no rooms are found. Thankyou for using our reccomendation service");
                        return;
                    }
                }
                else {
                    System.out.println("-------------------------------");
                    return;
                }
            }
            System.out.println("Found "+rooms.size()+" rooms.");
            rooms.stream().forEach(System.out::println);

            System.out.println("Enter a room number");
            String roomNumber="";
            while(true) {
                roomNumber=sc.nextLine();
                String finalRoomNumber=roomNumber;
                if(rooms.stream().anyMatch(room->room.getRoomNumber().equals(finalRoomNumber))) {
                    break;
                }else {
                    System.out.println("Room number is invalid. Please enter a valid room number.");
                }
            }

            Reservation reservation=adminResource.bookARoom(email, adminResource.getRoom(roomNumber), checkInDate, checkOutDate);
            System.out.println("Your reservation is: \n"+reservation);

        }
        System.out.println("-------------------------------");


    }


    private static void createAnAccount() {
        System.out.println("Please enter your first name");
        String firstName = "";
        while (true) {
            firstName=sc.nextLine();
            if(firstName.matches("^[a-zA-Z\s]+$")) {
                break;
            }
            else {
                System.out.println("Please enter a valid first name");

            }
        }

        System.out.println("Please enter your last name");
        String lastName = "";
        while (true) {
            lastName=sc.nextLine();
            if(lastName.matches("^[a-zA-Z\s]+$")) {
                break;
            }
            else {
                System.out.println("Please enter a valid first name");
            }
        }


        String email=getEmail();
        if(adminResource.getCustomer(email)==null) {
            adminResource.createACustomer(email, firstName, lastName);
            System.out.println("Customer account created successfully!");

        }
        else {
            System.out.println("Customer already exists");
        }
        System.out.println("-------------------------------");
    }

    private static void checkMyReservations() {
        String email=getEmail();
        if(adminResource.getCustomer(email)==null) {
            System.out.println("Customer doesnot exists. Please create an account");
            createAnAccount();
        }
        else {
            Collection<Reservation> reservations=adminResource.getCustomersReservations(email);
            if(reservations.size()==0) {
                System.out.println("No reservations");
            }
            else {
                System.out.println("Your reservations:");
                reservations.stream().forEach(System.out::println);
            }
        }
        System.out.println("-------------------------------");
    }






    private static String getEmail() {
        String email;
        while(true) {
            System.out.println("Enter email");
            email=sc.nextLine();
            if(isValidEmail(email)) {
                return email.toLowerCase();
            }
            else {
                System.out.println("Enter a valid email address in the format [name@domain.extension]");
            }

        }
    }


    private static boolean isValidEmail(String email) {
        final String emailPattern="^(.+)@(.+)[.](.+)$";
        Pattern pattern=Pattern.compile(emailPattern);
        return pattern.matcher(email).matches();

    }

    public static boolean yesOrNo() {
        String errorStatement="Invalid response. Please enter y for Yes, n for No";
        while(true) {
            String response=sc.nextLine();
            if(response.equalsIgnoreCase("y")) {
                return true;
            }
            else if(response.equalsIgnoreCase("n")) {
                return false;
            }
            else {
                System.out.println(errorStatement);
            }
        }

    }

    public static Date addToDate(Date date,int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
}
