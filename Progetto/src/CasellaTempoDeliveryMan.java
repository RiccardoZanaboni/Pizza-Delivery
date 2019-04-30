public class CasellaTempoDeliveryMan {
    private boolean occupato;
    private int ordini;

    public CasellaTempoDeliveryMan() {
        this.occupato = false;
        this.ordini = 0;
    }

    public void InserisciOrdine() {
        this.ordini += 1;
        if(this.ordini==2){
            this.occupato=true;
        }
    }

    public boolean isOccupato() {
        return occupato;
    }
}
