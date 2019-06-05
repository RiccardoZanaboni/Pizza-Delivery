package pizzeria;

import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.*;
@SuppressWarnings("deprecation")

public class Pizzeria {
    private String name;
    private String address;
    private LocalTime[] openings = new LocalTime[7];    // orari di apertura in tutti i giorni della settimana
    private LocalTime[] closings = new LocalTime[7];    // orari di chiusura in tutti i giorni della settimana
    private Oven[] ovens;
    private ArrayList<DeliveryMan> deliveryMen;
    private HashMap<String, Pizza> menu;
    private HashMap<String, Toppings> pizzeriaIngredients;
    private ArrayList<Order> orders;
    private int availablePlaces;
    private int numDailyOrders;
    private final int OVEN_MINUTES = 5;      // ogni 5 minuti
    private final int DELIVERYMAN_MINUTES = 10;   // ogni 10 minuti
    private final double SUPPL_PRICE;
    private final String userPizzeria;
    private final String pswPizzeria;

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
        this.userPizzeria = "wolf";
        this.pswPizzeria = "wolf";
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

    /**
     * Riempie i vettori della pizzeria contenenti gli orari
     * di apertura e di chiusura per ogni giorno della settimana.
     * Utilizzato nel costruttore della pizzeria, ma riutilizzabile in caso di cambiamenti.
     * */
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

    public ArrayList<Order> getOrders() {
        return orders;
    }

    /** Aggiunge l'ordine, completato, a quelli che la pizzeria deve evadere. */
    public void addOrder(Order order) {
        this.orders.add(order);
    }

    /** Aggiunge la pizza specificata al menu dnameella pizzeria. */
    private void addPizza(Pizza pizza){
        this.menu.put(pizza.getName(false),pizza);
    }

    public void addDeliveryMan(DeliveryMan deliveryMan){
        this.deliveryMen.add(deliveryMan);
    }

    /** Crea o ripristina il vettore di infornate, ad ogni apertura della pizzeria */
    public void openPizzeriaToday(){

        setIngredientsPizzeria();       // FIXME: questi due andrebbero fatti una tantum con Database.
        createMenu();

        // inizializza il vettore di infornate di oggi, in base agli orari di apertura e chiusura di oggi.
        int closeMinutes = Services.getMinutes(getClosingToday());
        int openMinutes = Services.getMinutes(getOpeningToday());
        this.ovens = new Oven[(closeMinutes - openMinutes)/ this.OVEN_MINUTES];    // minutiTotali/5
        for(int i = 0; i< this.ovens.length; i++) {
            this.ovens[i] = new Oven(this.availablePlaces);
        }
    }

    public Date getOpeningToday(){
        Calendar cal = new GregorianCalendar();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);  // oggi
        Date op = new Date();
        op.setHours(this.openings[dayOfWeek-1].getHour());
        op.setMinutes(this.openings[dayOfWeek-1].getMinute());
        return op;
    }

    public Date getClosingToday(){
        Calendar cal = new GregorianCalendar();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);  // oggi
        Date cl = new Date();
        cl.setHours(this.closings[dayOfWeek-1].getHour());
        cl.setMinutes(this.closings[dayOfWeek-1].getMinute());
        return cl;
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
        Database.openDatabase();
        try {
            for(String s :Database.getPizze(menu).keySet()){
                addPizza(menu.get(s));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
/*
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
        addPizza(thanos);*/
    }

    public HashMap<String, Pizza> getMenu() {
        return this.menu;
    }

    public HashMap<String, Toppings> getIngredientsPizzeria() {
        return this.pizzeriaIngredients;
    }

    /** Crea un nuovo ordine e aggiorna il numero di ordini giornalieri. */
    public Order initializeNewOrder() {
        Order order = new Order(this.numDailyOrders);
        this.numDailyOrders++;
        return order;
    }

    /** Su Interfaces.TextInterface dà il benvenuto al cliente, fornendo le informazioni essenziali della pizzeria. */
    public String helloThere(){
        String opTime = Services.timeStamp(getOpeningToday().getHours(), getOpeningToday().getMinutes());
        String clTime = Services.timeStamp(getClosingToday().getHours(), getClosingToday().getMinutes());
        StringBuilder hello = new StringBuilder("\n");
        hello.append(Services.colorSystemOut("\nBenvenuto!\n", Color.GREEN,true,true));
        hello.append(Services.colorSystemOut("\nPIZZERIA ", Color.ORANGE,false,false));
        hello.append(Services.colorSystemOut("\"" + this.name + "\"\n\t",Color.RED,true,false));
        hello.append(Services.colorSystemOut(this.address,Color.ORANGE,false,false));
        if(getOpeningToday().equals(getClosingToday()))
            hello.append(Services.colorSystemOut("\n\tOGGI CHIUSO", Color.RED, true, false));
        else {
            hello.append(Services.colorSystemOut("\n\tApertura oggi: ", Color.ORANGE, false, false));
            hello.append(Services.colorSystemOut(opTime + " - " + clTime, Color.RED, true, false));
        }
        hello.append("\n").append(Services.getLine());
        return hello.toString();
    }

    /** Da Interfaces.TextInterface, permette di stampare a video il menu completo. */
    public String printMenu() {
        String line = Services.getLine();
        Services.paintMenuString();
        StringBuilder s = new StringBuilder();
        for (String a : this.menu.keySet()) {
            s.append("\n").append(this.menu.get(a).toString());
        }
        return s+"\n"+line;
    }

    /** Controlla che la pizzeria sia aperta in un determinato orario, nella giornata odierna. */
    public boolean isOpen(Date d){
        int openTime = Services.getMinutes(getOpeningToday());
        int closeTime = Services.getMinutes(getClosingToday());
        int requestTime = Services.getMinutes(d);

        return (requestTime >= openTime && requestTime < closeTime);
    }

    /** ritorna l'indice della casella temporale (forno) desiderata. */
    public int findTimeBoxOven(int oraDesiderata, int minutiDesiderati){
        int openMinutes = Services.getMinutes(getOpeningToday());
        int desiredMinutes = Services.getMinutes(oraDesiderata,minutiDesiderati);
        return (desiredMinutes - openMinutes)/this.OVEN_MINUTES;
    }

    /** ritorna l'indice della casella temporale (fattorino) desiderata. */
    public int findTimeBoxDeliveryMan(int oraDesiderata, int minutiDesiderati){
        int openMinutes = Services.getMinutes(getOpeningToday());
        int desiredMinutes = Services.getMinutes(oraDesiderata,minutiDesiderati);
        return (desiredMinutes - openMinutes)/this.DELIVERYMAN_MINUTES;
    }

    /** restituisce il primo fattorino della pizzeria che sia disponibile all'orario indicato. */
    public DeliveryMan aFreeDeliveryMan(int oraDesiderata, int minutiDesiderati){
        for(DeliveryMan a : this.deliveryMen){
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
        int esclusiIniziali = Services.calculateStartIndex(this, now, tot);     // primo orario da visualizzare (in minuti)

        for(int i = esclusiIniziali; i < restaAperta; i++) {    // considera i tempi minimi di preparazione e consegna
            if(i % 5 == 0) {
                if (this.ovens[i / 5].getPostiDisp() + this.ovens[(i / 5) - 1].getPostiDisp() >= tot) {
                    for (DeliveryMan a : this.deliveryMen) {
                        if (a.getDeliveryManTimes()[i / 10].isFree()) {
                            int newMinutes = Services.getMinutes(getOpeningToday()) + i;   // NON POSSO PARTIRE DA TROVACASELLA MENO 1: RISCHIO ECCEZIONE
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
        if(this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].getPostiDisp()<tot){
            int disp = this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].getPostiDisp();
            this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].inserisciInfornate(disp);
            this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())-1].inserisciInfornate(tot-disp);
        } else{
            this.ovens[findTimeBoxOven(d.getHours(), d.getMinutes())].inserisciInfornate(tot);
        }
        Objects.requireNonNull(aFreeDeliveryMan(d.getHours(), d.getMinutes())).assignDelivery(findTimeBoxDeliveryMan(d.getHours(), d.getMinutes()));
    }

    public double getSUPPL_PRICE() {
        return this.SUPPL_PRICE;
    }

    public Oven[] getOvens() {
        return this.ovens;
    }

    /** In Interfaces.TextInterface, elenca tutte gli ingredienti che l'utente può scegliere, per modificare una pizza. */
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

    /** Verifica che sia possibile cuocere le pizze nell'infornata richiesta e in quella appena precedente. */
    public boolean checkTimeBoxOven(int ora, int minuti, int tot) {
        if (this.ovens[findTimeBoxOven(ora, minuti)].getPostiDisp() + this.ovens[findTimeBoxOven(ora, minuti) - 1].getPostiDisp() < tot)
            return false;
        else
            return true;
    }

    public int getAvailablePlaces() {
        return this.availablePlaces;
    }

    public int getNumDailyOrders() {
        return this.numDailyOrders;
    }

    public int getOVEN_MINUTES() {
        return this.OVEN_MINUTES;
    }

    public int getDELIVERYMAN_MINUTES() {
        return this.DELIVERYMAN_MINUTES;
    }

    //TODO: sistemare quando avremo login
    public String checkLogin(String user, String psw) throws SQLException {
        if(user.equals(this.userPizzeria) && psw.equals(this.pswPizzeria)){
            // se è la pizzeria, allora accede come tale.
            return "P";
        } else if (Database.getCustomers(user,psw)){
            // se è un utente identificato, accede come tale.
            return "OK";
        } else {
            // se la combinazione utente-password è errata
            return "NO";
        }
    }

    public String createAccount(String newUser, String newPsw, String confPsw) throws SQLException {
        boolean existing = false;
        // faccio scorrere tutti gli account e controllo che non esista già.
        // se esistente, pongo existing a true.

        if(!existing && newPsw.equals(confPsw)){
            if(newPsw.length()>2) {
                // se si registra correttamente, va bene.
                // createNewAccount(newUser,newPsw);
                Database.putCustomer(newUser,newPsw);
                //checkLogin(newUser, newPsw);
                return "OK";
            } else
                // password troppo breve.
                return "SHORT";
        } else if (existing){
            // se esiste già un account con questo nome, non va bene.
            return "EXISTING";
        } else {
            // se la password non viene confermata correttamente
            return "DIFFERENT";
        }
    }
}