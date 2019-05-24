package pizzeria;

import java.util.HashMap;

public class Pizza {
    private String name;
    private double price;
    private HashMap <String, Toppings> ingredients;
    private int count = 0;      // FIXME: ma che senso ha "count" in Pizza?

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
        return "- " + name + "\n\t\tPrezzo: " + price + " €\n\t\tIngredienti: " + descrizione;
    }

    public String getMaiuscName() {
        return name.toUpperCase();
    }

    /** Restituisce il nome della pizza, con tutte le iniziali maiuscole. */
    public String getCamelName() {
        String nome = this.name.toUpperCase();
        nome = nome.charAt(0) + nome.substring(1).toLowerCase();
        for(int i=1; i<nome.length(); i++){
            if(nome.substring(i,i+1).equals("_") || nome.substring(i,i+1).equals(" ")){
                nome = nome.replace(nome.substring(i,i+1)," ");
                nome = nome.replace(nome.substring(i+1,i+2),nome.substring(i+1,i+2).toUpperCase());
            }
        }
        return nome;
    }

    public HashMap<String, Toppings> getToppings() {
        return ingredients;
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
        if(!(ingredients.containsKey(ing.name())))
            ingredients.put(ing.name(),ing);
    }

    /** Se presente, rimuove l'ingrediente selezionato dalla pizza. */
    public void rmvIngredients(Toppings ing){
        if(ingredients.containsKey(ing.name()))
            ingredients.remove(ing.name(),ing);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double prezzo) {
        this.price = prezzo;
    }

    public int getCount() {
        return count;
    }

    public void increaseCount() {
        this.count++;
    }

    public void decreaseCount() {
        this.count--;
    }
}
