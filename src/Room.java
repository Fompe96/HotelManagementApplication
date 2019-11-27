import java.time.LocalDate;

public class Room {


    private int roomNumber;
    private int numberOfBeds;
    private boolean hasBalcony;
    private double pricePerNight;
    private boolean isBooked;
    // temporary variable used in "available rooms in time period."
    private Booking booking = new Booking();

    public Room(int roomNumber, int numberOfBeds, boolean hasBalcony, double pricePerNight, boolean isBooked) {
        this.roomNumber = roomNumber;
        this.numberOfBeds = numberOfBeds;
        this.hasBalcony = hasBalcony;
        this.pricePerNight = pricePerNight;
        this.isBooked = isBooked;
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
        return booking;
    }
}



