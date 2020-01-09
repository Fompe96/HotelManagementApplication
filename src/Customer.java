import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
    private static final long serialversionUID = 3L;
    private String customerName;
    private String customerSSN;
    private String customerAddress;
    private String customerPhoneNumber;
    private String customerPassword;

    public Customer(String customerName, String customerSSN, String customerAdress, String customerPhoneNumber) {
        this.customerName = customerName;
        this.customerSSN = customerSSN;
        this.customerAddress = customerAdress;
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerName() {
        return customerName;
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
    public void setCustomerPassword(String customerPassword){
        this.customerPassword = customerPassword;
    }

    public String getCustomerPassword() {
        return customerPassword;
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
