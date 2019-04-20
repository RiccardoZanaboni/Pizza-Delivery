import exceptions.OutOfTimeExc;
import exceptions.RestartOrderExc;

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

    public void ApriPizzeria(int postidisponibili){     // ripristina il vettore di infornate ad ogni apertura della pizzeria
        for(int i=0;i<infornate.length;i++){
            infornate[i]=new Forno(postidisponibili);
        }
    }

    public void makeOrder() {
        Order order = new Order(this.ordiniDelGiorno);
        this.ordiniDelGiorno++;
        System.out.println(helloThere());
        System.out.println(stampaMenu());
        scegliPizze(order);     // si potrebbe fare un metodo scegliPizze() che racchiude quanteP, richiestaP, richiestaNumeroP.
        //inserisciDati(order);
        //order.setCompleto();
        //ordini.add(order);
        //scan.close();
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

    public void scegliPizze(Order order) {
        int tot=0;
        String line;   // necessaria per usare nextLine() ovunque (per evitare problemi con letture errate di newlines)
        //Scanner scan = new Scanner(System.in);
        System.out.println("Quante pizze vuoi ordinare?");
        line = scan.nextLine();
        try {
            tot = Integer.parseInt(line);
            if(tot<=0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Spiacenti: inserito numero non valido. Riprovare:");
            scegliPizze(order);
        }
        this.inserisciOrario(order, tot);
    }

    public void pizzaRichiesta (Order order, int tot) {
        //Scanner scan = new Scanner(System.in);
        System.out.println("Quale pizza desideri?\t\t(Inserisci 'F' per annullare e ricominciare)");
        try {
            String nomePizza = scan.nextLine().toUpperCase();
            if (nomePizza.equals("F")) {
                throw new RestartOrderExc();
            } else if (!(menu.containsKey(nomePizza))) {         // qui ci vorrebbe una eccezione invece della if-else
                System.out.println("Spiacenti: \"" + nomePizza + "\" non presente sul menu. Riprovare:");
                pizzaRichiesta(order, tot);
            } else
                numeroPizzaRichiesta(order, nomePizza, tot);
        } catch (RestartOrderExc e){
            makeOrder();
        }
    }

    public void numeroPizzaRichiesta(Order order, String pizza, int tot) {
        //Scanner scan = new Scanner(System.in);
        int num=0;
        String err = null;
        try {
            do {
                if(err!=null) { System.out.println(err); }
                System.out.println("Quante " + pizza + " vuoi?\t[0..n]");
                String line = scan.nextLine();
                num = Integer.parseInt(line);
                if(num<0) {       // c'è la possibilità di mettere 0, se uno non voleva quella pizza, senza creare casini
                    throw new NumberFormatException();
                }
                err = "Massimo numero di pizze ordinate superato. Riprovare:";
            } while (num>tot);
            tot -= num;
            for (int i = 0; i < num; i++) {
                order.AddPizza(menu.get(pizza));
            }
            if (tot != 0) {
                pizzaRichiesta(order, tot);
            }
        } catch (NumberFormatException e) {
            num = 0;
            System.out.println("Spiacenti: inserito numero non valido. Riprovare:");
        }
    }

    public void inserisciDati(Order order){
        //Scanner scan = new Scanner(System.in);
        System.out.println("Come ti chiami?\t\t(Inserisci 'F' per annullare e ricominciare)");
        try {
            String nome = scan.nextLine();
            if (nome.toUpperCase().equals("F")) {
                throw new RestartOrderExc();
            }
            Customer c = new Customer(nome);
            order.setCustomer(c);
            System.out.println("Inserisci l'indirizzo di consegna:\t\t(Inserisci 'F' per annullare e ricominciare)");
            String indirizzo = scan.nextLine();
            if (indirizzo.toUpperCase().equals("F")) {
                throw new RestartOrderExc();
            }
            order.setIndirizzo(indirizzo);
        } catch (RestartOrderExc e){
            makeOrder();
        }
    }

    public void inserisciOrario(Order order,int tot){
        //Scanner scan = new Scanner(System.in);
        System.out.println("A che ora vuoi ricevere la consegna? [formato HH:mm]\t\t(Inserisci 'F' per annullare e ricominciare)");
        String token= null;
        Calendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        try {
            String sDate1 = scan.nextLine();
            if(sDate1.toUpperCase().equals("F")){
                throw new RestartOrderExc();
            }
            StringTokenizer st = new StringTokenizer(sDate1, ":");
            token = st.nextToken();
            if(token.length()!=2)
                throw new NumberFormatException();
            int ora = Integer.parseInt(token);
            token = st.nextToken();
            if(token.length()!=2)
                throw new NumberFormatException();
            int minuti = Integer.parseInt(token);
            sDate1 = day + "/" + month + "/" + year + " " + sDate1  ;
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date d = formato.parse(sDate1);
            if(ora >23 || minuti >59)
                throw new NumberFormatException();
            if(ora<this.orarioApertura.getHours() || ora>this.orarioChiusura.getHours() || (ora==this.orarioChiusura.getHours() && minuti>this.orarioChiusura.getMinutes()) || (ora==this.orarioApertura.getHours() && minuti<this.orarioApertura.getMinutes())){
                throw new OutOfTimeExc(); //DA SISTEMARE SE SI CHIUDE ALLE 02:00
            }
            if(infornate[trovaCasellaTempoForno(this.orarioApertura,ora,minuti)].getPostiDisp()>=tot){
                pizzaRichiesta(order, tot);
                inserisciDati(order);
                System.out.println("Confermi l'ordine? Premere 'S' per confermare, altro tasto per annullare.");
                if (scan.nextLine().toUpperCase().equals("S")) {
                    order.setOrario(d);     //PRIMA CONDIZIONE PER LE INFORNATE, SUCCESSIVA SUI FATTORINI
                    infornate[trovaCasellaTempoForno(this.orarioApertura, ora, minuti)].inserisciInfornate(tot);
                    order.setCompleto();
                } else
                    System.out.println("L'ordine è stato annullato.");
            } else{
                System.out.println("Orario desiderato non disponibile, ecco gli orari disponibili: ");
                for(int i=trovaCasellaTempoForno(this.orarioApertura,ora,minuti); i<this.infornate.length; i++) {
                    if (infornate[i].getPostiDisp() >= tot) {
                        int oraNew = this.orarioApertura.getHours() + i/12;   //NON POSSO PARTIRE DA TROVACASELLA MENO 1: RISCHIO ECCEZIONE
                        int min = 5 * (i - 12*(i/12));      // divisione senza resto, quindi ha un suo senso
                        if(min<=5){
                            System.out.print(oraNew + ":0" + min + "\n");
                        } else {
                            System.out.print(oraNew + ":" + min + "\n");
                        }
                    }
                }
                this.inserisciOrario(order,tot);
            }

        } catch (RestartOrderExc e){
            makeOrder();
        } catch (java.text.ParseException | NumberFormatException | NoSuchElementException e) {
            System.out.println("L'orario non è stato inserito correttamente. Riprovare:");
            inserisciOrario(order, tot);
        } catch (OutOfTimeExc e){
            System.out.println("La pizzeria è chiusa nell'orario inserito. Riprovare:");
            inserisciOrario(order,tot);
        }
    }

    public int trovaCasellaTempoForno(Date oraApertura, int oraDesiderata, int minutiDesiderati){
        int casellaTempo = this.TEMPI_FORNO*(oraDesiderata - oraApertura.getHours());
        casellaTempo += minutiDesiderati/5;
        return casellaTempo;

    }

    public Date getOrarioChiusura() {
        return orarioChiusura;
    }

    public Date getOrarioApertura() {
        return orarioApertura;
    }
}