public class Pizza {
    private String nome;
    private String descrizione;
    private double prezzo;
    private String modifiche;

    public Pizza(String nome, String descrizione, double prezzo) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.modifiche=descrizione;
    }

    @Override
    public String toString() {
        return "- Pizza" + "\t" + nome + "\t\t\t" + prezzo + "€\n\t\tIngredienti: " + descrizione;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getModifiche() {
        return modifiche;
    }

    public void setModifiche(String modifiche) {
        this.modifiche = modifiche;
    }

    public double getPrezzo() {
        return prezzo;
    }
}
