import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Booking {
    private static int incrementer = 0; // Used to increment bookingId each time a booking is created.
    private int bookingId = 0;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalPrice;
    private String Ssn;
    private ArrayList<Room> bookedRooms;

    public Booking (Date checkInDate, Date checkOutDate, ArrayList<Room> bookedRooms) {
        this.bookingId = ++incrementer;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookedRooms = bookedRooms;
        this.totalPrice = findTotalPrice(checkInDate, checkOutDate, bookedRooms);
    }

    private double findTotalPrice(Date checkInDate, Date checkOutDate, ArrayList<Room> bookedRooms) {   // Function to calculate the total price for a booking
        double totalPrice = 0;
        long days = 0;
        days = Math.abs((checkInDate.getTime() - checkOutDate.getTime()) / 86400000);   // Milliseconds for 24 hours
        for(Room room : bookedRooms) {
            totalPrice += room.getPricePerNight() * days;
        }
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

    public void setBookedRooms(ArrayList<Room> bookedRooms) {
        this.bookedRooms = bookedRooms;
    }

    @Override
    public String toString() {
        return "Booking ID: " + getBookingId() +"\n" +
                "Check in date: " + getCheckInDate() +"\n" +
                "Check out date: " + getCheckOutDate() + "\n" +
                "Total price: " + getTotalPrice() + "kr";
    }

     //temporary method used to try out "available rooms in time period."
    public LocalDate inCheck(LocalDate in){
        return in;
    }

      //temporary method used to try out "available rooms in time period."
    public LocalDate outCheck(LocalDate out){
        return out;
    }
}
