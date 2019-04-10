import java.util.Date;

public class Order {
    private String codice;
    private String indirizzo;
    private Date orario;

    public Order(String codice, String indirizzo, Date orario) {
        this.codice = codice;
        this.indirizzo = indirizzo;
        this.orario = orario;
    }
}
