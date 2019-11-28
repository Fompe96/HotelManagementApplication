import java.time.LocalDate;
import java.util.Scanner;

public class HotelApp {
    Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        // temporary object with method call used to try "available rooms in time period."
        HotelApp myHotel = new HotelApp();
        myHotel.availableInTimeMenu();
    }
    // menu for "available rooms in time period"
    private void availableInTimeMenu(){
        HotelApp myHotel = new HotelApp();
        HotelLogic myApp = new HotelLogic();

        try {
            System.out.println("Enter desired date for check in: ");
            System.out.print("yyyy: ");
            int yyyy = input.nextInt();
            System.out.print("mm: ");
            int mm = input.nextInt();
            System.out.print("dd: ");
            int dd = input.nextInt();

            LocalDate in = LocalDate.of(yyyy, mm, dd);
            System.out.println("Enter desired date for check out: ");
            System.out.print("yyyy: ");
            yyyy = input.nextInt();
            System.out.print("mm: ");
            mm = input.nextInt();
            System.out.print("dd: ");
            dd = input.nextInt();
            LocalDate out = LocalDate.of(yyyy, mm, dd);
            if (in.compareTo(LocalDate.now()) < 0){
                System.out.println("It is not possible to check in too a date in history, try again.");
                myHotel.availableInTimeMenu();
            }else if (out.compareTo(LocalDate.now().plusYears(25)) > 0){
                System.out.println("maximum time to book in the future is 25 years, try again.");
                myHotel.availableInTimeMenu();
            }else{
               System.out.println(myApp.availableInTime(in, out));
            }
        }catch (Exception e){
           System.out.println("Something went wrong, try again.");
            myHotel.availableInTimeMenu();
        }

    }

    public void addNewCustomer() {
        Scanner input = new Scanner(System.in);
        ArrayList<Customer> customers = new ArrayList<>();

        System.out.println("\n------------------------------");
        System.out.println("< Enter customer information > \n");
        System.out.print("Name: ");
        String customerName = input.nextLine();
        System.out.print("SSN: ");
        String customerSSN = input.nextLine();
        System.out.print("Adress: ");
        String customerAdress = input.nextLine();
        System.out.print("Phone number: ");
        String customerPhoneNumber = input.nextLine();
        System.out.println("------------------------------\n");

        Customer customerInfo = new Customer(customerName, customerSSN, customerAdress, customerPhoneNumber);
        customers.add(customerInfo);
}
