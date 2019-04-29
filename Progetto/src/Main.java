import java.util.Date;

/**
 * * @author Javengers
 */

public class Main {
    public static void main(String[] args) {
        Pizzeria wolfOfPizza = new Pizzeria("Wolf Of PizzaMenu","Via de Gasperi 5, Pavia", new Date(2019,0,1,19,0),new Date(2019,0,31,23,0,0));


        //wolfOfPizza.AddFattorino(new DeliveryMan("Marco",wolfOfPizza));
        wolfOfPizza.AddFattorino(new DeliveryMan("Musi",wolfOfPizza));

        wolfOfPizza.ApriPizzeria(8);
        //wolfOfPizza.makeOrder();
        wolfOfPizza.makeOrder(); //utilizzare due make order per testare vettore data
        //wolfOfPizza.makeOrder();
        //System.out.print(wolfOfPizza.stampaMenu()+"\n");
        //System.out.print(napoli.getDescrizione()+"\n");
        //System.out.print(cotto.getDescrizione());
    }
}