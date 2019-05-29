import exceptions.RestartOrderExc;
import exceptions.TryAgainExc;
import pizzeria.*;

import java.awt.*;
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

    /**
     * 16 parametri: nome, indirizzo, 7 orari di apertura (da domenica a sabato),
     * 7 orari di chiusura (da domenica a sabato).
     *
     * Gli orari partono sempre da LocalTime.MIN, che corrisponde a mezzanotte.
     * A questo si aggiunge (con il metodo plus()) ora e minuti desiderati.
     *
     * ATTENZIONE: Per lasciare la pizzeria chiusa in un particolare giorno, porre openTime = closeTime.
     * PRESTARE PARTICOLARE ATTENZIONE: assicurarsi che ogni giorno la pizzeria rimanga aperta almeno 20 minuti.
     *
     * Per modificare gli orari successivamente, lavorerò con il metodo Pizzeria.setDayOfTheWeek().
     * */
    private Pizzeria wolf = new Pizzeria("Wolf Of Pizza", "Via Bolzano 10, Pavia",
            // orari di apertura, da domenica a sabato
            LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(Services.getMinutes(0,0), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(Services.getMinutes(16,40), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
            // orari di chiusura, da domenica a sabato
            LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(Services.getMinutes(0,0), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(Services.getMinutes(17,0), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
            LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES)
    );
    private Scanner scan = new Scanner(System.in);

    private TextInterface() {}

    /** Effettua tutte le operazioni necessarie ad effettuare un nuovo ordine.
     * Utilizzo la sigla-chiave "OK" una volta terminata la scelta delle pizze, per continuare.
     * Utilizzo ovunque la sigla-chiave "F" per l'annullamento dell'ordine: si torna all'inizio. */
    private void makeOrderText() {
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
                System.out.println(order.recapOrder());
                askConfirm(order, orario, tot);
            }
        } catch (RestartOrderExc e) {
            String annullato = Services.colorSystemOut("L'ordine è stato annullato.",Color.ORANGE,true,false);
            System.out.println("\t>> " + annullato);
            System.out.println(Services.getLine());
            wantNewOrder();
        }
    }

    /** Al lancio di TextInterface, inizia un nuovo ordine solo se richiesto. */
    private void wantNewOrder() {
        String open = Services.checkTimeOrder(wolf);
        switch (open) {
            // se la pizzeria è aperta.
            case "OPEN":
                String domanda = Services.colorSystemOut("Desideri eseguire un nuovo ordine?   ", Color.YELLOW, false, false);
                System.out.println(domanda + "[S/N]");
                String risposta = scan.nextLine().toUpperCase();
                switch (risposta) {
                    case "S":
                        makeOrderText();
                        break;
                    case "N":
                        System.out.println(Services.colorSystemOut("Ok, come non detto... :)\nA presto!", Color.YELLOW, false, false));
                        break;
                    default:
                        System.out.println(Services.colorSystemOut("Spiacenti: inserito carattere non corretto. Risprovare: ", Color.RED, false, false));
                        wantNewOrder();
                        break;
                }
                break;
            // se la pizzeria è ancora aperta, ma non ci sono i tempi per eseguire un nuovo ordine.
            case "CLOSING":
                String chiusura = "Spiacenti: la pizzeria al momento è in chiusura. Torna a trovarci domani!";
                System.out.println(Services.colorSystemOut(chiusura, Color.RED, false, false));
                break;
            // se la pizzeria per oggi ha terminato il turno lavorativo, quindi è chiusa.
            case "CLOSED":
                String chiusa = "Spiacenti: la pizzeria per oggi è chiusa. Torna a trovarci domani!";
                System.out.println(Services.colorSystemOut(chiusa, Color.RED, false, false));
                break;
        }
    }

    /** Esegue i controlli dovuti sulla stringa relativa all'orario e,
     * in caso di successo, restituisce il Date "orarioScelto". */
    private Date orderTime(Order order, int tot) throws RestartOrderExc {
        String orarioScelto = insertTime(tot);
        Date d;
        try {
            if (orarioScelto.toUpperCase().equals("F")) {
                throw new RestartOrderExc();
            }
            if (Services.checkValidTime(orarioScelto)) {
                d = Services.stringToDate(orarioScelto);
                assert d != null : "Error"; // se condizione non è verificata, il programma viene terminato
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
        } catch (ArrayIndexOutOfBoundsException obe) {
            String spiacenti = "Spiacenti: la pizzeria è chiusa nell'orario inserito. Riprovare: ";
            System.out.println(Services.colorSystemOut(spiacenti,Color.RED,false,false));
            d = orderTime(order, tot);
        } catch (TryAgainExc re) {
            String spiacenti = "Spiacenti: inserito orario non valido. Riprovare: ";
            System.out.println(Services.colorSystemOut(spiacenti,Color.RED,false,false));
            d = orderTime(order, tot);
        }
        return d;
    }

    /** Stampa a video tutti gli orari disponibili per la consegna
     * e ritorna la stringa inserita dall'utente. */
    private String insertTime (int tot) throws RestartOrderExc {
        String domanda = Services.colorSystemOut("A che ora vuoi ricevere la consegna? [formato HH:mm]",Color.YELLOW,false,false);
        System.out.println(domanda + " \t\t(Inserisci 'F' per annullare.)");
        System.out.println(Services.colorSystemOut("\tEcco gli orari disponibili:",Color.YELLOW,false,false));
        int c = 0;
        System.out.print("\t");
        try {
            for (String s : wolf.availableTimes(tot)) {
                System.out.print(s);
                c++;
                if (c % 18 == 0) System.out.print("\n\t");      // stampa 18 orari su ogni riga
            }
            System.out.print("\n");
        } catch (NullPointerException npe) {
            throw new RestartOrderExc();
        }
        return scan.nextLine();
    }

    /** Assicura che all'orario attuale della richiesta
     * sia effettivamente possibile portare a termine un ordine */
    private boolean checkNotTooLate(Date orarioScelto, int tot) {
        int chosenTime = Services.getMinutes(orarioScelto);
        int nowTime = Services.getNowMinutes();
        if(tot<wolf.getOvens()[0].getPostiDisp())
            nowTime += 14;       // tengo conto dei tempi minimi di una infornata e consegna.
        else
            nowTime += 19;       // tengo conto dei tempi minimi di due infornate e consegna.
        return (chosenTime >= nowTime);
    }

    /** Restituisce il nome della pizza desiderata, dopo avere effettuato i dovuti controlli:
     * - che non sia stato inserito "OK" oppure "F";
     * - che la stringa inserita corrisponda ad una pizza valida. */
    private String whichPizza(boolean isPrimaRichiesta) throws RestartOrderExc {
        String nomePizza;
        boolean ok = false;
        do {
            System.out.print(Services.colorSystemOut("Quale pizza desideri?",Color.YELLOW,false,false));
            if(isPrimaRichiesta)
                System.out.print("\n");
            else
                System.out.print("\t\t(Inserisci 'OK' se non desideri altro, oppure 'F' per annullare.)\n");
            nomePizza = scan.nextLine().toUpperCase();
            try {
                if (nomePizza.equals("F")) {
                    ok = true;
                    throw new RestartOrderExc();
                }
                String err;
                if (nomePizza.equals("OK") && isPrimaRichiesta) {
                    err = "Numero di pizze non valido. Riprovare:";
                    System.out.println(Services.colorSystemOut(err, Color.RED, false, false));
                } else if (nomePizza.equals("OK"))
                    ok = true;
                else if (!(wolf.getMenu().containsKey(nomePizza)))
                    throw new TryAgainExc();
                else    // pizza inserita correttamente
                    ok = true;
            } catch (TryAgainExc tae) {
                String spiacenti = "Spiacenti: \"" + nomePizza + "\" non presente sul menu. Riprovare:";
                System.out.println(Services.colorSystemOut(spiacenti,Color.RED,false,false));
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
            String domanda = Services.colorSystemOut("Quante " + nomePizza + " vuoi?",Color.YELLOW,false,false);
            System.out.println(domanda + "\t[1.." + (2*(wolf.getAvailablePlaces())-totOrdinate) + "]");
            String line = scan.nextLine();
            try {
                num = Integer.parseInt(line);
                if (num <= 0)
                    throw new NumberFormatException();
                else if (totOrdinate + num > 2*wolf.getAvailablePlaces())    // max 16 pizze per ordine
                    throw new TryAgainExc();
                else {
                    ok = true;
                    askModifyPizza(order, nomePizza, num);
                }
            } catch (NumberFormatException e) {
                String spiacenti = "Spiacenti: inserito numero non valido. Riprovare:";
                System.out.println(Services.colorSystemOut(spiacenti,Color.RED,false,false));
            } catch (TryAgainExc e) {
                String spiacenti = "Spiacenti: massimo numero di pizze superato. Riprovare:";
                System.out.println(Services.colorSystemOut(spiacenti,Color.RED,false,false));
            }
        } while (!ok);
        return num;
    }

    /** Gestisce la possibilità che la pizza desiderata necessiti di aggiunte o rimozioni
     * di ingredienti, rispetto ad una specifica presente sul menu. */
    private void askModifyPizza(Order order, String nomePizza, int num) {
        String domanda = Services.colorSystemOut("Vuoi apportare modifiche alle " + num + " " + nomePizza + "?",Color.YELLOW,false,false);
        System.out.println(domanda + "\t[S/N]: ");
        String answer = scan.nextLine().toUpperCase();
        switch (answer) {
            case "S":
                System.out.println(possibleAddictions(wolf));
                String adding = Services.colorSystemOut("Inserisci gli ingredienti da AGGIUNGERE, separati da virgola, poi invio:",Color.YELLOW,false,false);
                System.out.println(adding);
                String aggiunte = scan.nextLine();
                String rmving = Services.colorSystemOut("Inserisci gli ingredienti da RIMUOVERE, separati da virgola, poi invio:",Color.YELLOW,false,false);
                System.out.println(rmving);
                String rimozioni = scan.nextLine();
                addAndRmvToppingsText(order, wolf.getMenu().get(nomePizza), aggiunte, rimozioni, num, wolf.getSUPPL_PRICE());
                break;
            case "N":
                order.addPizza(wolf.getMenu().get(nomePizza), num);
                System.out.println("\t> Aggiunte " + num + " pizze " + nomePizza);
                break;
            default:
                String spiacenti = "Spiacenti: inserito carattere non corretto. Riprovare: ";
                System.out.println(Services.colorSystemOut(spiacenti,Color.RED,false,false));
                askModifyPizza(order, nomePizza, num);
                break;
        }
    }

    /** Gestisce l'inserimento del nome del cliente e dell'indirizzo di spedizione. */
    private boolean insertNameAndAddress(Order order) throws RestartOrderExc {

        String domanda1 = Services.colorSystemOut("Come ti chiami?",Color.YELLOW,false,false);
        System.out.println(domanda1 + "\t\t(Inserisci 'F' per annullare l'ordine)");
        String nome = scan.nextLine();
        if (nome.toUpperCase().equals("F")) {
            throw new RestartOrderExc();
        }
        Customer c = new Customer(nome);
        order.setCustomer(c);
        String domanda2 = Services.colorSystemOut("Inserisci l'indirizzo di consegna:",Color.YELLOW,false,false);
        System.out.println(domanda2 + "\t\t(Inserisci 'F' per annullare l'ordine)");
        String indirizzo = scan.nextLine();
        if (indirizzo.toUpperCase().equals("F")) {

            throw new RestartOrderExc();
        }
        order.setAddress(indirizzo);
        return true;
    }

    /** Aggiunge la pizza all'Order, nella quantità inserita.
     * Effettua, nel caso, tutte le modifiche richieste, aggiornando il prezzo. */
    private void addAndRmvToppingsText(Order order, Pizza pizza, String aggiunte, String rimozioni, int num, double prezzoSupl) {
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
        String conferma = Services.colorSystemOut("Aggiunte " + num + " pizze " + p.getMaiuscName(),Color.YELLOW,false,false);
        System.out.println("\t> " + conferma + " (" + p.getDescription() + ").");
    }

    /** Chiede conferma dell'ordine e lo salva tra quelli completati
     * (pronti all'evasione), aggiornando il vettore orario del forno e del fattorino. */
    private void askConfirm(Order order, Date orario, int tot) {
        String domanda = Services.colorSystemOut("Confermi l'ordine?",Color.YELLOW,false,false);
        String s = Services.colorSystemOut("S",Color.ORANGE,true,false);
        String n = Services.colorSystemOut("N",Color.ORANGE,true,false);
        System.out.println(domanda + "  Premere '" + s + "' per confermare, '" + n + "' per annullare: ");
        String risp = scan.nextLine().toUpperCase();
        switch (risp) {
            case "S":
                // conferma l'ordine e lo aggiunge a quelli della pizzeria.
                wolf.updateOvenAndDeliveryMan(orario, tot);
                order.setFull();
                String confirm = "\nGrazie! L'ordine è stato effettuato correttamente.";
                System.out.println(Services.colorSystemOut(confirm, Color.GREEN,true,false));
				String confirmedTime = Services.timeStamp(orario.getHours(),orario.getMinutes());
				confirmedTime = Services.colorSystemOut(confirmedTime,Color.GREEN,true,false);
				System.out.println("\t>> Consegna prevista: " + confirmedTime + ".");
                System.out.println(Services.getLine());
				wolf.addOrder(order);
                //wantNewOrder();
                break;
            case "N":
                // annulla l'ordine.
                try {
                    throw new RestartOrderExc();
                } catch (RestartOrderExc roe) {
                    String annullato = Services.colorSystemOut("L'ordine è stato annullato.",Color.ORANGE,true,false);
                    System.out.println("\t>> " + annullato);
                    System.out.println(Services.getLine());
                    wantNewOrder();
                }
                break;
            default:
                // è stato inserito un carattere diverso da 'S' o 'N'.
                try {
                    throw new TryAgainExc();
                } catch (TryAgainExc re) {
                    String spiacenti = "Spiacenti: carattere inserito non valido. Riprovare: ";
                    System.out.println(Services.colorSystemOut(spiacenti,Color.RED,false,false));
                    askConfirm(order, orario, tot);
                }
                break;
        }
    }

    /** In TextInterface, elenca tutte gli ingredienti che l'utente può scegliere, per modificare una pizza. */
    private String possibleAddictions(Pizzeria pizzeria) {
        StringBuilder possibiliIngr = new StringBuilder();
        possibiliIngr.append(Services.colorSystemOut("\tPossibili aggiunte: ",Color.ORANGE,false,false));
        possibiliIngr.append(pizzeria.possibleAddictions());
        return possibiliIngr.substring(0, possibiliIngr.lastIndexOf(",")); // elimina ultima virgola
    }


    public static void main(String[] args) {
        TextInterface textInterface = new TextInterface();
        System.out.println(textInterface.wolf.helloThere());
        System.out.println(textInterface.wolf.printMenu());
        textInterface.wantNewOrder();
    }
}