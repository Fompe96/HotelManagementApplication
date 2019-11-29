import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class HotelLogic {
    private ArrayList<Customer> customerList = new ArrayList<>();
    private ArrayList<Room> roomList = new ArrayList<>();
    private ArrayList<Booking> bookingList = new ArrayList<>();
    private Scanner input = new Scanner(System.in);


    public Room getRoom(int roomNbr) {  // Demo version
        Room roomToReturn = null;
        for (Room room : roomList) {
            if (room.getRoomNumber() == roomNbr) {
                roomToReturn = room;
            }
        }
        return roomToReturn;
    }

    // logic for "available rooms in time period."
    public int availableInTime(LocalDate in, LocalDate out) {
        ArrayList<Room> listOfRooms = new ArrayList<>();
        listOfRooms.add(new Room(1, 2, true, 500, true));
        ArrayList<Room> availableRooms = new ArrayList<>();
        for (Room listOfRoom : listOfRooms) {
            if (!listOfRoom.getBooked()) {
                availableRooms.add(listOfRoom);
                // temporary dates will get exchanged for actual dates of bookings
            } /*else if (listOfRoom.getBooking().inCheck(LocalDate.of(2020, 11, 25)).compareTo(out) > 0 || listOfRoom.getBooking().outCheck(LocalDate.of(2020, 11, 30)).compareTo(in) < 0) {
                availableRooms.add(listOfRoom);
            }*/

        }

        for (int i = 0; i <availableRooms.size() ; i++) {
            return availableRooms.get(i).getRoomNumber();
        }
        return 0;

    }

    public boolean makeBooking() { // String ssn, Date checkInDate, Date checkOutDate, ArrayList<Room> bookedRooms
        boolean successfully = false;
        System.out.println("Please enter SSN for customer to book: YYYYMMDD-XXXX");
        String ssn = input.nextLine();
        for (Customer customer : customerList) {
            if (customer.getCustomerSSN().equals(ssn)) {
                successfully = true;
                break;
            }
        }

        if (successfully) { // Handle check in date
            System.out.println("What day would you like to check in? yyyy-mm-dd");
            Date checkInDate = promptForDate();
            Date currentDate = new Date();
            currentDate.toInstant();
            if (checkInDate == null) {
                successfully = false;
            } else if (checkInDate.compareTo(currentDate) < 0) {
                successfully = false;
                System.out.println("Check in date has to be at least one day from now.");
            }

            if (successfully) {
                System.out.println("What day would you like to check out? yyyy-mm-dd");
                Date checkOutDate = promptForDate();
                if (checkOutDate == null) {
                    successfully = false;
                } else if (checkOutDate.getTime() - checkInDate.getTime() <= 0) {
                    successfully = false;
                    System.out.println("Check out date has the be at least 1 day after check in date.");
                }
                if (successfully) {
                    ArrayList<Room> roomsToBook = new ArrayList<>();
                    ArrayList<Room> availableRooms = availableInTime();// Calls availableInTime to retrieve arraylist with available rooms.
                    for (Room room : availableRooms) {
                        System.out.println(room);
                    }
                    System.out.println("How many rooms would you like to book?");
                    int numOfRoomsToBook = promptForInteger();
                    if (numOfRoomsToBook < 1) {
                        successfully = false;
                        System.out.println("Invalid input.");
                    } else if (numOfRoomsToBook > availableRooms.size()) {
                        successfully = false;
                        System.out.println("There are not that many rooms available in that time period.");
                    }
                    if (successfully) {
                        int[] roomNumberArray = new int[numOfRoomsToBook];
                        for (int i = 0; i < roomNumberArray.length && successfully; i++) {
                            System.out.println("Enter room number you wish to book:");
                            roomNumberArray[i] = promptForInteger();
                            if (roomNumberArray[i] < 1) {
                                System.out.println("Invalid input");
                                successfully = false;
                                break;
                            }
                            for (int j = 0; j < availableRooms.size(); j++) {
                                if (roomNumberArray[i] == availableRooms.get(j).getRoomNumber()) {
                                    successfully = true;
                                    break;
                                } else {
                                    successfully = false;
                                }
                            }
                            if (successfully) {
                                if (i > 0) {
                                    for (int j = 0; j < i; j++) {
                                        if (roomNumberArray[i] == roomNumberArray[j]) {
                                            successfully = false;
                                            System.out.println("You can't enter the same room number twice!");
                                            break;
                                        }
                                    }
                                }
                            } else {
                                System.out.println("No room with that room number is available. Pick from list of available rooms!");
                                break;
                            }
                        }
                        if (successfully) {
                            System.out.println("All data is okay!");
                            for (int i = 0; i < roomNumberArray.length; i++) {
                                roomsToBook.add(getRoom(roomNumberArray[i]));
                            }

                            Booking booking = new Booking(checkInDate, checkOutDate, roomsToBook);
                            bookingList.add(booking);
                            for (Customer customer : customerList) {
                                if (customer.getCustomerSSN().equals(ssn)) {
                                    customer.getBookings().add(booking);
                                    break;
                                }
                            }
                            System.out.println(booking);
                        }
                    }
                }
            }
        } else {
            System.out.println("No customer with entered SSN could be found.");
        }

        //customer.addBooking(new Booking(checkInDate, checkOutDate, bookedRooms));
        return successfully;
    }

    private Date stringToDate(String stringDate) {  // Method used by makeBooking to convert strings to dates.
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(stringDate);
        } catch (Exception e) {
            System.out.println("Invalid input for check in/out date");
            return null;
        }
    }

    private Date promptForDate() {  // Method used by makeBooking to prompt user to enter a date in the form of a string and call stringToDate
        String stringDate = input.nextLine();
        return stringToDate(stringDate);
    }

    private int promptForInteger() {    // Method used by makeBooking to prompt user for integers.
        int integer = 0;
        try {
            integer = input.nextInt();
        } catch (Exception ignored) {
            // The integers returned will be controlled in makeBooking method, hence exception ignored.
        }
        input.nextLine();
        return integer;
    }
}

