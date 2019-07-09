package textualElements;

import database.CustomerDB;
import javafx.scene.paint.Color;
import pizzeria.Customer;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;
import pizzeria.services.TimeServices;

import java.util.ArrayList;
import java.util.Scanner;

public class TextCustomerSide {
	private Scanner scan = new Scanner(System.in);

	/** Elenca tutte gli ingredienti che l'utente può scegliere, per modificare una pizza. */
	public static String possibleAddictions(Pizzeria pizzeria) {
		StringBuilder possibiliIngr = new StringBuilder();
		possibiliIngr.append(TextColorServices.colorSystemOut("\tPossibili aggiunte: ",Color.ORANGE,false,false));
		possibiliIngr.append(listPossibleAddictions(pizzeria));
		return possibiliIngr.substring(0, possibiliIngr.lastIndexOf(",")); // elimina ultima virgola
	}

	/** In interfaces.TextualInterface, elenca tutte gli ingredienti che l'utente può scegliere, per modificare una pizza.
	 * */
	public static String listPossibleAddictions(Pizzeria pizzeria) {
		StringBuilder possibiliIngr = new StringBuilder();
		int i = 0;
		for (String ingr : pizzeria.getIngredientsPizzeria().values()) {
			if (i % 10 == 0)
				possibiliIngr.append("\n\t");
			possibiliIngr.append(ingr.toLowerCase().replace("_", " ")).append(", ");
			i++;
		}
		return possibiliIngr.toString();
	}

	/** Su interfaces.TextualInterface dà il benvenuto al cliente, fornendo le informazioni essenziali della pizzeria.
	 * @param pizzeria*/
	public static String helloThere(Pizzeria pizzeria){
		String opTime = TimeServices.timeStamp(pizzeria.getOpeningToday().getHours(), pizzeria.getOpeningToday().getMinutes());
		String clTime = TimeServices.timeStamp(pizzeria.getClosingToday().getHours(), pizzeria.getClosingToday().getMinutes());
		StringBuilder hello = new StringBuilder("\n");
		hello.append(TextColorServices.colorSystemOut("\nBenvenuto!\n", Color.GREEN,true,true));
		hello.append(TextColorServices.colorSystemOut("\nPIZZERIA ", Color.ORANGE,false,false));
		hello.append(TextColorServices.colorSystemOut("\"" + pizzeria.getName() + "\"\n\t",Color.RED,true,false));
		hello.append(TextColorServices.colorSystemOut(pizzeria.getAddress(),Color.ORANGE,false,false));
		if(pizzeria.getOpeningToday().equals(pizzeria.getClosingToday()))
			hello.append(TextColorServices.colorSystemOut("\n\tOGGI CHIUSO", Color.RED, true, false));
		else {
			hello.append(TextColorServices.colorSystemOut("\n\tApertura oggi: ", Color.ORANGE, false, false));
			hello.append(TextColorServices.colorSystemOut(opTime + " - " + clTime, Color.RED, true, false));
		}
		hello.append("\n").append(TextColorServices.getLine());
		return hello.toString();
	}

	/** Da interfaces.TextualInterface, permette di stampare a video il menu completo.
	 * @param pizzeria*/
	public static String printMenu(Pizzeria pizzeria) {
		String line = TextColorServices.getLine();
		TextColorServices.paintMenuString();
		StringBuilder s = new StringBuilder();
		for (String a : pizzeria.getMenu().keySet()) {
			s.append("\n").append(pizzeria.getMenu().get(a).toString());
		}
		return s.toString() + "\n" + line;
	}

	/** In interfaces.TextualInterface.whatDoYouWant(), chiede quali siano le intenzioni del cliente per procedere. */
	public static String whatDoYouWantPossibilities(boolean isOpen){
		String intro = TextColorServices.colorSystemOut("\nEcco che cosa puoi fare:\n", Color.YELLOW,false,false);
		String con = "\t- con '";
		String newOrd = TextColorServices.colorSystemOut("N", Color.ORANGE,true,false);
		String newOrdS = "' puoi effetturare un nuovo ordine;\n";
		String last = TextColorServices.colorSystemOut("L",Color.ORANGE,true,false);
		String lastS = "' puoi visualizzare il tuo ultimo ordine;\n";
		String off = TextColorServices.colorSystemOut("M",Color.ORANGE,true,false);
		String offS = "' puoi modificare i tuoi dati;\n";
		String hist = TextColorServices.colorSystemOut("H",Color.ORANGE,true,false);
		String histS = "' puoi sapere di più sulla nostra attività;\n";
		String exit = TextColorServices.colorSystemOut("E",Color.ORANGE,true,false);
		String exitS = "' puoi uscire dalla tua area riservata.\n";

		StringBuilder string = new StringBuilder(TextColorServices.getLine());
		string.append(intro);
		if(isOpen) {
			string.append(con).append(newOrd).append(newOrdS);
		}
		string.append(con).append(last).append(lastS);
		string.append(con).append(off).append(offS);
		string.append(con).append(hist).append(histS);
		string.append(con).append(exit).append(exitS);

		return string.toString();
	}

	public void modifyAccount(Customer customer) {
		String user = customer.getUsername();
		System.out.print("\nInserisci il tuo nome: ");
		String nome = scan.nextLine();
		System.out.print("Inserisci il tuo cognome: ");
		String cognome = scan.nextLine();
		System.out.print("Inserisci il tuo indirizzo principale: ");
		String indirizzo = scan.nextLine();
		if(CustomerDB.addInfoCustomer(user,nome,cognome,indirizzo)) {
			System.out.println(TextColorServices.colorSystemOut("\nGrazie! Dati aggiornati.", Color.YELLOW, false, false));
			customer.setName(nome);
			customer.setSurname(cognome);
			customer.setAddress(indirizzo);
		} else System.out.println(TextColorServices.colorSystemOut("\nErrore nell'aggiornamento dei dati.",Color.RED,false,false));
	}

	/** In interfaces.TextualInterface, stampa a video il riepilogo dell'ordine. */
	public static String recapOrder(Order order){
		String line = TextColorServices.getLine();
		StringBuilder recap = new StringBuilder();
		recap.append(TextColorServices.colorSystemOut("ORDINE N. ", Color.RED,true,false));
		recap.append(TextColorServices.colorSystemOut(order.getOrderCode(),Color.RED,true,false));
		recap.append(TextColorServices.colorSystemOut("\nSIG.\t\t",Color.YELLOW,false,false));
		recap.append(TextColorServices.colorSystemOut(order.getCustomer().getUsername().toLowerCase(),Color.GREEN,true,false));
		recap.append(TextColorServices.colorSystemOut("\nCITOFONO:\t",Color.YELLOW,false,false));
		recap.append(TextColorServices.colorSystemOut(order.getName(),Color.GREEN,true,false));
		recap.append(TextColorServices.colorSystemOut("\nINDIRIZZO:\t",Color.YELLOW,false,false));
		recap.append(TextColorServices.colorSystemOut(order.getCustomerAddress(),Color.GREEN,true,false));
		recap.append(TextColorServices.colorSystemOut("\nORARIO:\t\t",Color.YELLOW,false,false));
		recap.append(TextColorServices.colorSystemOut(TimeServices.dateTimeStamp(order.getTime()),Color.GREEN,true,false));
		recap.append(textRecapProducts(order));
		recap.append(TextColorServices.colorSystemOut("TOTALE: € ",Color.YELLOW,true,false));
		recap.append(TextColorServices.colorSystemOut(String.valueOf(order.getTotalPrice()),Color.RED,true,false));
		return line + recap + line;
	}

	/** Restituisce una stringa con i vari prodotti, per il riepilogo. */
	public static String textRecapProducts(Order order) {		// todo: va in testuale?
		StringBuilder prodotti = new StringBuilder("\n");
		ArrayList<Pizza> elencate = new ArrayList<>();
		for (int i = 0; i < order.getNumPizze(); i++) {
			Pizza p = order.getOrderedPizze().get(i);
			int num = 0;
			boolean contains = false;
			for (Pizza pizza : elencate) {
				if (p.getName(false).equals(pizza.getName(false)) && p.getToppings().equals(pizza.getToppings())) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				elencate.add(p);
				for (int j = 0; j < order.getNumPizze(); j++) {
					if (p.getName(false).equals(order.getOrderedPizze().get(j).getName(false)) && p.getToppings().equals(order.getOrderedPizze().get(j).getToppings()))
						num++;
				}
				prodotti.append("\t€ ").append(p.getPrice()).append("  x  ");
				prodotti.append(TextColorServices.colorSystemOut(String.valueOf(num),Color.WHITE,true,false));
				prodotti.append("  ").append(TextColorServices.colorSystemOut(p.getName(true).toUpperCase(),Color.WHITE,true,false));
				prodotti.append("\t\t").append(p.getDescription()).append("\n");
			}
		}
		return prodotti.toString();
	}
}
