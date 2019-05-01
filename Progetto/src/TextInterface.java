import exceptions.OutOfTimeExc;
import exceptions.RestartOrderExc;
import exceptions.RiprovaExc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 *
 *
 * * @authors: Javengers, 2019
 *
 *
 *  @author  Fecchio Andrea
 *  @author  Gobbo Matteo
 *  @author  Musitano Francesco
 *  @author  Rossanigo Fabio
 *  @author  Zanaboni Riccardo
 *
 *
 *
 *
 */

public class TextInterface {

    Pizzeria wolf = new Pizzeria("Wolf Of Pizza","Via Bolzano 10, Pavia", new Date(2019,0,1,19,0),new Date(2019,0,31,23,0,0));
    private Scanner scan = new Scanner(System.in);

    public void makeOrderText(){
        Order order = wolf.inizializeNewOrder();
        int num;
        String nomePizza;
        int tot = 0;
        try {
            do {
                nomePizza = qualePizza(tot);
                if (nomePizza.equals("AVANTI")) {
                    break;
                }
                num = quantePizzaSpecifica(order, nomePizza);
                if(order.getNumeroPizze()==16){
                    System.out.println("Spiacenti: massimo numero di pizze ordinate raggiunto. Per aggiungere altre pizze effettuare un secondo ordine.");
                    break;
                }
                //System.out.println("ordinate " + num + " " + nomePizza + " (" + wolf.getMenu().get(nomePizza).getDescrizione() + ")");
                tot += num;
            } while (true);

            Date orario = inserisciOrario(order, tot);
            if (orario != null) {
                order.setOrario(orario);
                System.out.println(orario);
                if (inserisciDati(order)) {
                    wolf.recapOrdine(order);
                    chiediConfermaText(order, orario, tot);
                    //System.out.println(order.getPizzeordinate().get(0).equals(order.getPizzeordinate().get(1)));
                    //placeOrder(order,orario,tot);    NON SO COSA SIANO QUESTE DUE RIGHE VE LE LASCIO
                }
            }
        }catch (RestartOrderExc e){
            makeOrderText();
        }
    }

    private void superOrarioMegaGalattico (Order order, int tot) {
        String orarioScelto = insertTime(order, tot);
        if (checkValidTime(orarioScelto)) {
            Date d = stringToDate(orarioScelto);
        }

    }

    private String insertTime(Order order, int tot) {
        System.out.println("A che ora vuoi ricevere la consegna? [formato HH:mm] \t\t(Inserisci 'F' per annullare e ricominciare) \nEcco gli orari disponibili:");
        int c=0;
        for (String s : wolf.OrariDisponibili(tot)) {
            System.out.print(s);
            c++;
            if(c%20 == 0)
                System.out.print("\n");
        }
        System.out.println("\n");
        String sDate1 = scan.nextLine();
        return sDate1;
    }

    private Date stringToDate(String sDate1){
        Date d;
        Calendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        sDate1 = day + "/" + month + "/" + year + " " + sDate1;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            d = formato.parse(sDate1);
        } catch (ParseException e) {
            return null;
        }
        return d;
    }

    private boolean checkValidTime(String sDate1) {
        boolean b = false;
        try {
            StringTokenizer st = new StringTokenizer(sDate1, ":");
            String token = st.nextToken();
            if (token.length() != 2) {
                return b;
            }
            int ora = Integer.parseInt(token);
            token = st.nextToken();
            if (token.length() != 2)
                return b;
            int minuti = Integer.parseInt(token);
            if (ora > 23 || minuti > 59)
                return b;
        } catch (NumberFormatException | NoSuchElementException e) {
            return b;
        }
        b=true;
        return b;
    }

    private Date inserisciOrario (Order order, int tot){
        Date d = null;
        boolean ok = false;

        String sDate1 = insertTime(order, tot);
            do {
                try {
                if (sDate1.toUpperCase().equals("F")) {
                    ok=true;
                    throw new RestartOrderExc();
                } else {
                    d = checkValiditaOrario(sDate1, order, tot);
                    if(d==null)
                        throw new NumberFormatException();
                    else {
                        int ora = d.getHours();
                        int minuti = d.getMinutes();
                        if (!wolf.controllaApertura(d))
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

    public Date checkValiditaOrario(String sDate1, Order order, int tot){
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

    private String qualePizza(int tot) throws RestartOrderExc{
        String nomePizza;
        boolean ok=false;
        do {
            System.out.println("Quale pizza desideri?\t\t(Inserisci 'Avanti' per proseguire o 'F' per annullare e ricominciare)");
            nomePizza = scan.nextLine().toUpperCase();
            try {
                if (nomePizza.equals("F")) {
                    ok=true;
                    throw new RestartOrderExc();
                }
                if(nomePizza.equals("AVANTI") && tot<=0 )
                    System.out.println("Numero di pizze non valido. Riprovare:");
                else if (nomePizza.equals("AVANTI"))      // inutile, c'è else alla fine
                    ok = true;
                else if (!(wolf.getMenu().containsKey(nomePizza)))
                    throw new RiprovaExc();
                else
                    //return nomePizza;
                    ok = true;
            } catch (RiprovaExc e){
                System.out.println("Spiacenti: \"" + nomePizza + "\" non presente sul menu. Riprovare:");
            }
        } while(!ok);
        return nomePizza;
    }

    private int quantePizzaSpecifica(Order order, String nomePizza) {
        boolean ok = false;
        int num = 0;
        do {
            System.out.println("Quante " + nomePizza + " vuoi?\t[0..n]");
            String line = scan.nextLine();
            try {
                num = Integer.parseInt(line);
                if(num<=0)
                    throw new NumberFormatException();
                else if(order.getNumeroPizze()+num >16)
                    throw new RiprovaExc();
                else {
                    ok = true;
                    chiediModificaPizza(order,nomePizza,num);

                }
            } catch (NumberFormatException e) {
                System.out.println("Spiacenti: inserito numero non valido. Riprovare:");
            } catch (RiprovaExc e) {
                System.out.println("Massimo numero di pizze ordinate superato. Puoi ordinare massimo 16 pizze:");
            }
        } while(!ok);
        return num;
    }

    public void chiediModificaPizza(Order order, String nomePizza, int num){
        System.out.println("Vuoi apportare modifiche alle " + num + " " + nomePizza + "?\t(S/N):");
        String answer = scan.nextLine().toUpperCase();
        if(answer.equals("S")) {
            System.out.println("Inserisci gli ingredienti da AGGIUNGERE, separati da virgola, poi invio:");
            String possibiliIngr = "Possibili aggiunte: ";
            int i = 0;
            for (Ingredienti ingr: wolf.getIngredientiPizzeria().values()) {
                possibiliIngr += (ingr.name().toLowerCase().replace("_"," ") + ", ");
                i++;
                if(i%10 == 0)
                    possibiliIngr += "\n";
            }
            System.out.println(possibiliIngr.substring(0, possibiliIngr.lastIndexOf(",")));
            String aggiunte = scan.nextLine();
            System.out.println("Inserisci gli ingredienti da RIMUOVERE, separati da virgola, poi invio:");
            String rimozioni = scan.nextLine();
            order.addPizza(wolf.getMenu().get(nomePizza), aggiunte, rimozioni, num, wolf.getPREZZO_SUPPL());
        } else if(answer.equals("N")) {
            order.addPizza(wolf.getMenu().get(nomePizza), num);
        } else {
            System.out.println("Spiacenti: inserito carattere non corretto.");
            chiediModificaPizza(order, nomePizza, num);
        }
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

    public void chiediConfermaText(Order order, Date orario, int tot){
        System.out.println("Confermi l'ordine? Premere 'S' per confermare, altro tasto per annullare: ");
        if (scan.nextLine().toUpperCase().equals("S")) {
            wolf.chiediConferma(order,orario,tot);
        } else {
            System.out.println("L'ordine è stato annullato.");
        }
    }
}

class Tester {
    public static void main(String[] args) {
        TextInterface textInterface = new TextInterface();
        textInterface.wolf.ApriPizzeria(8);     // ma è qui che va creata la pizzeria?
        textInterface.wolf.AddFattorino(new DeliveryMan("Musi",textInterface.wolf));
        //textInterface.wolf.AddFattorino(new DeliveryMan("Zanzatroni",textInterface.wolf));
        textInterface.wolf.creaMenu();
        textInterface.makeOrderText();
        //textInterface.makeOrderText();  //Per prova vettori orario
    }
}
