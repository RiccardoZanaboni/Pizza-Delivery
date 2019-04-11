import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Pizzeria {
    private String nome;
    private String indirizzo;
    private Date orario_Chiusura;
    private Date orario_Apertura;
    private Forno infornate[];
    private DeliveryMan fattorini_tempi[];
    private int numordine;
    private ArrayList <Order> ordini;

    public Pizzeria(String nome, String indirizzo, Date orario_Apertura, Date orario_Chiusura) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.orario_Chiusura = orario_Chiusura;
        this.orario_Apertura = orario_Apertura;
        this.infornate = new Forno[12 * (orario_Chiusura.getHours() - orario_Apertura.getHours())];
        this.fattorini_tempi = new DeliveryMan[6 * (orario_Chiusura.getHours() - orario_Apertura.getHours())];
        this.ordini=new ArrayList<>();
    }

    public void makeorder(String indirizzo,String username,Date orario){
        String codice="cod"+Math.random()+numordine;
        numordine++;
        Order or=new Order(codice,indirizzo,orario);
        ordini.add(or);
    }

    public Date getOrario_Chiusura() {
        return orario_Chiusura;
    }

    public Date getOrario_Apertura() {
        return orario_Apertura;
    }
}
