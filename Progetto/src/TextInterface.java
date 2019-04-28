import exceptions.RestartOrderExc;
import exceptions.RiprovaExc;

import java.util.Date;
import java.util.Scanner;

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

        int tot = quantePizze();
        System.out.println(tot);


        //Date orario = inserisciOrario(order,tot);
        //if(orario!=null) {
            //order.setOrario(orario);
            //System.out.println(orario);

            do {
                nomePizza = qualePizza();
                if(nomePizza.equals("F"))
                    break;
                num = quantePizzaSpecifica(order,nomePizza,tot-ordinate);
                System.out.println("ordinate " + num + " " + nomePizza + " (" + wolf.getMenu().get(nomePizza).getDescrizione() + ")");
                ordinate += num;
            } while(ordinate<tot);

            if(!nomePizza.equals("F")) {
                ok = wolf.inserisciDati(order);
                if (ok) {
                    wolf.recapOrdine(order);
                    System.out.println(order.getPizzeordinate().get(0).equals(order.getPizzeordinate().get(1)));
                //    chiediConferma(order,orario,tot);
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


    /*private Date inserisciOrario (Order order, int tot){
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
                    d = wolf.controllaOrario(sDate1, order, tot);          // CHECK ORARIO!!!
                    if(d==null)
                        throw new NumberFormatException();
                    else {
                        int ora = d.getHours();
                        int minuti = d.getMinutes();
                        if (!wolf.controllaApertura(ora, minuti))
                            throw new OutOfTimeExc();       //DA SISTEMARE SE SI CHIUDE ALLE 02:00
                        else if(wolf.checkInfornate(wolf.getOrarioApertura(),tot,ora,minuti,order))
                        else
                            ok = true;
                    }
                }
            } catch (RestartOrderExc e) {
                makeOrderText();
            } catch (NumberFormatException | NoSuchElementException e) {
                System.out.println("L'orario non è stato inserito correttamente. Riprovare:");
            } catch (OutOfTimeExc e) {
                System.out.println("La pizzeria è chiusa nell'orario inserito. Riprovare:");
            }
        } while(!ok);
        return d;
    }*/

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
                } else if (!(wolf.getMenu().containsKey(nomePizza)))         // qui ci vorrebbe una eccezione invece della if-else
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
                else {
                    ok = true;
                    chiediModificaPizza(order,nomePizza,num);
                }
            } catch (NumberFormatException e) {
                System.out.println("Spiacenti: inserito numero non valido. Riprovare:");
            } catch (RiprovaExc e) {
                System.out.println("Massimo numero di pizze ordinate superato. Puoi ordinare ancora " + disponibili + " pizze:");
            }
        } while(!ok);
        return num;
    }

    public void chiediModificaPizza(Order order, String nomePizza, int num){
        PizzaOrdinata p = new PizzaOrdinata(wolf.getMenu().get(nomePizza).getNome(), wolf.getMenu().get(nomePizza).getIngredienti(), wolf.getMenu().get(nomePizza).getPrezzo());
        System.out.print("Vuoi apportare modifiche alle " + num + " " + nomePizza + "?\t(S/N):  ");
        if(scan.nextLine().toUpperCase().equals("S")) {
            System.out.print("Inserisci gli ingredienti da AGGIUNGERE, separati da virgola, poi invio:\n");
            String aggiunte = scan.nextLine();
            System.out.print("Inserisci gli ingredienti da RIMUOVERE, separati da virgola, poi invio:\n");
            String rimozioni = scan.nextLine();
            order.addPizza(wolf.getMenu().get(nomePizza), aggiunte, rimozioni, num, wolf.getPREZZO_SUPPL());
        } else {
            order.addPizza(wolf.getMenu().get(nomePizza), num);
        }
    }


}


class Tester {
    public static void main(String[] args) {
        TextInterface textInterface = new TextInterface();

        textInterface.wolf.creaMenu();
        textInterface.makeOrderText();


    }
}
