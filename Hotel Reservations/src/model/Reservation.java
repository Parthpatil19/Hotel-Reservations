package model;

import java.util.Date;

public class Reservation {
    final Customer customer;
    final IRoom room;
    final Date checkInDate;
    final Date checkOutDate;


    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer=customer;
        this.room=room;
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;
    }


    public Customer getCustomer() {
        return customer;
    }


    public IRoom getRoom() {
        return room;
    }


    public Date getCheckInDate() {
        return checkInDate;
    }


    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public final String toString() {

        String details="Reservation Details:\nCustomer details: "+customer+"\n"
                +"Room details    : "+room+"\ncheck in date   : "+dateToString(checkInDate)+"\n"
                +"Check out date  : "+dateToString(checkOutDate)+"\n";
        return details;
    }

    public static final String dateToString(Date date) {
        int year=date.getYear()+1900;
        int month=date.getMonth()+1;
        return year+"-"+month+"-"+date.getDate();
    }
}
