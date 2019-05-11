import exceptions.RestartOrderExc;
import exceptions.RiprovaExc;
import pizzeria.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * * @authors: Javengers, 2019
 *
 *  @author  Fecchio Andrea
 *  @author  Gobbo Matteo
 *  @author  Musitano Francesco
 *  @author  Rossanigo Fabio
 *  @author  Zanaboni Riccardo
 *
 *
 */
//

@SuppressWarnings("deprecation")

public class TextInterface {

    private Pizzeria wolf;
    private Scanner scan = new Scanner(System.in);

    private TextInterface() {
        wolf = new Pizzeria("Wolf Of Pizza", "Via Bolzano 10, Pavia", new Date(2019, 0, 1, 19, 0), new Date(2019, 0, 31, 23, 0, 0));
    }

    private void makeOrderText() {
        Order order = wolf.inizializeNewOrder();
        int num;
        String nomePizza;
        int tot = 0;
        boolean isPrimaRichiesta = true;
        try {
            do {
                nomePizza = qualePizza(isPrimaRichiesta);
                if (nomePizza.equals("OK")) {
                    break;      // smette di chiedere pizze
                }
                isPrimaRichiesta = false;
                num = quantePizzaSpecifica(order, nomePizza);
                if (order.getNumeroPizze() == 16) {
                    tot += num;
                    break;      // hai chiesto esattamente 16 pizze in totale: smette di chiedere pizze
                }
                tot += num;
            } while (true);

            Date orario;
            orario = superOrarioMegaGalattico(order, tot);
            System.out.println(orario);
            if (inserisciDati(order)) {
                wolf.recapOrdine(order);
                chiediConfermaText(order, orario, tot);
                //System.out.println("Riga in più");
            }
        } catch (RestartOrderExc e) {
            makeOrderText();
        }
    }

    private Date superOrarioMegaGalattico(Order order, int tot) {
        String orarioScelto = insertTime(tot);
        Date d = null;
        try {
            if (orarioScelto.toUpperCase().equals("F")) {
                throw new RestartOrderExc();
            }
            if (checkValidTime(orarioScelto)) {
                d = stringToDate(orarioScelto);
                assert d != null :"Error"; // se condizione non è verificata, il programma viene terminato
                int ora = d.getHours();
                int minuti = d.getMinutes();
                if (!wolf.controllaApertura(d)) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                if (wolf.getInfornate()[wolf.trovaCasellaTempoForno(wolf.getOrarioApertura(),ora,minuti)].getPostiDisp() + wolf.getInfornate()[wolf.trovaCasellaTempoForno(wolf.getOrarioApertura(), ora, minuti) -1].getPostiDisp() < tot) {
                    throw new RiprovaExc();
                }
                if(wolf.fattorinoLibero(wolf.getOrarioApertura(),ora,minuti,0)==null){
                    throw new RiprovaExc();
                }
                else {
                    order.setOrario(d);
                }
            } else {
                throw new RiprovaExc();
            }
        } catch (RestartOrderExc roe) {
            makeOrderText();
        } catch (ArrayIndexOutOfBoundsException obe) {
            System.out.println("Spiacenti: la pizzeria è chiusa nell'orario inserito. :(");
            d = superOrarioMegaGalattico(order, tot);
        } catch (RiprovaExc re) {
            System.out.println("Spiacenti: inserito orario non valido. :(");
            d = superOrarioMegaGalattico(order, tot);
        }
        return d;
    }

    private String insertTime (int tot) {
        System.out.println("A che ora vuoi ricevere la consegna? [formato HH:mm]\t\t(Inserisci 'F' per annullare e ricominciare)\n\tEcco gli orari disponibili:");
        int c = 0;
        System.out.print("\t");
        for (String s : wolf.orarioDisponibile(tot)) {
            System.out.print(s);
            c++;
            if (c % 18 == 0)
                System.out.print("\n\t");
        }
        System.out.print("\n");
        return scan.nextLine();
    }

    private Date stringToDate(String sDate1) {
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
        try {
            StringTokenizer st = new StringTokenizer(sDate1, ":");
            String token = st.nextToken();
            if (token.length() != 2) {
                return false;
            }
            int ora = Integer.parseInt(token);
            token = st.nextToken();
            if (token.length() != 2)
                return false;
            int minuti = Integer.parseInt(token);
            if (ora > 23 || minuti > 59)
                return false;
        } catch (NumberFormatException | NoSuchElementException e) {
            return false;
        }
        return true;
    }

    private String qualePizza(boolean isPrimaRichiesta) throws RestartOrderExc {
        String nomePizza;
        boolean ok = false;
        do {
            System.out.print("Quale pizza desideri?");
            if(isPrimaRichiesta)
                System.out.print("\n");
            else
                System.out.print("\t\t(Inserisci 'OK' per proseguire o 'F' per annullare e ricominciare)\n");
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
                    throw new RiprovaExc();
                else    // pizza inserita correttamente
                    ok = true;
            } catch (RiprovaExc e) {
                System.out.println("Spiacenti: \"" + nomePizza + "\" non presente sul menu. Riprovare:");
            }
        } while (!ok);
        return nomePizza;
    }

    private int quantePizzaSpecifica(Order order, String nomePizza) {
        boolean ok = false;
        int num = 0;
        int totOrdinate = order.getNumeroPizze();
        do {
            System.out.println("Quante " + nomePizza + " vuoi?\t[1.." + (16-totOrdinate) + "]");
            String line = scan.nextLine();
            try {
                num = Integer.parseInt(line);
                if (num <= 0)
                    throw new NumberFormatException();
                else if (totOrdinate + num > 16)
                    throw new RiprovaExc();
                else {
                    ok = true;
                    chiediModificaPizza(order, nomePizza, num);

                }
            } catch (NumberFormatException e) {
                System.out.println("Spiacenti: inserito numero non valido. Riprovare:");
            } catch (RiprovaExc e) {
                System.out.println("Spiacenti: massimo numero di pizze superato. Riprovare:");
            }
        } while (!ok);
        return num;
    }

    private void chiediModificaPizza(Order order, String nomePizza, int num) {
        System.out.println("Vuoi apportare modifiche alle " + num + " " + nomePizza + "?\t(S/N):");
        String answer = scan.nextLine().toUpperCase();
        switch (answer) {
            case "S":
                System.out.println("Inserisci gli ingredienti da AGGIUNGERE, separati da virgola, poi invio:");
                //String possibiliIngr = "Possibili aggiunte: ";
                StringBuilder possibiliIngr = new StringBuilder("Possibili aggiunte: ");
                int i = 0;
                for (Ingredienti ingr : wolf.getIngredientiPizzeria().values()) {
                    //possibiliIngr += (ingr.name().toLowerCase().replace("_", " ") + ", ");
                    possibiliIngr.append(ingr.name().toLowerCase().replace("_", " ")).append(", ");
                    i++;
                    if (i % 10 == 0)
                        possibiliIngr.append("\n");
                    //possibiliIngr += "\n";
                }
                System.out.println(possibiliIngr.substring(0, possibiliIngr.lastIndexOf(",")));
                String aggiunte = scan.nextLine();
                System.out.println("Inserisci gli ingredienti da RIMUOVERE, separati da virgola, poi invio:");
                String rimozioni = scan.nextLine();
                order.addPizza(wolf.getMenu().get(nomePizza), aggiunte, rimozioni, num, wolf.getPREZZO_SUPPL());
                break;
            case "N":
                order.addPizza(wolf.getMenu().get(nomePizza), num);
                break;
            default:
                System.out.println("Spiacenti: inserito carattere non corretto.");
                chiediModificaPizza(order, nomePizza, num);
                break;
        }
    }

    private boolean inserisciDati(Order order) {
        boolean ok = true;
        try {
            System.out.println("Come ti chiami?\t\t(Inserisci 'F' per annullare e ricominciare)");
            String nome = scan.nextLine();
            if (nome.toUpperCase().equals("F")) {
                ok = false;
                throw new RestartOrderExc();
            }
            Customer c = new Customer(nome);
            order.setCustomer(c);
            System.out.println("Inserisci l'indirizzo di consegna:\t\t(Inserisci 'F' per annullare e ricominciare)");
            String indirizzo = scan.nextLine();
            if (indirizzo.toUpperCase().equals("F")) {
                ok = false;
                throw new RestartOrderExc();
            }
            order.setIndirizzo(indirizzo);
        } catch (RestartOrderExc e) {
            makeOrderText();
        }
        return ok;
    }

    private void chiediConfermaText(Order order, Date orario, int tot) {
        System.out.println("Confermi l'ordine? Premere 'S' per confermare, 'N' per annullare: ");
        String risp= scan.nextLine().toUpperCase();
        switch (risp) {
            case "S":
                wolf.checkFornoFattorino(order, orario, tot);
                order.setCompleto();
                wolf.addOrdine(order);
                break;
            case "N":
                try {
                    throw new RestartOrderExc();
                } catch (RestartOrderExc roe) {
                    System.out.println("L'ordine è stato annullato.");
                    makeOrderText();
                }
                break;
            default:
                try {
                    throw new RiprovaExc();
                } catch (RiprovaExc re) {
                    System.out.println("Spiacenti: carattere inserito non valido. Riprovare: ");
                    chiediConfermaText(order, orario, tot);
                }
                break;
        }
    }

    public static void main(String[] args) {
        TextInterface textInterface = new TextInterface();
        textInterface.wolf.ApriPizzeria(8);     // ma è qui che va creata la pizzeria? Bho non lo so
        textInterface.wolf.AddFattorino(new DeliveryMan("Musi", textInterface.wolf));
        //textInterface.wolf.AddFattorino(new pizzeria.DeliveryMan("Zanzatroni",textInterface.wolf));
        textInterface.wolf.creaMenu();
        textInterface.makeOrderText();
        textInterface.makeOrderText();  //Per prova vettori orario
        //textInterface.makeOrderText();
        //textInterface.makeOrderText();
    }
}


/*private Date inserisciOrario (pizzeria.Order order, int tot){
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
    }*/

    /*public Date checkValiditaOrario(String sDate1, pizzeria.Order order, int tot){
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
    }*/