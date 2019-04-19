import java.text.SimpleDateFormat;
import java.util.*;

public class Pizzeria {
    private String nome;
    private String indirizzo;
    private Date orarioChiusura;
    private Date orarioApertura;
    private Forno infornate[];
    private ArrayList<DeliveryMan> fattorini;
    private HashMap<String, Pizza> menu;
    private ArrayList<Order> ordini;
    private int ordiniDelGiorno;
    public final int TEMPI_FORNO = 12;

    public Pizzeria(String nome, String indirizzo, Date orarioApertura, Date orarioChiusura) {
        this.menu = new HashMap<>();
        this.nome = nome;
        this.ordiniDelGiorno = 0;
        this.indirizzo = indirizzo;
        this.orarioChiusura = orarioChiusura;
        this.orarioApertura = orarioApertura;
        this.infornate = new Forno[TEMPI_FORNO * (orarioChiusura.getHours() - orarioApertura.getHours())];
        this.fattorini= new ArrayList<>();
    }

    public void AddPizza(Pizza pizza){
        menu.put(pizza.getNome(),pizza);
    }

    public void ApriPizzeria(int postidisponibili){//INIZIALIZZA IL VETTORE DI INFORNATE CHE OGNI APERTURA VA RIPRISTINATO
        for(int i=0;i<infornate.length;i++){
            infornate[i]=new Forno(postidisponibili);
        }
    }

    public void makeOrder() {
        Order order = new Order(this.ordiniDelGiorno);
        System.out.println(stampaMenu());
        scegliPizze(order);
        inserisciDati(order);
        order.setCompleto();
        this.ordiniDelGiorno++;
    }

    public String stampaMenu () {
        String s= "    >>  MENU di "+ this.nome;
        for (String a:menu.keySet()) {
            s+="\n"+ menu.get(a).toString();
        }
        return s;
    }


    public void scegliPizze(Order order) {
        int tot=0;
        String line;   // necessaria per usare nextLine() ovunque (per evitare problemi con letture errate di newlines)
        Scanner scan = new Scanner(System.in);
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
        this.inserisciOrario(order,tot);
        pizzaRichiesta(order, tot);
    }

    public void pizzaRichiesta (Order order, int tot) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Quale pizza desideri?" + "      Inserisci 'F' per tornare indietro.");
            String nomePizza = scan.nextLine().toUpperCase();
            if (nomePizza.equals("F"))                      // nel caso si voglia tornare indietro
                scegliPizze(order);
            if (!(menu.containsKey(nomePizza)))           // qui ci vorrebbe una eccezione invece della if-else
                System.out.println("Spiacenti: \"" + nomePizza + "\" non presente sul menu. Riprovare:");
            else {
                numeroPizzaRichiesta(order, nomePizza, tot);
            }
    }


    public void numeroPizzaRichiesta(Order order, String nome, int tot) {
        Scanner scan = new Scanner(System.in);
        int num=0;
        String s=null;
        try{
            do {
                if(s!=null) { System.out.println(s); }
                System.out.println("Quante " + nome + " vuoi?");
                String line = scan.nextLine();
                num = Integer.parseInt(line);
                if(num<=0)
                    throw new NumberFormatException();
                s = "Numero di pizze ordinate massimo superato. Riprova:";
            } while (num>tot);
        } catch (NumberFormatException e) {
            System.out.println("Spiacenti: inserito numero non valido. Riprovare:");
            num=0;
        }
        s = null;
        tot -= num;
        for(int i=0; i<num; i++) {
            order.AddPizza(menu.get(nome));
        }
        if (tot!=0) {
            pizzaRichiesta(order, tot);
        }
    }




 /*   public void scegliPizze(Order order) {
        int num=0;
        int tot=0;
        String line;   // necessaria per usare nextLine() ovunque (per evitare problemi con letture errate di newlines)
        String s = null;
        Scanner scan = new Scanner(System.in);
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
        this.inserisciOrario(order,tot);
        while(tot>0){
            System.out.println("Quale pizza desideri?");
            String nome = scan.nextLine().toUpperCase();
            if(!(menu.containsKey(nome)))           // qui ci vorrebbe una eccezione invece della if-else
                System.out.println("Spiacenti: \"" + nome + "\" non presente sul menu. Riprovare:");
            else {
                try{
                    do {
                        if(s!=null) { System.out.println(s); }
                        System.out.println("Quante " + nome + " vuoi?");
                        line = scan.nextLine();
                        num = Integer.parseInt(line);
                        if(num<=0)
                            throw new NumberFormatException();
                        s = "Numero di pizze ordinate massimo superato. Riprova:";
                    } while (num>tot);
                } catch (NumberFormatException e) {
                    System.out.println("Spiacenti: inserito numero non valido. Riprovare:");
                    num=0;
                }
                s = null;
                tot -= num;
                for(int i=0; i<num; i++) {
                    order.AddPizza(menu.get(nome));
                }
            }
        }
    }
*/
    public void inserisciDati(Order order){
        Scanner scan = new Scanner(System.in);
        System.out.println("Come ti chiami?");
        String nome = scan.nextLine();
        Customer c = new Customer(nome);
        order.setCustomer(c);
        System.out.println("Inserisci l'indirizzo di consegna:");
        String indirizzo = scan.nextLine();
        order.setIndirizzo(indirizzo);
    }

    public void inserisciOrario(Order order,int tot){
        Scanner scan = new Scanner(System.in);
        System.out.println("A che ora vuoi ricevere la consegna? [formato HH:mm]");
        Calendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        try {
            String sDate1 = scan.next();
            StringTokenizer st = new StringTokenizer(sDate1, ":");
            int ora=Integer.parseInt(st.nextToken());
            int minuti=Integer.parseInt(st.nextToken());
            sDate1 = day + "/" + month + "/" + year + " " + sDate1  ;
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date d = formato.parse(sDate1);
            if(ora >23 || minuti >59 || ora<this.orarioApertura.getHours() || ora>this.orarioChiusura.getHours()){
                System.out.println("L'orario inserito non è valido. Riprovare:"); //DA SISTEMARE SE SI CHIUDE ALLE 02:00
                inserisciOrario(order,tot);
            }
            if(infornate[trovaCasellaTempoForno(this.orarioApertura,ora,minuti)].getPostiDisp()>=tot){
                order.setOrario(d);     //PRIMA CONDIZIONE PER LE INFORNATE ,SUCCESSIVA SUI FATTORINI
                infornate[trovaCasellaTempoForno(this.orarioApertura,ora,minuti)].inserisciInfornate(tot);
            }else{
                System.out.println("Orario desiderato non disponibile,ecco gli orari disponibili: ");
                for(int i=trovaCasellaTempoForno(this.orarioApertura,ora,minuti);i<this.infornate.length ;i++) {
                    if (infornate[i].getPostiDisp() >= tot) {
                        int oraNew = this.orarioApertura.getHours() + i / 12;   //NON POSSO PARTIRE DA TROVACASELLA MENO 1 RISCHIO ECCEZZIONE
                        int min = 5 * (i - 12 * (i / 12));
                        if(min<=5){
                            System.out.println(oraNew + ":0" + min);
                        }else {
                            System.out.println(oraNew + ":" + min);
                        }
                    }
                }
                this.inserisciOrario(order,tot);
            }

        } catch (java.text.ParseException | NumberFormatException e){
            System.out.println("L'orario non è stato inserito correttamente. Riprovare:");
            inserisciOrario(order,tot);
        }
    }

    public int trovaCasellaTempoForno(Date oraApertura,int oraDesiderata,int minutiDesiderati){
        int casellaTempo=this.TEMPI_FORNO*(oraDesiderata - oraApertura.getHours());
        casellaTempo+=minutiDesiderati/5;
        return casellaTempo;

    }

    public Date getOrarioChiusura() {
        return orarioChiusura;
    }

    public Date getOrarioApertura() {
        return orarioApertura;
    }

}