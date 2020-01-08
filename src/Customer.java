import java.util.ArrayList;

public class Customer {
    private String customerName;
    private String customerSSN;
    private String customerAddress;
    private String customerPhoneNumber;
    private ArrayList<Booking> bookingList = new ArrayList<>();

    public Customer(String customerName, String customerSSN, String customerAdress, String customerPhoneNumber) {
        this.customerName = customerName;
        this.customerSSN = customerSSN;
        this.customerAddress = customerAdress;
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

    public String getCustomerSsn() {
        return customerSSN;
    }

    public void setCustomerSsn(String customerSsn) {
        this.customerSSN = customerSsn;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAdress) {
        this.customerAddress = customerAdress;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    @Override
    public String toString() {
        return "Name: " + getCustomerName() + "\n" +
                "Address: " + getCustomerAddress() + "\n" +
                "Telephone number: " + getCustomerPhoneNumber();
    }
}
