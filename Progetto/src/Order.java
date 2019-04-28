import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;


public class Order {
    private Customer customer;
    private String codice;
    private String indirizzo;
    private Date orario;
    private ArrayList <PizzaOrdinata> pizzeordinate;
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

    public ArrayList<PizzaOrdinata> getPizzeordinate() {
        return pizzeordinate;
    }

    public String getCodice() {
        return codice;
    }

    public void addPizza(PizzaMenu p, int num) {
        PizzaOrdinata pizza = new PizzaOrdinata(p.getNome(), p.getIngredienti(), p.getPrezzo());
        for(int i=0; i<num; i++) {
            pizzeordinate.add(pizza);
        }
    }

    public void addPizza(PizzaMenu p, String aggiunte, String rimozioni, int num, double prezzoSuppl) {
        PizzaOrdinata pizza = new PizzaOrdinata(p.getNome(), p.getIngredienti(), p.getPrezzo());
        HashMap <String, Ingredienti> ingredientiModificati = pizza.getIngredienti();
        StringTokenizer stAgg = new StringTokenizer(aggiunte);
        int piu=0;
        int meno=0;
        while (stAgg.hasMoreTokens()) {
            try {
                Ingredienti ingr = Ingredienti.valueOf(stAgg.nextToken(", ").toUpperCase());
                //ingredientiModificati.put(ingr.name(), ingr);   // è qui il problema!!! va a cambiare il menu!
                pizza.addIngredienti(ingr);
            } catch (Exception ignored) { ;}
        }
        StringTokenizer stRmv = new StringTokenizer(rimozioni);
        while (stRmv.hasMoreTokens()) {
            try {
                Ingredienti ingr = Ingredienti.valueOf(stRmv.nextToken(", ").toUpperCase());
                //ingredientiModificati.remove(ingr.name());   // è qui il problema!!! va a cambiare il menu!
                pizza.rmvIngredienti(ingr);
            } catch (Exception ignored) { ;}
        }
        for (int i = 0; i < num; i++) {
            pizzeordinate.add(pizza);
        }
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

    public String recap(){
        String prodotti = "";
        ArrayList elencate = new ArrayList <PizzaMenu>();
        for (int i = 0; i < getNumeroPizze(); i++) {
            PizzaOrdinata p = getPizzeordinate().get(i);
            int num = 0;
            if (!(elencate.contains(p))) {
                elencate.add(p);
                for (int j = 0; j < getNumeroPizze(); j++) {
                    if (p.equals(getPizzeordinate().get(j)))
                        num++;
                }
                prodotti += "\t" + num + "\t" + p.getNome() + "\t\t" + p.getDescrizione() + "\t\t-->\t" + num*p.getPrezzo() + "€\n";
            }
        }
        return prodotti;
    }

    public double getTotaleCosto() {
        double totale = 0;
        for(int i=0; i<getNumeroPizze(); i++){
            totale += pizzeordinate.get(i).getPrezzo();
        }
        return totale;
    }
}