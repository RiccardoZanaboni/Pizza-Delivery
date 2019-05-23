package pizzeria;

public class DeliveryMan {
    private String name;
    private TimeBoxDeliveryMan[] deliveryManTimes;
    private final int DELIVERYMAN_TIMES_FOR_HOURS = 6;   // ogni 10 minuti

    public DeliveryMan(String name, Pizzeria pizzeria) {
        this.name = name;
        this.deliveryManTimes = new TimeBoxDeliveryMan[DELIVERYMAN_TIMES_FOR_HOURS * pizzeria.getClosingTime().getHours() - pizzeria.getOpeningTime().getHours()];
        for(int i = 0; i < deliveryManTimes.length; i++){
            deliveryManTimes[i] = new TimeBoxDeliveryMan();
        }
    }

    /** assegna una consegna al fattorino in un certo orario. */
    void assignDelivery(int i) {
        this.deliveryManTimes[i].insertOrder();
    }


    TimeBoxDeliveryMan[] getDeliveryManTimes() {
        return deliveryManTimes;
    }

    public void setDeliveryManTimes(TimeBoxDeliveryMan[] deliveryManTimes) {
        this.deliveryManTimes = deliveryManTimes;
    }
}
