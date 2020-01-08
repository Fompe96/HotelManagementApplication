import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Booking implements java.io.Serializable {
    private static final long serialversionUID = 1L;
    private int bookingId = 0;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalPrice;
    private String ssn;
    private ArrayList<Room> bookedRooms;

    public Booking (int bookingId, Date checkInDate, Date checkOutDate, String ssn, ArrayList<Room> bookedRooms) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.ssn = ssn;
        this.bookedRooms = bookedRooms;
        this.totalPrice = findTotalPrice(checkInDate, checkOutDate, bookedRooms);
    }

    public double findTotalPrice(Date checkInDate, Date checkOutDate, ArrayList<Room> bookedRooms) {   // Function to calculate the total price for a booking
        double totalPrice = 0;
        long days = 0;
        days = Math.abs((checkInDate.getTime() - checkOutDate.getTime()) / 86400000);   // Milliseconds for 24 hours
        for(Room room : bookedRooms) {
            totalPrice += room.getPricePerNight() * days;
        }
        this.totalPrice = totalPrice;
        return totalPrice;
    }

    public int getBookingId() {
        return bookingId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getSsn() {
        return ssn;
    }

    public ArrayList<Room> getBookedRooms() {
        return bookedRooms;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addBookedRoom(Room bookedRoom) {
        this.bookedRooms.add(bookedRoom);
    }
    public void removeBookedRoom(Room bookedRoom){
        this.bookedRooms.remove(bookedRoom);
    }



    @Override
    public String toString() {
        return "Booking ID: " + getBookingId() +"\n" +
                "Check in date: " + getCheckInDate() +"\n" +
                "Check out date: " + getCheckOutDate() + "\n" +
                "Total price: " + getTotalPrice() + "kr";
    }


}
