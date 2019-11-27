import java.time.LocalDate;

public class Room {
    private Booking booking;
    // temporary method used to try out "available rooms in time period."
    public boolean isBooked(){
        return true;
    }
    // temporary method used to try out "available rooms in time period."
    public LocalDate getInCheck(){
       return booking.inCheck(LocalDate.of(2020, 11, 25));
    }
    // temporary method used to try out "available rooms in time period."
    public LocalDate getOutCheck(){
        return booking.outCheck(LocalDate.of(2020, 11, 30));
    }


}
