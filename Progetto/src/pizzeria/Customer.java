package pizzeria;

public class Customer {
    private String username;

    /**
     * Definisce il cliente attraverso il suo username univoco.
     */
    public Customer(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
