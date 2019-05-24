package pizzeria;

class TimeBoxDeliveryMan {
    private boolean isBusy;
    private int orders;
    final int maxDeliveries = 2;

    /**
     * La "casella temporale" del fattorino, ovvero l'arco di tempo (definito
     * nella classe Pizzeria) in cui il fattorino può effettuare al massimo due consegne.
     */

    TimeBoxDeliveryMan() {
        this.isBusy = false;
        this.orders = 0;
    }

    void insertOrder() {
        this.orders++;
        if(this.orders == maxDeliveries)    // un fattorino non può consegnare più di 2 orders per volta
            this.isBusy = true;
    }

    boolean isBusy() {
        return isBusy;
    }
}
