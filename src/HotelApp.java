import java.util.Scanner;


public class HotelApp {
    HotelLogic myLogic = new HotelLogic();

    public static void main(String[] args) {

        HotelApp myHotel = new HotelApp();
        myHotel.showMenu();

    }

    public void showMenu() {
        Scanner input = new Scanner(System.in);
        int userChoice;

        do {
            System.out.println("Press a number according to the action you want to make");
            System.out.println("1.Add Room");
            System.out.println("2.Add new customer");
            System.out.println("3.Make booking");
            System.out.println("4.View available rooms in period");
            System.out.println("5.Check in");
            System.out.println("6.Check out");
            System.out.println("7.Edit room");
            System.out.println("8.Edit customer");
            System.out.println("9.View customer information");
            System.out.println("10.Exit program");
            userChoice = input.nextInt();
            input.nextLine();

            switch (userChoice) {
                case 1:
                    myLogic.addRoom();
                    break;
                case 2:
                    myLogic.addNewCustomer();
                    break;
                case 3:
                    myLogic.makeBooking();
                    break;
                case 4:
                    myLogic.availableInTimeMenu();
                    break;
                case 5:
                    myLogic.checkIn();
                    break;
                case 6:
                    //Add check out method
                    break;
                case 7:
                    myLogic.editRoom();
                    break;
                case 8:
                    myLogic.editCustomerInput(); // Call to editCustomerInput ---> editCustomer.
                    break;
                case 9:
                    myLogic.viewCustomerInformation();
                    break;
                case 10:
                    System.out.println("Have a nice day!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid input try again");
                    break;
            }

        } while (userChoice != 9);

    }

}

