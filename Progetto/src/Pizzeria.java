import exceptions.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class Pizzeria {
    private String nome;
    private String indirizzo;
    private Date orarioChiusura;
    private Date orarioApertura;
    private Forno[] infornate;
    private ArrayList<DeliveryMan> fattorini;
    private HashMap<String, PizzaMenu> menu;
    private ArrayList<Order> ordini;
    private int ordiniDelGiorno;
    private final int TEMPI_FORNO = 12;      // ogni 5 minuti
    private final int TEMPI_FATTORINI = 6;   // ogni 10 minuti
    private final double PREZZO_SUPPL = 0.5;    // costo singolo ingrediente
    private Scanner scan = new Scanner(System.in);

    public Pizzeria(String nome, String indirizzo, Date orarioApertura, Date orarioChiusura) {
        this.menu = new HashMap<>();
        this.nome = nome;
        this.ordiniDelGiorno = 0;
        this.ordini = new ArrayList<>();
        this.indirizzo = indirizzo;
        this.orarioChiusura = orarioChiusura;
        this.orarioApertura = orarioApertura;
        this.infornate = new Forno[TEMPI_FORNO * (orarioChiusura.getHours() - orarioApertura.getHours())];
        this.fattorini= new ArrayList<>();
    }

    public void AddPizza(PizzaMenu pizza){
        menu.put(pizza.getNome(),pizza);
    }

    public void AddFattorino(DeliveryMan deliveryMan){
        fattorini.add(deliveryMan);
    }

    public void ApriPizzeria(int postidisponibili){     // ripristina il vettore di infornate ad ogni apertura della pizzeria
        for(int i=0;i<infornate.length;i++){
            infornate[i]=new Forno(postidisponibili);
        }
    }

    public void creaMenu(){
        HashMap i1 = new HashMap <String, Ingredienti>();
        i1.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i1.put(Ingredienti.ORIGANO.name(), Ingredienti.ORIGANO);
        PizzaMenu marinara = new PizzaMenu("MARINARA", i1, 3.5);
        AddPizza(marinara);

        HashMap i2 = new HashMap <String, Ingredienti>();
        i2.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i2.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        PizzaMenu margherita = new PizzaMenu("MARGHERITA", i2, 4.5);
        AddPizza(margherita);

        HashMap i3 = new HashMap <String, Ingredienti>();
        i3.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i3.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i3.put(Ingredienti.PATATINE.name(), Ingredienti.PATATINE);
        PizzaMenu patatine = new PizzaMenu("PATATINE", i3, 5.5);
        AddPizza(patatine);

        HashMap i4 = new HashMap <String, Ingredienti>();
        i4.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i4.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i4.put(Ingredienti.WURSTEL.name(), Ingredienti.WURSTEL);
        PizzaMenu wurstel = new PizzaMenu("WURSTEL", i4, 5.5);
        AddPizza(wurstel);

        HashMap i5 = new HashMap <String, Ingredienti>();
        i5.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i5.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i5.put(Ingredienti.ALICI.name(), Ingredienti.ALICI);
        PizzaMenu napoli = new PizzaMenu("NAPOLI", i5, 5.5);
        AddPizza(napoli);

        HashMap i6 = new HashMap <String, Ingredienti>();
        i6.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i6.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i6.put(Ingredienti.COTTO.name(), Ingredienti.COTTO);
        PizzaMenu cotto = new PizzaMenu("COTTO", i6, 5.5);
        AddPizza(cotto);

        HashMap i7 = new HashMap <String, Ingredienti>();
        i7.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i7.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i7.put(Ingredienti.COTTO.name(), Ingredienti.COTTO);
        i7.put(Ingredienti.FUNGHI.name(), Ingredienti.FUNGHI);
        PizzaMenu cottoFunghi = new PizzaMenu("COTTO E FUNGHI", i7, 6);
        AddPizza(cottoFunghi);

        HashMap i8 = new HashMap <String, Ingredienti>();
        i8.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i8.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i8.put(Ingredienti.SALAME_PICCANTE.name(), Ingredienti.SALAME_PICCANTE);
        PizzaMenu salamePicc = new PizzaMenu("SALAME PICCANTE", i8, 5.5);
        AddPizza(salamePicc);

        HashMap i9 = new HashMap <String, Ingredienti>();
        i9.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i9.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i9.put(Ingredienti.SALSICCIA.name(), Ingredienti.SALSICCIA);
        i9.put(Ingredienti.PEPERONI.name(), Ingredienti.PEPERONI);
        PizzaMenu americana = new PizzaMenu("AMERICANA", i9, 6.5);
        AddPizza(americana);

        HashMap i10 = new HashMap <String, Ingredienti>();
        i10.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i10.put(Ingredienti.COTTO.name(), Ingredienti.COTTO);
        i10.put(Ingredienti.CARCIOFI.name(), Ingredienti.CARCIOFI);
        i10.put(Ingredienti.FUNGHI.name(), Ingredienti.FUNGHI);
        i10.put(Ingredienti.OLIVE_NERE.name(), Ingredienti.OLIVE_NERE);
        PizzaMenu capricciosa = new PizzaMenu("CAPRICCIOSA", i10, 7);
        AddPizza(capricciosa);

        HashMap i11 = new HashMap <String, Ingredienti>();
        i11.put(Ingredienti.POMODORINI.name(), Ingredienti.POMODORINI);
        i11.put(Ingredienti.MOZZARELLA_DI_BUFALA.name(), Ingredienti.MOZZARELLA_DI_BUFALA);
        i11.put(Ingredienti.RUCOLA.name(), Ingredienti.RUCOLA);
        PizzaMenu italia = new PizzaMenu("ITALIA", i11, 7.5);
        AddPizza(italia);

        HashMap i12 = new HashMap <String, Ingredienti>();
        i12.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i12.put(Ingredienti.MOZZARELLA.name(), Ingredienti.MOZZARELLA);
        i12.put(Ingredienti.GALANTERIA.name(), Ingredienti.GALANTERIA);
        i12.put(Ingredienti.BELLE_DONNE.name(), Ingredienti.BELLE_DONNE);
        PizzaMenu allaMusi = new PizzaMenu("ALLA MUSI", i12, 100000);
        AddPizza(allaMusi);

        HashMap i13 = new HashMap <String, Ingredienti>();
        i13.put(Ingredienti.POMODORO.name(), Ingredienti.POMODORO);
        i13.put(Ingredienti.MOZZARELLA_DI_BUFALA.name(), Ingredienti.MOZZARELLA_DI_BUFALA);
        i13.put(Ingredienti.GEMME_DELL_INFINITO.name(), Ingredienti.GEMME_DELL_INFINITO);
        i13.put(Ingredienti.ONNIPOTENZA.name(), Ingredienti.ONNIPOTENZA);
        i13.put(Ingredienti.ORIGANO.name(), Ingredienti.ORIGANO);
        PizzaMenu thanos = new PizzaMenu("THANOS", i13, 0.50);
        AddPizza(thanos);
    }

    public HashMap<String, PizzaMenu> getMenu() {
        return menu;
    }

    public int getOrdiniDelGiorno() {
        return ordiniDelGiorno;
    }

    public double getPREZZO_SUPPL() {
        return PREZZO_SUPPL;
    }

    public void setOrdiniDelGiorno(int ordiniDelGiorno) {
        this.ordiniDelGiorno = ordiniDelGiorno++;
    }

    public void makeOrder() {
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
                    chiediConferma(order,orario,tot);
                }
            }
        }
    }

    public String helloThere(){         // da sistemare orario apertura-chiusura!!!
        String dati = "\nPIZZERIA \"" + this.nome + "\"\n\t" + this.indirizzo;
        String open = "\n\tApertura: "+ this.orarioApertura.getHours() + ":00 - " + this.orarioChiusura.getHours() + ":00";
        return "\n--------------------------------------------------------------------------------------\n" + dati + open;
    }

    public String stampaMenu() {
        String line= "\n--------------------------------------------------------------------------------------\n";
        String s = "    >>  MENU\n";
        for (String a : menu.keySet()) {
            s += "\n"+ menu.get(a).toString();
        }
        return line+s+line;
    }

    private int quantePizze(){
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
    }

    private Date inserisciOrario (Order order, int tot){
        Date d = null;
        boolean ok = false;
        do {
            System.out.println("A che ora vuoi ricevere la consegna? [formato HH:mm]\t\t(Inserisci 'F' per annullare e ricominciare)");
            for(String s:OrariDisponibili(tot)){
                System.out.print(s);
            }
            String sDate1 = scan.nextLine();
            try {
                if (sDate1.toUpperCase().equals("F")) {
                    ok=true;
                    throw new RestartOrderExc();
                } else {
                    d = controllaOrario(sDate1, order, tot);          // CHECK ORARIO!!!
                    if(d==null)
                        throw new NumberFormatException();
                    else {
                        int ora = d.getHours();
                        int minuti = d.getMinutes();
                        if (!controllaApertura(ora, minuti))
                            throw new OutOfTimeExc();       //DA SISTEMARE SE SI CHIUDE ALLE 02:00
                        /*if (infornate[trovaCasellaTempoForno(this.orarioApertura, ora, minuti)].getPostiDisp() < tot || fattorinoLibero(this.orarioApertura,ora,minuti,0)==null) {
                            int postiDispTot = infornate[trovaCasellaTempoForno(this.orarioApertura, ora, minuti) - 1].getPostiDisp() + infornate[trovaCasellaTempoForno(this.orarioApertura, ora, minuti)].getPostiDisp();
                            if (postiDispTot < tot || fattorinoLibero(this.orarioApertura, ora, minuti, 0) == null) {
                                OrarioNonDisponibile(order, tot, ora, minuti);
                            }else {
                                ok = true;
                            }
                        }else{*/
                        else
                            ok =true;
                        //}
                    }
                }
            } catch (RestartOrderExc e) {
                makeOrder();
            } catch (NumberFormatException | NoSuchElementException e) {
                System.out.println("L'orario non è stato inserito correttamente. Riprovare:");
            } catch (OutOfTimeExc e) {
                System.out.println("La pizzeria è chiusa nell'orario inserito. Riprovare:");
            }
        } while(!ok);
        return d;
    }

            private Date controllaOrario(String sDate1, Order order, int tot){
                Date d= null;
                try {
                    StringTokenizer st = new StringTokenizer(sDate1, ":");
                    Calendar calendar = new GregorianCalendar();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int year = calendar.get(Calendar.YEAR);
                    String token = st.nextToken();
                    if (token.length() != 2) {
                        return null;
                    }
                    int ora = Integer.parseInt(token);
                    token = st.nextToken();
                    if (token.length() != 2)
                        return null;
                    int minuti = Integer.parseInt(token);
                    if (ora > 23 || minuti > 59)
                        return null;
                    sDate1 = day + "/" + month + "/" + year + " " + sDate1;
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    d = formato.parse(sDate1);
                } catch (java.text.ParseException | NumberFormatException | NoSuchElementException e) {
                    return null;
                }
                return d;
            }

            public boolean controllaApertura(int ora, int minuti){
                return !(ora < this.orarioApertura.getHours() || ora > this.orarioChiusura.getHours() || (ora == this.orarioChiusura.getHours() && minuti > this.orarioChiusura.getMinutes()) || (ora == this.orarioApertura.getHours() && minuti < this.orarioApertura.getMinutes()));
            }

            public int trovaCasellaTempoForno(Date oraApertura, int oraDesiderata, int minutiDesiderati){
                int casellaTempo = this.TEMPI_FORNO*(oraDesiderata - oraApertura.getHours());
                casellaTempo += minutiDesiderati/5;
                return casellaTempo;
            }

            private int trovaCasellaTempoFattorino(Date oraApertura, int oraDesiderata, int minutiDesiderati){
                int casellaTempo=this.TEMPI_FATTORINI*(oraDesiderata - oraApertura.getHours());
                casellaTempo+=minutiDesiderati/10;
                return casellaTempo;
            }

            private DeliveryMan fattorinoLibero(Date oraApertura, int oraDesiderata, int minutiDesiderati,int indice){
                for(DeliveryMan a:this.fattorini){
                    if(!a.getFattoriniTempi()[trovaCasellaTempoFattorino(oraApertura,oraDesiderata,minutiDesiderati)-indice]){
                        return a;
                    }
                }
                return null;
            }

            //private void OrarioNonDisponibile(Order order, int tot, int ora, int minuti){
            //    System.out.println("Orario desiderato non disponibile, ecco gli orari disponibili:");
            //    for(int i=trovaCasellaTempoForno(this.orarioApertura,ora,minuti); i<this.infornate.length; i++) {
            public ArrayList<String> OrariDisponibili(int tot){
                ArrayList<String> disp=new ArrayList<>();
                for(int i=1; i<this.infornate.length; i++) {
                    if (infornate[i].getPostiDisp()+infornate[i-1].getPostiDisp() >= tot) {
                        for(DeliveryMan a:this.fattorini){
                            if(!a.getFattoriniTempi()[i/2]){
                                int oraNew = this.orarioApertura.getHours() + i/12;   //NON POSSO PARTIRE DA TROVACASELLA MENO 1: RISCHIO ECCEZIONE
                                int min = 5 * (i - 12*(i/12));      // divisione senza resto, quindi ha un suo senso
                                if(min<=5){
                                    disp.add(oraNew + ":0" + min + "\n");
                                } else {
                                    disp.add(oraNew + ":" + min + "\n");
                                }

                                break;
                            }
                        }
                    }
                }
                return disp;
            }

    private String qualePizza(){
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
    }

    private int quantePizzaSpecifica(Order order, String nomePizza, int disponibili) {   // FUNZIONA BENE
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
    }

    public void chiediModificaPizza(Order order, String nomePizza, int num){
        System.out.print("Vuoi apportare modifiche alle " + num + " " + nomePizza + "?\t(S/N)");
        if(scan.nextLine().toUpperCase().equals("S")) {
            System.out.print("Inserisci gli ingredienti da AGGIUNGERE, separati da virgola, poi invio:\n");
            String aggiunte = scan.nextLine();
            System.out.print("Inserisci gli ingredienti da RIMUOVERE, separati da virgola, poi invio:\n");
            String rimozioni = scan.nextLine();
                order.addPizza(menu.get(nomePizza), aggiunte, rimozioni, num, PREZZO_SUPPL);
        } else
            order.addPizza(menu.get(nomePizza), num);
    }

    public boolean inserisciDati(Order order){
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

    public void recapOrdine(Order order){
        String line = "\n---------------------------------------------\n";
        String codice = "ORDINE N. " + order.getCodice() + "\n";
        String dati = "SIG. " + order.getCustomer().getUsername() + "\tINDIRIZZO: " + order.getIndirizzo() + "\tORARIO: " + order.getOrario() + "\n";
        String prodotti = order.recap();
        double totaleCosto = order.getTotaleCosto();
        System.out.println(line + codice + dati + prodotti + "\t\t\tTOTALE: € " + totaleCosto + line);
    }

    private boolean chiediConferma(Order order, Date d, int tot){
        System.out.println("Confermi l'ordine? Premere 'S' per confermare, altro tasto per annullare.");
        if (scan.nextLine().toUpperCase().equals("S")) {
            //order.setOrario(d);     //PRIMA CONDIZIONE PER LE INFORNATE, SUCCESSIVA SUI FATTORINI
            if(infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].getPostiDisp()<tot){
                int disp=infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].getPostiDisp();
                infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].inserisciInfornate(disp);
                infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())-1].inserisciInfornate(tot-disp);
            }else{
                infornate[trovaCasellaTempoForno(this.orarioApertura, d.getHours(), d.getMinutes())].inserisciInfornate(tot);
            }
            fattorinoLibero(this.orarioApertura,d.getHours(),d.getMinutes(),0).setFattoriniTempi(trovaCasellaTempoFattorino(this.orarioApertura, d.getHours(), d.getMinutes()));
            order.setCompleto();
            ordini.add(order);
            return true;
        } else {
            System.out.println("L'ordine è stato annullato.");
            return false;
        }
    }

    public Date getOrarioChiusura() {
        return orarioChiusura;
    }

    public Date getOrarioApertura() {
        return orarioApertura;
    }

    /*public void success(Order order, int num, String nomePizza, String modifiche) {
        for (int i=0; i<num; i++) {
            if(modifiche.equals(""))
                order.addPizza(menu.get(nomePizza));
            else
                order.addPizza(menu.get(nomePizza), modifiche);
        }
    }*/

}






///////////////////////  VERSIONE DI PRIMA  ///////////////////////


/* private int quantePizzaSpecifica(Order order, String nomePizza, int disponibili) {   // FUNZIONA BENE
        boolean ok=false;
        int num=0;
        do{
            System.out.println("Quante " + nomePizza + " vuoi?\t[0..n]");
            String line = scan.nextLine();
            try {
                num = Integer.parseInt(line);
                if(num<0){
                    throw new NumberFormatException();
                }
                else if(num>disponibili){
                    throw new RiprovaExc();
                } else
                    ok=true;
                for (int i=0; i<num; i++) {
                    order.addPizza(menu.get(nomePizza));
                }
            } catch (NumberFormatException e) {
                System.out.println("Spiacenti: inserito numero non valido. Riprovare:");
            } catch (RiprovaExc e) {
                System.out.println("Massimo numero di pizze ordinate superato. Puoi ordinare ancora " + disponibili + " pizze:");
            }
        } while(!ok);
        return num;
    }
*/