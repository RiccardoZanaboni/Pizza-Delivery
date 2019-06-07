package pizzeria;

public class Customer {
    private String username;
    private String password;
    private boolean loggedIn;
    private String address;
    private String name;
    private String surname;

    /**
     * Definisce il cliente attraverso il suo username univoco.
     */
    public Customer(String username,String password) {
        this.username = username;
        this.name = "";
        this.surname= "";
        this.loggedIn = false;
        this.address = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
