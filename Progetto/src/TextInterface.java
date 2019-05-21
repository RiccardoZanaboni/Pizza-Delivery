import exceptions.RestartOrderExc;
import exceptions.RiprovaExc;
import pizzeria.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 */

@SuppressWarnings("deprecation")

public class TextInterface {

    private Pizzeria wolf;
    private Scanner scan = new Scanner(System.in);

    private TextInterface() {
        wolf = new Pizzeria("Wolf Of Pizza", "Via Bolzano 10, Pavia", new Date(2019, 0, 1, 19, 0), new Date(2019, 0, 31, 23, 0, 0));
    }

    private void makeOrderText() {
        System.out.println(wolf.helloThere());
        System.out.println(wolf.printMenu());
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
                wolf.recapOrder(order);
                askConfirm(order, orario, tot);
                //System.out.println("Riga in più");
            }
        } catch (RestartOrderExc e) {
            makeOrderText();
        }
    }

    private Date orderTime(Order order, int tot) {
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
                if (!wolf.isOpen(d)) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                if (wolf.getOvens()[wolf.findTimeBoxOven(wolf.getOpeningTime(),ora,minuti)].getPostiDisp() + wolf.getOvens()[wolf.findTimeBoxOven(wolf.getOpeningTime(), ora, minuti) -1].getPostiDisp() < tot) {
                    throw new RiprovaExc();
                }
                if(wolf.aFreeDeliveryMan(wolf.getOpeningTime(),ora,minuti,0)==null){
                    throw new RiprovaExc();
                }
                else {
                    order.setTime(d);
                }
            } else {
                throw new RiprovaExc();
            }
        } catch (RestartOrderExc roe) {
            makeOrderText();
        } catch (ArrayIndexOutOfBoundsException obe) {
            System.out.println("Spiacenti: la pizzeria è chiusa nell'orario inserito. :(");
            d = orderTime(order, tot);
        } catch (RiprovaExc re) {
            System.out.println("Spiacenti: inserito orario non valido. :(");
            d = orderTime(order, tot);
        }
        return d;
    }

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
                    throw new RiprovaExc();
                else    // pizza inserita correttamente
                    ok = true;
            } catch (RiprovaExc e) {
                System.out.println("Spiacenti: \"" + nomePizza + "\" non presente sul menu. Riprovare:");
            }
        } while (!ok);
        return nomePizza;
    }

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
                else if (totOrdinate + num > 16)
                    throw new RiprovaExc();
                else {
                    ok = true;
                    askModifyPizza(order, nomePizza, num);

                }
            } catch (NumberFormatException e) {
                System.out.println("Spiacenti: inserito numero non valido. Riprovare:");
            } catch (RiprovaExc e) {
                System.out.println("Spiacenti: massimo numero di pizze superato. Riprovare:");
            }
        } while (!ok);
        return num;
    }

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
            makeOrderText();
        }
        return ok;
    }

    private void addPizza(Order order, Pizza pizza, String aggiunte, String rimozioni, int num, double prezzoSupl) {
        HashMap<String, Ingredients> ingr = new HashMap<>(pizza.getIngredients());
        Pizza p = new Pizza(pizza.getMaiuscName(), ingr, pizza.getPrice());
        int piu=0;
        StringTokenizer stAgg = new StringTokenizer(aggiunte);
        while (stAgg.hasMoreTokens()) {
            try {
                String ingredienteAggiuntoString = arrangeIngredientString(stAgg);
                Ingredients ingredients = Ingredients.valueOf(ingredienteAggiuntoString);
                p.addIngredients(ingredients);
                if(!pizza.getIngredients().containsKey(ingredienteAggiuntoString))
                    piu++;
            } catch (Exception ignored) { }
        }
        StringTokenizer stRmv = new StringTokenizer(rimozioni);
        while (stRmv.hasMoreTokens()) {
            try {
                String ingredienteRimossoString = arrangeIngredientString(stRmv);
                Ingredients ingredients = Ingredients.valueOf(ingredienteRimossoString);
                if(!pizza.getIngredients().containsKey(ingredienteRimossoString) && p.getIngredients().containsKey(ingredienteRimossoString))
                    piu--;
                p.rmvIngredients(ingredients);
            } catch (Exception ignored) { }
        }
        p.setPrice(p.getPrice() + (piu * prezzoSupl));        // aggiunto 0.50 per ogni ingrediente
        for (int i = 0; i < num; i++) {
            order.getOrderedPizze().add(p);
        }
        System.out.println("\t> Aggiunte " + num + " pizze " + p.getMaiuscName() + " (" + p.getDescription() + ").");
    }

    private String arrangeIngredientString(StringTokenizer st){
        String ingred = st.nextToken(",");
        if(ingred.startsWith(" "))
            ingred = ingred.substring(1);
        if(ingred.endsWith(" "))
            ingred = ingred.substring(0,ingred.length()-1);
        ingred = ingred.replace(" ","_");
        ingred = ingred.toUpperCase();
        return ingred;
    }

    private void askConfirm(Order order, Date orario, int tot) {
        System.out.println("Confermi l'ordine? Premere 'S' per confermare, 'N' per annullare: ");
        String risp = scan.nextLine().toUpperCase();
        switch (risp) {
            case "S":
                wolf.checkOvenAndDeliveryMan(orario, tot);
                order.setFull();
                wolf.addOrder(order);
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
                    askConfirm(order, orario, tot);
                }
                break;
        }
    }

    public static void main(String[] args) {
        TextInterface textInterface = new TextInterface();
        textInterface.wolf.OpenPizzeria(8);     // ma è qui che va creata la pizzeria? Bho non lo so
        textInterface.wolf.AddDeliveryMan(new DeliveryMan("Musi", textInterface.wolf));
        //textInterface.wolf.AddDeliveryMan(new pizzeria.DeliveryMan("Zanzatroni",textInterface.wolf));
        textInterface.wolf.createMenu();
        textInterface.makeOrderText();
        //textInterface.makeOrderText();  //Per prova vettori orario
        //textInterface.makeOrderText();
    }
}