package services;

import javafx.scene.paint.Color;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;

import java.util.ArrayList;

public class TextualServices {

	/** In interfaces.TextualInterface, elenca tutte gli ingredienti che l'utente può scegliere, per modificare una pizza.
	 * */
	public static String possibleAddictions(Pizzeria pizzeria) {
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
		hello.append(TextualColorServices.colorSystemOut("\nBenvenuto!\n", Color.GREEN,true,true));
		hello.append(TextualColorServices.colorSystemOut("\nPIZZERIA ", Color.ORANGE,false,false));
		hello.append(TextualColorServices.colorSystemOut("\"" + pizzeria.getName() + "\"\n\t",Color.RED,true,false));
		hello.append(TextualColorServices.colorSystemOut(pizzeria.getAddress(),Color.ORANGE,false,false));
		if(pizzeria.getOpeningToday().equals(pizzeria.getClosingToday()))
			hello.append(TextualColorServices.colorSystemOut("\n\tOGGI CHIUSO", Color.RED, true, false));
		else {
			hello.append(TextualColorServices.colorSystemOut("\n\tApertura oggi: ", Color.ORANGE, false, false));
			hello.append(TextualColorServices.colorSystemOut(opTime + " - " + clTime, Color.RED, true, false));
		}
		hello.append("\n").append(TextualColorServices.getLine());
		return hello.toString();
	}

	/** Da interfaces.TextualInterface, permette di stampare a video il menu completo.
	 * @param pizzeria*/
	public static String printMenu(Pizzeria pizzeria) {
		String line = TextualColorServices.getLine();
		TextualColorServices.paintMenuString();
		StringBuilder s = new StringBuilder();
		for (String a : pizzeria.getMenu().keySet()) {
			s.append("\n").append(pizzeria.getMenu().get(a).toString());
		}
		return s.toString() + "\n" + line;
	}

	/** In interfaces.TextualInterface.whatDoYouWant(), chiede quali siano le intenzioni del cliente per procedere. */
	public static String whatDoYouWantPossibilities(boolean isOpen){
		String intro = TextualColorServices.colorSystemOut("\nEcco che cosa puoi fare:\n", Color.YELLOW,false,false);
		String con = "\t- con '";
		String newOrd = TextualColorServices.colorSystemOut("N", Color.ORANGE,true,false);
		String newOrdS = "' puoi effetturare un nuovo ordine;\n";
		String last = TextualColorServices.colorSystemOut("L",Color.ORANGE,true,false);
		String lastS = "' puoi visualizzare il tuo ultimo ordine;\n";
		String off = TextualColorServices.colorSystemOut("M",Color.ORANGE,true,false);
		String offS = "' puoi modificare i tuoi dati;\n";
		String hist = TextualColorServices.colorSystemOut("H",Color.ORANGE,true,false);
		String histS = "' puoi sapere di più sulla nostra attività;\n";
		String exit = TextualColorServices.colorSystemOut("E",Color.ORANGE,true,false);
		String exitS = "' puoi uscire dalla tua area riservata.\n";

		StringBuilder string = new StringBuilder(TextualColorServices.getLine());
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

	/** In interfaces.TextualInterface.whatDoYouWant(), chiede quali siano le intenzioni della pizzeria, per procedere. */
	public static String whatDoesPizzeriaWantPossibilities(){
		String intro = TextualColorServices.colorSystemOut("\nPizzeria, ecco che cosa puoi fare:\n",Color.YELLOW,false,false);
		String con = "\t- con '";
		String ordini = TextualColorServices.colorSystemOut("V", Color.ORANGE,true,false);
		String ordiniS = "' puoi visualizzare l'elenco degli ordini da evadere;\n";
		String modMenu = TextualColorServices.colorSystemOut("M",Color.ORANGE,true,false);
		String modMenuS = "' puoi modificare voci del menu;\n";
		String mail = TextualColorServices.colorSystemOut("S",Color.ORANGE,true,false);
		String mailS = "' puoi inviare una mail ad un utente;\n";
		String exit = TextualColorServices.colorSystemOut("E",Color.ORANGE,true,false);
		String exitS = "' puoi uscire dalla tua area privilegiata.\n";

		return (TextualColorServices.getLine() + intro + con + ordini + ordiniS + con + modMenu + modMenuS
				+ con + mail + mailS + con + exit + exitS);
	}

	public static String textModifyMenuPossibilities() {
		String intro = TextualColorServices.colorSystemOut("\nQuesto è ciò che puoi fare:\n",Color.YELLOW,false,false);
		String con = "\t- con '";
		String add = TextualColorServices.colorSystemOut("A", Color.ORANGE,true,false);
		String addS = "' puoi aggiungere una pizza al menu;\n";
		String rmv = TextualColorServices.colorSystemOut("R",Color.ORANGE,true,false);
		String rmvS = "' puoi rimuovere una pizza dal menu;\n";
		String addIng = TextualColorServices.colorSystemOut("AI",Color.ORANGE,true,false);
		String addIngS = "' puoi aggiungere un ingrediente possibile;\n";
		String rmvIng = TextualColorServices.colorSystemOut("RI",Color.ORANGE,true,false);
		String rmvIngS = "' puoi rimuovere un ingrediente da quelli possibili;\n";
		String ret = TextualColorServices.colorSystemOut("B",Color.ORANGE,true,false);
		String retS = "' puoi tornare indietro.\n";

		return (intro + con + add + addS + con + rmv + rmvS + con + addIng + addIngS + con + rmvIng + rmvIngS
				+ con + ret + retS);
	}

	/** In interfaces.TextualInterface, stampa a video il riepilogo dell'ordine. */
	public static String recapOrder(Order order){
		String line = TextualColorServices.getLine();
		StringBuilder recap = new StringBuilder();
		recap.append(TextualColorServices.colorSystemOut("ORDINE N. ", Color.RED,true,false));
		recap.append(TextualColorServices.colorSystemOut(order.getOrderCode(),Color.RED,true,false));
		recap.append(TextualColorServices.colorSystemOut("\nSIG.\t\t",Color.YELLOW,false,false));
		recap.append(TextualColorServices.colorSystemOut(order.getCustomer().getUsername().toLowerCase(),Color.GREEN,true,false));
		recap.append(TextualColorServices.colorSystemOut("\nCITOFONO:\t",Color.YELLOW,false,false));
		recap.append(TextualColorServices.colorSystemOut(order.getName(),Color.GREEN,true,false));
		recap.append(TextualColorServices.colorSystemOut("\nINDIRIZZO:\t",Color.YELLOW,false,false));
		recap.append(TextualColorServices.colorSystemOut(order.getCustomerAddress(),Color.GREEN,true,false));
		recap.append(TextualColorServices.colorSystemOut("\nORARIO:\t\t",Color.YELLOW,false,false));
		recap.append(TextualColorServices.colorSystemOut(TimeServices.dateTimeStamp(order.getTime()),Color.GREEN,true,false));
		recap.append(textRecapProducts(order));
		recap.append(TextualColorServices.colorSystemOut("TOTALE: € ",Color.YELLOW,true,false));
		recap.append(TextualColorServices.colorSystemOut(String.valueOf(order.getTotalPrice()),Color.RED,true,false));
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
				prodotti.append(TextualColorServices.colorSystemOut(String.valueOf(num),Color.WHITE,true,false));
				prodotti.append("  ").append(TextualColorServices.colorSystemOut(p.getName(true).toUpperCase(),Color.WHITE,true,false));
				prodotti.append("\t\t").append(p.getDescription()).append("\n");
			}
		}
		return prodotti.toString();
	}
}
