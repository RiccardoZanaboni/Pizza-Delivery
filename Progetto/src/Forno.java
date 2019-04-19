public class Forno {
    private int postiDisponibili;
    private int postiOccupati;

    public Forno(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
        this.postiOccupati = 0;
    }

    public int getPostiDisp() {
        return postiDisponibili;
    }

    public void inserisciInfornate(int pizze){
        this.postiDisponibili-=pizze;
    }
}
