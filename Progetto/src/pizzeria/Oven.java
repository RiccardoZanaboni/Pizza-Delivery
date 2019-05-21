package pizzeria;

public class Oven {
    private int availablePlaces;

    Oven(int avail) {
        this.availablePlaces = avail;
    }

    public int getPostiDisp() {
        return availablePlaces;
    }

    void inserisciInfornate(int pizze){
        this.availablePlaces -= pizze;
    }
}
