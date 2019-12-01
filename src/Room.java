import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Room {


    private int roomNumber;
    private int numberOfBeds;
    private boolean hasBalcony;
    private double pricePerNight;
    private boolean isBooked;


    public Room(int roomNumber, int numberOfBeds, boolean hasBalcony, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.numberOfBeds = numberOfBeds;
        this.hasBalcony = hasBalcony;
        this.pricePerNight = pricePerNight;
        this.isBooked = false;

    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean getBooked() {
        return isBooked;
    }

    public boolean getHasBalcony() {
        return hasBalcony;
    }

    // temporary method used to try out "available rooms in time period."
    public Booking getBooking() {
        // Temporary objects in order to return a booking object.
        Date date1 = new Date();
        Date date2 = new Date();
        ArrayList<Room> testList = new ArrayList<>();
        return new Booking(date1, date2, testList);
    }
}



