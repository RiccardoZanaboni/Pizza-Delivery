package pizzeria;

import java.awt.*;
import java.time.LocalTime;
import java.util.*;
@SuppressWarnings("deprecation")

public class Pizzeria {
    private String name;
    private String address;
    private LocalTime[] openings = new LocalTime[7];    // apertura in tutti i giorni della settimana
    private LocalTime[] closings = new LocalTime[7];    // chiusura in tutti i giorni della settimana
    private Date openingToday = new Date();
    private Date closingToday = new Date();
    private Oven[] ovens;
    private ArrayList<DeliveryMan> deliveryMen;
    private HashMap<String, Pizza> menu;
    private HashMap<String, Toppings> pizzeriaIngredients;
    private ArrayList<Order> orders;
    private int availablePlaces;
    private int numDailyOrders;
    private final int OVEN_TIMES_FOR_HOUR = 12;      // ogni 5 minuti
    private final int DELIVERYMAN_TIMES_FOR_HOUR = 6;   // ogni 10 minuti
    private final double SUPPL_PRICE;

    /**
     * La Pizzeria è il locale che riceve le ordinazioni e le evade nei tempi richiesti.
     * @param name: nome identificativo della Pizzeria
     * @param address: indirizzo della Pizzeria
     *
     * Inizializza anche il forno, con tutte le possibili infornate,
     * una ArrayList di fattorini e una di ordini del giorno.
     */

    public Pizzeria(String name, String address,
                    LocalTime op1, LocalTime op2, LocalTime op3, LocalTime op4, LocalTime op5, LocalTime op6, LocalTime op7,
                    LocalTime cl1, LocalTime cl2, LocalTime cl3, LocalTime cl4, LocalTime cl5, LocalTime cl6, LocalTime cl7) {
        //System.out.println(Calendar.getInstance().toString());
        this.menu = new HashMap<>();
        this.pizzeriaIngredients = new HashMap<>();
        this.name = name;
        this.numDailyOrders = 0;
        this.orders = new ArrayList<>();
        this.address = address;
        setDayOfTheWeek(op1,op2,op3,op4,op5,op6,op7,cl1,cl2,cl3,cl4,cl5,cl6,cl7);  // 1 = domenica, 2 = lunedi, ... 7 = sabato.
        this.deliveryMen = new ArrayList<>();
        this.SUPPL_PRICE = 0.5;
        this.availablePlaces = 8;
        openPizzeriaToday();
        addDeliveryMan(new DeliveryMan("Musi", this));
        //addDeliveryMan(new DeliveryMan("Zanzatroni", this));
    }

    /** una tantum: riempie i vettori della pizzeria contenenti gli orari
     * di apertura e di chiusura per ogni giorno della settimana. */
    private void setDayOfTheWeek(LocalTime op1, LocalTime op2, LocalTime op3, LocalTime op4, LocalTime op5, LocalTime op6, LocalTime op7, LocalTime cl1, LocalTime cl2, LocalTime cl3, LocalTime cl4, LocalTime cl5, LocalTime cl6, LocalTime cl7) {
        this.openings[0] = op1;
        this.openings[1] = op2;
        this.openings[2] = op3;
        this.openings[3] = op4;
        this.openings[4] = op5;
        this.openings[5] = op6;
        this.openings[6] = op7;
        this.closings[0] = cl1;
        this.closings[1] = cl2;
        this.closings[2] = cl3;
        this.closings[3] = cl4;
        this.closings[4] = cl5;
        this.closings[5] = cl6;
        this.closings[6] = cl7;
    }

    /** Aggiunge l'ordine, completato, a quelli che la pizzeria deve evadere. */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /** Aggiunge la pizza specificata al menu della pizzeria. */
    private void addPizza(Pizza pizza){
        menu.put(pizza.getMaiuscName(),pizza);
    }

    public void addDeliveryMan(DeliveryMan deliveryMan){
        deliveryMen.add(deliveryMan);
    }

    /** Crea o ripristina il vettore di infornate, ad ogni apertura della pizzeria */
    public void openPizzeriaToday(){
        setIngredientsPizzeria();
        createMenu();

        Calendar cal = new GregorianCalendar();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        // inizializza il vettore di infornate di oggi, in base agli orari di apertura e chiusura di oggi.
        this.ovens = new Oven[OVEN_TIMES_FOR_HOUR * (closings[dayOfWeek-1].getHour() - openings[dayOfWeek-1].getHour())];
        for(int i = 0; i< ovens.length; i++) {
            this.ovens[i] = new Oven(availablePlaces);
        }

        this.openingToday.setHours(1);
        this.openingToday.setHours(this.openings[dayOfWeek-1].getHour());
        this.openingToday.setMinutes(this.openings[dayOfWeek-1].getMinute());
        this.closingToday.setHours(this.closings[dayOfWeek-1].getHour());
        this.closingToday.setMinutes(this.closings[dayOfWeek-1].getMinute());
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
    private void createMenu(){
        HashMap<String, Toppings> i1 = new HashMap <>();
        i1.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i1.put(Toppings.ORIGANO.name(), Toppings.ORIGANO);
        Pizza marinara = new Pizza("MARINARA", i1, 3.5);
        addPizza(marinara);

        HashMap<String, Toppings> i2 = new HashMap <>();
        i2.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i2.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        Pizza margherita = new Pizza("MARGHERITA", i2, 4.5);
        addPizza(margherita);

        HashMap<String, Toppings> i3 = new HashMap <>();
        i3.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i3.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i3.put(Toppings.PATATINE.name(), Toppings.PATATINE);
        Pizza patatine = new Pizza("PATATINE", i3, 5);
        addPizza(patatine);

        HashMap<String, Toppings> i4 = new HashMap <>();
        i4.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i4.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i4.put(Toppings.WURSTEL.name(), Toppings.WURSTEL);
        Pizza wurstel = new Pizza("WURSTEL", i4, 5);
        addPizza(wurstel);

        HashMap<String, Toppings> i5 = new HashMap <>();
        i5.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i5.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i5.put(Toppings.ALICI.name(), Toppings.ALICI);
        Pizza napoli = new Pizza("NAPOLI", i5, 5);
        addPizza(napoli);

        HashMap<String, Toppings> i6 = new HashMap <>();
        i6.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i6.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i6.put(Toppings.COTTO.name(), Toppings.COTTO);
        Pizza cotto = new Pizza("COTTO", i6, 5);
        addPizza(cotto);

        HashMap<String, Toppings> i7 = new HashMap <>();
        i7.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i7.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i7.put(Toppings.COTTO.name(), Toppings.COTTO);
        i7.put(Toppings.FUNGHI.name(), Toppings.FUNGHI);
        Pizza cottoFunghi = new Pizza("COTTO E FUNGHI", i7, 5.5);
        addPizza(cottoFunghi);

        HashMap<String, Toppings> i8 = new HashMap <>();
        i8.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i8.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i8.put(Toppings.SALAME_PICCANTE.name(), Toppings.SALAME_PICCANTE);
        Pizza salamePicc = new Pizza("SALAME PICCANTE", i8, 5);
        addPizza(salamePicc);

        HashMap<String, Toppings> i9 = new HashMap <>();
        i9.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i9.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i9.put(Toppings.SALSICCIA.name(), Toppings.SALSICCIA);
        i9.put(Toppings.PEPERONI.name(), Toppings.PEPERONI);
        Pizza americana = new Pizza("AMERICANA", i9, 6);
        addPizza(americana);

        HashMap<String, Toppings> i10 = new HashMap <>();
        i10.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i10.put(Toppings.COTTO.name(), Toppings.COTTO);
        i10.put(Toppings.CARCIOFI.name(), Toppings.CARCIOFI);
        i10.put(Toppings.FUNGHI.name(), Toppings.FUNGHI);
        i10.put(Toppings.OLIVE_NERE.name(), Toppings.OLIVE_NERE);
        Pizza capricciosa = new Pizza("CAPRICCIOSA", i10, 6);
        addPizza(capricciosa);

        HashMap<String, Toppings> i11 = new HashMap <>();
        i11.put(Toppings.POMODORINI.name(), Toppings.POMODORINI);
        i11.put(Toppings.MOZZARELLA_DI_BUFALA.name(), Toppings.MOZZARELLA_DI_BUFALA);
        i11.put(Toppings.RUCOLA.name(), Toppings.RUCOLA);
        Pizza italia = new Pizza("ITALIA", i11, 6);
        addPizza(italia);

        HashMap<String, Toppings> i12 = new HashMap <>();
        i12.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i12.put(Toppings.MOZZARELLA.name(), Toppings.MOZZARELLA);
        i12.put(Toppings.GALANTERIA.name(), Toppings.GALANTERIA);
        i12.put(Toppings.BELLE_DONNE.name(), Toppings.BELLE_DONNE);
        Pizza allaMusi = new Pizza("ALLA MUSI", i12, 8.5);
        addPizza(allaMusi);

        HashMap<String, Toppings> i13 = new HashMap <>();
        i13.put(Toppings.POMODORO.name(), Toppings.POMODORO);
        i13.put(Toppings.MOZZARELLA_DI_BUFALA.name(), Toppings.MOZZARELLA_DI_BUFALA);
        i13.put(Toppings.GEMME_DELL_INFINITO.name(), Toppings.GEMME_DELL_INFINITO);
        i13.put(Toppings.ONNIPOTENZA.name(), Toppings.ONNIPOTENZA);
        i13.put(Toppings.ORIGANO.name(), Toppings.ORIGANO);
        Pizza thanos = new Pizza("THANOS", i13, 0.50);
        addPizza(thanos);
    }

    public HashMap<String, Pizza> getMenu() {
        return menu;
    }

    public HashMap<String, Toppings> getIngredientsPizzeria() {
        return pizzeriaIngredients;
    }

    /** Crea un nuovo ordine e aggiorna il numero di ordini giornalieri. */
    public Order initializeNewOrder() {
        Order order = new Order(this.numDailyOrders);
        this.numDailyOrders++;
        return order;
    }

    /** Su TextInterface dà il benvenuto al cliente, fornendo le informazioni essenziali della pizzeria. */
    public String helloThere(){
        String opTime = Services.timeStamp(openingToday.getHours(), openingToday.getMinutes());
        String clTime = Services.timeStamp(closingToday.getHours(), closingToday.getMinutes());
        StringBuilder hello = new StringBuilder(Services.getLine());
        hello.append(Services.colorSystemOut("PIZZERIA ", Color.ORANGE,false,false));
        hello.append(Services.colorSystemOut("\"" + this.name + "\"\n\t",Color.RED,true,false));
        hello.append(Services.colorSystemOut(this.address,Color.ORANGE,false,false));
        if(openingToday.equals(closingToday))
            hello.append(Services.colorSystemOut("\n\tOGGI CHIUSO", Color.RED, true, false));
        else {
            hello.append(Services.colorSystemOut("\n\tApertura oggi: ", Color.ORANGE, false, false));
            hello.append(Services.colorSystemOut(opTime + " - " + clTime, Color.RED, true, false));
        }
        return hello.toString();
    }

    /** Da TextInterface, permette di stampare a video il menu completo. */
    public String printMenu() {
        String line = Services.getLine();
        Services.paintMenuString();
        //StringBuilder s = new StringBuilder("\n\t>>>  ");
        //s.append(Services.colorSystemOut(" M E N U \n",Color.YELLOW,false,true));
        StringBuilder s = new StringBuilder();
        for (String a:menu.keySet()) {
            s.append("\n").append(menu.get(a).toString());
        }
        return s+"\n"+line;
    }

    /** Controlla che la pizzeria sia aperta in un determinato orario, nella giornata odierna. */
    public boolean isOpen(Date d){
        int ora = d.getHours();
        int minuti = d.getMinutes();
        return !(ora < this.openingToday.getHours() || ora > this.closingToday.getHours() || (ora == this.closingToday.getHours() && minuti >= this.closingToday.getMinutes()) || (ora == this.openingToday.getHours() && minuti <= this.openingToday.getMinutes()));
    }

    /** ritorna l'indice della casella temporale (forno) desiderata. */		// mancherebbero minuti di apertura
    private int findTimeBoxOven(int oraDesiderata, int minutiDesiderati){
        int openMinutes = 60*openingToday.getHours() + openingToday.getMinutes();
        int desiredMinutes = 60*oraDesiderata + minutiDesiderati;
        return (desiredMinutes - openMinutes)/OVEN_TIMES_FOR_HOUR;
    }

    /** ritorna l'indice della casella temporale (fattorino) desiderata. */
    private int findTimeBoxDeliveryMan(int oraDesiderata, int minutiDesiderati){
        int openMinutes = 60*openingToday.getHours() + openingToday.getMinutes();
        int desiredMinutes = 60*oraDesiderata + minutiDesiderati;
        return (desiredMinutes - openMinutes)/ DELIVERYMAN_TIMES_FOR_HOUR;
    }

    /** restituisce il primo fattorino della pizzeria che sia disponibile all'orario indicato. */
    public DeliveryMan aFreeDeliveryMan(int oraDesiderata, int minutiDesiderati){
        for(DeliveryMan a:this.deliveryMen){
            if(a.getDeliveryManTimes()[findTimeBoxDeliveryMan(oraDesiderata,minutiDesiderati)].isFree()){
                return a;
            }
        }
        return null;
    }

    /** Restituisce tutti gli orari in cui la pizzeria potrebbe garantire la consegna di "tot" pizze.
     * la var "scarto" risponde all'eventualità che la pizzeria sia già aperta al momento attuale. */
    public ArrayList<String> availableTimes(int tot){
        ArrayList<String> availables = new ArrayList<>();
        int now = Services.getNowMinutes();
        int restaAperta = Services.calculateOpeningMinutesPizzeria(this);
        int esclusiIniziali = Services.calculateStartIndex(this, now, tot, restaAperta);     // primo orario da visualizzare (in minuti)

        for(int i = esclusiIniziali; i < restaAperta; i++) {    // considera i tempi minimi di preparazione e consegna
            if(i % 5 == 0) {
                if (ovens[i / 12].getPostiDisp() + ovens[(i / 12) - 1].getPostiDisp() >= tot) {
                    for (DeliveryMan a : this.deliveryMen) {
                        if (a.getDeliveryManTimes()[i / 24].isFree()) {
                            int newMinutes = Services.getMinutes(openingToday) + i;   // NON POSSO PARTIRE DA TROVACASELLA MENO 1: RISCHIO ECCEZIONE
                            int ora = newMinutes / 60;
                            int min = newMinutes % 60;
                            String nuovoOrario = Services.timeStamp(ora,min);
                            availables.add(nuovoOrario + "  ");
                            break;
                        }
                    }
                }
            }
        }
        if(availables.size() > 0) {
            return availables;
        } else {
            /* se l'ordine inizia in un orario ancora valido, ma impiega troppo tempo e diventa troppo tardi: */
            String spiacenti = "\nSpiacenti: si è fatto tardi, la pizzeria è ormai in chiusura. Torna a trovarci!\n";
            System.out.println(Services.colorSystemOut(spiacenti,Color.RED,false,false));
            return null;
        }
    }

    /** Controlla che la pizzeria possa garantire la consegna di "tot" pizze all'orario "d",
     * in base alla disponibilità di forno e fattorini. */
    public void updateOvenAndDeliveryMan(Date d, int tot) {
        // PRIMA CONDIZIONE PER LE INFORNATE, SUCCESSIVA SUI FATTORINI
        if(ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].getPostiDisp()<tot){
            int disp = ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].getPostiDisp();
            ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].inserisciInfornate(disp);
            ovens[findTimeBoxOven(d.getHours(), d.getMinutes())-1].inserisciInfornate(tot-disp);
        } else{
            ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].inserisciInfornate(tot);
        }
        Objects.requireNonNull(aFreeDeliveryMan(d.getHours(), d.getMinutes())).assignDelivery(findTimeBoxDeliveryMan(d.getHours(), d.getMinutes()));
    }

    Date getClosingTime() {
        return closingToday;
    }

    Date getOpeningTime() {
        return openingToday;
    }

    public double getSUPPL_PRICE() {
        return SUPPL_PRICE;
    }

    public Oven[] getOvens() {
        return ovens;
    }

    /** In TextInterface, elenca tutte gli ingredienti che l'utente può scegliere, per modificare una pizza. */
    public String possibleAddictions() {
        StringBuilder possibiliIngr = new StringBuilder();
        int i = 0;
        for (Toppings ingr : getIngredientsPizzeria().values()) {
            if (i % 10 == 0)
                possibiliIngr.append("\n\t");
            possibiliIngr.append(ingr.name().toLowerCase().replace("_", " ")).append(", ");
            i++;
        }
        return possibiliIngr.toString();
    }

    /** Verifica che sia possibile cuocere le pizze nell' infornata richiesta e in quella appena precedente. */
    public boolean checkTimeBoxOven(int ora, int minuti, int tot) {
        if (ovens[findTimeBoxOven(ora, minuti)].getPostiDisp() + ovens[findTimeBoxOven(ora, minuti) - 1].getPostiDisp() < tot)
            return false;
        else
            return true;
    }

    public int getAvailablePlaces() {
        return this.availablePlaces;
    }

    public int getNumDailyOrders() {
        return numDailyOrders;
    }
}