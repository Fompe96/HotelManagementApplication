import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class HotelLogic {
    Scanner input = new Scanner(System.in);
    ArrayList<Room> listOfRooms = new ArrayList<>();

    public void addRoom(){
        System.out.println("Enter Room Number");
        int roomNumber = input.nextInt();

        System.out.println("Enter the number of beds");
        int numberOfBeds = input.nextInt();

        System.out.println("Does the room have Balcony? Enter Yes or No.");
        boolean hasBalcony = false;
        String answerBalcony;

        do{
        answerBalcony = input.next();
            if (answerBalcony.equals("Yes")) {
                hasBalcony = true;
                break;
            } else if (answerBalcony.equals("No")) {
                hasBalcony = false;
                break;
            } else {
                System.out.println("Invalid input, Enter Yes or No");
            }
        }
        while(!"Yes".equals(answerBalcony) || !"No".equals(answerBalcony));

        System.out.println("Enter the price for renting this room per night");
        double pricePerNight = input.nextDouble();


        Room hotelRoom = new Room(roomNumber,numberOfBeds,hasBalcony,pricePerNight);
        listOfRooms.add(hotelRoom);
        System.out.println(listOfRooms.size());

    }

    // logic for "available rooms in time period."
    public int availableInTime(LocalDate in, LocalDate out) {
        ArrayList<Room> listOfRooms = new ArrayList<>();
        listOfRooms.add(new Room(1, 2, true, 500));
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
        return 0;

    }
}

