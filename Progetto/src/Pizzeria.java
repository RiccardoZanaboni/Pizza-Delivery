//import com.sun.corba.se.spi.orb.ParserData;

import java.text.SimpleDateFormat;
import java.util.*;

public class Pizzeria {
    private String nome;
    private String indirizzo;
    private Date orarioChiusura;
    private Date orarioApertura;
    private Forno infornate[];
    private ArrayList<DeliveryMan> fattorini;
    private HashMap<String, Pizza> menu;
    private ArrayList<Order> ordini;
    public final int TEMPI_FORNO = 12;

    public Pizzeria(String nome, String indirizzo, Date orarioApertura, Date orarioChiusura) {
        this.menu = new HashMap<>();
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.orarioChiusura = orarioChiusura;
        this.orarioApertura = orarioApertura;
        this.infornate = new Forno[TEMPI_FORNO * (orarioChiusura.getHours() - orarioApertura.getHours())];
        this.fattorini= new ArrayList<>();
    }

    public void AddPizza(Pizza pizza){
        menu.put(pizza.getNome(),pizza);
    }

    public String stampaMenu () {
        String s= "    >>  MENU"+"\n";
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

    public void makeOrder(Order order) {
        scegliPizze(order);
        inserisciDati(order);
        inserisciOrario(order);
        order.setCompleto();
    }

    public void scegliPizze(Order order) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Quante pizze vuoi ordinare?");
        int tot = scan.nextInt();
        while(tot>0){
            System.out.println("Quale pizza desideri?");
            String nome = scan.next();
            if(!(menu.containsKey(nome)))           // qui ci vorrebbe una eccezione
                System.out.println("Spiacenti: \"" + nome + "\" non presente. Riprovare:");
            else {
                System.out.println("Quante " + nome + " vuoi?");
                int num = scan.nextInt();
                tot -= num;
                for(int i=0; i<num; i++) {
                    order.AddPizza(menu.get(nome));
                }
            }
        }
    }

    public void inserisciDati(Order order){
        Scanner scan = new Scanner(System.in);
        System.out.println("Come ti chiami?");
        String nome = scan.nextLine();
        Customer c = new Customer(nome);
        order.setCustomer(c);
        System.out.println("Inserisci l'indirizzo di consegna:");
        String indirizzo = scan.nextLine();
        order.setIndirizzo(indirizzo);
    }

    public void inserisciOrario(Order order){
        Scanner scan = new Scanner(System.in);
        System.out.println("A che ora vuoi ricevere la consegna? [formato HH:mm]");
        try {
            String sDate1 = scan.next();
            Date date1 = new SimpleDateFormat("HH:mm").parse(sDate1);   // attenzione: 45:45 è accettato!
            System.out.println(date1);
        } catch (Exception e){
            System.out.println("L'orario non è stato inserito correttamente: riprovare.");
            inserisciOrario(order);
        }
    }
}