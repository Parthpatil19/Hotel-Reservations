package model;

public class Room implements IRoom {
    final String roomNumber;
    final Double price;
    final RoomType enumeration;

    public Room(String roomNumber,Double price, RoomType enumeration) {
        this.roomNumber=roomNumber;
        this.price=price;
        this.enumeration=enumeration;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        if(price==0) {
            return true;
        }
        return false;
    }

    @Override
    public final String toString() {
        String details="Room Number:"+roomNumber +"    Price: "+price+"    Type: "+enumeration;
        return details;

    }

}
