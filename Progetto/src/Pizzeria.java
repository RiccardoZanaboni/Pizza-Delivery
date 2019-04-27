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
    private HashMap<String, Pizza> menu;
    private ArrayList<Order> ordini;
    private int ordiniDelGiorno;
    private final int TEMPI_FORNO = 12;      // ogni 5 minuti
    private final int TEMPI_FATTORINI = 6;   // ogni 10 minuti
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

    public void AddPizza(Pizza pizza){
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
        Pizza marinara = new Pizza("MARINARA", "pomodoro, origano", 3.5);
        AddPizza(marinara);
        Pizza margherita = new Pizza("MARGHERITA", "pomodoro, mozzarella", 4.5);
        AddPizza(margherita);
        Pizza patatine = new Pizza("PATATINE", "pomodoro, mozzarella, patatine", 6);
        AddPizza(patatine);
        Pizza wurstel = new Pizza("WURSTEL", "pomodoro, mozzarella, wurstel", 6);
        AddPizza(wurstel);
        Pizza napoli = new Pizza("NAPOLI", "pomodoro, mozzarella, alici", 6.5);
        AddPizza(napoli);
        Pizza cotto = new Pizza("COTTO", "pomodoro, mozzarella, prosciutto cotto", 6);
        AddPizza(cotto);
        Pizza cottoFunghi = new Pizza("COTTO E FUNGHI", "pomodoro, mozzarella, prosciutto cotto, funghi", 7);

        AddPizza(cottoFunghi);
        Pizza salamePicc = new Pizza("SALAME PICCANTE", "pomodoro, mozzarella, salame piccante", 5.5);
        AddPizza(salamePicc);
        Pizza americana = new Pizza("AMERICANA", "pomodoro, mozzarella, salsiccia e peperoni", 7);
        AddPizza(americana);
        Pizza capricciosa = new Pizza("CAPRICCIOSA", "pomodoro, prosciutto cotto, carciofi, funghi, olive", 7);
        AddPizza(capricciosa);
        Pizza italia = new Pizza("ITALIA", "pomodorini, bufala, rucola", 7.5);
        AddPizza(italia);
        Pizza allaMusi = new Pizza("ALLA MUSI", "pomodoro, mozzarella, galanteria e belle donne", 100000);
        AddPizza(allaMusi);
        Pizza thanos = new Pizza("THANOS", "pomodoro, mozzarella di bufala, 6 gemme, onnipotenza, origano", 0.50);
        AddPizza(thanos);
        Pizza marvel = new Pizza("RIP TONY STARK", "semplicemente: grazie di aver salvato l'universo.", 1000);
        AddPizza(marvel);
    }

    public HashMap<String, Pizza> getMenu() {
        return menu;
    }

    public int getOrdiniDelGiorno() {
        return ordiniDelGiorno;
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
        String s= "    >>  MENU\n";
        for (String a:menu.keySet()) {
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
                        if (infornate[trovaCasellaTempoForno(this.orarioApertura, ora, minuti)].getPostiDisp() < tot || fattorinoLibero(this.orarioApertura,ora,minuti,0)==null) {
                            int postiDispTot = infornate[trovaCasellaTempoForno(this.orarioApertura, ora, minuti) - 1].getPostiDisp() + infornate[trovaCasellaTempoForno(this.orarioApertura, ora, minuti)].getPostiDisp();
                            if (postiDispTot < tot || fattorinoLibero(this.orarioApertura, ora, minuti, 0) == null) {
                                OrarioNonDisponibile(order, tot, ora, minuti);
                            }else {
                                ok = true;
                            }
                        }
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

            private boolean controllaApertura(int ora, int minuti){
                return !(ora < this.orarioApertura.getHours() || ora > this.orarioChiusura.getHours() || (ora == this.orarioChiusura.getHours() && minuti > this.orarioChiusura.getMinutes()) || (ora == this.orarioApertura.getHours() && minuti < this.orarioApertura.getMinutes()));
            }

            private int trovaCasellaTempoForno(Date oraApertura, int oraDesiderata, int minutiDesiderati){
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

            private void OrarioNonDisponibile(Order order, int tot, int ora, int minuti){
                System.out.println("Orario desiderato non disponibile, ecco gli orari disponibili:");
                for(int i=trovaCasellaTempoForno(this.orarioApertura,ora,minuti); i<this.infornate.length; i++) {
                    if (infornate[i].getPostiDisp()+infornate[i-1].getPostiDisp() >= tot) {
                        for(DeliveryMan a:this.fattorini){
                            if(!a.getFattoriniTempi()[i/2]){
                                int oraNew = this.orarioApertura.getHours() + i/12;   //NON POSSO PARTIRE DA TROVACASELLA MENO 1: RISCHIO ECCEZIONE
                                int min = 5 * (i - 12*(i/12));      // divisione senza resto, quindi ha un suo senso
                                if(min<=5){
                                    System.out.print(oraNew + ":0" + min + "\n");
                                } else {
                                    System.out.print(oraNew + ":" + min + "\n");
                                }
                                break;
                            }
                        }
                    }
                }
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
        String modifiche="";
        do {
            System.out.println("Quante " + nomePizza + " vuoi?\t[0..n]");
            String line = scan.nextLine();
            try {
                num = Integer.parseInt(line);
                if(num<0)
                    throw new NumberFormatException();
                else if(num>disponibili)
                    throw new RiprovaExc();
                else
                    ok = true;
                System.out.print("Vuoi apportare modifiche alle " + num + " " + nomePizza + "?\t(S/N)");
                if(scan.nextLine().toUpperCase().equals("S")) {
                    System.out.print("Inserisci le modifiche:\t\t('+ ...' per le aggiunte, '- ...' per le rimozioni, digita invio e 'ok' per terminare)\n");
                    do {
                        modifiche += scan.nextLine();
                    } while (!(scan.nextLine().toUpperCase().equals("OK")));
                }
                for (int i=0; i<num; i++) {
                    if(modifiche.equals(""))
                        order.addPizza(menu.get(nomePizza));
                    else
                        order.addPizza(menu.get(nomePizza), modifiche);
                }
            } catch (NumberFormatException e) {
                System.out.println("Spiacenti: inserito numero non valido. Riprovare:");
            } catch (RiprovaExc e) {
                System.out.println("Massimo numero di pizze ordinate superato. Puoi ordinare ancora " + disponibili + " pizze:");
            }
        } while(!ok);
        return num;
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
        String prodotti = "";
        double totale = 0;
            for (int i = 0; i < order.getPizzeordinate().size(); i++) {
                Pizza p=order.getPizzeordinate().get(i);
                prodotti += "\t" + "1" + "\t" + p.getNome() + "\t\t" +p.getModifiche()+ "\t\t"+ p.getPrezzo() + "€\n";
                totale +=p.getPrezzo();
            }
        System.out.println(line + codice + dati + prodotti + "\t\t\tTOTALE: € " + totale + line);
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

    public void success(Order order, int num, String nomePizza, String modifiche) {
        for (int i=0; i<num; i++) {
            if(modifiche.equals(""))
                order.addPizza(menu.get(nomePizza));
            else
                order.addPizza(menu.get(nomePizza), modifiche);
        }
    }

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