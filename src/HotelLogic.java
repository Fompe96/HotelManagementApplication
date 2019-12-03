import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class HotelLogic {

    // lists contains all objects of corresponding type.
    private ArrayList<Customer> customerList = new ArrayList<>();
    private ArrayList<Room> roomList = new ArrayList<>();
    private ArrayList<Booking> bookingsList = new ArrayList<>();

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
    public ArrayList<Room> availableInTime(Date in, Date out) {
        ArrayList<Room> availableRooms = new ArrayList<>();

        // checks if booking to a room exist and adds it if not.
        for (Room room : roomList) {
            if (!room.getBooked()) {
                availableRooms.add(room);
            }
            // checks if booking of room intersects desired booking dates and adds it if not.
            for (int i = 0; i < bookingsList.size(); i++) {
                if (bookingsList.get(i).getCheckInDate().compareTo(out) > 0 || bookingsList.get(i).getCheckOutDate().compareTo(in) < 0) {
                    availableRooms.addAll(bookingsList.get(i).getBookedRooms());
                }
            }
        }
        // returns added rooms.
        return availableRooms;
    }

    // menu for "available rooms in time period"
    public void availableInTimeMenu() {
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();
        cal.add(Calendar.YEAR, 25);
        Date in25Years = cal.getTime();
        Date in;
        Date out;

        do {
            System.out.println("Enter desired date for check in (yyyy-mm-dd): ");
            in = promptForDate();
        } while (in == null);

        do {
            System.out.println("Enter desired date for check out (yyyy-mm-dd): ");
            out = promptForDate();
        } while (out == null);
        // checks if check in date i in the past
        if (in.compareTo(currentDate) < 0) {
            System.out.println("It is not possible to check in too a date in history, try again.");
            availableInTimeMenu();
            //checks if sheck in date is more than 25 years in the future.
        } else if (out.compareTo(in25Years) > 0) {
            System.out.println("maximum time to book in the future is 25 years, try again.");
            availableInTimeMenu();
            // passes two dates to availableInTime
        } else {
            for (int i = 0; i < availableInTime(in, out).size(); i++) {
                System.out.println(availableInTime(in, out).get(i).toString());
            }
        }
    }


    public boolean makeBooking() { // String ssn, Date checkInDate, Date checkOutDate, ArrayList<Room> bookedRooms
        customerList.add(new Customer("Anders", "1", "1", "1"));
        boolean successfully = false;
        System.out.println("Please enter SSN for customer to book: YYYYMMDD-XXXX");
        String ssn = input.next();
        for (Customer customer : customerList) {
            if (customer.getCustomerSSN().equals(ssn)) {
                successfully = true;
                break;
            }
        }

        if (successfully) { // Handle check in date
            System.out.println("What day would you like to check in? YYYY-MM-DD");
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
                System.out.println("What day would you like to check out? YYYY-MM-DD");
                Date checkOutDate = promptForDate();
                if (checkOutDate == null) {
                    successfully = false;
                } else if (checkOutDate.getTime() - checkInDate.getTime() <= 0) {
                    successfully = false;
                    System.out.println("Check out date has the be at least 1 day after check in date.");
                }
                if (successfully) {
                    ArrayList<Room> roomsToBook = new ArrayList<>();
                    ArrayList<Room> availableRooms = availableInTime(checkInDate, checkOutDate);// Calls availableInTime function to retrieve arraylist with available rooms.
                    System.out.println("Available rooms in period: \n" +
                            "---------------------------------");
                    for (Room room : availableRooms) {
                        System.out.print(room);
                    }
                    System.out.println("--------------------------------- \n" +
                            "How many rooms would you like to book?");
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
                                // added this line in order to set is booked to true /Anders
                                getRoom(roomNumberArray[i]).setBooked(true);
                            }

                            Booking booking = new Booking(checkInDate, checkOutDate, ssn, roomsToBook);
                            bookingsList.add(booking);
                            for (Customer customer : customerList) {
                                if (customer.getCustomerSSN().equals(ssn)) {
                                    customer.getBookings().add(booking);
                                    System.out.println(customer.getBookings().get(0));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("No customer with entered SSN could be found.");
        }
        return successfully;
    }

    private Date stringToDate(String stringDate) {  // Method used by makeBooking to convert strings to dates.
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int year = (Integer.parseInt(stringDate.substring(0, 4)));
            int month = (Integer.parseInt(stringDate.substring(5, 7)));
            int day = (Integer.parseInt(stringDate.substring(8, 10)));
            YearMonth yearMonthObject = YearMonth.of(year, month);  // Creates a yearMonth object of the particular year and month inputted by user.
            int daysInMonth = yearMonthObject.lengthOfMonth();  // Saves number of days in month into daysInMonth variable
            if (day <= daysInMonth && day > 0) {   // If day inputted by user exists in month (1-31 in december for example)
                return formatter.parse(stringDate);
            } else {
                System.out.println("Invalid input for check in/out date");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Invalid input for check in/out date");
            return null;
        }
    }

    private Date promptForDate() {  // Method used by makeBooking to prompt user to enter a date in the form of a string and call stringToDate
        String stringDate = input.next();
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

    public void addRoom() {
        System.out.println("Enter Room Number");
        int roomNumber = input.nextInt();

        System.out.println("Enter the number of beds");
        int numberOfBeds = input.nextInt();

        System.out.println("Does the room have Balcony? Enter Yes or No.");
        boolean hasBalcony = false;
        String answerBalcony;

        do {
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
        while (!"Yes".equals(answerBalcony) || !"No".equals(answerBalcony));

        System.out.println("Enter the price for renting this room per night");
        double pricePerNight = input.nextDouble();


        roomList.add(new Room(roomNumber, numberOfBeds, hasBalcony, pricePerNight));
        System.out.println(roomList.size());

    }

    public void addNewCustomer() {
        Scanner input = new Scanner(System.in);
        //  ArrayList<Customer> customers = new ArrayList<>();

        System.out.println("\n------------------------------");
        System.out.println("< Enter customer information > \n");
        System.out.print("Name: ");
        String customerName = input.nextLine();
        System.out.print("SSN (YYYYMMDD-XXXX): ");
        String customerSSN = input.nextLine();
        System.out.print("Adress: ");
        String customerAdress = input.nextLine();
        System.out.print("Phone number: ");
        String customerPhoneNumber = input.nextLine();
        System.out.println("------------------------------\n");

        Customer customerInfo = new Customer(customerName, customerSSN, customerAdress, customerPhoneNumber);

        customerList.add(customerInfo);
    }
}