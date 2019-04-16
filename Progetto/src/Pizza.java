public class Pizza {
    private String nome;
    private String descrizione;
    private int prezzo;

    public Pizza(String nome, String descrizione, int prezzo) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "- Pizza"+"\t"+nome+"\t"+"\t"+"\t"+prezzo+"€"+"\n"+"\t"+"\t"+descrizione;
    }

    public String getNome() {
        return nome;
    }
}
