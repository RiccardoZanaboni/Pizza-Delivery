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
    private HashMap<String, Ingredients> pizzeriaIngredients;
    private ArrayList<Order> orders;
    private int numDailyOrders;
    private final int OVEN_TIMES_FOR_HOUR = 12;      // ogni 5 minuti
    private final int DELIVERYMAN_TIMES_FOR_HOURS = 6;   // ogni 10 minuti
    private final double SUPPL_PRICE = 0.5;

    public Pizzeria(String name, String address, Date closingTime, Date openingTime) {
        this.menu = new HashMap<>();
        this.pizzeriaIngredients = new HashMap<>();
        this.name = name;
        this.numDailyOrders = 0;
        this.orders = new ArrayList<>();
        this.address = address;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.ovens = new Oven[OVEN_TIMES_FOR_HOUR * (openingTime.getHours() - closingTime.getHours())];
        this.deliveryMen = new ArrayList<>();
        setIngredientsPizzeria();
        createMenu();
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    private void AddPizza(Pizza pizza){
        menu.put(pizza.getMaiuscName(),pizza);
    }

    public void AddDeliveryMan(DeliveryMan deliveryMan){
        deliveryMen.add(deliveryMan);
    }

    public void OpenPizzeria(int postidisponibili){
        for(int i = 0; i< ovens.length; i++){        // ripristina il vettore di ovens ad ogni apertura della pizzeria
            ovens[i]=new Oven(postidisponibili);
        }
    }

    private void setIngredientsPizzeria(){
        this.pizzeriaIngredients.put(Ingredients.ALICI.name(), Ingredients.ALICI);
        this.pizzeriaIngredients.put(Ingredients.BASILICO.name(), Ingredients.BASILICO);
        this.pizzeriaIngredients.put(Ingredients.BELLE_DONNE.name(), Ingredients.BELLE_DONNE);
        this.pizzeriaIngredients.put(Ingredients.CAPPERI.name(), Ingredients.CAPPERI);
        this.pizzeriaIngredients.put(Ingredients.COTTO.name(), Ingredients.COTTO);
        this.pizzeriaIngredients.put(Ingredients.CRUDO.name(), Ingredients.CRUDO);
        this.pizzeriaIngredients.put(Ingredients.FUNGHI.name(), Ingredients.FUNGHI);
        this.pizzeriaIngredients.put(Ingredients.GALANTERIA.name(), Ingredients.GALANTERIA);
        this.pizzeriaIngredients.put(Ingredients.GEMME_DELL_INFINITO.name(), Ingredients.GEMME_DELL_INFINITO);
        this.pizzeriaIngredients.put(Ingredients.MOZZARELLA.name(), Ingredients.MOZZARELLA);
        this.pizzeriaIngredients.put(Ingredients.MOZZARELLA_DI_BUFALA.name(), Ingredients.MOZZARELLA_DI_BUFALA);
        this.pizzeriaIngredients.put(Ingredients.OLIVE_NERE.name(), Ingredients.OLIVE_NERE);
        this.pizzeriaIngredients.put(Ingredients.ONNIPOTENZA.name(), Ingredients.ONNIPOTENZA);
        this.pizzeriaIngredients.put(Ingredients.ORIGANO.name(), Ingredients.ORIGANO);
        this.pizzeriaIngredients.put(Ingredients.PATATINE.name(), Ingredients.PATATINE);
        this.pizzeriaIngredients.put(Ingredients.PEPERONI.name(), Ingredients.PEPERONI);
        this.pizzeriaIngredients.put(Ingredients.POMODORINI.name(), Ingredients.POMODORINI);
        this.pizzeriaIngredients.put(Ingredients.POMODORO.name(), Ingredients.POMODORO);
        this.pizzeriaIngredients.put(Ingredients.RUCOLA.name(), Ingredients.RUCOLA);
        this.pizzeriaIngredients.put(Ingredients.SALAME_PICCANTE.name(), Ingredients.SALAME_PICCANTE);
        this.pizzeriaIngredients.put(Ingredients.SALSICCIA.name(), Ingredients.SALSICCIA);
        this.pizzeriaIngredients.put(Ingredients.WURSTEL.name(), Ingredients.WURSTEL);
    }

    public void createMenu(){
        HashMap<String, Ingredients> i1 = new HashMap <>();
        i1.put(Ingredients.POMODORO.name(), Ingredients.POMODORO);
        i1.put(Ingredients.ORIGANO.name(), Ingredients.ORIGANO);
        Pizza marinara = new Pizza("MARINARA", i1, 3.5);
        AddPizza(marinara);

        HashMap<String, Ingredients> i2 = new HashMap <>();
        i2.put(Ingredients.POMODORO.name(), Ingredients.POMODORO);
        i2.put(Ingredients.MOZZARELLA.name(), Ingredients.MOZZARELLA);
        Pizza margherita = new Pizza("MARGHERITA", i2, 4.5);
        AddPizza(margherita);

        HashMap<String, Ingredients> i3 = new HashMap <>();
        i3.put(Ingredients.POMODORO.name(), Ingredients.POMODORO);
        i3.put(Ingredients.MOZZARELLA.name(), Ingredients.MOZZARELLA);
        i3.put(Ingredients.PATATINE.name(), Ingredients.PATATINE);
        Pizza patatine = new Pizza("PATATINE", i3, 5);
        AddPizza(patatine);

        HashMap<String, Ingredients> i4 = new HashMap <>();
        i4.put(Ingredients.POMODORO.name(), Ingredients.POMODORO);
        i4.put(Ingredients.MOZZARELLA.name(), Ingredients.MOZZARELLA);
        i4.put(Ingredients.WURSTEL.name(), Ingredients.WURSTEL);
        Pizza wurstel = new Pizza("WURSTEL", i4, 5);
        AddPizza(wurstel);

        HashMap<String, Ingredients> i5 = new HashMap <>();
        i5.put(Ingredients.POMODORO.name(), Ingredients.POMODORO);
        i5.put(Ingredients.MOZZARELLA.name(), Ingredients.MOZZARELLA);
        i5.put(Ingredients.ALICI.name(), Ingredients.ALICI);
        Pizza napoli = new Pizza("NAPOLI", i5, 5);
        AddPizza(napoli);

        HashMap<String, Ingredients> i6 = new HashMap <>();
        i6.put(Ingredients.POMODORO.name(), Ingredients.POMODORO);
        i6.put(Ingredients.MOZZARELLA.name(), Ingredients.MOZZARELLA);
        i6.put(Ingredients.COTTO.name(), Ingredients.COTTO);
        Pizza cotto = new Pizza("COTTO", i6, 5);
        AddPizza(cotto);

        HashMap<String, Ingredients> i7 = new HashMap <>();
        i7.put(Ingredients.POMODORO.name(), Ingredients.POMODORO);
        i7.put(Ingredients.MOZZARELLA.name(), Ingredients.MOZZARELLA);
        i7.put(Ingredients.COTTO.name(), Ingredients.COTTO);
        i7.put(Ingredients.FUNGHI.name(), Ingredients.FUNGHI);
        Pizza cottoFunghi = new Pizza("COTTO E FUNGHI", i7, 5.5);
        AddPizza(cottoFunghi);

        HashMap<String, Ingredients> i8 = new HashMap <>();
        i8.put(Ingredients.POMODORO.name(), Ingredients.POMODORO);
        i8.put(Ingredients.MOZZARELLA.name(), Ingredients.MOZZARELLA);
        i8.put(Ingredients.SALAME_PICCANTE.name(), Ingredients.SALAME_PICCANTE);
        Pizza salamePicc = new Pizza("SALAME PICCANTE", i8, 5);
        AddPizza(salamePicc);

        HashMap<String, Ingredients> i9 = new HashMap <>();
        i9.put(Ingredients.POMODORO.name(), Ingredients.POMODORO);
        i9.put(Ingredients.MOZZARELLA.name(), Ingredients.MOZZARELLA);
        i9.put(Ingredients.SALSICCIA.name(), Ingredients.SALSICCIA);
        i9.put(Ingredients.PEPERONI.name(), Ingredients.PEPERONI);
        Pizza americana = new Pizza("AMERICANA", i9, 6);
        AddPizza(americana);

        HashMap<String, Ingredients> i10 = new HashMap <>();
        i10.put(Ingredients.POMODORO.name(), Ingredients.POMODORO);
        i10.put(Ingredients.COTTO.name(), Ingredients.COTTO);
        i10.put(Ingredients.CARCIOFI.name(), Ingredients.CARCIOFI);
        i10.put(Ingredients.FUNGHI.name(), Ingredients.FUNGHI);
        i10.put(Ingredients.OLIVE_NERE.name(), Ingredients.OLIVE_NERE);
        Pizza capricciosa = new Pizza("CAPRICCIOSA", i10, 6);
        AddPizza(capricciosa);

        HashMap<String, Ingredients> i11 = new HashMap <>();
        i11.put(Ingredients.POMODORINI.name(), Ingredients.POMODORINI);
        i11.put(Ingredients.MOZZARELLA_DI_BUFALA.name(), Ingredients.MOZZARELLA_DI_BUFALA);
        i11.put(Ingredients.RUCOLA.name(), Ingredients.RUCOLA);
        Pizza italia = new Pizza("ITALIA", i11, 6);
        AddPizza(italia);

        HashMap<String, Ingredients> i12 = new HashMap <>();
        i12.put(Ingredients.POMODORO.name(), Ingredients.POMODORO);
        i12.put(Ingredients.MOZZARELLA.name(), Ingredients.MOZZARELLA);
        i12.put(Ingredients.GALANTERIA.name(), Ingredients.GALANTERIA);
        i12.put(Ingredients.BELLE_DONNE.name(), Ingredients.BELLE_DONNE);
        Pizza allaMusi = new Pizza("ALLA MUSI", i12, 8.5);
        AddPizza(allaMusi);

        HashMap<String, Ingredients> i13 = new HashMap <>();
        i13.put(Ingredients.POMODORO.name(), Ingredients.POMODORO);
        i13.put(Ingredients.MOZZARELLA_DI_BUFALA.name(), Ingredients.MOZZARELLA_DI_BUFALA);
        i13.put(Ingredients.GEMME_DELL_INFINITO.name(), Ingredients.GEMME_DELL_INFINITO);
        i13.put(Ingredients.ONNIPOTENZA.name(), Ingredients.ONNIPOTENZA);
        i13.put(Ingredients.ORIGANO.name(), Ingredients.ORIGANO);
        Pizza thanos = new Pizza("THANOS", i13, 0.50);
        AddPizza(thanos);
    }

    public HashMap<String, Pizza> getMenu() {
        return menu;
    }

    public int getDailyOrders() {
        return numDailyOrders;
    }

    public HashMap<String, Ingredients> getIngredientsPizzeria() {
        return pizzeriaIngredients;
    }

    private void increaseDailyOrders() {
        this.numDailyOrders++;
    }

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

    public String printMenu() {
        String line= "\n--------------------------------------------------------------------------------------\n";
        StringBuilder s= new StringBuilder("    >>  MENU\n");
        for (String a:menu.keySet()) {
            s.append("\n").append(menu.get(a).toString());
        }
        return line+s+line;
    }

    public boolean isOpen(Date d){
        int ora= d.getHours();
        int minuti= d.getMinutes();
        return !(ora < this.closingTime.getHours() || ora > this.openingTime.getHours() || (ora == this.openingTime.getHours() && minuti >= this.openingTime.getMinutes()) || (ora == this.closingTime.getHours() && minuti <= this.closingTime.getMinutes()));
    }

    public int findTimeBoxOven(Date oraApertura, int oraDesiderata, int minutiDesiderati){
        int casellaTempo = this.OVEN_TIMES_FOR_HOUR *(oraDesiderata - oraApertura.getHours());
        casellaTempo += minutiDesiderati/5;
        return casellaTempo;
    }

    private int findTimeBoxDeliveryMan(Date oraApertura, int oraDesiderata, int minutiDesiderati){
        int casellaTempo = this.DELIVERYMAN_TIMES_FOR_HOURS *(oraDesiderata - oraApertura.getHours());
        casellaTempo += minutiDesiderati/10;
        return casellaTempo;
    }

    public DeliveryMan aFreeDeliveryMan(Date oraApertura, int oraDesiderata, int minutiDesiderati, int indice){
        for(DeliveryMan a:this.deliveryMen){
            if(!a.getDeliveryManTimes()[findTimeBoxDeliveryMan(oraApertura,oraDesiderata,minutiDesiderati)-indice].isBusy()){
                return a;
            }
        }
        return null;
    }

    public ArrayList<String> availableTimes(int tot){
        ArrayList<String> disp = new ArrayList<>();
        for(int i = 1; i<this.ovens.length; i++) {
            if (ovens[i].getPostiDisp() + ovens[i-1].getPostiDisp() >= tot) {
                for(DeliveryMan a:this.deliveryMen){
                    if(!a.getDeliveryManTimes()[i/2].isBusy()){
                        int oraNew = this.closingTime.getHours() + i/12;   //NON POSSO PARTIRE DA TROVACASELLA MENO 1: RISCHIO ECCEZIONE
                        int min = 5 * (i - 12*(i/12));      // divisione senza resto, quindi ha un suo senso
                        if(min<=5){
                            disp.add(oraNew + ":0" + min + "  ");
                        } else {
                            disp.add(oraNew + ":" + min + "  ");
                        }
                        break;
                    }
                }
            }
        }
        return disp;
    }

    public void recapOrder(Order order){
        String line = "\n---------------------------------------------\n";
        String codice = "ORDINE N. " + order.getOrderCode() + "\n";
        String dati = "SIG. " + order.getCustomer().getUsername() + "\tINDIRIZZO: " + order.getAddress() + "\tORARIO: " + order.getTime() + "\n";
        String prodotti = order.textRecap();
        double totaleCosto = order.getTotalPrice();
        System.out.println(line + codice + dati + prodotti + "\t\t\tTOTALE: € " + totaleCosto + line);
    }

    public boolean checkOvenAndDeliveryMan(Date d, int tot){
        //PRIMA CONDIZIONE PER LE INFORNATE, SUCCESSIVA SUI FATTORINI
        if(ovens[findTimeBoxOven(this.closingTime, d.getHours(), d.getMinutes())].getPostiDisp()<tot){
            int disp = ovens[findTimeBoxOven(this.closingTime, d.getHours(), d.getMinutes())].getPostiDisp();
            ovens[findTimeBoxOven(this.closingTime, d.getHours(), d.getMinutes())].inserisciInfornate(disp);
            ovens[findTimeBoxOven(this.closingTime, d.getHours(), d.getMinutes())-1].inserisciInfornate(tot-disp);
        } else{
            ovens[findTimeBoxOven(this.closingTime, d.getHours(), d.getMinutes())].inserisciInfornate(tot);
        }
        aFreeDeliveryMan(this.closingTime,d.getHours(),d.getMinutes(),0).OccupaFattorino(findTimeBoxDeliveryMan(this.closingTime, d.getHours(), d.getMinutes()));
        return true;            // ma ritorna sempre true???
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

    public String possibleAddictions() {
        StringBuilder possibiliIngr = new StringBuilder("Possibili aggiunte: ");
        int i = 0;
        for (Ingredients ingr : getIngredientsPizzeria().values()) {
            possibiliIngr.append(ingr.name().toLowerCase().replace("_", " ")).append(", ");
            i++;
            if (i % 10 == 0)
                possibiliIngr.append("\n");
        }
        return possibiliIngr.substring(0, possibiliIngr.lastIndexOf(",")); // elimina ultima virgola
    }
}