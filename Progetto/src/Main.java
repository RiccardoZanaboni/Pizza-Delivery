import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Pizzeria wolfOfPizza = new Pizzeria("Wolf Of PizzaMenu","Via de Gasperi 5, Pavia", new Date(2019,0,1,19,0),new Date(2019,0,31,23,0,0));

        /*PizzaMenu marinara = new PizzaMenu("MARINARA", "pomodoro, origano", 3.5);
        wolfOfPizza.AddPizza(marinara);
        PizzaMenu margherita = new PizzaMenu("MARGHERITA", "pomodoro, mozzarella", 4.5);
        wolfOfPizza.AddPizza(margherita);
        PizzaMenu patatine = new PizzaMenu("PATATINE", "pomodoro, mozzarella, patatine", 6);
        wolfOfPizza.AddPizza(patatine);
        PizzaMenu wurstel = new PizzaMenu("WURSTEL", "pomodoro, mozzarella, wurstel", 6);
        wolfOfPizza.AddPizza(wurstel);
        PizzaMenu napoli = new PizzaMenu("NAPOLI", "pomodoro, mozzarella, alici", 6.5);
        wolfOfPizza.AddPizza(napoli);
        PizzaMenu cotto = new PizzaMenu("COTTO", "pomodoro, mozzarella, prosciutto cotto", 6);
        wolfOfPizza.AddPizza(cotto);
        PizzaMenu cottoFunghi = new PizzaMenu("COTTO E FUNGHI", "pomodoro, mozzarella, prosciutto cotto, funghi", 7);

    wolfOfPizza.AddPizza(cottoFunghi);
        PizzaMenu salamePicc = new PizzaMenu("SALAME PICCANTE", "pomodoro, mozzarella, salame piccante", 5.5);
        wolfOfPizza.AddPizza(salamePicc);
        PizzaMenu americana = new PizzaMenu("AMERICANA", "pomodoro, mozzarella, salsiccia e peperoni", 7);
        wolfOfPizza.AddPizza(americana);
        PizzaMenu capricciosa = new PizzaMenu("CAPRICCIOSA", "pomodoro, prosciutto cotto, carciofi, funghi, olive", 7);
        wolfOfPizza.AddPizza(capricciosa);
        PizzaMenu italia = new PizzaMenu("ITALIA", "pomodorini, bufala, rucola", 7.5);
        wolfOfPizza.AddPizza(italia);
        PizzaMenu allaMusi = new PizzaMenu("ALLA MUSI", "pomodoro, mozzarella, galanteria e belle donne", 100000);
        wolfOfPizza.AddPizza(allaMusi);
        PizzaMenu thanos = new PizzaMenu("THANOS", "pomodoro, mozzarella di bufala, 6 gemme, onnipotenza, origano", 0.50);
        wolfOfPizza.AddPizza(thanos);
        PizzaMenu marvel = new PizzaMenu("RIP TONY STARK", "semplicemente: grazie di aver salvato l'universo.", 1000);
        wolfOfPizza.AddPizza(marvel);
        */

        //wolfOfPizza.AddFattorino(new DeliveryMan("Marco",wolfOfPizza));
        wolfOfPizza.AddFattorino(new DeliveryMan("Musi",wolfOfPizza));

        wolfOfPizza.ApriPizzeria(8);
        //wolfOfPizza.makeOrder();
        wolfOfPizza.makeOrder(); //utilizzare due make order per testare vettore data
        //wolfOfPizza.makeOrder();
        //System.out.print(margherita.getDescrizione()+"\n");
        //System.out.print(napoli.getDescrizione()+"\n");
        //System.out.print(cotto.getDescrizione());
    }
}