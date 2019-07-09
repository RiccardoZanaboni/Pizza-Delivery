package pizzeria;

import services.TimeServices;

@SuppressWarnings("deprecation")
public class DeliveryMan {
    private String name;
    private TimeBoxDeliveryMan[] deliveryManTimes;

    /**
     * Il fattorino, identificato da un nome, fa partire una consegna ogni 10 minuti.
     * Se necessario, nello slot orario di 10 minuti, può portare fino a 2 ordinazioni.
     */

    public DeliveryMan(String name, Pizzeria pizzeria) {
        this.name = name;
        int startMinutes = TimeServices.getMinutes(pizzeria.getOpeningToday());
        int endMinutes = TimeServices.getMinutes(pizzeria.getClosingToday());
        int length = (endMinutes-startMinutes)/pizzeria.getDELIVERYMAN_MINUTES();     // (..-..)/5
        this.deliveryManTimes = new TimeBoxDeliveryMan[length];
        for(int i = 0; i < length; i++){
            deliveryManTimes[i] = new TimeBoxDeliveryMan();
        }
    }

    /** assegna una consegna al fattorino in un certo orario. */
    void assignDelivery(int i) {
        this.deliveryManTimes[i].insertOrder();
    }

    public TimeBoxDeliveryMan[] getDeliveryManTimes() {
        return deliveryManTimes;
    }
}
