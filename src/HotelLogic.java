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
            else {
                for (int i = 0; i < bookingsList.size(); i++) {
                    if (bookingsList.get(i).getCheckInDate().compareTo(out) > 0 || bookingsList.get(i).getCheckOutDate().compareTo(in) < 0) {
                        availableRooms.addAll(bookingsList.get(i).getBookedRooms());
                    }
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
        input.nextLine();

        System.out.println("Enter the number of beds");
        int numberOfBeds = input.nextInt();
        input.nextLine();

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
        input.nextLine();


        roomList.add(new Room(roomNumber, numberOfBeds, hasBalcony, pricePerNight));
        System.out.println(roomList.size());

    }

    public void addNewCustomer() {
        System.out.println("------------------------------");
        System.out.println("< Enter customer information >");
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

    public void editRoom() {
        if (roomList.size() > 0) {
            boolean successfully = true;
            int roomNumber = 0;
            Room roomToEdit = null;
            System.out.println("Enter room number of room you wish to edit:");
            try {
                roomNumber = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input.");
                successfully = false;
            }
            if (successfully) {
                successfully = false;
                for (Room room : roomList) {    // Loop controls that the room with entered room number exists in list.
                    if (roomNumber == room.getRoomNumber()) {
                        successfully = true;
                        roomToEdit = room;
                        break;
                    }
                }

                if (successfully) {
                    int choice = editRoomMenu();
                    switch (choice) {
                        case 1: // Change number of beds
                            int numberOfBeds = 0; // Since we know that the room exists in list we can initialize to 0, value will be changed later.
                            System.out.println("Currently the room has " + roomToEdit.getNumberOfBeds() + " beds.");
                            System.out.println("Input new value: (1-12)");
                            int newNumberOfBeds = 0;
                            try {
                                newNumberOfBeds = input.nextInt();
                                input.nextLine();
                            } catch (Exception e) {
                                System.out.println("New value has to be a number.");
                                successfully = false;
                            }
                            if (newNumberOfBeds < 0 || newNumberOfBeds > 12) {
                                successfully = false;
                                System.out.println("Invalid input.");
                            }
                            if (successfully) {
                                System.out.print("The number of beds has successfully been changed from " + roomToEdit.getNumberOfBeds());
                                roomToEdit.setNumberOfBeds(newNumberOfBeds);
                                System.out.println(" to " + roomToEdit.getNumberOfBeds() + " beds.");
                            }
                            break;
                        case 2: // Change balcony status
                            System.out.println("Currently the room has " + (roomToEdit.getHasBalcony() ? "a balcony" : "no balcony"));
                            System.out.println("Would you like to change the balcony status? \n" +
                                    "1. Yes \n" +
                                    "2. No");
                            int balconyChoice = 0;
                            try {
                                balconyChoice = input.nextInt();
                                input.nextLine();
                            } catch (Exception e) {
                                System.out.println("Invalid input.");
                                successfully = false;
                            }
                            if (successfully) {
                                switch (balconyChoice) {
                                    case 1:
                                        roomToEdit.setHasBalcony(!roomToEdit.getHasBalcony());
                                        break;
                                    case 2:
                                        System.out.println("No changes has been made.");
                                        break;
                                    default:
                                        break;
                                }
                                System.out.println(roomToEdit);
                            }
                            break;
                        case 3: // Change price per night
                            System.out.println("Currently the price per night of the room is " + roomToEdit.getPricePerNight() + " kr.");
                            System.out.println("Input new value:");
                            double newPrice = 0;
                            try {
                                newPrice = input.nextDouble();
                                input.nextLine();
                            } catch (Exception e) {
                                System.out.println("Invalid input.");
                                successfully = false;
                            }
                            if (successfully) {
                                // The most expensive room might be 3000 per night
                                successfully = newPrice > 0 && newPrice < 3000;
                            }
                            if (successfully) {
                                System.out.print("Price has successfully been changed from " + roomToEdit.getPricePerNight());
                                roomToEdit.setPricePerNight(newPrice);
                                System.out.println(" kr to " + roomToEdit.getPricePerNight() + " kr");
                            } else {
                                System.out.println("Invalid input.");
                            }
                            break;
                        case 4:
                            // Does nothing but lets user get out of menu.
                            break;
                        default:
                            System.out.println("Choice outside menu range.");
                            break;
                    }
                } else {
                    System.out.println("No room with entered room number could be found.");
                }
            }
        } else {
            System.out.println("There are no rooms to edit.");
        }
    }

    private int editRoomMenu() { // Prints the menu and reads the menu choice input from user trying to edit a room.
        int choice = 0;
        System.out.println("What information about the room do you wish to edit? \n" +
                "1. Number of beds \n" +
                "2. Balcony status \n" +
                "3. Price per night \n" +
                "4. Exit");
        try {
            choice = input.nextInt();
            input.nextLine();
        } catch (Exception e) {
            System.out.println("Input has to be a number between 1-4");
        }
        return choice;
    }

    public void editCustomerInput() { // Used when editCustomer is called from employee menu to take the SSN as input from the employee.
        if (customerList.size() > 0) {
            String customerSsn = null;
            boolean successfully = true;
            System.out.println("Enter ssn number of customer you wish to edit (YYYYMMDD-XXXX):");               // This method is ONLY called from the employee menu!!! Call below method (editCustomer) from customerMenu!
            try {
                customerSsn = input.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input.");
                successfully = false;
            }
            if (successfully) {
                editCustomer(customerSsn);
            }
        }
    }

    public void editCustomer(String customerSsn) { // Called immediately from customerMenu and from editCustomerInput when the employee has entered a SSN number.
        boolean successfully = false;
        Customer customerToEdit = null;

        for (Customer customer : customerList) {    // Loop controls that the customer with entered ssn number exists in list.
            if (customerSsn.equals(customer.getCustomerSSN())) {
                successfully = true;
                customerToEdit = customer;
                break;
            }
        }
        if (successfully) {
            int choice = editCustomerMenu();
            switch (choice) {
                case 1: // Edit customer telephone number
                    String newTelephoneNumber = null;
                    System.out.println("Current telephone number of customer is " + customerToEdit.getCustomerPhoneNumber());
                    System.out.println("Enter new telephone number: ");
                    try {
                        newTelephoneNumber = input.nextLine();
                    } catch (Exception e) {
                        System.out.println("Invalid input.");
                    }
                    if (newTelephoneNumber != null && newTelephoneNumber.matches("[0-9]+") && newTelephoneNumber.length() > 8 && newTelephoneNumber.length() < 11) {
                        System.out.print("Telephone number has successfully been changed from " + customerToEdit.getCustomerPhoneNumber());
                        customerToEdit.setCustomerPhoneNumber(newTelephoneNumber);
                        System.out.println(" to " + customerToEdit.getCustomerPhoneNumber());
                    } else {
                        System.out.println("Invalid input.");
                    }
                    break;
                case 2: // Edit customer address
                    String newAddress = null;
                    System.out.println("Current address of customer is: " + customerToEdit.getCustomerAdress());
                    System.out.println("Enter new address: ");
                    try {
                        newAddress = input.nextLine();
                    } catch (Exception e) {
                        System.out.println("Invalid input.");
                    }
                    if (newAddress != null) {
                        System.out.print("Address has successfully been changed from " + customerToEdit.getCustomerAdress());
                        customerToEdit.setCustomerAdress(newAddress);
                        System.out.println(" to " + customerToEdit.getCustomerAdress());
                    }
                    break;
                case 3:
                    // Does nothing but lets user get out of menu.
                    break;
                default:
                    System.out.println("Choice outside menu range.");
                    break;
            }
        } else {
            System.out.println("No customers with entered SSN number could be found.");
        }

    }

    private int editCustomerMenu() {
        int choice = 0;
        System.out.println("What information about the customer do you wish to edit? \n" +
                "1. Telephone number \n" +
                "2. Address \n" +
                "3. Exit ");

        try {
            choice = input.nextInt();
            input.nextLine();
        } catch (Exception e) {
            System.out.println("Input has to be a number between 1-3");
        }
        return choice;
    }

    public void viewCustomerInformation() {
        if (customerList.size() > 0) {
            String customerSsn = null;
            boolean successfully = true;
            System.out.println("Enter ssn number of customer you wish to retrieve information about (YYYYMMDD-XXXX):");
            try {
                customerSsn = input.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input.");
                successfully = false;
            }
            if (successfully) {
                successfully = false;
                Customer customerToPrint = null;
                for (Customer customer : customerList) {
                    if (customerSsn.equals(customer.getCustomerSSN())) {
                        successfully = true;
                        customerToPrint = customer;
                        break;
                    }
                }
                if (successfully) {
                    System.out.println("----- Information about customer with SSN " + customerToPrint.getCustomerSSN());
                    System.out.println(customerToPrint);
                    System.out.println("------------ Bookings ------------");
                    for (int i = 0; i < bookingsList.size(); i++) {
                        if (bookingsList.get(i).getSsn().equals(customerToPrint.getCustomerSSN())) {
                            System.out.println(bookingsList.get(i) + "\n" +
                                    "------------ Rooms ------------");
                            for (int j = 0; j < bookingsList.get(i).getBookedRooms().size(); j++) {
                                System.out.println("Room number: " + bookingsList.get(i).getBookedRooms().get(j).getRoomNumber() + "\n" +
                                        "Price per night: " + bookingsList.get(i).getBookedRooms().get(j).getPricePerNight() + "\n" +
                                        "Number of beds: " + bookingsList.get(i).getBookedRooms().get(j).getNumberOfBeds() + "\n" +
                                        "Has balcony: " + bookingsList.get(i).getBookedRooms().get(j).getHasBalcony() + "\n" +
                                        "------------------------------------------------");
                            }
                            System.out.println();

                        }
                    }
                } else {
                    System.out.println("No customer with that ssn could be found.");
                }
            }
        } else {
            System.out.println("No customers to view.");
        }
    }
// menu that shows the options for editing a booking
    private int editBookingMenu() {
        int choice = 0;
        System.out.println("What information about the customer do you wish to edit? \n" +
                "1. Check in/out \n" +
                "2. Room(s) \n" +
                "3. Exit ");

        try {
            choice = input.nextInt();
            input.nextLine();
        } catch (Exception e) {
            System.out.println("Input has to be a number between 1-3");
        }
        return choice;
    }
// method with logic for editing a booking.
    public void editBooking(String customerSsn) {
        Calendar cal = Calendar.getInstance();
        Date newDate;
        ArrayList<Booking> customerBookings = new ArrayList<>();
// prints all bookings registered on the customer.
        for (Booking booking : bookingsList) {
            if (booking.getSsn().equals(customerSsn)) {
                System.out.println(booking);
                customerBookings.add(booking);
            }else{
                System.out.println("No customer with that ssn could be found.");
                return;
            }
        }
// specifies which booking to edit.
        int bookingId = 0;
        do {
            System.out.println("enter booking id for the booking you want to change.");
             bookingId = promptForInt();
        }while (bookingId == 0);


        int choice = editBookingMenu();

        for (Booking booking : customerBookings) {
            if (booking.getBookingId() == bookingId) {

                switch (choice) {
// Logic for editing check in/out dates.
                    case 1:

                        System.out.print("Enter new check in: (yyyy-mm-dd) ");
                        Date newInCheck = promptForDate();
                        System.out.print("Enter new check out: (yyyy-mm-dd)");
                        Date newOutCheck = promptForDate();

                        if (booking.getCheckInDate().compareTo(newInCheck) >= 0 && booking.getCheckOutDate().compareTo(newOutCheck) >= 0) {
                            ArrayList<Room> rooms = new ArrayList();
                            cal.setTime(booking.getCheckInDate());
                            cal.add(Calendar.DATE, -1);
                            newDate = cal.getTime();
                            for (Room availableRoom : availableInTime(newInCheck, newDate)) {
                                for (Room room : booking.getBookedRooms()) {

                                    if (availableRoom.getRoomNumber() == room.getRoomNumber()) {
                                        rooms.add(room);
                                    }
                                }
                            }
                            if (rooms.size() == 0) {
                                System.out.println("sorry there already is a booking for selected room(s) at that date.");
                            } else {
                                System.out.println("The desired date is available for the following room(s) of your booking. ");
                                for (Room room : rooms) {
                                    System.out.println("Nr: " + room);
                                }
                            }
                            System.out.println("please confirm to change the check in/out for these room(s). (Yes/No):");
                            String string = input.next();
                            if (string.equals("Yes")) {
                                for (Room room : booking.getBookedRooms()) {
                                    int checker = 0;
                                    for (int i = 0; i < rooms.size(); i++) {
                                        if (rooms.get(i) != room) {
                                            booking.removeBookedRoom(room);
                                        }
                                    }
                                }
                                booking.setCheckInDate(newInCheck);
                                booking.setCheckOutDate(newOutCheck);
                                booking.findTotalPrice(newInCheck, newOutCheck, booking.getBookedRooms());
                                System.out.println("the booking has been edited successfully");
                                System.out.println("new booking info: ");
                                System.out.println(booking);
                            } else if (string.equals("No")) {
                                System.out.println("No changes were made.");
                            } else {
                                System.out.println("You must enter either Yes or No.");
                            }
                        }
                        if (booking.getCheckInDate().compareTo(newInCheck) <= 0 && booking.getCheckOutDate().compareTo(newOutCheck) <= 0) {
                            ArrayList<Room> rooms = new ArrayList();
                            cal.setTime(booking.getCheckOutDate());
                            cal.add(Calendar.DATE, 1);
                            newDate = cal.getTime();
                            for (Room availableRoom : availableInTime(newDate, newOutCheck)) {
                                for (Room room : booking.getBookedRooms()) {

                                    if (availableRoom.getRoomNumber() == room.getRoomNumber()) {
                                        rooms.add(room);
                                    }
                                }
                            }
                            if (rooms.size() == 0) {
                                System.out.println("sorry there already is a booking for selected room(s) at that date.");
                            } else {
                                System.out.println("The desired date is available for the following room(s) of your booking. ");
                                for (Room room : rooms) {
                                    System.out.println(room);
                                }
                            }
                            System.out.println("please confirm to change the check in/out for these room(s). (Yes/No):");
                            String string = input.next();

                            if (string.equals("Yes")) {
                                for (Room room : booking.getBookedRooms()) {
                                    int checker = 0;
                                    for (int i = 0; i < rooms.size(); i++) {
                                        if (rooms.get(i) != room) {
                                            booking.getBookedRooms().remove(room);
                                        }
                                    }

                                }
                                booking.setCheckInDate(newInCheck);
                                booking.setCheckOutDate(newOutCheck);
                                booking.findTotalPrice(newInCheck, newOutCheck, booking.getBookedRooms());
                                System.out.println("the booking has been edited successfully");
                                System.out.println("new booking info: ");
                                System.out.println(booking);
                            } else if (string.equals("No")) {
                                System.out.println("No changes were made.");
                            } else {
                                System.out.println("You must enter either Yes or No.");
                            }

                        }


                        break;
// Logic for adding or removing a room to the booking.
                    case 2:
                        System.out.print("do you want to add or remove a room in your booking?: (add/remove)");
                        String action = input.nextLine();
                        if (action.equals("add")) {
                            System.out.println("the following rooms are available for the duration of your booking.");
                            for (int i = 0; i < availableInTime(booking.getCheckInDate(), booking.getCheckOutDate()).size(); i++) {
                                System.out.println(availableInTime(booking.getCheckInDate(), booking.getCheckOutDate()).get(i).toString());
                            }
                            System.out.print("which room do you want to add to your booking?: ");
                            int roomNr = promptForInt();
                            for (int i = 0; i < availableInTime(booking.getCheckInDate(), booking.getCheckOutDate()).size(); i++) {
                                if (availableInTime(booking.getCheckInDate(), booking.getCheckOutDate()).get(i).getRoomNumber() == roomNr) {
                                    booking.addBookedRoom(availableInTime(booking.getCheckInDate(), booking.getCheckOutDate()).get(i));
                                    booking.findTotalPrice(booking.getCheckInDate(), booking.getCheckOutDate(), booking.getBookedRooms());
                                    availableInTime(booking.getCheckInDate(), booking.getCheckOutDate()).get(i).setBooked(true);
                                    System.out.println("Room number: " + roomNr + " added.");
                                    System.out.println("new booking info: ");
                                    System.out.println(booking);
                                }
                            }
                        } else if (action.equals("remove")) {
                            for (Room room : booking.getBookedRooms()) {
                                System.out.println(room);
                            }
                            System.out.print("which of the rooms do you want to remove from your booking? (enter room number): ");
                            int roomNr = promptForInt();
                            ArrayList<Room> removeRooms = new ArrayList<>();
                            for (Room room : booking.getBookedRooms()) {
                                if (room.getRoomNumber() == roomNr) {
                                    removeRooms.add(room);
                                }
                            }
                            for (Room room: removeRooms) {
                                System.out.println("Room nr: " + room.getRoomNumber() + " removed.");
                                booking.removeBookedRoom(room);
                            }
                        } else {
                            System.out.println("You must type add or remove.");
                        }
                        break;
                    case 3:
                        System.out.println("Returning to main menu.");
                        break;
                    default:
                        System.out.println("an invalid number was entered.");
                        break;
                }

            }
        }
    }
    // Used when editCustomer is called from employee menu to take the SSN as input from the employee.
    public void editBookingInput() {
        if (customerList.size() > 0) {
            String customerSsn = null;
            boolean successfully = true;
            System.out.println("Enter ssn of customer which booking you want to edit (YYYYMMDD-XXXX):");
            try {
                customerSsn = input.next();
            } catch (Exception e) {
                System.out.println("Invalid input.");
                successfully = false;
            }
            if (successfully) {
                editBooking(customerSsn);
            }
        }
    }
// Checks that input is an Integer before returning it.
    private int promptForInt() {
        int number = 0;
        try {
            number = Integer.parseInt(input.next());
        } catch (Exception e) {
            System.out.println("You must enter an integer.");
            return 0;
        }
        return number;
    }
    // Logic for removing a customer.
    public void removeCustomer(){
        if (customerList.size() > 0) {
            String customerSsn = null;
            boolean successfully = true;
            System.out.println("Enter ssn of customer which you want to remove (YYYYMMDD-XXXX):");
            try {
                customerSsn = input.next();
            } catch (Exception e) {
                System.out.println("Invalid input.");
                successfully = false;
            }
            if (successfully) {
                ArrayList<Customer> removeCustomer = new ArrayList<>();
                ArrayList<Booking> removeBooking = new ArrayList<>();
                for (Customer customer: customerList) {
                    if (customer.getCustomerSSN().equals(customerSsn)){
                        removeCustomer.add(customer);
                    }
                }
                if (removeCustomer.size() == 0){
                    System.out.println("No customer with that ssn could be found");
                }else{
                    ArrayList<Integer> bookedRooms = new ArrayList<>();
                    for (Customer customer: removeCustomer) {
                        customerList.remove(customer);
                        System.out.println("customer with ssn: " + customerSsn + " was successfully removed.");
                    }
                    for (Booking booking: bookingsList) {
                        for (Room room: booking.getBookedRooms()) {
                            bookedRooms.add(room.getRoomNumber());
                        }

                        if (booking.getSsn().equals(customerSsn)){
                            removeBooking.add(booking);
                        }
                    }
                    for (Booking booking: removeBooking) {

                        bookingsList.remove(booking);
                        System.out.println("Booking with Id: " + booking.getBookingId() + " has been removed.");
                    }
// Removes bookings associated with the customer.
                    int size = bookedRooms.size();
                    for (Booking booking: bookingsList) {
                        for (Room room: booking.getBookedRooms()) {
                            for (int i = 0; i < size; i++){
                                if (room.getRoomNumber() == bookedRooms.get(i)){
                                    bookedRooms.remove(i);
                                }
                            }
                        }
                    }
// Sets rooms that no longer have a booking as not booked (show available rooms in time period shows faulty information without this.)
                    for (Room room: roomList) {
                        for (int i = 0; i < bookedRooms.size(); i++){
                            if (room.getRoomNumber() == bookedRooms.get(i)){
                                room.setBooked(false);
                            }
                        }
                    }
                }

            }
        }else {
            System.out.println("there is no customers to remove.");
        }
    }
}