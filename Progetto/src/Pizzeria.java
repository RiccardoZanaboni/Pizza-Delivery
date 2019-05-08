import java.util.*;
@SuppressWarnings("deprecation")


public class Pizzeria {
    private String nome;
    private String indirizzo;
    private Date orarioChiusura;
    private Date orarioApertura;
    private Forno[] infornate;
    private ArrayList<DeliveryMan> fattorini;
    private HashMap<String, Pizza> menu;
    private HashMap<String, Ingredienti> ingredientiPizzeria;
    private ArrayList<Order> ordini;
    private int ordiniDelGiorno;
    private final int TEMPI_FORNO = 12;      // ogni 5 minuti
    private final int TEMPI_FATTORINI = 6;   // ogni 10 minuti
    //private Scanner scan = new Scanner(System.in);
    private final double PREZZO_SUPPL = 0.5;

    public Pizzeria(String nome, String indirizzo, Date orarioApertura, Date orarioChiusura) {
        this.menu = new HashMap<>();
        this.ingredientiPizzeria = new HashMap<>();
        this.nome = nome;
        this.ordiniDelGiorno = 0;
        this.ordini = new ArrayList<>();
        this.indirizzo = indirizzo;
        this.orarioChiusura = orarioChiusura;
        this.orarioApertura = orarioApertura;
        this.infornate = new Forno[TEMPI_FORNO * (orarioChiusura.getHours() - orarioApertura.getHours())];
        this.fattorini= new ArrayList<>();
        creaMenu();
        setIngredientiPizzeria();
    }

    public void addOrdine(Order order) {
        this.ordini.add(order);
    }

    public void AddPizza(Pizza pizza){
        menu.put(pizza.getNomeMaiusc(),pizza);
    }

    public void AddFattorino(DeliveryMan deliveryMan){
        fattorini.add(deliveryMan);
    }

    public void ApriPizzeria(int postidisponibili){     // ripristina il vettore di infornate ad ogni apertura della pizzeria
        for(int i=0;i<infornate.length;i++){
            infornate[i]=new Forno(postidisponibili);
        }
    }

    public void setIngredientiPizzeria(){
        this.ingredientiPizzeria.put(Ingredienti.ALICI.name(), Ingredienti.ALICI);
        this.ingredientiPizzeria.put(Ingredienti.BASILICO.name(), Ingredienti.BASILICO);
        this.ingredientiPizzeria.put(Ingredienti.BELLE_DONNE.name(), Ingredienti.BELLE_DONNE);
        this.ingredientiPizzeria.put(Ingredienti.CAPPERI.name(), Ingredienti.CAPPERI);
        this.ingredientiPizzeria.put(Ingredienti.COTTO.name(), Ingredienti.COTTO);
        this.ingredientiPizzeria.put(Ingredienti.CRUDO.name(), Ingredienti.CRUDO);
        this.ingredientiPizzeria.put(Ingredienti.FUNGHI.name(), Ingredienti.FUNGHI);
        this.ingredientiPizzeria.put(Ingredienti.GALANTERIA.name(), Ingredienti.GALANTERIA);
        this.ingredientiPizzeria.put(Ingredienti.GEMME_DELL_INFINITO.name(), Ingredienti.GEMME_DELL_INFINITO);
        this.ingredientiPizzeria.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        this.ingredientiPizzeria.put(Ingredienti.MOZZARELLA_DI_BUFALA.name(), Ingredienti.MOZZARELLA_DI_BUFALA);
        this.ingredientiPizzeria.put(Ingredienti.OLIVE_NERE.name(), Ingredienti.OLIVE_NERE);
        this.ingredientiPizzeria.put(Ingredienti.ORIGANO.name(), Ingredienti.ORIGANO);
        this.ingredientiPizzeria.put(Ingredienti.PATATINE.name(), Ingredienti.PATATINE);
        this.ingredientiPizzeria.put(Ingredienti.PEPERONI.name(), Ingredienti.PEPERONI);
        this.ingredientiPizzeria.put(Ingredienti.POMODORINI.name(), Ingredienti.POMODORINI);
        this.ingredientiPizzeria.put(Ingredienti.RUCOLA.name(), Ingredienti.RUCOLA);
        this.ingredientiPizzeria.put(Ingredienti.SALAME_PICCANTE.name(), Ingredienti.SALAME_PICCANTE);
        this.ingredientiPizzeria.put(Ingredienti.SALSICCIA.name(), Ingredienti.SALSICCIA);
        this.ingredientiPizzeria.put(Ingredienti.WURSTEL.name(), Ingredienti.WURSTEL);
    }

    public void creaMenu(){
        HashMap<String, Ingredienti> i1 = new HashMap <>();
        i1.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i1.put(Ingredienti.ORIGANO.name(), Ingredienti.ORIGANO);
        Pizza marinara = new Pizza("MARINARA", i1, 3.5);
        AddPizza(marinara);

        HashMap<String, Ingredienti> i2 = new HashMap <>();
        i2.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i2.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        Pizza margherita = new Pizza("MARGHERITA", i2, 4.5);
        AddPizza(margherita);

        HashMap<String, Ingredienti> i3 = new HashMap <>();
        i3.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i3.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i3.put(Ingredienti.PATATINE.name(), Ingredienti.PATATINE);
        Pizza patatine = new Pizza("PATATINE", i3, 5.5);
        AddPizza(patatine);

        HashMap<String, Ingredienti> i4 = new HashMap <>();
        i4.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i4.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i4.put(Ingredienti.WURSTEL.name(), Ingredienti.WURSTEL);
        Pizza wurstel = new Pizza("WURSTEL", i4, 5.5);
        AddPizza(wurstel);

        HashMap<String, Ingredienti> i5 = new HashMap <>();
        i5.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i5.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i5.put(Ingredienti.ALICI.name(), Ingredienti.ALICI);
        Pizza napoli = new Pizza("NAPOLI", i5, 5.5);
        AddPizza(napoli);

        HashMap<String, Ingredienti> i6 = new HashMap <>();
        i6.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i6.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i6.put(Ingredienti.COTTO.name(), Ingredienti.COTTO);
        Pizza cotto = new Pizza("COTTO", i6, 5.5);
        AddPizza(cotto);

        HashMap<String, Ingredienti> i7 = new HashMap <>();
        i7.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i7.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i7.put(Ingredienti.COTTO.name(), Ingredienti.COTTO);
        i7.put(Ingredienti.FUNGHI.name(), Ingredienti.FUNGHI);
        Pizza cottoFunghi = new Pizza("COTTO E FUNGHI", i7, 6);
        AddPizza(cottoFunghi);

        HashMap<String, Ingredienti> i8 = new HashMap <>();
        i8.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i8.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i8.put(Ingredienti.SALAME_PICCANTE.name(), Ingredienti.SALAME_PICCANTE);
        Pizza salamePicc = new Pizza("SALAME PICCANTE", i8, 5.5);
        AddPizza(salamePicc);

        HashMap<String, Ingredienti> i9 = new HashMap <>();
        i9.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i9.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i9.put(Ingredienti.SALSICCIA.name(), Ingredienti.SALSICCIA);
        i9.put(Ingredienti.PEPERONI.name(), Ingredienti.PEPERONI);
        Pizza americana = new Pizza("AMERICANA", i9, 6.5);
        AddPizza(americana);

        HashMap<String, Ingredienti> i10 = new HashMap <>();
        i10.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i10.put(Ingredienti.COTTO.name(), Ingredienti.COTTO);
        i10.put(Ingredienti.CARCIOFI.name(), Ingredienti.CARCIOFI);
        i10.put(Ingredienti.FUNGHI.name(), Ingredienti.FUNGHI);
        i10.put(Ingredienti.OLIVE_NERE.name(), Ingredienti.OLIVE_NERE);
        Pizza capricciosa = new Pizza("CAPRICCIOSA", i10, 7);
        AddPizza(capricciosa);

        HashMap<String, Ingredienti> i11 = new HashMap <>();
        i11.put(Ingredienti.POMODORINI.name(), Ingredienti.POMODORINI);
        i11.put(Ingredienti.MOZZARELLA_DI_BUFALA.name(), Ingredienti.MOZZARELLA_DI_BUFALA);
        i11.put(Ingredienti.RUCOLA.name(), Ingredienti.RUCOLA);
        Pizza italia = new Pizza("ITALIA", i11, 7.5);
        AddPizza(italia);

        HashMap<String, Ingredienti> i12 = new HashMap <>();
        i12.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i12.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i12.put(Ingredienti.GALANTERIA.name(), Ingredienti.GALANTERIA);
        i12.put(Ingredienti.BELLE_DONNE.name(), Ingredienti.BELLE_DONNE);
        Pizza allaMusi = new Pizza("ALLA MUSI", i12, 100000);
        AddPizza(allaMusi);

        HashMap<String, Ingredienti> i13 = new HashMap <>();
        i13.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i13.put(Ingredienti.MOZZARELLA_DI_BUFALA.name(), Ingredienti.MOZZARELLA_DI_BUFALA);
        i13.put(Ingredienti.GEMME_DELL_INFINITO.name(), Ingredienti.GEMME_DELL_INFINITO);
        i13.put(Ingredienti.ONNIPOTENZA.name(), Ingredienti.ONNIPOTENZA);
        i13.put(Ingredienti.ORIGANO.name(), Ingredienti.ORIGANO);
        Pizza thanos = new Pizza("THANOS", i13, 0.50);
        AddPizza(thanos);
    }

    public HashMap<String, Pizza> getMenu() {
        return menu;
    }

    public int getOrdiniDelGiorno() {
        return ordiniDelGiorno;
    }

    public HashMap<String, Ingredienti> getIngredientiPizzeria() {
        return ingredientiPizzeria;
    }

    public void setOrdiniDelGiorno() {
        this.ordiniDelGiorno ++;
    }

    public Order inizializeNewOrder() {
        Order order = new Order(ordiniDelGiorno);
        setOrdiniDelGiorno();
        //System.out.println(helloThere());
        //System.out.println(stampaMenu());
        return order;
    }

    public String helloThere(){         // da sistemare orario apertura-chiusura!!!
        String dati = "\nPIZZERIA \"" + this.nome + "\"\n\t" + this.indirizzo;
        String open = "\n\tApertura: "+ this.orarioApertura.getHours() + ":00 - " + this.orarioChiusura.getHours() + ":00";
        return "\n--------------------------------------------------------------------------------------\n" + dati + open;
    }

    public String stampaMenu() {
        String line= "\n--------------------------------------------------------------------------------------\n";
        StringBuilder s= new StringBuilder("    >>  MENU\n");
        for (String a:menu.keySet()) {
            s.append("\n").append(menu.get(a).toString());
        }
        return line+s+line;
    }

    public boolean controllaApertura(Date d){
        int ora= d.getHours();
        int minuti= d.getMinutes();
        return !(ora < this.orarioApertura.getHours() || ora > this.orarioChiusura.getHours() || (ora == this.orarioChiusura.getHours() && minuti >= this.orarioChiusura.getMinutes()) || (ora == this.orarioApertura.getHours() && minuti <= this.orarioApertura.getMinutes()));
    }

    public int trovaCasellaTempoForno(Date oraApertura, int oraDesiderata, int minutiDesiderati){
        int casellaTempo = this.TEMPI_FORNO*(oraDesiderata - oraApertura.getHours());
        casellaTempo += minutiDesiderati/5;
        return casellaTempo;
    }

    public int trovaCasellaTempoFattorino(Date oraApertura, int oraDesiderata, int minutiDesiderati){
        int casellaTempo = this.TEMPI_FATTORINI*(oraDesiderata - oraApertura.getHours());
        casellaTempo += minutiDesiderati/10;
        return casellaTempo;
    }

    public DeliveryMan fattorinoLibero(Date oraApertura, int oraDesiderata, int minutiDesiderati,int indice){
        for(DeliveryMan a:this.fattorini){
            if(!a.getFattoriniTempi()[trovaCasellaTempoFattorino(oraApertura,oraDesiderata,minutiDesiderati)-indice].isOccupato()){
                return a;
            }
        }
        return null;
    }

    public ArrayList<String> orarioDisponibile(int tot){
        ArrayList<String> disp = new ArrayList<>();
        for(int i=1; i<this.infornate.length; i++) {
            if (infornate[i].getPostiDisp() + infornate[i-1].getPostiDisp() >= tot) {
                for(DeliveryMan a:this.fattorini){
                    if(!a.getFattoriniTempi()[i/2].isOccupato()){
                        int oraNew = this.orarioApertura.getHours() + i/12;   //NON POSSO PARTIRE DA TROVACASELLA MENO 1: RISCHIO ECCEZIONE
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

    public void recapOrdine(Order order){
        String line = "\n---------------------------------------------\n";
        String codice = "ORDINE N. " + order.getCodice() + "\n";
        String dati = "SIG. " + order.getCustomer().getUsername() + "\tINDIRIZZO: " + order.getIndirizzo() + "\tORARIO: " + order.getOrario() + "\n";
        String prodotti = order.recap();
        double totaleCosto = order.getTotaleCosto();
        System.out.println(line + codice + dati + prodotti + "\t\t\tTOTALE: € " + totaleCosto + line);
    }

    public boolean checkFornoFattorino(Order order, Date d, int tot){
        //if (s.toUpperCase().equals("S")) {
            //order.setOrario(d);     //PRIMA CONDIZIONE PER LE INFORNATE, SUCCESSIVA SUI FATTORINI
            if(infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].getPostiDisp()<tot){
                int disp=infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].getPostiDisp();
                infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].inserisciInfornate(disp);
                infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())-1].inserisciInfornate(tot-disp);
            } else{
                infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].inserisciInfornate(tot);
            }
            fattorinoLibero(this.orarioApertura,d.getHours(),d.getMinutes(),0).OccupaFattorino(trovaCasellaTempoFattorino(this.orarioApertura, d.getHours(), d.getMinutes()));
            return true;
    }

    public Date getOrarioChiusura() {
        return orarioChiusura;
    }

    public Date getOrarioApertura() {
        return orarioApertura;
    }

    public double getPREZZO_SUPPL() {
        return PREZZO_SUPPL;
    }

    public Forno[] getInfornate() {
        return infornate;
    }
}



/*    public void makeOrder() {
        int num;
        int ordinate=0;
        boolean ok;
        String nomePizza;

        Order order = new Order(this.ordiniDelGiorno);
        this.ordiniDelGiorno++;
        System.out.println(helloThere());
        System.out.println(stampaMenu());

        int tot = quantePizze();
        System.out.println(tot);

        Date orario = inserisciOrario(order,tot);
        if(orario!=null) {
            order.setOrario(orario);
            System.out.println(orario);

            do {
                nomePizza = qualePizza();
                if(nomePizza.equals("F"))
                    break;
                num = quantePizzaSpecifica(order,nomePizza,tot-ordinate);
                System.out.println("ordinate " + num + " " + nomePizza);
                ordinate += num;
            } while(ordinate<tot);

            if(!nomePizza.equals("F")) {
                ok=inserisciDati(order);
                if (ok) {
                    recapOrdine(order);
                    checkFornoFattorino(order,orario,tot);
                }
            }
        }
    }*/

/*private int quantePizze(){
        int tot=0;
        String line;
        while(tot<=0){
            System.out.println("Quante pizze vuoi ordinare?");
            line = scan.nextLine();
            try {
                tot = Integer.parseInt(line);   // può generare NumberFormatException
                if(tot<=0)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Spiacenti: inserito numero non valido. Riprovare:");
            }
        }
        return tot;
    }*/



    /*public boolean chiediConferma(Order order, Date d, int tot){
        //if (s.toUpperCase().equals("S")) {
        //order.setOrario(d);     //PRIMA CONDIZIONE PER LE INFORNATE, SUCCESSIVA SUI FATTORINI
        if(infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].getPostiDisp()<tot){
            int disp=infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].getPostiDisp();
            infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].inserisciInfornate(disp);
            infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())-1].inserisciInfornate(tot-disp);
        } else{
            infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].inserisciInfornate(tot);
        }
        fattorinoLibero(this.orarioApertura,d.getHours(),d.getMinutes(),0).OccupaFattorino(trovaCasellaTempoFattorino(this.orarioApertura, d.getHours(), d.getMinutes()));
        order.setCompleto();
        ordini.add(order);
        return true;
    }*/

   /* private String qualePizza(){
        String nomePizza;
        boolean ok=false;
        do {
            System.out.println("Quale pizza desideri?\t\t(Inserisci 'F' per annullare e ricominciare)");
            nomePizza = scan.nextLine().toUpperCase();
            try {
                if (nomePizza.equals("F")) {
                    ok=true;
                    throw new RestartOrderExc();
                } else if (!(menu.containsKey(nomePizza)))         // qui ci vorrebbe una eccezione invece della if-else
                    throw new RiprovaExc();
                else
                    ok = true;
            } catch (RestartOrderExc e){
                makeOrder();
            } catch (RiprovaExc e){
                System.out.println("Spiacenti: \"" + nomePizza + "\" non presente sul menu. Riprovare:");
            }
        } while(!ok);
        return nomePizza;
    }*/

    /*private int quantePizzaSpecifica(Order order, String nomePizza, int disponibili) {   // FUNZIONA BENE
        boolean ok = false;
        int num = 0;
        do {
            System.out.println("Quante " + nomePizza + " vuoi?\t[0..n]");
            String line = scan.nextLine();
            try {
                num = Integer.parseInt(line);
                if(num<0)
                    throw new NumberFormatException();
                else if(num>disponibili)
                    throw new RiprovaExc();
                else {
                    ok = true;
                    chiediModificaPizza(order,nomePizza,num);
                }
            } catch (NumberFormatException e) {
                System.out.println("Spiacenti: inserito numero non valido. Riprovare:");
            } catch (RiprovaExc e) {
                System.out.println("Massimo numero di pizze ordinate superato. Puoi ordinare ancora " + disponibili + " pizze:");
            }
        } while(!ok);
        return num;
    }*/

    /*public void chiediModificaPizza(Order order, String nomePizza, int num){
        System.out.print("Vuoi apportare modifiche alle " + num + " " + nomePizza + "?\t(S/N)");
        if(scan.nextLine().toUpperCase().equals("S")) {
            System.out.print("Inserisci gli ingredienti da AGGIUNGERE, separati da virgola, poi invio:\n");
            String aggiunte = scan.nextLine();
            System.out.print("Inserisci gli ingredienti da RIMUOVERE, separati da virgola, poi invio:\n");
            String rimozioni = scan.nextLine();
            order.addPizza(menu.get(nomePizza), aggiunte, rimozioni, num, PREZZO_SUPPL);
        } else
            order.addPizza(menu.get(nomePizza), num);
    }*/

  /*  public boolean inserisciDati(Order order){
        boolean ok=true;
        try {
            System.out.println("Come ti chiami?\t\t(Inserisci 'F' per annullare e ricominciare)");
            String nome = scan.nextLine();
            if (nome.toUpperCase().equals("F")) {
                ok=false;
                throw new RestartOrderExc();
            }
            Customer c = new Customer(nome);
            order.setCustomer(c);
            System.out.println("Inserisci l'indirizzo di consegna:\t\t(Inserisci 'F' per annullare e ricominciare)");
            String indirizzo = scan.nextLine();
            if (indirizzo.toUpperCase().equals("F")) {
                ok=false;
                throw new RestartOrderExc();
            }
            order.setIndirizzo(indirizzo);
        } catch (RestartOrderExc e){
            makeOrder();
        }
        return ok;
    }

   */

    /*public void recapOrdine(Order order){
        String line = "\n---------------------------------------------\n";
        String codice = "ORDINE N. " + order.getCodice() + "\n";
        String dati = "SIG. " + order.getCustomer().getUsername() + "\tINDIRIZZO: " + order.getIndirizzo() + "\tORARIO: " + order.getOrario() + "\n";
        String prodotti = order.recap();
        double totaleCosto = order.getTotaleCosto();
        System.out.println(line + codice + dati + prodotti + "\t\t\tTOTALE: € " + totaleCosto + line);
    }

    public boolean checkFornoFattorino(Date d, int tot){   //Order order, Date d, int tot
        //if (s.toUpperCase().equals("S")) {
            //order.setOrario(d);     //PRIMA CONDIZIONE PER LE INFORNATE, SUCCESSIVA SUI FATTORINI
            if(infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].getPostiDisp()<tot){
                int disp=infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].getPostiDisp();
                infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].inserisciInfornate(disp);
                infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())-1].inserisciInfornate(tot-disp);
            } else{
                infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].inserisciInfornate(tot);
            }
            fattorinoLibero(this.orarioApertura,d.getHours(),d.getMinutes(),0).OccupaFattorino(trovaCasellaTempoFattorino(this.orarioApertura, d.getHours(), d.getMinutes()));
            return true;
    }

    public Date getOrarioChiusura() {
        return orarioChiusura;
    }

    public Date getOrarioApertura() {
        return orarioApertura;
    }

    public double getPREZZO_SUPPL() {
        return PREZZO_SUPPL;
    }

    public Forno[] getInfornate() {
        return infornate;
    }
}*/