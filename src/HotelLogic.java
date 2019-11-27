import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
public class HotelLogic {

    public ArrayList<Room> availableInTime(LocalDate in, LocalDate out){
        ArrayList<Room> listOfRooms = new ArrayList<>();
        listOfRooms.add(new Room());
        ArrayList<Room> availableRooms = new ArrayList<>();
        for (Room listOfRoom : listOfRooms) {
            if (!listOfRoom.isBooked()) {
                availableRooms.add(listOfRoom);
            } else if( listOfRoom.getInCheck().compareTo(out) > 0 || listOfRoom.getOutCheck().compareTo(in) < 0) {
                availableRooms.add(listOfRoom);
            }

        }
        return availableRooms;
    }
}
