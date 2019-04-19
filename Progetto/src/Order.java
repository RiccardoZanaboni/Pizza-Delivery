import java.util.ArrayList;
import java.util.Date;

public class Order {
    private Customer customer;
    private String codice;
    private String indirizzo;
    private Date orario;
    private ArrayList <Pizza> pizzeordinate;
    private boolean completo;

    public Order(int codice) {
        this.customer = null;
        this.codice = "ORD - " + codice;
        this.indirizzo = "";
        this.orario = null;
        this.pizzeordinate = new ArrayList<>();
        this.completo = false;
    }

    public String getCodice() {
        return codice;
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

    public void setOrario(Date orario) {
        this.orario = orario;
    }

    public int getNumeroPizze(){
        return pizzeordinate.size();
    }

}
