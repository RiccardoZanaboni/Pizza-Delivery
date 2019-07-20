package textualElements;

import database.CustomerDB;
import enums.OpeningPossibilities;
import interfaces.TextualInterface;
import javafx.scene.paint.Color;
import pizzeria.Customer;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;
import pizzeria.services.PizzeriaServices;
import pizzeria.services.SettleStringsServices;
import pizzeria.services.TextColorServices;
import pizzeria.services.TimeServices;
import pizzeria.services.sendMail.SendJavaMail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static enums.OpeningPossibilities.CLOSING;

/** Gestisce ciò che il cliente può fare, attraverso interfaccia testuale, dopo essersi autenticato. */
public class TextCustomerSide {
	private Scanner scan = new Scanner(System.in);

	/** Controlla, in base all'orario, cosa il cliente può effettivamente fare e lo comunica. */
	public void whatDoYouWant(Customer customer, Pizzeria pizzeria) throws SQLException {
		String risposta;
		OpeningPossibilities isOpen = TimeServices.checkTimeOrder(pizzeria.getOpeningToday(), pizzeria.getClosingToday());
		/* se la pizzeria è aperta */
		if (isOpen == OpeningPossibilities.OPEN) {
			System.out.println(whatDoYouWantPossibilities(true));
			System.out.print(TextColorServices.colorSystemOut("\t>> ", Color.YELLOW, false, false));
			risposta = scan.nextLine().toUpperCase();
			whatDoYouWantAnswers(true, risposta, customer,pizzeria);
		} else {
			/* se la pizzeria è chiusa o in chiusura */
			String chiusura;
			if (isOpen == CLOSING)
				chiusura = "\nAttenzione: la pizzeria è in chiusura. Impossibile effettuare ordini al momento.";
			else
				chiusura = "\nAttenzione: la pizzeria per oggi è chiusa. Impossibile effettuare ordini al momento.";
			System.out.println(TextColorServices.colorSystemOut(chiusura, Color.RED, false, false));
			/* stampa a video le varie possibilità */
			System.out.println(whatDoYouWantPossibilities(false));
			System.out.print(TextColorServices.colorSystemOut("\t>> ", Color.YELLOW, false, false));
			risposta = scan.nextLine().toUpperCase();
			whatDoYouWantAnswers(false, risposta, customer,pizzeria);
		}
	}

	/** Gestisce le possibili azioni che il cliente decide di intraprendere. */
	private void whatDoYouWantAnswers(boolean isOpen, String risposta, Customer customer, Pizzeria pizzeria) throws SQLException {
		switch (risposta){
			case "L":
				/* Visualizzare l'ultimo ordine effettuato */
				Order last = PizzeriaServices.CustomerLastOrder(customer,pizzeria.getOrders());
				if(last != null){
					System.out.println("\n" + customer.getUsername() + ", questo è l'ultimo ordine che hai effettuato:");
					System.out.println(TextCustomerSide.recapOrder(last));
				} else System.out.println(TextColorServices.colorSystemOut("\n" + customer.getUsername() + ", non hai ancora effettuato nessun ordine!\n",Color.RED,false,false));
				whatDoYouWant(customer,pizzeria);
				break;
			case "M":
				/* Modificare i propri dati personali */
				TextCustomerSide customerSide = new TextCustomerSide();
				customerSide.modifyAccount(customer);
				whatDoYouWant(customer,pizzeria);
				break;
			case "H":
				/* Visualizzare la History della Pizzeria */
				System.out.println(PizzeriaServices.getHistory(false));
				whatDoYouWant(customer,pizzeria);
				break;
			case "E":
				/* Effettuare il logout */
				System.out.println(TextColorServices.colorSystemOut("Uscendo dall'area riservata...\n", Color.YELLOW, false, false));
				TextualInterface textualInterface = new TextualInterface();
				textualInterface.askAccess();
				break;
			default:
				if(isOpen && risposta.equals("N")){
					/* Effettuare un nuovo ordine */
					TextNewOrder newOrder = new TextNewOrder();
					newOrder.makeOrderText(customer, pizzeria, new TextCustomerSide());
				} else {
					/* Inserito carattere non valido */
					System.out.println(TextColorServices.colorSystemOut("\nSpiacenti: inserito carattere non valido. Riprovare:", Color.RED, false, false));
					whatDoYouWant(customer,pizzeria);
				} break;
		}
	}

	/** Inizializza l'elenco di tutti gli ingredienti che l'utente può scegliere, per modificare una pizza. */
	static String possibleAddictions(Pizzeria pizzeria) {
		StringBuilder possibiliIngr = new StringBuilder();
		possibiliIngr.append(TextColorServices.colorSystemOut("\tPossibili aggiunte: ",Color.ORANGE,false,false));
		possibiliIngr.append(listPossibleAddictions(pizzeria));
		return possibiliIngr.substring(0, possibiliIngr.lastIndexOf(",")); // elimina ultima virgola
	}

	/** Elenca tutte gli ingredienti che l'utente può scegliere, per modificare una pizza. */
	private static String listPossibleAddictions(Pizzeria pizzeria) {
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

	/** Permette di stampare a video il menu completo. */
	static String printMenu(Pizzeria pizzeria) {
		String line = TextColorServices.getLine();
		TextColorServices.paintMenuString();
		StringBuilder s = new StringBuilder();
		for (String a : pizzeria.getMenu().keySet()) {
			s.append("\n").append(pizzeria.getMenu().get(a).toString());
		}
		return s.toString() + "\n" + line;
	}

	/** Specifica quali siano le intenzioni del cliente per procedere. */
	private String whatDoYouWantPossibilities(boolean isOpen){
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

	/** Consente al cliente di modificare i propri dati salvati nel DB. */
	private void modifyAccount(Customer customer) {
		String user = customer.getUsername();
		System.out.print("\nInserisci il tuo nome: ");
		String nome = scan.nextLine();
		System.out.print("Inserisci il tuo cognome: ");
		String cognome = scan.nextLine();
		System.out.print("Inserisci il tuo indirizzo di casa: ");
		String indirizzo = scan.nextLine();
        System.out.print("Inserisci il tuo indirizzo e-mail: ");
        String newMail = scan.nextLine();
		SendJavaMail javaMail = new SendJavaMail();
		if(newMail.equals("") || newMail.equals(CustomerDB.getCustomerFromUsername(customer.getUsername(),3))) {	/* se la mail non è stata variata */
			newMail = CustomerDB.getCustomerFromUsername(customer.getUsername(),3);
		}else{
			System.out.print("Conferma il tuo nuovo indirizzo e-mail: ");
			String confirmNewMail = scan.nextLine();
			if(newMail.equals(confirmNewMail))
				javaMail.changeMailAddress(customer,newMail);
			else {
				String err = TextColorServices.colorSystemOut("Attenzione: l'indirizzo e-mail è rimasto invariato.",Color.YELLOW,false,false);
				System.out.println(err);
			}
		}
		if(CustomerDB.addInfoCustomer(user,nome,cognome,indirizzo,newMail))
			System.out.println(TextColorServices.colorSystemOut("\nGrazie! Dati aggiornati.", Color.YELLOW, false, false));
		else
			System.out.println(TextColorServices.colorSystemOut("\nErrore nell'aggiornamento dei dati.",Color.RED,false,false));
	}

	/** Stampa a video il riepilogo dell'ordine. */
	static String recapOrder(Order order){
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
		recap.append(TextColorServices.colorSystemOut(order.getTotalPrice(),Color.RED,true,false));
		return line + recap + line;
	}

	/** Restituisce una stringa con i vari prodotti, per il riepilogo. */
	private static String textRecapProducts(Order order) {
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
				prodotti.append("\t€ ").append(SettleStringsServices.settlePriceDecimal(p.getPrice())).append("  x  ");
				prodotti.append(TextColorServices.colorSystemOut(String.valueOf(num),Color.WHITE,true,false));
				prodotti.append("  ").append(TextColorServices.colorSystemOut(p.getName(true).toUpperCase(),Color.WHITE,true,false));
				prodotti.append("\t\t").append(p.getDescription()).append("\n");
			}
		}
		return prodotti.toString();
	}
}
