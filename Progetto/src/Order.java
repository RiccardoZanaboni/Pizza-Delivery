import java.util.ArrayList;
import java.util.Date;

public class Order {
    private Customer customer;
    private String codice;
    private String indirizzo;
    private Date orario;
    private ArrayList <Pizza> pizzeordinate;
    private boolean completo;

    public Order(Customer customer,String codice, String indirizzo/*, Date orario*/) {
        this.customer = customer;
        this.codice = codice;
        this.indirizzo = indirizzo;
        this.orario = orario;
        this.pizzeordinate=new ArrayList<>();
        this.completo = false;
    }

    public void AddPizza(Pizza pizza){
        pizzeordinate.add(pizza);
    }

    public void setCustomer(Customer c){
        this.customer = c;
    }

    public void setIndirizzo(String indirizzo){
        this.indirizzo = indirizzo;
    }

    public void setCompleto(){
        this.completo= true;
    }
}
