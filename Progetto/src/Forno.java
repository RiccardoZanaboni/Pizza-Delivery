public class Forno {
    private int postiDisponibili;
    private int postiOccupati;

    public Forno(int postiDisponibili, int postiOccupati) {
        this.postiDisponibili = postiDisponibili;
        this.postiOccupati = postiOccupati;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }
}
