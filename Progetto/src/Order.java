import java.util.ArrayList;
import java.util.Date;

public class Order {
    private String codice;
    private String indirizzo;
    private Date orario;
    private ArrayList <Pizza> pizzeordinate;


    public Order(String codice, String indirizzo, Date orario) {
        this.codice = codice;
        this.indirizzo = indirizzo;
        this.orario = orario;
        this.pizzeordinate=new ArrayList<>();
    }

    public void AddPizza(Pizza pizza){
        pizzeordinate.add(pizza);
    }
}
