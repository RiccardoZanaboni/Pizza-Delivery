import java.util.HashMap;

public class PizzaMenu {
    private String nome;
    //private String descrizione;
    private double prezzo;
    //private String modifiche;
    private HashMap <String, Ingredienti> ingredienti;

    public PizzaMenu(String nome, HashMap ingr, double prezzo) {
        this.nome = nome;
        //this.descrizione = descrizione;
        this.prezzo = prezzo;
        //this.modifiche = descrizione;
        this.ingredienti = ingr;
    }

    @Override
    public String toString() {
        String descrizione = this.getDescrizione();
        return "- PizzaMenu" + "\t" + nome + "\n\t\tPrezzo: " + prezzo + " €\n\t\tIngredienti: " + descrizione;
    }

    public String getNome() {
        return nome;
    }

    public HashMap<String, Ingredienti> getIngredienti() {
        return ingredienti;
    }

    public String getDescrizione() {
        String descrizione = "";
        for (Ingredienti ingr: this.ingredienti.values()) {
            descrizione = descrizione.concat(ingr.name() + ", ");
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

    /*public String getModifiche() {
        return modifiche;
    }

    public void setModifiche(String modifiche) {
        this.modifiche = modifiche;
    }*/

    public double getPrezzo() {
        return prezzo;
    }
}
