package tapandtake.model;

/**
 * This class contains the customer information
 * @author Hamnah Atif
 */
public class Customer {

    private long phoneNum;
    private String name;
    private String password;

    /**
     * Three arguments constructor to create a Customer
     * @param phoneNum this variable will store the user's phone number
     * @param name this variable will store the name of the customer
     * @param password this variable will store the password of the customer/ user
     */
    public Customer(long phoneNum, String name, String password) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.password = password;
    }

    /**
    * @param lineIn this variable will be used to split the user's information
    * @throws IllegalArgumentException
    */
    
    Customer(String lineIn) throws IllegalArgumentException {
        String[] lineInSplit = lineIn.split(",");

        try {
            phoneNum = Integer.parseInt(lineInSplit[0]);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Phone Number should be in digits");
        }

        name = lineInSplit[1];
        password = lineInSplit[2];
    }

    public String getName() {
        return name;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}