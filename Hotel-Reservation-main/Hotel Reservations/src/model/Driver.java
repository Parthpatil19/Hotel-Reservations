package model;

import java.util.Date;

class Driver {

    public static void main(String args[]) {
        Customer customer=new Customer("first","second","a@domain.com");
        System.out.println(customer);
        IRoom room=new Room("200",2000.0,RoomType.SINGLE);
        System.out.println(room);
        Date d1 = new Date(2001, 11, 11);
        Date d2 = new Date(2025, 11, 11);
        Reservation reservation=new Reservation(customer,room,d1,d2);
        System.out.println(reservation);
    }

}
