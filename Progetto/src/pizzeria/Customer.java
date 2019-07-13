package pizzeria;

public class Customer {
    private String username;

    /**
     * Definisce il cliente attraverso il suo username univoco.
     * Al cliente viene sempre fatto riferimento attraverso i dati salvati nel DB.
     */
    public Customer(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}