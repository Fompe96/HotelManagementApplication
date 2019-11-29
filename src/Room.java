import java.util.ArrayList;
import java.util.Date;

public class Room {


    private int roomNumber;
    private int numberOfBeds;
    private boolean hasBalcony;
    private double pricePerNight;
    private boolean isBooked;

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

    @Override
    public String toString() {
        return "Room numer: " + getRoomNumber() + "\n" +
                "Number of beds: " + getNumberOfBeds() + "\n" +
                "Has balcony: " + getHasBalcony() + "\n" +
                "Price per night: " + getPricePerNight() + "\n" +
                "----------------------------------";
    }
}



