package pizzeria;

@SuppressWarnings("deprecation")
public class TimeBoxDeliveryMan {
    private boolean isFree;
    private int orders;
    private final int MAX_DELIVERIES = 2;

    /**
     * La "casella temporale" del fattorino, ovvero l'arco di tempo (la cui durata è definita
     * nella classe Pizzeria) in cui il fattorino può effettuare al massimo TOT=2 consegne.
     */

    TimeBoxDeliveryMan() {
        this.isFree = true;
        this.orders = 0;
    }

    /** Aggiunge una consegna al fattorino, in questo TimeBox. Nello stesso TimeBox il fattorino
     * non può farsi carico di più di TOT consegne. */
    void insertOrder() {
        this.orders++;
        if(this.orders == MAX_DELIVERIES)
            this.isFree = false;
    }

    public boolean isFree() {
        return isFree;
    }
}
