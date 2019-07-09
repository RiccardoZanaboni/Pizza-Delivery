package pizzeria;

public class Customer {
    private String username;
    private String address;
    private String name;
    private String surname;

    /**
     * Definisce il cliente attraverso il suo username univoco.
     */
    public Customer(String username) {
        this.username = username;
        this.name = "";
        this.surname = "";
        this.address = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
