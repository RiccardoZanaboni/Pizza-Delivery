package pizzeria;

import pizzeria.Customer;
import pizzeria.Ingredienti;
import pizzeria.Pizza;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Order {
    private Customer customer;
    private String codice;
    private String indirizzo;
    private Date orario;
    private ArrayList<Pizza> pizzeordinate;
    private boolean completo;
    private int countPizzeModificate;

    public Order(int num) {
        this.customer = null;
        this.codice = "ORD-" + num;
        this.indirizzo = "";
        this.orario = null;
        this.pizzeordinate = new ArrayList<>();
        this.completo = false;
        this.countPizzeModificate=0;
    }

    public int getCountPizzeModificate() {
        return countPizzeModificate;
    }

    public void setCountPizzeModificate() {
        this.countPizzeModificate++;
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

    public void addPizza(Pizza pizza, int num) {
        for (int i = 0; i < num; i++) {
            pizzeordinate.add(pizza);
        }
        System.out.println("\t> Aggiunte " + num + " pizze " + pizza.getNomeMaiusc());
    }

    public String recap() {
        StringBuilder prodotti = new StringBuilder();
        ArrayList<Pizza> elencate = new ArrayList<>();
        for (int i = 0; i < getNumeroPizze(); i++) {

            Pizza p = pizzeordinate.get(i);

            int num = 0;
            if (!(elencate.contains(p))) {
                elencate.add(p);
                for (int j = 0; j < getNumeroPizze(); j++) {
                    if (p.equals(getPizzeordinate().get(j)))
                        num++;
                }
                prodotti.append("\t").append(num).append("\t").append(p.getNomeMaiusc()).append("\t\t").append(p.getDescrizione()).append("\t\t-->\t").append(num * p.getPrezzo()).append("€\n");
            }
        }
        return prodotti.toString();
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

    public boolean searchPizza(Pizza pizza){
        for (int i=0; i<pizzeordinate.size();i++){
           if(pizzeordinate.get(i)==pizza)
               return true;
        }
    return false;
    }
}