import java.util.ArrayList;
import java.util.Date;

public class Order {
    private Customer customer;
    private String codice;
    private String indirizzo;
    private Date orario;
    private ArrayList <Pizza> pizzeordinate;


    public Order(Customer customer,String codice, String indirizzo/*, Date orario*/) {
        this.customer = customer;
        this.codice = codice;
        this.indirizzo = indirizzo;
        this.orario = orario;
        this.pizzeordinate=new ArrayList<>();
    }

    public void AddPizza(Pizza pizza){
        pizzeordinate.add(pizza);
    }
}
