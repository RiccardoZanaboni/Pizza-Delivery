import exceptions.RestartOrderExc;
import exceptions.TryAgainExc;
import pizzeria.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * * @authors: Javengers, 2019
 *
 *  @author  Fecchio Andrea
 *  @author  Gobbo Matteo
 *  @author  Musitano Francesco
 *  @author  Rossanigo Fabio
 *  @author  Zanaboni Riccardo
 *
 * Avvia il programma tramite interfaccia testuale.
 */

@SuppressWarnings("deprecation")

public class TextInterface {

    // FIXME: ma cosa significano i valori di mese e giorno???

    private Pizzeria wolf = new Pizzeria("Wolf Of Pizza", "Via Bolzano 10, Pavia",
            LocalTime.MIN.plus(60*16+45, ChronoUnit.MINUTES), LocalTime.MIN, LocalTime.MIN.plus(60*11+30, ChronoUnit.MINUTES),
            LocalTime.MIN.plus(60*11+30, ChronoUnit.MINUTES), LocalTime.MIN.plus(60*11+30, ChronoUnit.MINUTES),
            LocalTime.MIN.plus(60*11+30, ChronoUnit.MINUTES), LocalTime.MIN.plus(60*11+30, ChronoUnit.MINUTES),
            LocalTime.MIN.plus(60*17+45, ChronoUnit.MINUTES), LocalTime.MIN, LocalTime.MIN.plus(60*21+30, ChronoUnit.MINUTES),
            LocalTime.MIN.plus(60*21+30, ChronoUnit.MINUTES), LocalTime.MIN.plus(60*21+30, ChronoUnit.MINUTES),
            LocalTime.MIN.plus(60*22+30, ChronoUnit.MINUTES), LocalTime.MIN.plus(60*23+30, ChronoUnit.MINUTES));
    private Scanner scan = new Scanner(System.in);

    private TextInterface() {}

    /** Effettua tutte le operazioni necessarie ad effettuare un nuovo ordine.
     * Utilizzo la sigla-chiave "OK" una volta terminata la scelta delle pizze, per continuare.
     * Utilizzo ovunque la sigla-chiave "F" per l'annullamento dell'ordine: si torna all'inizio. */
    private void makeOrderText() {
        if(Services.checkTimeOrder(wolf).equals("OPEN")) {
            Order order = wolf.initializeNewOrder();
            int num;
            String nomePizza;
            int tot = 0;
            boolean isPrimaRichiesta = true;
            try {
                do {
                    nomePizza = whichPizza(isPrimaRichiesta);
                    if (nomePizza.equals("OK")) {
                        break;      // smette di chiedere pizze
                    }
                    isPrimaRichiesta = false;
                    num = howManySpecificPizza(order, nomePizza);
                    if (order.getNumPizze() == 16) {
                        tot += num;
                        break;      // hai chiesto esattamente 16 pizze in totale: smette di chiedere pizze
                    }
                    tot += num;
                } while (true);

                Date orario;
                orario = orderTime(order, tot);
                System.out.println(orario);
                if (insertNameAndAddress(order)) {
                    order.recapOrder();
                    askConfirm(order, orario, tot);
                }
            } catch (RestartOrderExc e) {
                makeOrderText();
            }
        } else if (Services.checkTimeOrder(wolf).equals("CLOSING"))
            System.out.println("Spiacenti: la pizzeria al momento è in chiusura. Torna a trovarci domani!");
        else
            System.out.println("Spiacenti: la pizzeria al momento è chiusa. Torna a trovarci domani!");
    }

    /** Esegue i controlli dovuti sulla stringa relativa all'orario e,
     * in caso di successo, restituisce il Date "orarioScelto". */
    private Date orderTime(Order order, int tot) {
        String orarioScelto = insertTime(tot);
        Date d = null;
        try {
            if (orarioScelto.toUpperCase().equals("F")) {
                throw new RestartOrderExc();
            }
            if (Services.checkValidTime(orarioScelto)) {
                d = Services.stringToDate(orarioScelto);
                assert d != null :"Error"; // se condizione non è verificata, il programma viene terminato
                int ora = d.getHours();
                int minuti = d.getMinutes();
                if (!wolf.isOpen(d)) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                if (!checkNotTooLate(d,tot)) {
                	throw new TryAgainExc();
                }
                if (!wolf.checkTimeBoxOven(ora, minuti, tot)) {
                    throw new TryAgainExc();
				}
                if(wolf.aFreeDeliveryMan(ora,minuti)==null){
                    throw new TryAgainExc();
				}
                else {
                    order.setTime(d);
                }
            } else {
                throw new TryAgainExc();
            }
        } catch (RestartOrderExc roe) {
            System.out.println("\n----------------------------------------------------------------------\n");
            makeOrderText();
        } catch (ArrayIndexOutOfBoundsException obe) {
            System.out.println("Spiacenti: la pizzeria è chiusa nell'orario inserito. Riprovare: ");
            d = orderTime(order, tot);
        } catch (TryAgainExc re) {
            System.out.println("Spiacenti: inserito orario non valido. Riprovare: ");
            d = orderTime(order, tot);
        }
        return d;
    }

    /** Stampa a video tutti gli orari disponibili per la consegna e ritorna la stringa inserita dall'utente. */
    private String insertTime (int tot) {
        System.out.println("A che ora vuoi ricevere la consegna? [formato HH:mm]\t\t(Inserisci 'F' per annullare e ricominciare)\n\tEcco gli orari disponibili:");
        int c = 0;
        System.out.print("\t");
        for (String s : wolf.availableTimes(tot)) {
            System.out.print(s);
            c++;
            if (c % 18 == 0)
                System.out.print("\n\t");
        }
        System.out.print("\n");
        return scan.nextLine();
    }

    private boolean checkNotTooLate(Date orarioScelto, int tot) {
        int orderHour = orarioScelto.getHours();
        int orderMinutes = orarioScelto.getMinutes();
        Calendar cal = new GregorianCalendar();
        if(tot<wolf.getOvens()[0].getPostiDisp())
            cal.add(Calendar.MINUTE, 14);       // tengo conto dei tempi minimi di una infornata e consegna.
        else
            cal.add(Calendar.MINUTE, 19);       // tengo conto dei tempi minimi di due infornate e consegna.
        int nowHour = cal.get(Calendar.HOUR_OF_DAY);
        int nowMinutes = cal.get(Calendar.MINUTE);

        if(orderHour < nowHour || (orderHour == nowHour && orderMinutes < nowMinutes))
            return false;
        else
            return true;
    }

    /** Restituisce il nome della pizza desiderata, dopo avere effettuato i dovuti controlli:
     * - che non sia stato inserito "OK" oppure "F";
     * - che la stringa inserita corrisponda ad una pizza valida. */
    private String whichPizza(boolean isPrimaRichiesta) throws RestartOrderExc {
        String nomePizza;
        boolean ok = false;
        do {
            System.out.print("Quale pizza desideri?");
            if(isPrimaRichiesta)
                System.out.print("\n");
            else
                System.out.print("\t\t(Inserisci 'OK' se non desideri altro, oppure 'F' per annullare e ricominciare)\n");
            nomePizza = scan.nextLine().toUpperCase();
            try {
                if (nomePizza.equals("F")) {
                    ok = true;
                    throw new RestartOrderExc();
                }
                if (nomePizza.equals("OK") && isPrimaRichiesta)
                    System.out.println("Numero di pizze non valido. Riprovare:");
                else if (nomePizza.equals("OK"))
                    ok = true;
                else if (!(wolf.getMenu().containsKey(nomePizza)))
                    throw new TryAgainExc();
                else    // pizza inserita correttamente
                    ok = true;
            } catch (TryAgainExc e) {
                System.out.println("Spiacenti: \"" + nomePizza + "\" non presente sul menu. Riprovare:");
            }
        } while (!ok);
        return nomePizza;
    }

    /** Ritorna il numero desiderato della pizza specifica richiesta.
     * Il numero di pizze complessivamente ordinate non deve superare
     * il valore massimo consentito. */
    private int howManySpecificPizza(Order order, String nomePizza) {
        boolean ok = false;
        int num = 0;
        int totOrdinate = order.getNumPizze();
        do {
            System.out.println("Quante " + nomePizza + " vuoi?\t[1.." + (16-totOrdinate) + "]");
            String line = scan.nextLine();
            try {
                num = Integer.parseInt(line);
                if (num <= 0)
                    throw new NumberFormatException();
                else if (totOrdinate + num > 16)    // max 16 pizze per ordine
                    throw new TryAgainExc();
                else {
                    ok = true;
                    askModifyPizza(order, nomePizza, num);
                }
            } catch (NumberFormatException e) {
                System.out.println("Spiacenti: inserito numero non valido. Riprovare:");
            } catch (TryAgainExc e) {
                System.out.println("Spiacenti: massimo numero di pizze superato. Riprovare:");
            }
        } while (!ok);
        return num;
    }

    /** Gestisce la possibilità che la pizza desiderata necessiti di aggiunte o rimozioni
     * di ingredienti, rispetto ad una specifica presente sul menu. */
    private void askModifyPizza(Order order, String nomePizza, int num) {
        System.out.println("Vuoi apportare modifiche alle " + num + " " + nomePizza + "?\t(S/N):");
        String answer = scan.nextLine().toUpperCase();
        switch (answer) {
            case "S":
                System.out.println(wolf.possibleAddictions());
                System.out.println("Inserisci gli ingredienti da AGGIUNGERE, separati da virgola, poi invio:");
                String aggiunte = scan.nextLine();
                System.out.println("Inserisci gli ingredienti da RIMUOVERE, separati da virgola, poi invio:");
                String rimozioni = scan.nextLine();
                addPizza(order, wolf.getMenu().get(nomePizza), aggiunte, rimozioni, num, wolf.getSUPPL_PRICE());
                break;
            case "N":
                order.addPizza(wolf.getMenu().get(nomePizza), num);
                System.out.println("\t> Aggiunte " + num + " pizze " + nomePizza);
                break;
            default:
                System.out.println("Spiacenti: inserito carattere non corretto. Riprovare: ");
                askModifyPizza(order, nomePizza, num);
                break;
        }
    }

    /** Gestisce l'inserimento del nome del cliente e dell'indirizzo di spedizione. */
    private boolean insertNameAndAddress(Order order) {
        boolean ok = true;
        try {
            System.out.println("Come ti chiami?\t\t(Inserisci 'F' per annullare l'ordine e ricominciare)");
            String nome = scan.nextLine();
            if (nome.toUpperCase().equals("F")) {
                ok = false;
                throw new RestartOrderExc();
            }
            Customer c = new Customer(nome);
            order.setCustomer(c);
            System.out.println("Inserisci l'indirizzo di consegna:\t\t(Inserisci 'F' per annullare l'ordine e ricominciare)");
            String indirizzo = scan.nextLine();
            if (indirizzo.toUpperCase().equals("F")) {
                ok = false;
                throw new RestartOrderExc();
            }
            order.setAddress(indirizzo);
        } catch (RestartOrderExc e) {
            System.out.println("\n----------------------------------------------------------------------\n");
            makeOrderText();
        }
        return ok;
    }

    /** Aggiunge la pizza all'Order, nella quantità inserita.
     * Effettua, nel caso, tutte le modifiche richieste, aggiornando il prezzo. */
    private void addPizza(Order order, Pizza pizza, String aggiunte, String rimozioni, int num, double prezzoSupl) {
        HashMap<String, Toppings> ingr = new HashMap<>(pizza.getToppings());
        Pizza p = new Pizza(pizza.getMaiuscName(), ingr, pizza.getPrice());
        int piu=0;
        StringTokenizer stAgg = new StringTokenizer(aggiunte);
        while (stAgg.hasMoreTokens()) {
            try {
                String ingredienteAggiuntoString = Services.arrangeIngredientString(stAgg);
                Toppings toppings = Toppings.valueOf(ingredienteAggiuntoString);
                p.addIngredients(toppings);
                if(!pizza.getToppings().containsKey(ingredienteAggiuntoString))
                    piu++;
            } catch (Exception ignored) { }
        }
        StringTokenizer stRmv = new StringTokenizer(rimozioni);
        while (stRmv.hasMoreTokens()) {
            try {
                String ingredienteRimossoString = Services.arrangeIngredientString(stRmv);
                Toppings toppings = Toppings.valueOf(ingredienteRimossoString);
                if(!pizza.getToppings().containsKey(ingredienteRimossoString) && p.getToppings().containsKey(ingredienteRimossoString))
                    piu--;
                p.rmvIngredients(toppings);
            } catch (Exception ignored) { }
        }
        p.setPrice(p.getPrice() + (piu * prezzoSupl));        // aggiunto 0.50 per ogni ingrediente
        for (int i = 0; i < num; i++) {
            order.getOrderedPizze().add(p);
        }
        System.out.println("\t> Aggiunte " + num + " pizze " + p.getMaiuscName() + " (" + p.getDescription() + ").");
    }

    /** Chiede conferma dell'ordine e lo salva tra quelli completati
     * (pronti all'evasione), aggiornando il vettore orario del forno e del fattorino. */
    private void askConfirm(Order order, Date orario, int tot) {
        System.out.println("Confermi l'ordine? Premere 'S' per confermare, 'N' per annullare: ");
        String risp = scan.nextLine().toUpperCase();
        switch (risp) {
            case "S":
                wolf.updateOvenAndDeliveryMan(orario, tot);
                order.confirmAndSetFull();
				String ore, minuti;
				if(orario.getHours()<10)
					ore = "0"+orario.getHours();
				else
					ore = String.valueOf(orario.getHours());
				if(orario.getMinutes()<10)
					minuti = "0"+orario.getMinutes();
				else
					minuti = String.valueOf(orario.getMinutes());
				System.out.println("\t>> Consegna prevista: " + ore + ":" + minuti + ".");
                wolf.addOrder(order);
                break;
            case "N":
                try {
                    throw new RestartOrderExc();
                } catch (RestartOrderExc roe) {
                    System.out.println("L'ordine è stato annullato.");
                    System.out.println("\n----------------------------------------------------------------------\n");
                    makeOrderText();
                }
                break;
            default:
                try {
                    throw new TryAgainExc();
                } catch (TryAgainExc re) {
                    System.out.println("Spiacenti: carattere inserito non valido. Riprovare: ");
                    askConfirm(order, orario, tot);
                }
                break;
        }
    }

    public static void main(String[] args) {
        TextInterface textInterface = new TextInterface();
        System.out.println(textInterface.wolf.helloThere());
        System.out.println(textInterface.wolf.printMenu());
        textInterface.makeOrderText();
        //textInterface.makeOrderText();  //Per prova vettori orario
        //textInterface.makeOrderText();
    }
}