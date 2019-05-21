package pizzeria;

import java.util.HashMap;

public class Pizza {
    private String name;
    private double price;
    private HashMap <String, Ingredients> ingredients;
    private int count=0;

    public Pizza(String name, HashMap<String, Ingredients> ingred, double price) {
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

    public HashMap<String, Ingredients> getIngredients() {
        return ingredients;
    }

    public String getDescription() {
        String descrizione = "";
        for (Ingredients ingr: this.ingredients.values()) {
            descrizione = descrizione.concat(ingr.name().replace("_"," ") + ", ");
        }
        descrizione = descrizione.toLowerCase().substring(0, descrizione.lastIndexOf(","));
        return descrizione;
    }

    public void addIngredients(Ingredients ing){
        if(!(ingredients.containsKey(ing.name())))
            ingredients.put(ing.name(),ing);
    }

    public void rmvIngredients(Ingredients ing){
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
