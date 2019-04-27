public class DeliveryMan {
    private String nome;
    private boolean fattoriniTempi[]; //false=libero?
    public final int TEMPI_FATTORINI = 6;   // ogni 10 minuti

    public DeliveryMan(String nome, Pizzeria pizzeria) {
        this.nome = nome;
        this.fattoriniTempi = new boolean[TEMPI_FATTORINI * pizzeria.getOrarioChiusura().getHours() - pizzeria.getOrarioApertura().getHours()];
    }

    public boolean[] getFattoriniTempi() {
        return fattoriniTempi;
    }

    public void setFattoriniTempi(int i) {
        this.fattoriniTempi[i] = true;
    }
}
