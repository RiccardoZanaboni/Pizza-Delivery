package pizzeria;

class TimeBoxDeliveryMan {
    private boolean isFree;
    private int orders;
    private final int maxDeliveries = 2;    // un fattorino non può consegnare più di 2 orders per volta

    /**
     * La "casella temporale" del fattorino, ovvero l'arco di tempo (definito
     * nella classe Pizzeria) in cui il fattorino può effettuare al massimo due consegne.
     */

    TimeBoxDeliveryMan() {
        this.isFree = true;
        this.orders = 0;
    }

    void insertOrder() {
        this.orders++;
        if(this.orders == maxDeliveries)
            this.isFree = false;
    }

    boolean isFree() {
        return isFree;
    }
}
