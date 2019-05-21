package pizzeria;

public class DeliveryMan {
    private String name;
    private TimeBoxDeliveryMan deliveryManTimes[];
    public final int DELIVERYMAN_TIMES_FOR_HOURS = 6;   // ogni 10 minuti

    public DeliveryMan(String name, Pizzeria pizzeria) {
        this.name = name;
        this.deliveryManTimes = new TimeBoxDeliveryMan[DELIVERYMAN_TIMES_FOR_HOURS * pizzeria.getClosingTime().getHours() - pizzeria.getOpeningTime().getHours()];
        for(int i = 0; i< deliveryManTimes.length; i++){
            deliveryManTimes[i]=new TimeBoxDeliveryMan();
        }
    }

    public TimeBoxDeliveryMan[] getDeliveryManTimes() {
        return deliveryManTimes;
    }

    public void OccupaFattorino(int i) {
        this.deliveryManTimes[i].insertOrder();
    }

    public void setDeliveryManTimes(TimeBoxDeliveryMan[] deliveryManTimes) {
        this.deliveryManTimes = deliveryManTimes;
    }
}
