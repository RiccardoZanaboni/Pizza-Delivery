package pizzeria;

/**
 * Ogni istanza di Oven è da considerarsi una infornata, ad un certo orario,
 * con un dato numero di posti disponibili ed una durata di cottura, definiti nella classe Pizzeria.
 */

public class Oven {
    private int availablePlaces;

    Oven(int avail) {
        this.availablePlaces = avail;
    }

    public int getAvailablePlaces() {
        return this.availablePlaces;
    }

    void updateAvailablePlaces(int pizze){
        this.availablePlaces -= pizze;
    }
}
