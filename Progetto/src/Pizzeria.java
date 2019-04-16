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

    public Pizzeria(String nome, String indirizzo, Date orarioApertura, Date orarioChiusura) {
        this.menu = new HashMap<>();
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.orarioChiusura = orarioChiusura;
        this.orarioApertura = orarioApertura;
        this.infornate = new Forno[12 * (orarioChiusura.getHours() - orarioApertura.getHours())];
        this.fattorini= new ArrayList<>();
        this.ordini = new ArrayList<>();
    }

    public void AddPizzaMenu(Pizza pizza){
        menu.put(pizza.getNome(), pizza);
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

    public void scegliPizza(Order order) {
      Scanner scan = new Scanner(System.in);

      System.out.println("Inserisci la pizza:");
      String nomePizza = scan.next();

      Pizza p = menu.get(nomePizza);
      order.AddPizza(p);
      //Pizza pizza = new Pizza(menu.get(nomePizza), menu.get(nomePizza).getDescrizione());
      //menu.get(nomePizza).
    }
}
