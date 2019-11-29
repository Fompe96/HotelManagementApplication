import java.util.ArrayList;

public class Customer {
    private String customerName;
    private String customerSSN;
    private String customerAdress;
    private String customerPhoneNumber;
    private ArrayList<Booking> bookingList = new ArrayList<>();

    public Customer(String customerName, String customerSSN, String customerAdress, String customerPhoneNumber) {
        this.customerName = customerName;
        this.customerSSN = customerSSN;
        this.customerAdress = customerAdress;
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ArrayList<Booking> getBookings() {
        return bookingList;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerSSN() {
        return customerSSN;
    }

    public void setCustomerSSN(String customerSSN) {
        this.customerSSN = customerSSN;
    }

    public String getCustomerAdress() {
        return customerAdress;
    }

    public void setCustomerAdress(String customerAdress) {
        this.customerAdress = customerAdress;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }
}
