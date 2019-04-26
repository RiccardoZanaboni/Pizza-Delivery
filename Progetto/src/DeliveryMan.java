public class DeliveryMan {
    private String nome;
    private boolean fattoriniTempi[];
    public final int TEMPI_FATTORINI = 6;   // ogni 10 minuti

    public DeliveryMan(String nome, boolean[] fattoriniTempi, Pizzeria pizzeria) {
        this.nome = nome;
        this.fattoriniTempi = new boolean[TEMPI_FATTORINI * pizzeria.getOrarioChiusura().getHours() - pizzeria.getOrarioApertura().getHours()];
    }
}
