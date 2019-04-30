import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Order {
    private Customer customer;
    private String codice;
    private String indirizzo;
    private Date orario;
    private ArrayList<PizzaMenu> pizzeordinate;
    private boolean completo;

    public Order(int num) {
        this.customer = null;
        this.codice = "ORD-" + num;
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

    public ArrayList<PizzaMenu> getPizzeordinate() {
        return pizzeordinate;
    }

    public String getCodice() {
        return codice;
    }

    public void addPizza(PizzaMenu pizza, int num) {
        for (int i = 0; i < num; i++) {
            pizzeordinate.add(pizza);
        }
        System.out.println("\t> Aggiunte " + num + " pizze " + pizza.getNome());
    }

    public void addPizza(PizzaMenu pizza, String aggiunte, String rimozioni, int num, double prezzoSupl) {
        HashMap<String, Ingredienti> ingr = new HashMap<>(pizza.getIngredienti());
        PizzaMenu p = new PizzaMenu(pizza.getNome(), ingr, pizza.getPrezzo());
        int piu=0;
        StringTokenizer stAgg = new StringTokenizer(aggiunte);
        while (stAgg.hasMoreTokens()) {
            try {
                String ingredienteAggiuntoString = sistemaStringaIngrediente(stAgg);
                Ingredienti ingredienti = Ingredienti.valueOf(ingredienteAggiuntoString);
                piu++;
                p.addIngredienti(ingredienti);
            } catch (Exception ignored) { ;}
        }
        p.setPrezzo(p.getPrezzo() + (piu * prezzoSupl));        // aggiunto 0.50 per ogni ingrediente
        StringTokenizer stRmv = new StringTokenizer(rimozioni);
        while (stRmv.hasMoreTokens()) {
            try {
                String ingredienteRimossoString = sistemaStringaIngrediente(stRmv);
                Ingredienti ingredienti = Ingredienti.valueOf(ingredienteRimossoString);
                p.rmvIngredienti(ingredienti);
            } catch (Exception ignored) { ;}
        }
        for (int i = 0; i < num; i++) {
            pizzeordinate.add(p);
        }
        System.out.println("\t> Aggiunte " + num + " pizze " + p.getNome() + " (" + p.getDescrizione() + ").");
    }

    private String sistemaStringaIngrediente(StringTokenizer st){
        String ingred = st.nextToken(",");
        if(ingred.startsWith(" "))
            ingred = ingred.substring(1);
        if(ingred.endsWith(" "))
            ingred = ingred.substring(0,ingred.length()-1);
        ingred = ingred.replace(" ","_");
        ingred = ingred.toUpperCase();
        return ingred;
    }

    public String recap() {
        String prodotti = "";
        ArrayList elencate = new ArrayList<PizzaMenu>();
        for (int i = 0; i < getNumeroPizze(); i++) {

            PizzaMenu p = pizzeordinate.get(i);

            int num = 0;
            if (!(elencate.contains(p))) {
                elencate.add(p);
                for (int j = 0; j < getNumeroPizze(); j++) {
                    if (p.equals(getPizzeordinate().get(j)))
                        num++;
                }
                prodotti += "\t" + num + "\t" + p.getNome() + "\t\t" + p.getDescrizione() + "\t\t-->\t" + num * p.getPrezzo() + "€\n";
            }
        }
        return prodotti;
    }

    public void setCustomer(Customer c) {
        this.customer = c;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public boolean isCompleto() {
        return completo;
    }

    public void setCompleto() {
        this.completo = true;
        System.out.println("\nGrazie! L'ordine è stato effettuato correttamente.");
    }

    public void setOrario(Date orario) {
        this.orario = orario;
    }

    public int getNumeroPizze() {
        return pizzeordinate.size();
    }

    public double getTotaleCosto() {
        double totale = 0;
        for(int i=0; i<getNumeroPizze(); i++){
            totale += pizzeordinate.get(i).getPrezzo();
        }
        return totale;
    }
}