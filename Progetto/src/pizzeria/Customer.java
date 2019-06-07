package pizzeria;

public class Customer {
    private String username;
    private String password;
    private boolean loggedIn;
    private String address;

    /**
     * Definisce il cliente attraverso il suo username univoco.
     */
    public Customer(String username,String password) {
        this.username = username;
        this.loggedIn = false;
        this.address = "";
    }

    public String getUsername() {
        return username;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
