import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Pizzeria wolfOfPizza = new Pizzeria("Wolf Of Pizza","Via de Gasperi 5, Pavia", new Date(2019,0,1,19,0),new Date(2019,0,31,23,0,0));

        Pizza marinara = new Pizza("MARINARA", "pomodoro, origano", 3.5);
        wolfOfPizza.AddPizza(marinara);
        Pizza margherita = new Pizza("MARGHERITA", "pomodoro, mozzarella", 4.5);
        wolfOfPizza.AddPizza(margherita);
        Pizza patatine = new Pizza("PATATINE", "pomodoro, mozzarella, patatine", 6);
        wolfOfPizza.AddPizza(patatine);
        Pizza wurstel = new Pizza("WURSTEL", "pomodoro, mozzarella, wurstel", 6);
        wolfOfPizza.AddPizza(wurstel);
        Pizza napoli = new Pizza("NAPOLI", "pomodoro, mozzarella, alici", 6.5);
        wolfOfPizza.AddPizza(napoli);
        Pizza cotto = new Pizza("COTTO", "pomodoro, mozzarella, prosciutto cotto", 6);
        wolfOfPizza.AddPizza(cotto);
        Pizza cottoFunghi = new Pizza("COTTO E FUNGHI", "pomodoro, mozzarella, prosciutto cotto, funghi", 7);

    wolfOfPizza.AddPizza(cottoFunghi);
        Pizza salamePicc = new Pizza("SALAME PICCANTE", "pomodoro, mozzarella, salame piccante", 5.5);
        wolfOfPizza.AddPizza(salamePicc);
        Pizza americana = new Pizza("AMERICANA", "pomodoro, mozzarella, salsiccia e peperoni", 7);
        wolfOfPizza.AddPizza(americana);
        Pizza capricciosa = new Pizza("CAPRICCIOSA", "pomodoro, prosciutto cotto, carciofi, funghi, olive", 7);
        wolfOfPizza.AddPizza(capricciosa);
        Pizza italia = new Pizza("ITALIA", "pomodorini, bufala, rucola", 7.5);
        wolfOfPizza.AddPizza(italia);
        Pizza allaMusi = new Pizza("ALLA MUSI", "pomodoro, mozzarella, galanteria e belle donne", 100000);
        wolfOfPizza.AddPizza(allaMusi);
        Pizza thanos = new Pizza("THANOS", "pomodoro, mozzarella di bufala, 6 gemme, onnipotenza, origano", 0.50);
        wolfOfPizza.AddPizza(thanos);
        Pizza marvel = new Pizza("RIP TONY STARK", "semplicemente: grazie di aver salvato l'universo.", 1000);
        wolfOfPizza.AddPizza(marvel);

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