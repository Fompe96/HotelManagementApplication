public class Room implements java.io.Serializable {
    private static final long serialversionUID = 2L;
    private int roomNumber;
    private int numberOfBeds;
    private boolean hasBalcony;
    private double pricePerNight;
    private boolean isBooked;
    private boolean checkInOrOut;    //Whilst true the customer has checked in, false the customer has checked out


    public Room(int roomNumber, int numberOfBeds, boolean hasBalcony, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.numberOfBeds = numberOfBeds;
        this.hasBalcony = hasBalcony;
        this.pricePerNight = pricePerNight;
        this.isBooked = false;
        this.checkInOrOut = false;

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

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public void setHasBalcony(boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public boolean getCheckInOrOut() {
        return checkInOrOut;
    }

    public void setCheckInOrOut(boolean checkInOrOut) {
        this.checkInOrOut = checkInOrOut;
    }



    @ Override
    public String toString (){

        return String.format("%s%d%n%s%.2fkr%n%s%d%n%s%n%n","Room number: ",getRoomNumber(),
                "Price per night: ", getPricePerNight(),
                "Number of beds: ", getNumberOfBeds(),
                (getHasBalcony()? "Has balcony" : "No balcony"));
    }
}



