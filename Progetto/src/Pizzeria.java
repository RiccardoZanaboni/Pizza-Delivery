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

    public void makeOrder() {
        Order order = new Order(this.ordiniDelGiorno);
        System.out.println(stampaMenu());
        scegliPizze(order);
        inserisciDati(order);
        inserisciOrario(order);
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
        int num=0;
        String s="";
        Scanner scan = new Scanner(System.in);
        System.out.println("Quante pizze vuoi ordinare?");
        int tot = scan.nextInt();
        if(tot==0){
            System.out.println("Spiacenti: Numero di pizze errato. Ordine fallito. Riprovare:");
            scegliPizze(order);
        }
        while(tot>0){
            System.out.println("Quale pizza desideri?");
            String nome = scan.next().toUpperCase();
            if(!(menu.containsKey(nome)))           // qui ci vorrebbe una eccezione invece della if-else
                System.out.println("Spiacenti: \"" + nome + "\" non presente sul menu. Riprovare:");
            else {
                do {
                    System.out.println(s); // al primo ciclo non stampa niente
                    System.out.println("Quante " + nome + " vuoi?");
                    num = scan.nextInt();
                    s="Numero di pizze ordinate massimo superato. RIPROVA";
                    }while (num>tot);
                    tot -= num;
                    for(int i=0; i<num; i++) {
                        order.AddPizza(menu.get(nome));
                    }
            }
        }
    }

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

    public void inserisciOrario(Order order){
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
            if(ora >23 || minuti >59 && ora<this.orarioApertura.getHours() && ora>this.orarioChiusura.getHours()){
                System.out.println("L'orario inserito non è valido. Riprovare:"); //DA SISTEMARE LA CONDIZIONE SULL'ORARIO DI APERTURA
                inserisciOrario(order);
            }
            /*if(infornate[trovaCasellaTempoForno(this.orarioApertura,ora,minuti)].getPostiDisponibili()>order.getNumeroPizze()){
                order.setOrario(d);//PRIMA CONDIZIONE PER LE INFORNATE ,SUCCESSIVA SUI FATTORINI
            }else{
                ; //LA SCELTA VIRA SULL'ORARIO PIU VICINO
            }*/

        } catch (Exception e){
            System.out.println("L'orario non è stato inserito correttamente. Riprovare:");
            inserisciOrario(order);
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