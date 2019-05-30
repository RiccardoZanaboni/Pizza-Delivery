package pizzeria;

public class Customer {
    private String username;
    private String password;

    /**
     * Definisce il cliente attraverso il suo username univoco.
     */
    public Customer(String username,String password) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
