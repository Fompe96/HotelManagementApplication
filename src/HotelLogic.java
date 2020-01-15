import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class HotelLogic {
    private String bookingFile = "bookings.ser"; // File where bookings are stored.
    private String customerFile = "customers.ser";   // File where customers are stored.
    private String roomFile = "rooms.ser";   // File where rooms are stored.
    // lists contains all objects of corresponding type.
    private ArrayList<Customer> customerList = new ArrayList<>();
    private ArrayList<Room> roomList = new ArrayList<>();
    private ArrayList<Booking> bookingsList = new ArrayList<>();
    private Scanner input = new Scanner(System.in);

    public void loginMenu() {
        int userChoice;
        do {

            System.out.println(" ____________________ ");
            System.out.println("|      [LOG IN]      |");
            System.out.println("| 1. Employer        |");
            System.out.println("| 2. Customer        |");
            System.out.println("| 3. Register        |");
            System.out.println("|____________________|");
            System.out.println(" (Employer-Username: 1)\n (Employer-Password: 2)   //David :)");
            System.out.print(" >>");
            userChoice = promptForInt();

            if (userChoice == 1) {
                loginEmployee();
            }
            if (userChoice == 2) {
                loginCustomer();
            }
            if (userChoice == 3) {
                 register();
            }
        } while (userChoice <= 3);
        if (userChoice > 3) {
            System.out.println("Wrong input. Choose option 1, 2 or 3.");
            loginMenu();
        }
    }

    public void loginEmployee() {
        String userName;
        String password;
        String empUsername = "1";
        String empPassword = "2";

        do {
            System.out.println("Username: ");
            userName = input.nextLine();
            System.out.println("Password: ");
            password = input.nextLine();
            System.out.println("");
            if (!userName.equals(empUsername) || !password.equals(empPassword)) {
                System.out.println("Wrong username or password.\nPlease try again.\n");
            }
        } while (!userName.equals(empUsername) || !password.equals(empPassword));
        System.out.println("Welcome!\n");
        HotelApp.myHotel.showMenu();
    }

    public void loginCustomer() {
        String userName;
        String password;

        try {
            System.out.print("Enter social security number: ");
            userName = input.nextLine();
            System.out.print("Enter password: ");
            password = input.nextLine();
        }catch (Exception e){
            System.out.println("Invalid input for ssn or password");
            return;
        }


        boolean successfully = false;
        boolean checker = false;

        for (Customer customer : customerList) {    // Loop controls that the customer with entered ssn number exists in list.
            if (userName.equals(customer.getCustomerSsn())) {
                successfully = true;
                break;
            }
        }
        if (successfully) {
            // check if ssn and password match the same object.
            for (Customer customer : customerList){
                if (userName != null && userName.equals(customer.getCustomerSsn()) && password.equals(customer.getCustomerPassword())) {
                    System.out.println("Welcome!");
                    HotelApp.myHotel.customerMenu(userName);
                    checker = true;
                }
            }
            if (!checker){
                System.out.println("Wrong password!");
            }

        }else{
            System.out.println("no customer with that ssn exists.");
        }
    }



    public void register() {


        boolean successfully = false;
        String ssn;
        String password;


            int choice = customerRegSub();
            switch (choice) {
                case 1: // Register as an existing customer.
                    do {
                        System.out.print("Enter your ssn: ");
                        ssn = input.nextLine();
                    }while (ssn == null);
                    for (Customer customer : customerList) {    // Loop controls that the customer with entered ssn number exists in list.
                        if (ssn.equals(customer.getCustomerSsn())) {
                            successfully = true;
                            break;
                        }
                    }
                    if (successfully) {
                        // sets a password to the correct customer
                        for (Customer customer : customerList) {
                            if (customer.getCustomerSsn().equals(ssn)) {
                                if (customer.getCustomerPassword() == null){
                                    changePassword(ssn);
                                }else {
                                    System.out.println("Password already exists on entered ssn, please log in to change password.");
                                }

                            }
                        }
                    }else {
                        System.out.println("No customers with entered SSN number could be found.");
                    }

                    break;
                case 2: // Register as a new customer.
                   ssn = addNewCustomer();
                    for (Customer customer : customerList) {    // Loop controls that the customer with entered ssn number exists in list.
                        if (ssn.equals(customer.getCustomerSsn())) {
                            successfully = true;
                            break;
                        }
                    }
                    if (successfully) {
                        // sets a password to the correct customer
                        changePassword(ssn);
                    }


                    break;
                case 3:
                    // Goes back to login-menu
                    loginMenu();
                    break;
                default:
                    System.out.println("Choice outside menu range.");
                    break;
            }
        }
        private void changePassword (String ssn){
            String password;
            for (Customer customer : customerList) {
                if (customer.getCustomerSsn().equals(ssn)) {

                    do {
                        System.out.print("Enter desired password: ");
                        password = input.nextLine();
                        customer.setCustomerPassword(password);
                        if (password.length() < 4) {
                            password = null;
                            System.out.println("the password need to be longer than 4 characters.");
                        }
                    } while (password == null);
                    System.out.println("Password successfully set.");
                }
            }

        }
        public void changeInformation (String ssn) {
            int choice;
            do {


                System.out.println("-----What information do yo want to change?-----");
                System.out.println("[1] Password.");
                System.out.println("[2] Address.");
                System.out.println("[3] Phone number.");
                System.out.println("[4] Exit.");
                 choice = promptForInt();

                switch (choice) {
                    case 1:
                        changePassword(ssn);
                        break;
                    case 2:
                        System.out.print(" New Address: ");
                        String customerAdress = input.nextLine();
                        while (!customerAdress.matches("[a-öA-Ö0-9 ,]+") || customerAdress.length() < 7) {
                            System.out.print("Invalid input.\nAddress: ");
                            customerAdress = input.nextLine();
                        }
                        for (Customer customer : customerList) {
                            if (customer.getCustomerSsn().equals(ssn)) {
                                customer.setCustomerAddress(customerAdress);
                                System.out.println("New address successfully set.");
                            }
                        }

                        break;
                    case 3:
                        System.out.print(" New phone number: ");
                        String customerPhoneNumber = input.nextLine();
                        while (!customerPhoneNumber.matches("[0-9- ,]+") || customerPhoneNumber.length() < 10) {
                            System.out.print("Phone number has to be 10 numbers.\nPhone number: ");
                            customerPhoneNumber = input.nextLine();
                        }
                        for (Customer customer : customerList) {
                            if (customer.getCustomerSsn().equals(ssn)) {
                                customer.setCustomerPhoneNumber(customerPhoneNumber);
                                System.out.println("New phone number successfully set.");
                            }
                        }
                        break;
                    case 4:
                        return;
                    case 0:
                        break;
                    default:
                        System.out.println("invalid input, try again.");


                }
            }while (choice != 4);
        }



    private int customerRegSub() {
        int choice = 0;
        System.out.println("What do you want to do? \n" +
                "1. Register as an existing customer. \n" +
                "2. Register as a new customer. \n" +
                "3. Go back to login-menu. ");

        try {
            choice = input.nextInt();
            input.nextLine();Calendar cal = Calendar.getInstance();
            Date currentDate = cal.getTime();
        } catch (Exception e) {
            System.out.println("Input has to be a number between 1-3.");
        }
        return choice;
    }

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
    private ArrayList<Room> availableInTime(Date in, Date out) {
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

    public void availableRoomsMenu() {
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();
        int choice;
        boolean check = false;
        do {
            System.out.println("[1] Check for current date.");
            System.out.println("[2] Check for other dates.");
            System.out.println("[0] Exit.");
            choice = promptForInt();
            if (choice == 1 || choice == 2 || choice == 0) {
                check = true;
            } else {
                System.out.println("You entered an invalid number, please try again.");
            }
        } while (!check);
        switch (choice) {
            case 1:
                for (int i = 0; i < availableInTime(currentDate, currentDate).size(); i++) {
                    System.out.println(availableInTime(currentDate, currentDate).get(i).toString());
                }
                break;
            case 2:
                availableInTimeMenu();
                break;
            default:
                return;
        }


    }

    // menu for "available rooms in time period"
    private void availableInTimeMenu() {
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
        // checks if check in date is in the past
        if (in.compareTo(currentDate) < 0) {
            System.out.println("It is not possible to check in too a date in history, try again.");
            availableInTimeMenu();
            //checks if check in date is more than 25 years in the future.
        } else if (out.compareTo(in25Years) > 0) {
            System.out.println("maximum time to book in the future is 25 years, try again.");
            availableInTimeMenu();
            // checks if the in date is after the out date.
        } else if (in.compareTo(out) > 0) {
            System.out.println("the check in date can't be after the check out date");
        } else {
            for (int i = 0; i < availableInTime(in, out).size(); i++) {
                System.out.println(availableInTime(in, out).get(i).toString());
            }
        }
    }

    public void makeBookingInput() { // String ssn, Date checkInDate, Date checkOutDate, ArrayList<Room> bookedRooms
        boolean successfully = false;
        System.out.println("Please enter SSN for customer to book: YYYYMMDD-XXXX");
        String ssn = input.nextLine();
        for (Customer customer : customerList) {
            if (customer.getCustomerSsn().equals(ssn)) {
                successfully = true;
                break;
            }
        }
        if (successfully) {
            makeBooking(ssn);
        } else {
            System.out.println("No customer with entered SSN could be found.");
        }
    }

    public boolean makeBooking(String ssn) { // String ssn, Date checkInDate, Date checkOutDate, ArrayList<Room> bookedRooms
        boolean successfully = true;
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

                        Booking booking = new Booking(findIncrementerValueBooking(), checkInDate, checkOutDate, ssn, roomsToBook);
                        bookingsList.add(booking);
                        System.out.println(booking);
                    }
                }
            }
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
        input.nextLine();
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
        boolean proceed = true;
        double pricePerNight = 0;
        int numberOfBeds = 0;
        boolean hasBalcony = false;


        System.out.println("Enter the number of beds");

        try {
            numberOfBeds = input.nextInt();
            input.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input enter a number");
            input.nextLine();
            proceed = false;
        }

        if (proceed && numberOfBeds > 0) {
            System.out.println("Does the room have Balcony? Enter Yes or No.");

            String answerBalcony;

            do {
                answerBalcony = input.nextLine();
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
        }

        else {
            System.out.println("Invalid input");
            proceed = false;
        }

        if (proceed) {
            System.out.println("Enter the price for renting this room per night");

            try {
                pricePerNight = input.nextDouble();
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid option enter a number with two decimals");
                input.nextLine();
                proceed = false;
            }
        }

        if (proceed && pricePerNight > 0 ) {
            roomList.add(new Room(findIncrementerValueRoom(), numberOfBeds, hasBalcony, pricePerNight));
            System.out.println("Room added!");
        }
    }

    public String addNewCustomer() {
        String customerName;
        String customerSSN;
        String customerAdress;
        String customerPhoneNumber;

        System.out.println("------------------------------");
        System.out.println("< Enter customer information >");
        System.out.print("First and last name: ");
        customerName = input.nextLine();
        while (!customerName.matches("[a-öA-Ö ,]+") || customerName.length() < 5) {
            System.out.print("\nInvalid input, enter first and last name.\nFirst and last name: ");
            customerName = input.nextLine();
        }
        System.out.print("Social security number (YYYYMMDD-XXXX): ");
        customerSSN = input.nextLine();
        while (customerSSN.length() < 13 || customerSSN.length() > 13) {
            System.out.print("\nInvalid format of social security number.\nSSN (YYYYMMDD-XXXX): ");
            customerSSN = input.nextLine();
        }
        for (Customer customer : customerList){
            if (customerSSN.equals(customer.getCustomerSsn())){
                System.out.println("A customer with that ssn already exists, try again.");
                addNewCustomer();
            }
        }
        System.out.print("Address: ");
        customerAdress = input.nextLine();
        while (!customerAdress.matches("[a-öA-Ö0-9 ,]+") || customerAdress.length() < 7) {
            System.out.print("Invalid input.\nAdress: ");
            customerAdress = input.nextLine();
        }
        System.out.print("Phone number: ");
        customerPhoneNumber = input.nextLine();
        while (!customerPhoneNumber.matches("[0-9- ,]+") || customerPhoneNumber.length() < 10) {
            System.out.print("Phone number has to be 10 numbers.\nPhone number: ");
            customerPhoneNumber = input.nextLine();
        }
        System.out.println("------------------------------\n");

        Customer customerInfo = new Customer(customerName, customerSSN, customerAdress, customerPhoneNumber);

        customerList.add(customerInfo);
        return customerSSN;
    }

    public void viewAllRooms() {

        System.out.println("\n--------------All rooms--------------");
        if (roomList.size() > 0) {
            for (int i = 0; i < roomList.size(); i++) {

                boolean balcony;
                boolean noBalcony;
                boolean isBooked;
                boolean notBooked;
                balcony = roomList.get(i).getHasBalcony() == true;
                noBalcony = roomList.get(i).getHasBalcony() == false;
                isBooked = roomList.get(i).getBooked() == true;
                notBooked = roomList.get(i).getBooked() == false;

                System.out.print("\nRoom nr: " + roomList.get(i).getRoomNumber());

                if (isBooked) {
                    System.out.print("  [BOOKED]");
                } else if (notBooked) {
                    System.out.print("  [AVAILABLE]");
                }

                System.out.println("\nNumber of beds: " + roomList.get(i).getNumberOfBeds() +
                        "\nPrice per night: " + roomList.get(i).getPricePerNight() + "0kr");

                if (balcony) {
                    System.out.println("This room has a balcony.");
                } else if (noBalcony) {
                    System.out.println("This room has not a balcony.");
                }
            }
            System.out.println("-------------------------------------\n");
        } else {
            System.out.println("There are no rooms to view.");
            System.out.println("-------------------------------------\n");
        }
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
                input.nextLine();
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
                input.nextLine();
            }
            if (successfully) {
                editCustomer(customerSsn);
            }
        } else {
            System.out.println("No customers exists to be edited.");
        }
    }

    public void editCustomer(String customerSsn) { // Called immediately from customerMenu and from editCustomerInput when the employee has entered a SSN number.
        boolean successfully = false;
        Customer customerToEdit = null;

        for (Customer customer : customerList) {    // Loop controls that the customer with entered ssn number exists in list.
            if (customerSsn.equals(customer.getCustomerSsn())) {
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
                    System.out.println("Enter new telephone number: (9-10 numbers)");
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
                    System.out.println("Current address of customer is: " + customerToEdit.getCustomerAddress());
                    System.out.println("Enter new address: ");
                    try {
                        newAddress = input.nextLine();
                    } catch (Exception e) {
                        System.out.println("Invalid input.");
                        input.nextLine();
                    }
                    if (newAddress != null && !newAddress.equals("")) {
                        System.out.print("Address has successfully been changed from " + customerToEdit.getCustomerAddress());
                        customerToEdit.setCustomerAddress(newAddress);
                        System.out.println(" to " + customerToEdit.getCustomerAddress());
                    } else {
                        System.out.println("Not a valid address.");
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

    public void viewCustomer() {
        System.out.println("-----------Customers-----------");
        if (customerList.size() > 0) {
            for (Customer customer : customerList) {
                System.out.println("Name: " + customer.getCustomerName() +
                        "\nSSN: " + customer.getCustomerSsn() +
                        "\nAdress: " + customer.getCustomerAddress() +
                        "\nPhone number: " + customer.getCustomerPhoneNumber() + "\n");
            }
            System.out.println("-------------------------------\n");
        } else {
            System.out.println("There are no customers to view.");
            System.out.println("-------------------------------\n");
        }
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
                    if (customerSsn.equals(customer.getCustomerSsn())) {
                        successfully = true;
                        customerToPrint = customer;
                        break;
                    }
                }
                if (successfully) {
                    System.out.println("----- Information about customer with SSN " + customerToPrint.getCustomerSsn());
                    System.out.println(customerToPrint);
                    System.out.println("------------ Bookings ------------");
                    for (int i = 0; i < bookingsList.size(); i++) {
                        if (bookingsList.get(i).getSsn().equals(customerToPrint.getCustomerSsn())) {
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


    public void checkIn() {
        int roomNumber = 0;
        int bookingId = 0;
        Room room = null;
        String customerSSN = null;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date currentDate = cal.getTime();
        boolean proceed = false;


        while (true) {

            System.out.print("Social security number (YYYYMMDD-XXXX): ");
            customerSSN = input.next();
            while (customerSSN.length() < 13 || customerSSN.length() > 13) {
                System.out.print("\nInvalid format of social security number.\nSSN (YYYYMMDD-XXXX): ");
                customerSSN = input.nextLine();
            }


            for (Booking booking : bookingsList) {
                if (customerSSN.equals(booking.getSsn())) {
                    System.out.println(booking.toString() + "\n");
                } else {
                    System.out.println("The ssn does not match a existing ssn of a customer");
                }
            }


            do {
                System.out.println("Enter the bookingId of the booking");
                try {
                    bookingId = input.nextInt();
                    proceed = true;
                } catch (Exception e) {
                    System.out.println("Input has to be a number");
                    bookingId = input.nextInt();
                    input.nextLine();
                }
            } while (!proceed);

            if (proceed) {
                proceed = false;
                for (Booking booking : bookingsList) {
                    if (booking.getBookingId() == bookingId) {

                        if (booking.getCheckInDate().compareTo(currentDate) >= 0 && booking.getCheckOutDate().compareTo(currentDate) >= 0) {
                            proceed = true;

                        } else {
                            System.out.println("You can only check in during the dates of the booking");
                        }
                    }

                }


                if (proceed) {
                    proceed = false;
                    for (int j = 0; j < bookingsList.size(); j++) {
                        if (bookingsList.get(j).getBookingId() == bookingId) {
                            Booking temp = bookingsList.get(j);
                            for (int k = 0; k < temp.getBookedRooms().size(); k++) {
                                temp.getBookedRooms().get(k).setCheckInOrOut(true);
                            }
                            System.out.println("Your customer has now checked in");
                        }
                    }
                    break;
                } else {
                    System.out.println("Input has to a number of an existing booking");
                }
                break;
            }
        }
    }

    public void checkOut() {
        int roomNumber = 0;
        Room room = null;

        boolean proceed = false;

        while (true) {


            System.out.println("Enter the room number that the customer is checking in: ");
            try {
                roomNumber = input.nextInt();
                input.nextLine();
                proceed = true;
            } catch (Exception e) {
                System.out.println("Input has to be a number");
            }

            if (proceed) {
                for (int i = 0; i < roomList.size(); i++) {
                    if (roomList.get(i).getRoomNumber() == roomNumber) {
                        room = roomList.get(i);
                        room.setCheckInOrOut(false);
                        System.out.println("Your customer has now checked out from room number " + (i+1) + ".");
                    }
                    else {
                        System.out.println("Input has to be a number of an existing room");
                    }
                }
            }
            break;

        }
    }

    public void removeRoom() {
        boolean checker = false;

        do {
            System.out.println("Enter the room number of the room you want to remove");
            int choice = promptForInt();


            if (choice != 0) {
                for (int i = 0; i < roomList.size(); i++) {
                    if (choice == roomList.get(i).getRoomNumber()) {
                        roomList.remove(i);
                        System.out.println("Room number " + choice + " is now removed");
                        checker = true;
                    }
                }
                if (!checker) {
                    System.out.println("No room with that room number exists.");
                }
            }
            if (!checker){
                do {

                    System.out.println("-----Try again?-----");
                    System.out.println("[1] Yes.");
                    System.out.println("[2] No.");
                    choice = promptForInt();
                    if (choice == 2) {
                        checker = true;
                    } else if (choice == 1) {

                    } else {
                        choice = 3;
                    }
                }while (choice == 3);
            }
        } while (!checker);

    }


    // menu that shows the options for editing a booking
    private int editBookingMenu() {
        int choice = 0;
        System.out.println("What information about the booking do you wish to edit? \n" +
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
        Date currentDate = cal.getTime();
        Date newDate;
        ArrayList<Booking> customerBookings = new ArrayList<>();
// prints all bookings registered on the customer.
        for (Booking booking : bookingsList) {
            if (booking.getSsn().equals(customerSsn)) {
                System.out.println(booking);
                customerBookings.add(booking);
            }
        }
        if (customerBookings.size() == 0) {
            System.out.println("No customer with that ssn could be found.");
        }
// specifies which booking to edit.
        int bookingId = 0;
        do {
            System.out.println("enter booking id for the booking you want to change.");
            bookingId = promptForInt();
        } while (bookingId == 0);
        boolean checker = false;
        for (Booking booking : customerBookings) {
            if (booking.getBookingId() == bookingId) {
                checker = true;
            }
        }
        if (checker == false) {
            System.out.println("A booking with that id does not exist for this customer.");
            return;
        }


        int choice = editBookingMenu();

        for (Booking booking : customerBookings) {
            if (booking.getBookingId() == bookingId) {

                switch (choice) {
// Logic for editing check in/out dates.
                    case 1:
                        Date newInCheck;
                        Date newOutCheck;

                        do {
                            System.out.print("Enter new check in: (yyyy-mm-dd) ");
                            newInCheck = promptForDate();
                            System.out.print("Enter new check out: (yyyy-mm-dd)");
                            newOutCheck = promptForDate();
                            if (newInCheck == null || newOutCheck == null) {
                                System.out.println("Invalid input try again.");
                            }
                        } while (newInCheck == null || newOutCheck == null);
                        if (newInCheck.compareTo(currentDate) < 0) {
                            System.out.println("you cant check in to a date that has already been.");
                            return;
                        } else if (newOutCheck.compareTo(newInCheck) < 0) {
                            System.out.println("you cant check out before the check in date.");
                            return;
                        }

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
                            for (Room room : removeRooms) {
                                System.out.println("Room nr: " + room.getRoomNumber() + " removed.");
                                booking.removeBookedRoom(room);
                                room.setBooked(false);
                            }

                            if (booking.getBookedRooms().size() == 0){
                                bookingsList.remove(booking);
                                System.out.println("the booking has been removed due too no more rooms where registered to the booking.");
                            }else{
                                booking.findTotalPrice(booking.getCheckInDate(), booking.getCheckOutDate(), booking.getBookedRooms());
                                System.out.println("new booking info: ");
                                System.out.println(booking);
                            }

                            for (Room room : removeRooms){
                                for (int i = 0; i < bookingsList.size() ; i++) {
                                    for (int j = 0; j <bookingsList.get(i).getBookedRooms().size() ; j++) {
                                        if (room.getRoomNumber() == bookingsList.get(i).getBookedRooms().get(j).getRoomNumber()){

                                            room.setBooked(true);
                                        }
                                    }
                                }
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

    public void searchForBooking() {
        if (bookingsList.size() > 0) {
            System.out.println("Enter ID of booking you wish to view:");
            int idToView = 0;
            try {
                idToView = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input");
                input.nextLine();
            }
            if (idToView > 0) {
                boolean match = false;
                for (Booking booking : bookingsList) {
                    if (booking.getBookingId() == idToView) {
                        System.out.println("--- Information about booking with ID " + idToView + ": ---");
                        System.out.println(booking);
                        System.out.println("SSN of customer: " + booking.getSsn() + "\n" +
                                "---------------------------------------------");
                        match = true;
                        break;
                    }
                }
                if (!match) {
                    System.out.println("No booking with entered ID could be found.");
                }
            }
        } else {
            System.out.println("There are no bookings to view.");
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
            number = Integer.parseInt(input.nextLine());
        } catch (Exception e) {
            System.out.println("You must enter an integer.");
            return 0;
        }
        return number;
    }

    // Logic for removing a customer.
    public void removeCustomer() {
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
                for (Customer customer : customerList) {
                    if (customer.getCustomerSsn().equals(customerSsn)) {
                        removeCustomer.add(customer);
                    }
                }
                if (removeCustomer.size() == 0) {
                    System.out.println("No customer with that ssn could be found");
                } else {
                    for (Customer customer : removeCustomer) {
                        customerList.remove(customer);
                        System.out.println("customer with ssn: " + customerSsn + " was successfully removed.");
                    }
                    for (Booking booking : bookingsList) {

                        if (booking.getSsn().equals(customerSsn)) {
                            removeBooking.add(booking);
                        }
                    }
                    removeBookings(removeBooking);

                }


            }
        } else {
            System.out.println("there is no customers to remove.");
        }
    }
    public void removeBookings (ArrayList<Booking> removeBooking) {

        ArrayList<Integer> bookedRooms = new ArrayList<>();

        for (Booking booking : bookingsList) {
            for (Room room : booking.getBookedRooms()) {
                bookedRooms.add(room.getRoomNumber());
            }
        }
            for (Booking BookingToRemove : removeBooking) {

                bookingsList.remove(BookingToRemove);
                System.out.println("Booking with Id: " + BookingToRemove.getBookingId() + " has been removed.");
            }
// Removes bookings associated with the customer.
            int size = bookedRooms.size();
            for (Booking checkForRoom : bookingsList) {
                for (Room room : checkForRoom.getBookedRooms()) {
                    for (int i = 0; i < size; i++) {
                        if (room.getRoomNumber() == bookedRooms.get(i)) {
                            bookedRooms.remove(i);
                        }
                    }
                }
            }
// Sets rooms that no longer have a booking as not booked (show available rooms in time period shows faulty information without this.)
            for (Room room : roomList) {
                for (int i = 0; i < bookedRooms.size(); i++) {
                    if (room.getRoomNumber() == bookedRooms.get(i)) {
                        room.setBooked(false);
                    }

                }
            }
        }

    public void saveBookings() { // Method to save all existing bookings in bookingList to a text file.
        if (bookingsList.size() > 0) {
            try {
                FileOutputStream FOS = new FileOutputStream(bookingFile);
                ObjectOutputStream OOS = new ObjectOutputStream(FOS);
                OOS.writeObject(bookingsList);
                OOS.close();
                FOS.close();
                System.out.println("Bookings were successfully saved to bookings.ser");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void emptyBookingFile() {   // Method to empty the file before saving information to it (to prevent duplicate information)
        try {
            FileOutputStream FOS = new FileOutputStream(bookingFile);
            ObjectOutputStream OOS = new ObjectOutputStream(FOS);
            OOS.close();
            FOS.close();
            System.out.println("Booking file was successfully emptied.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadBookings() {   // Method to load all bookings from text file to the bookingList.
        try {
            FileInputStream FIS = new FileInputStream(bookingFile);
            ObjectInputStream OIS = new ObjectInputStream(FIS);
            bookingsList = (ArrayList) OIS.readObject();
            OIS.close();
            FIS.close();
            System.out.println("Bookings load done");
        } catch (IOException e) {
            System.out.println("No bookings exists in file yet.");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public void saveCustomers() { // Method to save all existing customers in customerList to a text file.
        if (customerList.size() > 0) {
            try {
                FileOutputStream FOS = new FileOutputStream(customerFile);
                ObjectOutputStream OOS = new ObjectOutputStream(FOS);
                OOS.writeObject(customerList);
                OOS.close();
                FOS.close();
                System.out.println("Customers were successfully saved to customers.ser");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void emptyCustomerFile() {   // Method to empty the file before saving information to it (to prevent duplicate information)
        try {
            FileOutputStream FOS = new FileOutputStream(customerFile);
            ObjectOutputStream OOS = new ObjectOutputStream(FOS);
            OOS.close();
            FOS.close();
            System.out.println("Customer file was successfully emptied.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadCustomers() {   // Method to load all customers from text file to the customerList.
        try {
            FileInputStream FIS = new FileInputStream(customerFile);
            ObjectInputStream OIS = new ObjectInputStream(FIS);
            customerList = (ArrayList) OIS.readObject();
            OIS.close();
            FIS.close();
            System.out.println("Customer load done");
        } catch (IOException e) {
            System.out.println("No customers exists in file yet.");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public void saveRooms() { // Method to save all existing rooms in roomList to a text file.
        if (roomList.size() > 0) {
            try {
                FileOutputStream FOS = new FileOutputStream(roomFile);
                ObjectOutputStream OOS = new ObjectOutputStream(FOS);
                OOS.writeObject(roomList);
                OOS.close();
                FOS.close();
                System.out.println("Rooms were successfully saved to bookings.ser");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void emptyRoomsFile() {   // Method to empty the file before saving information to it (to prevent duplicate information)
        try {
            FileOutputStream FOS = new FileOutputStream(roomFile);
            ObjectOutputStream OOS = new ObjectOutputStream(FOS);
            OOS.close();
            FOS.close();
            System.out.println("Room file was successfully emptied.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadRooms() {   // Method to load all rooms from text file to the roomList.
        try {
            FileInputStream FIS = new FileInputStream(roomFile);
            ObjectInputStream OIS = new ObjectInputStream(FIS);
            roomList = (ArrayList) OIS.readObject();
            OIS.close();
            FIS.close();
            System.out.println("Room load done");
        } catch (IOException e) {
            System.out.println("No rooms exists in file yet.");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public void writeBookings() {
        String bookingFile = "Bookings.txt";
        String date;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        for (Booking booking : bookingsList) {
            try {
                // Creation of filewriter coated in bufferedwriter.
                FileWriter fileWriter = new FileWriter(bookingFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                // writes the information of the booking to a file.
                bufferedWriter.write("Booking id: " + booking.getBookingId());
                bufferedWriter.newLine();
                bufferedWriter.write("Check in date: " + (date = dateFormat.format(booking.getCheckInDate())));
                bufferedWriter.newLine();
                bufferedWriter.write("Check out date: " + (date = dateFormat.format(booking.getCheckOutDate())));
                bufferedWriter.newLine();

                bufferedWriter.write("Customer ssn: " + booking.getSsn());
                bufferedWriter.newLine();
                bufferedWriter.write("Booked rooms: ");
                String roomNr;
                for (int i = 0; i < booking.getBookedRooms().size(); i++) {
                    roomNr = String.valueOf(booking.getBookedRooms().get(i).getRoomNumber());
                    bufferedWriter.write(roomNr + ", ");
                }
                bufferedWriter.newLine();
                bufferedWriter.write("----------END----------");
                bufferedWriter.newLine();
                bufferedWriter.close();
            } catch (Exception e) {
                System.out.println("Error in file writing for file: " + bookingFile);
                return;

            }
        }
        System.out.println("Bookings were successfully written to " + bookingFile);
    }

    public void writeCustomers() {
        String customerFile = "Customers.txt";
        String date;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        for (Customer customer : customerList) {
            try {
                // Creation of filewriter coated in bufferedwriter.
                FileWriter fileWriter = new FileWriter(customerFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                // writes the information of the Customers to a file.
                bufferedWriter.write(customer.toString());
                bufferedWriter.newLine();

                bufferedWriter.write("----------END----------");
                bufferedWriter.newLine();
                bufferedWriter.close();
            } catch (Exception e) {
                System.out.println("Error in file writing for file: " + customerFile);
                return;

            }
        }
        System.out.println("Customers were successfully written to " + customerFile);
    }

    public void writeRooms() {
        String roomFile = "Rooms.txt";
        for (Room room : roomList) {
            try {
                // Creation of FileWriter coated in BufferedWriter.
                FileWriter fileWriter = new FileWriter(roomFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                // writes the information of the Rooms to a file.
                bufferedWriter.write(room.toString());
                bufferedWriter.newLine();


                bufferedWriter.write("----------END----------");
                bufferedWriter.newLine();
                bufferedWriter.close();
            } catch (Exception e) {
                System.out.println("Error in file writing for file: " + roomFile);
                return;

            }
        }
        System.out.println("Rooms were successfully written to " + roomFile);
    }

    public int findIncrementerValueBooking() {  // Method to assign bookingID to the bookings as they are created.
        int incrementerValue = 1;
        for (Booking booking : bookingsList) {
            if (incrementerValue <= booking.getBookingId()) {
                incrementerValue = booking.getBookingId()+1;
            }
        }
        return incrementerValue;
    }

    public int findIncrementerValueRoom() {  // Method to assign roomNumber to the rooms as they are created.
        int incrementerValue = 1;
        for (Room room : roomList) {
            if (incrementerValue <= room.getRoomNumber()) {
                incrementerValue = room.getRoomNumber()+1;
            }
        }
        return incrementerValue;
    }

    public void viewBookingHistory(){
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();
        String userSSN;
        System.out.println("What is the customers SSN?");


        userSSN = input.next();
        if (userSSN != null) {
            for (Booking booking : bookingsList) {
                if (userSSN.equals(booking.getSsn()) && booking.getCheckInDate().compareTo(currentDate) < 0) {
                    booking.toString();
                }
                else{
                    System.out.println("Your SSN does not match any customer that have made a booking in the past, enter a valid SSN");
                }
            }
        }
    }

    }


