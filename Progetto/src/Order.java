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

    public Customer getCustomer() {
        return customer;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public Date getOrario() {
        return orario;
    }

    public ArrayList<Pizza> getPizzeordinate() {
        return pizzeordinate;
    }

    public String getCodice() {
        return codice;
    }

    public void addPizza(Pizza pizza)  {
        Pizza p=new Pizza(pizza.getNome(),pizza.getDescrizione(),pizza.getPrezzo());
        pizzeordinate.add(p);
    }

    public void addPizza(Pizza pizza, String descriz)  {
        Pizza p=new Pizza(pizza.getNome(),pizza.getDescrizione(),pizza.getPrezzo());
        pizzeordinate.add(p);
        pizzeordinate.get(pizzeordinate.size()-1).setModifiche(pizzeordinate.get(pizzeordinate.size()-1).getDescrizione() + " " + descriz);
    }

    public void setCustomer(Customer c){
        this.customer = c;
    }

    public void setIndirizzo(String indirizzo){
        this.indirizzo = indirizzo;
    }

    public boolean isCompleto() {
        return completo;
    }

    public void setCompleto(){
        this.completo= true;
        System.out.println("\nGrazie! L'ordine è stato effettuato correttamente.");
    }

    public void setOrario(Date orario) {
        this.orario = orario;
    }

    public int getNumeroPizze(){
        return pizzeordinate.size();
    }

}