@SuppressWarnings("ALL")
public class DeliveryMan {
    private String nome;
    private CasellaTempoDeliveryMan fattoriniTempi[]; //false=libero?
    public final int TEMPI_FATTORINI = 6;   // ogni 10 minuti

    public DeliveryMan(String nome, Pizzeria pizzeria) {
        this.nome = nome;
        this.fattoriniTempi = new CasellaTempoDeliveryMan[TEMPI_FATTORINI * pizzeria.getOrarioChiusura().getHours() - pizzeria.getOrarioApertura().getHours()];
        for(int i=0;i<fattoriniTempi.length;i++){
            fattoriniTempi[i]=new CasellaTempoDeliveryMan();
        }
    }

    public CasellaTempoDeliveryMan[] getFattoriniTempi() {
        return fattoriniTempi;
    }

    public void OccupaFattorino(int i) {
        this.fattoriniTempi[i].InserisciOrdine();
    }

    /*public void setFattoriniTempi(CasellaTempoDeliveryMan[] fattoriniTempi) {
        this.fattoriniTempi = fattoriniTempi;
    }*/
}
