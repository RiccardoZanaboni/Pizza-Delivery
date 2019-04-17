import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Pizzeria wolfOfPizza = new Pizzeria("Wolf Of Pizza","Via de Gasperi 5, Pavia", new Date(2019,0,1,19,0),new Date(2019,0,31,23,0,0));
        Pizza margherita = new Pizza("MARGHERITA", "pomodoro, mozzarella", 5);
        wolfOfPizza.AddPizza(margherita);
        Pizza patatine = new Pizza("PATATINE", "pomodoro, mozzarella, patatine", 6);
        wolfOfPizza.AddPizza(patatine);
        Pizza wurstel = new Pizza("WURSTEL", "pomodoro, mozzarella, wurstel", 6);
        wolfOfPizza.AddPizza(wurstel);

        wolfOfPizza.makeOrder();





    }
}
