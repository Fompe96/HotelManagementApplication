import java.awt.print.Book;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class HotelApp {

    static HotelApp myHotel = new HotelApp();
    HotelLogic myLogic = new HotelLogic();
    Scanner input = new Scanner(System.in);


    public static void main(String[] args) {
        myHotel.myLogic.Test();
        myHotel.myLogic.loadRooms();
        myHotel.myLogic.loadCustomers();
        myHotel.myLogic.loadBookings();
        myHotel.loginFeature();

    }

    public void loginFeature() {
        myLogic.loginMenu();
        showMenu();
    }

    public void showMenu() {
        Scanner input = new Scanner(System.in);
        int userChoice = 0;

        do {
            System.out.println("Press a number according to the action you want to make");
            System.out.println("1.Add Room");
            System.out.println("2.Add new customer");
            System.out.println("3.Make booking");
            System.out.println("4.View all rooms");
            System.out.println("5.View available rooms in period");
            System.out.println("6.Check in");
            System.out.println("7.Check out");
            System.out.println("8.Edit room");
            System.out.println("9.Edit customer");
            System.out.println("10.Edit booking");
            System.out.println("11.View customers");
            System.out.println("12.View customer information");
            System.out.println("13.Remove customer");
            System.out.println("14.Remove room");
            System.out.println("15.Search for a booking");
            System.out.println("16.View a customers booking history");
            System.out.println("0.Exit program");
            try {
                userChoice = Integer.parseInt(input.nextLine());
            }catch (Exception e){
                userChoice = 11;
            }

            switch (userChoice) {
                case 1:
                    myLogic.addRoom();
                    break;
                case 2:
                    myLogic.addNewCustomer();
                    break;
                case 3:
                    myLogic.makeBookingInput();  // Call to makeBookingInput to then calls makeBooking.
                    break;
                case 4:
                    myLogic.viewAllRooms();
                    break;
                case 5:
                    myLogic.availableRoomsMenu();
                    break;
                case 6:
                    myLogic.checkIn();
                    break;
                case 7:
                    myLogic.checkOut();
                    break;
                case 8:
                    myLogic.editRoom(); // Call to editCustomerInput ---> editCustomer.
                    break;
                case 9:
                    myLogic.editCustomerInput(); // Call to editBookingInput ---> editBooking.
                    break;
                case 10:
                    myLogic.editBookingInput();
                    break;
                case 11:
                    myLogic.viewCustomer();
                    break;

                case 12:
                    myLogic.viewCustomerInformation();
                    break;

                case 13:
                    myLogic.removeCustomer();
                    break;

                case 14:
                    myLogic.removeRoom();
                    break;

                case 15:
                    myLogic.searchForBooking();
                    break;

                case 16:
                    myLogic.viewBookingHistory();
                    break;

                case 0:
                    System.out.println("Have a nice day!");
                    myLogic.saveRooms();
                    myLogic.saveCustomers();
                    myLogic.saveBookings();
                    myLogic.writeBookings();
                    myLogic.writeCustomers();
                    myLogic.writeRooms();
                   // myLogic.emptyRoomsFile(); // remove comment in order to empty the room file when exiting.
                   // myLogic.emptyCustomerFile();  //remove comment in order to empty the customer file when exiting.
                   // myLogic.emptyBookingFile();   //remove comment in order to empty the booking file when exiting.
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid input try again");
                    break;
            }

        } while (userChoice != 0);

    }

    public void customerMenu(String ssn) {
        String customerSsn = ssn;

        // Taget från Rezas meny-feature.

        // Ska ha andra meny-val?

        int userChoice = 0;

        do {
            System.out.println("Press a number according to the action you want to make");
            System.out.println("1.Make booking");
            System.out.println("2.View available rooms in period");
            System.out.println("3. Edit booking");
            System.out.println("0.Exit program");

            try {
                userChoice = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
            }

            switch (userChoice) {
                case 1:
                    myLogic.makeBooking(customerSsn);
                    break;
                case 2:
                    myLogic.availableRoomsMenu();
                    break;
                case 3:
                    myLogic.editBooking(ssn);
                    break;
                case 0:
                    System.out.println("Thank you, have a nice day!");
                    myLogic.saveRooms();
                    myLogic.saveCustomers();
                    myLogic.saveBookings();
                    myLogic.writeBookings();
                    myLogic.writeCustomers();
                    myLogic.writeRooms();
                    System.exit(0);
                    break;
                default:
                    System.out.println("invalid input, try again.");
                    break;
            }

        } while (userChoice != 0);
    }

}

