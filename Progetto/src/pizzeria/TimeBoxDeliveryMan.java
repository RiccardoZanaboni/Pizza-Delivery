package pizzeria;

class TimeBoxDeliveryMan {
    private boolean isBusy;
    private int orders;

    TimeBoxDeliveryMan() {
        this.isBusy = false;
        this.orders = 0;
    }

    void insertOrder() {
        this.orders++;
        if(this.orders == 2)      // un fattorino non può consegnare più di 2 orders per volta
            this.isBusy = true;
    }

    boolean isBusy() {
        return isBusy;
    }
}
