import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
public class HotelLogic {

    // logic for "available rooms in time period."
    public ArrayList<Room> availableInTime(LocalDate in, LocalDate out){
        ArrayList<Room> listOfRooms = new ArrayList<>();
        listOfRooms.add(new Room());
        ArrayList<Room> availableRooms = new ArrayList<>();
        for (Room listOfRoom : listOfRooms) {
            if (!listOfRoom.isBooked()) {
                availableRooms.add(listOfRoom);
                // temporary dates will get exchanged for actual dates of bookings
            } else if( listOfRoom.getBooking().inCheck(LocalDate.of(2020, 11, 25)).compareTo(out) > 0 || listOfRoom.getBooking().outCheck(LocalDate.of(2020, 11, 30)).compareTo(in) < 0) {
                availableRooms.add(listOfRoom);
            }

        }
        return availableRooms.getRoomNr();
    }
}
