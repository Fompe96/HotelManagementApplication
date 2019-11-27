import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class HotelLogic {

    // logic for "available rooms in time period."
    public int availableInTime(LocalDate in, LocalDate out) {
        ArrayList<Room> listOfRooms = new ArrayList<>();
        listOfRooms.add(new Room(1, 2, true, 500, true));
        ArrayList<Room> availableRooms = new ArrayList<>();
        for (Room listOfRoom : listOfRooms) {
            if (!listOfRoom.getBooked()) {
                availableRooms.add(listOfRoom);
                // temporary dates will get exchanged for actual dates of bookings
            } else if (listOfRoom.getBooking().inCheck(LocalDate.of(2020, 11, 25)).compareTo(out) > 0 || listOfRoom.getBooking().outCheck(LocalDate.of(2020, 11, 30)).compareTo(in) < 0) {
                availableRooms.add(listOfRoom);
            }

        }

        for (int i = 0; i <availableRooms.size() ; i++) {
            return availableRooms.get(i).getRoomNumber();
        }


    }
}

