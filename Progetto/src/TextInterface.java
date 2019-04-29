import exceptions.OutOfTimeExc;
import exceptions.RestartOrderExc;
import exceptions.RiprovaExc;

import java.text.SimpleDateFormat;
import java.util.*;

public class TextInterface {

    Pizzeria wolf = new Pizzeria("Wolf Of PizzaMenu","Via Bolzano 10, Pavia", new Date(2019,0,1,19,0),new Date(2019,0,31,23,0,0));
    private Scanner scan = new Scanner(System.in);


    public void makeOrderText(){
        int num;
        int ordinate = 0;
        boolean ok;
        String nomePizza;
        Order order = new Order(wolf.getOrdiniDelGiorno());

        wolf.setOrdiniDelGiorno(wolf.getOrdiniDelGiorno());
        System.out.println(wolf.helloThere());
        System.out.println(wolf.stampaMenu());

        int tot = 0;
            do {
                nomePizza = qualePizza();
                if(nomePizza.equals("F")||nomePizza.equals("AVANTI")) {
                    nomePizza="";
                    break;
                }
                num = quantePizzaSpecifica(order,nomePizza);
                //System.out.println("ordinate " + num + " " + nomePizza + " (" + wolf.getMenu().get(nomePizza).getDescrizione() + ")");
                tot += num;
            } while(true);

        Date orario = inserisciOrario(order,tot);
        if(orario!=null) {
            order.setOrario(orario);
            System.out.println(orario);
        }

            if(!nomePizza.equals("F")) {
                ok = inserisciDati(order);
                if (ok) {
                    wolf.recapOrdine(order);
                    //System.out.println(order.getPizzeordinate().get(0).equals(order.getPizzeordinate().get(1)));
                    //placeOrder(order,orario,tot);
                }
            }
        //}

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
            System.out.println("A che ora vuoi ricevere la consegna? [formato HH:mm] \t\t(Inserisci 'F' per annullare e ricominciare) \nEcco gli orari disponibili:");
            for(String s:wolf.OrariDisponibili(tot)){
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
                        if (!wolf.controllaApertura(ora, minuti))
                            throw new OutOfTimeExc();       //DA SISTEMARE SE SI CHIUDE ALLE 02:00
                        if (wolf.getInfornate()[wolf.trovaCasellaTempoForno(wolf.getOrarioApertura(),ora,minuti)].getPostiDisp()+wolf.getInfornate()[wolf.trovaCasellaTempoForno(wolf.getOrarioApertura(),ora,minuti)-1].getPostiDisp() < tot) {
                            throw new RiprovaExc();
                        }
                        if(wolf.fattorinoLibero(wolf.getOrarioApertura(),ora,minuti,0)==null){
                            throw new RiprovaExc();
                        }
                        else
                            ok =true;
                    }
                }
            } catch (RestartOrderExc e) {
                makeOrderText();
            } catch (NumberFormatException | NoSuchElementException e) {
                System.out.println("L'orario non è stato inserito correttamente. Riprovare:");
            } catch (OutOfTimeExc e) {
                System.out.println("La pizzeria è chiusa nell'orario inserito. Riprovare:");
            } catch (RiprovaExc e) {
                System.out.println("L'orario scelto non è disponibile. Riprovare:");
            }
        } while(!ok);
        return d;
    }

    public Date controllaOrario(String sDate1, Order order, int tot){
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

    private String qualePizza(){
        String nomePizza;
        boolean ok=false;
        do {
            System.out.println("Quale pizza desideri?\t\t(Inserisci 'Avanti' per proseguire o 'F' per annullare e ricominciare");
            nomePizza = scan.nextLine().toUpperCase();
            try {
                if (nomePizza.equals("F")) {
                    ok=true;
                    throw new RestartOrderExc();
                }
                else if (nomePizza.equals("AVANTI"))
                    ok = true;
                else if (!(wolf.getMenu().containsKey(nomePizza)))         // qui ci vorrebbe una eccezione invece della if-else
                    throw new RiprovaExc();
                else
                    ok = true;
            } catch (RestartOrderExc e){
                makeOrderText();
            } catch (RiprovaExc e){
                System.out.println("Spiacenti: \"" + nomePizza + "\" non presente sul menu. Riprovare:");
            }
        } while(!ok);
        return nomePizza;
    }

    private int quantePizzaSpecifica(Order order, String nomePizza) {   // FUNZIONA BENE
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
                //else if(num>disponibili)
                //    throw new RiprovaExc();
                else {
                    ok = true;
                    chiediModificaPizza(order,nomePizza,num);
                }
            } catch (NumberFormatException e) {
                System.out.println("Spiacenti: inserito numero non valido. Riprovare:");
            } /*catch (RiprovaExc e) {
                System.out.println("Massimo numero di pizze ordinate superato. Puoi ordinare ancora " + disponibili + " pizze:");
            }*/
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
            order.addPizza(wolf.getMenu().get(nomePizza), aggiunte, rimozioni, num, wolf.getPREZZO_SUPPL());
        } else
            order.addPizza(wolf.getMenu().get(nomePizza), num);
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
            makeOrderText();
        }
        return ok;
    }
}

class Tester {
    public static void main(String[] args) {
        TextInterface textInterface = new TextInterface();
        textInterface.wolf.ApriPizzeria(8);
        textInterface.wolf.AddFattorino(new DeliveryMan("Musi",textInterface.wolf));
        textInterface.wolf.creaMenu();
        textInterface.makeOrderText();



    }
}
