public class DeliveryMan {
    private String nome;
    private boolean fattoriniTempi[];

    public DeliveryMan(String nome, boolean[] fattoriniTempi,Pizzeria pizzeria) {
        this.nome = nome;
        this.fattoriniTempi = new boolean[pizzeria.getOrarioChiusura().getHours() - pizzeria.getOrarioApertura().getHours()];
    }


}
