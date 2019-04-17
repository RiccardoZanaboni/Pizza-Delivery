public class Pizza {
    private String nome;
    private String descrizione;
    private double prezzo;

    public Pizza(String nome, String descrizione, double prezzo) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "- Pizza" + "\t" + nome + "\t\t\t" + prezzo + "€\n\t\tIngredienti: " + descrizione;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public double getPrezzo() {
        return prezzo;
    }
}
