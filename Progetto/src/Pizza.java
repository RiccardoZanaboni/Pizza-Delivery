import java.util.HashMap;

public class Pizza {
    private String nome;
    private double prezzo;
    private HashMap <String, Ingredienti> ingredienti;
    private int count=0;

    public Pizza(String nome, HashMap ingred, double prezzo) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.ingredienti = ingred;
    }

    @Override
    public String toString() {
        String descrizione = this.getDescrizione();
        return "- " + nome + "\n\t\tPrezzo: " + prezzo + " €\n\t\tIngredienti: " + descrizione;
    }

    public String getNomeMaiusc() {
        return nome;
    }

    public String getNomeCamel() {
        String nome = this.nome;
        nome = nome.charAt(0) + nome.substring(1).toLowerCase();
        return nome;
    }

    public HashMap<String, Ingredienti> getIngredienti() {
        return ingredienti;
    }

    public String getDescrizione() {
        String descrizione = "";
        for (Ingredienti ingr: this.ingredienti.values()) {
            descrizione = descrizione.concat(ingr.name().replace("_"," ") + ", ");
        }
        descrizione = descrizione.toLowerCase().substring(0, descrizione.lastIndexOf(","));
        return descrizione;
    }

    public void addIngredienti(Ingredienti ing){
        if(!(ingredienti.containsKey(ing.name())))
            ingredienti.put(ing.name(),ing);
    }

    public void rmvIngredienti(Ingredienti ing){
        if(ingredienti.containsKey(ing.name()))
            ingredienti.remove(ing.name(),ing);
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getCount() {
        return count;
    }

    public void setCount() {
        this.count++;
    }
    public void resetCount() {
        this.count--;
    }
}
