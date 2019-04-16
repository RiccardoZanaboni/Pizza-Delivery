import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Pizzeria {
    private String nome;
    private String indirizzo;
    private Date orarioChiusura;
    private Date orarioApertura;
    private Forno infornate[];
    private DeliveryMan fattoriniTempi[];
    private ArrayList<Pizza> menu;

    public Pizzeria(String nome, String indirizzo, Date orarioApertura, Date orarioChiusura) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.orarioChiusura = orarioChiusura;
        this.orarioApertura = orarioApertura;
        this.infornate = new Forno[12 * (orarioChiusura.getHours() - orarioApertura.getHours())];
        this.fattoriniTempi = new DeliveryMan[6 * (orarioChiusura.getHours() - orarioApertura.getHours())];
    }

    public void AddPizza(Pizza pizza){
        menu.add(pizza);
    }

    public ArrayList<Pizza> getMenu() {
        return menu;
    }

    public Date getOrarioChiusura() {
        return orarioChiusura;
    }

    public Date getOrarioApertura() {
        return orarioApertura;
    }
}
