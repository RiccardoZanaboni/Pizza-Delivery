import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Pizzeria {
    private String nome;
    private String indirizzo;
    private Date orarioChiusura;
    private Date orarioApertura;
    private Forno infornate[];
    private ArrayList<DeliveryMan> fattorini;
    private ArrayList<Pizza> menu;

    public Pizzeria(String nome, String indirizzo, Date orarioApertura, Date orarioChiusura) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.orarioChiusura = orarioChiusura;
        this.orarioApertura = orarioApertura;
        this.infornate = new Forno[12 * (orarioChiusura.getHours() - orarioApertura.getHours())];
        this.fattorini= new ArrayList<>();
    }

    public void AddPizza(Pizza pizza){
        menu.add(pizza);
    }

    public String stampaMenu () {
        String s="\n"+ "    >>  MENU"+"\n";
        for (int i=0; i<menu.size(); i++) {
            s+= menu.get(i).toString();
        }
        return s;
    }


    public Date getOrarioChiusura() {
        return orarioChiusura;
    }

    public Date getOrarioApertura() {
        return orarioApertura;
    }
}
