package pizzeria;

public class Forno {
    private int postiDisponibili;

    public Forno(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    public int getPostiDisp() {
        return postiDisponibili;
    }

    public void inserisciInfornate(int pizze){
        this.postiDisponibili -= pizze;
    }
}
