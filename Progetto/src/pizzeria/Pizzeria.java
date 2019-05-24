package pizzeria;

import java.util.*;
@SuppressWarnings("deprecation")

public class Pizzeria {
    private String name;
    private String address;
    private Date openingTime;
    private Date closingTime;
    private Oven[] ovens;
    private ArrayList<DeliveryMan> deliveryMen;
    private HashMap<String, Pizza> menu;
    private HashMap<String, Toppings> pizzeriaIngredients;
    private ArrayList<Order> orders;
    private int numDailyOrders;
    private final int OVEN_TIMES_FOR_HOUR = 12;      // ogni 5 minuti
    private final int DELIVERYMAN_TIMES_FOR_HOURS = 6;   // ogni 10 minuti
    private final double SUPPL_PRICE = 0.5;

    /**
     * La Pizzeria è il locale che riceve le ordinazioni e le evade nei tempi richiesti.
     * @param name: nome identificativo della Pizzeria
     * @param address: indirizzo della Pizzeria
     * @param closingTime: orario di chiusura, ogni giorno
     * @param openingTime: orario di apertura, ogni giorno
     *
     * Inizializza anche il forno, con tutte le possibili infornate,
     * una ArrayList di fattorini e una di ordini del giorno.
     */

    public Pizzeria(String name, String address, Date openingTime, Date closingTime) {
        this.menu = new HashMap<>();
        this.pizzeriaIngredients = new HashMap<>();
        this.name = name;
        this.numDailyOrders = 0;
        this.orders = new ArrayList<>();
        this.address = address;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.ovens = new Oven[OVEN_TIMES_FOR_HOUR * (closingTime.getHours() - openingTime.getHours())];
        this.deliveryMen = new ArrayList<>();
        setIngredientsPizzeria();
        createMenu();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    private void AddPizza(Pizza pizza){
        menu.put(pizza.getMaiuscName(),pizza);
    }

    public void AddDeliveryMan(DeliveryMan deliveryMan){
        deliveryMen.add(deliveryMan);
    }

    /** Crea o ripristina il vettore di infornate, ad ogni apertura della pizzeria */
    public void OpenPizzeria(int postidisponibili){
        for(int i = 0; i< ovens.length; i++) {
            this.ovens[i] = new Oven(postidisponibili);
        }
    }

    /** Una tantum: vengono aggiunti a "pizzeriaIngredients" tutti gli ingredienti utilizzabili. */
    private void setIngredientsPizzeria(){
        this.pizzeriaIngredients.put(Toppings.ALICI.name(), Toppings.ALICI);
        this.pizzeriaIngredients.put(Toppings.BASILICO.name(), Toppings.BASILICO);
        this.pizzeriaIngredients.put(Toppings.BELLE_DONNE.name(), Toppings.BELLE_DONNE);
        this.pizzeriaIngredients.put(Toppings.CAPPERI.name(), Toppings.CAPPERI);
        this.pizzeriaIngredients.put(Toppings.COTTO.name(), Toppings.COTTO);
        this.pizzeriaIngredients.put(Toppings.CRUDO.name(), Toppings.CRUDO);
        this.pizzeriaIngredients.put(Toppings.FUNGHI.name(), Toppings.FUNGHI);
        this.pizzeriaIngredients.put(Toppings.GALANTERIA.name(), Toppings.GALANTERIA);
        this.pizzeriaIngredients.put(Toppings.GEMME_DELL_INFINITO.name(), Toppings.GEMME_DELL_INFINITO);
        this.pizzeriaIngredients.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        this.pizzeriaIngredients.put(Toppings.MOZZARELLA_DI_BUFALA.name(), Toppings.MOZZARELLA_DI_BUFALA);
        this.pizzeriaIngredients.put(Toppings.OLIVE_NERE.name(), Toppings.OLIVE_NERE);
        this.pizzeriaIngredients.put(Toppings.ONNIPOTENZA.name(), Toppings.ONNIPOTENZA);
        this.pizzeriaIngredients.put(Toppings.ORIGANO.name(), Toppings.ORIGANO);
        this.pizzeriaIngredients.put(Toppings.PATATINE.name(), Toppings.PATATINE);
        this.pizzeriaIngredients.put(Toppings.PEPERONI.name(), Toppings.PEPERONI);
        this.pizzeriaIngredients.put(Toppings.POMODORINI.name(), Toppings.POMODORINI);
        this.pizzeriaIngredients.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        this.pizzeriaIngredients.put(Toppings.RUCOLA.name(), Toppings.RUCOLA);
        this.pizzeriaIngredients.put(Toppings.SALAME_PICCANTE.name(), Toppings.SALAME_PICCANTE);
        this.pizzeriaIngredients.put(Toppings.SALSICCIA.name(), Toppings.SALSICCIA);
        this.pizzeriaIngredients.put(Toppings.WURSTEL.name(), Toppings.WURSTEL);
    }

    /** Una tantum: viene creato il menu della pizzeria; ad ogni pizza vengono aggiunti i rispettivi toppings. */
    public void createMenu(){
        HashMap<String, Toppings> i1 = new HashMap <>();
        i1.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i1.put(Toppings.ORIGANO.name(), Toppings.ORIGANO);
        Pizza marinara = new Pizza("MARINARA", i1, 3.5);
        AddPizza(marinara);

        HashMap<String, Toppings> i2 = new HashMap <>();
        i2.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i2.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        Pizza margherita = new Pizza("MARGHERITA", i2, 4.5);
        AddPizza(margherita);

        HashMap<String, Toppings> i3 = new HashMap <>();
        i3.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i3.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i3.put(Toppings.PATATINE.name(), Toppings.PATATINE);
        Pizza patatine = new Pizza("PATATINE", i3, 5);
        AddPizza(patatine);

        HashMap<String, Toppings> i4 = new HashMap <>();
        i4.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i4.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i4.put(Toppings.WURSTEL.name(), Toppings.WURSTEL);
        Pizza wurstel = new Pizza("WURSTEL", i4, 5);
        AddPizza(wurstel);

        HashMap<String, Toppings> i5 = new HashMap <>();
        i5.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i5.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i5.put(Toppings.ALICI.name(), Toppings.ALICI);
        Pizza napoli = new Pizza("NAPOLI", i5, 5);
        AddPizza(napoli);

        HashMap<String, Toppings> i6 = new HashMap <>();
        i6.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i6.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i6.put(Toppings.COTTO.name(), Toppings.COTTO);
        Pizza cotto = new Pizza("COTTO", i6, 5);
        AddPizza(cotto);

        HashMap<String, Toppings> i7 = new HashMap <>();
        i7.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i7.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i7.put(Toppings.COTTO.name(), Toppings.COTTO);
        i7.put(Toppings.FUNGHI.name(), Toppings.FUNGHI);
        Pizza cottoFunghi = new Pizza("COTTO E FUNGHI", i7, 5.5);
        AddPizza(cottoFunghi);

        HashMap<String, Toppings> i8 = new HashMap <>();
        i8.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i8.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i8.put(Toppings.SALAME_PICCANTE.name(), Toppings.SALAME_PICCANTE);
        Pizza salamePicc = new Pizza("SALAME PICCANTE", i8, 5);
        AddPizza(salamePicc);

        HashMap<String, Toppings> i9 = new HashMap <>();
        i9.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i9.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i9.put(Toppings.SALSICCIA.name(), Toppings.SALSICCIA);
        i9.put(Toppings.PEPERONI.name(), Toppings.PEPERONI);
        Pizza americana = new Pizza("AMERICANA", i9, 6);
        AddPizza(americana);

        HashMap<String, Toppings> i10 = new HashMap <>();
        i10.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i10.put(Toppings.COTTO.name(), Toppings.COTTO);
        i10.put(Toppings.CARCIOFI.name(), Toppings.CARCIOFI);
        i10.put(Toppings.FUNGHI.name(), Toppings.FUNGHI);
        i10.put(Toppings.OLIVE_NERE.name(), Toppings.OLIVE_NERE);
        Pizza capricciosa = new Pizza("CAPRICCIOSA", i10, 6);
        AddPizza(capricciosa);

        HashMap<String, Toppings> i11 = new HashMap <>();
        i11.put(Toppings.POMODORINI.name(), Toppings.POMODORINI);
        i11.put(Toppings.MOZZARELLA_DI_BUFALA.name(), Toppings.MOZZARELLA_DI_BUFALA);
        i11.put(Toppings.RUCOLA.name(), Toppings.RUCOLA);
        Pizza italia = new Pizza("ITALIA", i11, 6);
        AddPizza(italia);

        HashMap<String, Toppings> i12 = new HashMap <>();
        i12.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i12.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i12.put(Toppings.GALANTERIA.name(), Toppings.GALANTERIA);
        i12.put(Toppings.BELLE_DONNE.name(), Toppings.BELLE_DONNE);
        Pizza allaMusi = new Pizza("ALLA MUSI", i12, 8.5);
        AddPizza(allaMusi);

        HashMap<String, Toppings> i13 = new HashMap <>();
        i13.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i13.put(Toppings.MOZZARELLA_DI_BUFALA.name(), Toppings.MOZZARELLA_DI_BUFALA);
        i13.put(Toppings.GEMME_DELL_INFINITO.name(), Toppings.GEMME_DELL_INFINITO);
        i13.put(Toppings.ONNIPOTENZA.name(), Toppings.ONNIPOTENZA);
        i13.put(Toppings.ORIGANO.name(), Toppings.ORIGANO);
        Pizza thanos = new Pizza("THANOS", i13, 0.50);
        AddPizza(thanos);
    }

    public HashMap<String, Pizza> getMenu() {
        return menu;
    }

    public int getDailyOrders() {
        return numDailyOrders;
    }

    public HashMap<String, Toppings> getIngredientsPizzeria() {
        return pizzeriaIngredients;
    }

    private void increaseDailyOrders() {
        this.numDailyOrders++;
    }

    /** Crea un nuovo ordine */
    public Order initializeNewOrder() {
        Order order = new Order(numDailyOrders);
        increaseDailyOrders();
        return order;
    }

    public String helloThere(){         // da sistemare orario apertura-chiusura!!!
        String dati = "\nPIZZERIA \"" + this.name + "\"\n\t" + this.address;
        String open = "\n\tApertura: "+ this.closingTime.getHours() + ":00 - " + this.openingTime.getHours() + ":00";
        return "\n--------------------------------------------------------------------------------------\n" + dati + open;
    }

    /** Da TextInterface, permette di visualizzare il menu completo. */
    public String printMenu() {
        String line= "\n--------------------------------------------------------------------------------------\n";
        StringBuilder s= new StringBuilder("    >>  MENU\n");
        for (String a:menu.keySet()) {
            s.append("\n").append(menu.get(a).toString());
        }
        return line+s+line;
    }

    /** Controlla che la pizzeria sia aperta in un determinato orario. */
    public boolean isOpen(Date d){
        int ora = d.getHours();
        int minuti = d.getMinutes();
        return !(ora < this.openingTime.getHours() || ora > this.closingTime.getHours() || (ora == this.closingTime.getHours() && minuti >= this.closingTime.getMinutes()) || (ora == this.openingTime.getHours() && minuti <= this.openingTime.getMinutes()));
    }

    /** ritorna l'indice della casella temporale (forno) desiderata. */		// mancherebbero minuti di apertura
    public int findTimeBoxOven(Date oraApertura, int oraDesiderata, int minutiDesiderati){
        int casellaTempo = this.OVEN_TIMES_FOR_HOUR *(oraDesiderata - oraApertura.getHours());
        casellaTempo += minutiDesiderati/5;
        return casellaTempo;
    }

    /** ritorna l'indice della casella temporale (fattorino) desiderata. */
    private int findTimeBoxDeliveryMan(Date oraApertura, int oraDesiderata, int minutiDesiderati){
        int casellaTempo = this.DELIVERYMAN_TIMES_FOR_HOURS *(oraDesiderata - oraApertura.getHours());
        casellaTempo += minutiDesiderati/10;
        return casellaTempo;
    }

    /** restituisce il primo fattorino della pizzeria che sia disponibile all'orario indicato. */
    public DeliveryMan aFreeDeliveryMan(Date oraApertura, int oraDesiderata, int minutiDesiderati, int indice){
        for(DeliveryMan a:this.deliveryMen){
            if(!a.getDeliveryManTimes()[findTimeBoxDeliveryMan(oraApertura,oraDesiderata,minutiDesiderati)-indice].isBusy()){
                return a;
            }
        }
        return null;
    }

    /** Restituisce tutti gli orari in cui la pizzeria potrebbe garantire la consegna di "tot" pizze. */
    public ArrayList<String> availableTimes(int tot){
        ArrayList<String> available = new ArrayList<>();

        Calendar cal = new GregorianCalendar();
        int nowHour = cal.get(Calendar.HOUR_OF_DAY);
        int nowMinutes = cal.get(Calendar.MINUTE);

        int now = 60*nowHour + nowMinutes;
        int open = 60*openingTime.getHours() + openingTime.getMinutes();
        int scarto = 0;
        if(now > open) {
            scarto = (now-open)/DELIVERYMAN_TIMES_FOR_HOURS;
        }
        for(int i = 6+scarto; i<this.ovens.length; i++) {       // considera i tempi minimi di preparazione e consegna
            if (ovens[i].getPostiDisp() + ovens[i-1].getPostiDisp() >= tot) {
                for(DeliveryMan a:this.deliveryMen){
                    if(!a.getDeliveryManTimes()[i/2].isBusy()){
                        int oraNew = this.openingTime.getHours() + i/12;   //NON POSSO PARTIRE DA TROVACASELLA MENO 1: RISCHIO ECCEZIONE
                        int min = 5 * (i - 12*(i/12));      // divisione senza resto, quindi ha un suo senso
                        if(min <= 5){
                            available.add(oraNew + ":0" + min + "  ");
                        } else {
                            available.add(oraNew + ":" + min + "  ");
                        }
                        break;
                    }
                }
            }
        }
        return available;
    }

    /** Controlla che la pizzeria possa garantire la consegna di "tot" pizze all'orario "d",
     * in base alla disponibilità di forno e fattorini. */
    public void updateOvenAndDeliveryMan(Date d, int tot){
        // PRIMA CONDIZIONE PER LE INFORNATE, SUCCESSIVA SUI FATTORINI
        if(ovens[findTimeBoxOven(this.closingTime, d.getHours(), d.getMinutes())].getPostiDisp()<tot){
            int disp = ovens[findTimeBoxOven(this.closingTime, d.getHours(), d.getMinutes())].getPostiDisp();
            ovens[findTimeBoxOven(this.closingTime, d.getHours(), d.getMinutes())].inserisciInfornate(disp);
            ovens[findTimeBoxOven(this.closingTime, d.getHours(), d.getMinutes())-1].inserisciInfornate(tot-disp);
        } else{
            ovens[findTimeBoxOven(this.closingTime, d.getHours(), d.getMinutes())].inserisciInfornate(tot);
        }
        aFreeDeliveryMan(this.closingTime,d.getHours(),d.getMinutes(),0).assignDelivery(findTimeBoxDeliveryMan(this.closingTime, d.getHours(), d.getMinutes()));
    }

    Date getClosingTime() {
        return openingTime;
    }

    public Date getOpeningTime() {
        return closingTime;
    }

    public double getSUPPL_PRICE() {
        return SUPPL_PRICE;
    }

    public Oven[] getOvens() {
        return ovens;
    }

    /** In TextInterface, elenca tutte gli ingredienti che l'utente può scegliere, per modificare una pizza. */
    public String possibleAddictions() {
        StringBuilder possibiliIngr = new StringBuilder("Possibili aggiunte: ");
        int i = 0;
        for (Toppings ingr : getIngredientsPizzeria().values()) {
            possibiliIngr.append(ingr.name().toLowerCase().replace("_", " ")).append(", ");
            i++;
            if (i % 10 == 0)
                possibiliIngr.append("\n");
        }
        return possibiliIngr.substring(0, possibiliIngr.lastIndexOf(",")); // elimina ultima virgola
    }
}