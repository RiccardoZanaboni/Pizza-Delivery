package pizzeria;

import javafx.scene.paint.Color;
import pizzeria.services.SettleStringsServices;
import pizzeria.services.TextColorServices;

import java.util.HashMap;

public class Pizza {
    private String name;
    private double price;
    private HashMap <String, String> ingredients;
    private int count = 0;

    /**
     * Il prodotto Pizza è caratterizzato da un nome, un prezzo ed una lista di ingredienti.
     * Rispetto alle pizze del menu, gli ingredienti (e conseguentemente il prezzo)
     * possono variare su richiesta del cliente.
     */

    public Pizza(String name, HashMap<String, String> ingred, double price) {
        this.name = name;
        this.price = price;
        this.ingredients = ingred;
    }

    @Override
    public String toString() {
        String descrizione = this.getDescription();
        String nome = TextColorServices.colorSystemOut(this.name, Color.YELLOW, false, true);
        String prezzo = TextColorServices.colorSystemOut("\n\t\tPrezzo: ",Color.ORANGE,false,false);
        String ingr = TextColorServices.colorSystemOut("\n\t\tIngredienti: ",Color.ORANGE,false,false);
        return "- " + nome + prezzo + this.price + " €" + ingr + descrizione;
    }

    /** Restituisce il nome (così come è salvato, se non è da visualizzare) */
    public String getName(boolean isToVisualize) {
        if(isToVisualize)
            return SettleStringsServices.getSettledName(this.name);
        else
            return this.name;
    }

    public HashMap<String, String> getToppings() {
        return this.ingredients;
    }

    /** Restituisce un'unica String che corrisponde alla lista degli ingredienti della pizza. */
    public String getDescription() {
        String descrizione = "";
        for (String ingr: this.ingredients.values()) {
            descrizione = descrizione.concat(ingr.replace("_"," ") + ", ");
        }
        descrizione = descrizione.toLowerCase().substring(0, descrizione.lastIndexOf(","));
        return descrizione;
    }

    /** Se assente, aggiunge l'ingrediente richiesto alla pizza. */
    public void addIngredients(String ing){
        if(!(this.ingredients.containsKey(ing)))
            this.ingredients.put(ing,ing);
    }

    /** Se presente, rimuove l'ingrediente selezionato dalla pizza. */
    public void rmvIngredients(String ing){
        if(this.ingredients.containsKey(ing))
            this.ingredients.remove(ing,ing);
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

    public String getName() {
        return name;
    }       // non cancellatelo

    /** Override del metodo che viene usato da contains in modo da non confrontare il puntatore ma gli attributi dell'oggetto*/

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Pizza)) {
            return false;
        }

        Pizza p = (Pizza) obj;
        return p.name.equals(this.name) && p.ingredients.equals(this.ingredients) && p.price==this.price;
    }
}
