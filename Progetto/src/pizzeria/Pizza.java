package pizzeria;

import javafx.scene.paint.Color;
import java.util.HashMap;

public class Pizza {
    private String name;
    private double price;
    private HashMap <String, Toppings> ingredients;
    private int count = 0;

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

    /** Restituisce il nome (così come è salvato) */
    public String getName(boolean isToVisualize) {
        if(isToVisualize)
            return Services.getSettledName(this.name);
        else
            return this.name;
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

    public void setIngredients(Toppings ingredients) {
        this.ingredients.put(ingredients.name(), ingredients);
    }

    /** Se presente, rimuove l'ingrediente selezionato dalla pizza. */
    public void rmvIngredients(Toppings ing){
        if(this.ingredients.containsKey(ing.name()))
            this.ingredients.remove(ing.name(),ing);
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double p) {
        this.price = p;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCount(boolean incr) {
        if (incr)
            this.count++;
        else
            this.count--;
    }
}
