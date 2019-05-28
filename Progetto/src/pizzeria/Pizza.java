package pizzeria;

import java.awt.*;
import java.util.HashMap;

public class Pizza {
    private String name;
    private double price;
    private HashMap <String, Toppings> ingredients;
    private int count = 0;

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Il prodotto Pizza è caratterizzato da un nome, un prezzo ed una lista di ingredienti.
     * Rispetto alle pizze del menu, gli ingredienti (e conseguentemente il prezzo)
     * possono variare su richiesta del cliente.
     */

    public Pizza(String name, HashMap<String, Toppings> ingred, double price) {
        this.name = name;
        this.price = price;
        this.ingredients = ingred;
    }

    @Override
    public String toString() {
        String descrizione = this.getDescription();
        String nome = Services.colorSystemOut(this.name, Color.YELLOW, false, true);
        String prezzo = Services.colorSystemOut("\n\t\tPrezzo: ",Color.ORANGE,false,false);
        String ingr = Services.colorSystemOut("\n\t\tIngredienti: ",Color.ORANGE,false,false);
        return "- " + nome + prezzo + this.price + " €" + ingr + descrizione;
    }

    public String getMaiuscName() {
        return this.name.toUpperCase();
    }

    public HashMap<String, Toppings> getToppings() {
        return this.ingredients;
    }

    /** Restituisce un'unica String che corrisponde alla lista degli ingredienti della pizza. */
    public String getDescription() {
        String descrizione = "";
        for (Toppings ingr: this.ingredients.values()) {
            descrizione = descrizione.concat(ingr.name().replace("_"," ") + ", ");
        }
        descrizione = descrizione.toLowerCase().substring(0, descrizione.lastIndexOf(","));
        return descrizione;
    }

    /** Se assente, aggiunge l'ingrediente richiesto alla pizza. */
    public void addIngredients(Toppings ing){
        if(!(this.ingredients.containsKey(ing.name())))
            this.ingredients.put(ing.name(),ing);
    }

    /** Se presente, rimuove l'ingrediente selezionato dalla pizza. */
    public void rmvIngredients(Toppings ing){
        if(this.ingredients.containsKey(ing.name()))
            this.ingredients.remove(ing.name(),ing);
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double prezzo) {
        this.price = prezzo;
    }

    public int getCount() {
        return this.count;
    }

    public void increaseCount() {
        this.count++;
    }

    public void decreaseCount() {
        this.count--;
    }
}
