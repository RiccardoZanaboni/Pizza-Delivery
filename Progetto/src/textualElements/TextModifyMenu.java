package textualElements;

import exceptions.TryAgainExc;
import interfaces.TextualInterface;
import javafx.scene.paint.Color;
import pizzeria.Pizza;
import pizzeria.Pizzeria;
import pizzeria.services.SettleStringsServices;
import pizzeria.services.TextColorServices;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

import static database.PizzaDB.putPizza;
import static database.PizzaDB.removePizza;
import static database.ToppingDB.putTopping;
import static database.ToppingDB.removeTopping;

/**
 * Contiene tutti i metodi necessari alla pizzeria per effettuare una modifica al menu, da interfaccia testuale.
 * Ogni metodo richiama un metodo nella corrispondente classe in "database" (PizzaDB, ToppingDB) che effettua
 * le modifiche richieste al Database.
 * */
class TextModifyMenu {
	private static Scanner scan = new Scanner(System.in);

	/** Consente alla pizzeria di aggiungere una pizza al Menu che è salvato sul Database. */
	static void putPizzaText(Pizzeria pizzeria){
		HashMap<String,String> ingredMap = new HashMap<>();
		try {
			System.out.println(TextColorServices.colorSystemOut("Inserire il nome della pizza da aggiungere:\t", Color.YELLOW, false, false));
			String name = scan.nextLine().toUpperCase();
			String descriz;
			StringBuilder descrizCorretta = new StringBuilder();
			String adding = TextColorServices.colorSystemOut("Inserire gli ingredienti da aggiungere, separati da virgola, poi invio:", Color.YELLOW, false, false);
			System.out.println(adding);
			System.out.println(TextCustomerSide.possibleAddictions(pizzeria));
			descriz = scan.nextLine().toUpperCase();
			StringTokenizer st = new StringTokenizer(descriz,",");
			while (st.hasMoreTokens()) {
				String ingr = SettleStringsServices.arrangeIngredientString(st);
				if(pizzeria.getIngredientsPizzeria().containsKey(ingr) && !ingredMap.containsKey(ingr)) {
					ingredMap.put(ingr, ingr);
					descrizCorretta.append(ingr).append(",");
				}
			}
			if(descrizCorretta.toString().length()>0) {
				descriz = descrizCorretta.toString().substring(0, descrizCorretta.toString().length() - 1);    // toglie l'ultima virgola
			} else {
				descriz = "ERR";
			}
			System.out.print(TextColorServices.colorSystemOut("Inserire il prezzo della nuova pizza (usa il punto per i decimali):\t", Color.YELLOW, false, false));
			double prezzo = Double.parseDouble(scan.nextLine());

			if(name.length() == 0 || pizzeria.getMenu().containsKey(name) || descriz.equals("ERR") || prezzo <= 0){
				throw new TryAgainExc();
			}
			if (putPizza(name, descriz, prezzo)) {
				pizzeria.getMenu().put(name, new Pizza(name,ingredMap,prezzo));
				String ok = name + " (" + descriz.toLowerCase() + ") aggiunta correttamente.";
				System.out.println(TextColorServices.colorSystemOut(ok,Color.YELLOW,false,false));
			} else {
				throw new SQLException();
			}
		} catch (TryAgainExc | NumberFormatException e) {
			String err = "Errore nell'inserimento dei dati della pizza.";
			System.out.println(TextColorServices.colorSystemOut(err, Color.RED, false, false));
		} catch (SQLException sqle) {
			String err = "Errore nell'inserimento della pizza nel Database.";
			System.out.println(TextColorServices.colorSystemOut(err, Color.RED, false, false));
		}
	}

	/** Consente alla pizzeria di rimuovere una pizza dal Menu salvato sul Database. */
	static void removePizzaText(Pizzeria pizzeria) {
		try {
			System.out.print(TextColorServices.colorSystemOut("Inserisci il nome della pizza da rimuovere:\t", Color.YELLOW, false, false));
			String name = scan.nextLine().toUpperCase();
			if (!removePizza(name))
				throw new SQLException();
			else{
				pizzeria.getMenu().remove(name);
				String ok = name + " rimossa correttamente.";
				System.out.println(TextColorServices.colorSystemOut(ok,Color.YELLOW,false,false));
			}
		} catch (SQLException sqle) {
			String err = "Errore nella rimozione della pizza dal Database.";
			System.out.println(TextColorServices.colorSystemOut(err, Color.RED, false, false));
		}
	}

	/** Consente alla pizzeria di aggiungere, tramite interfaccia testuale, un topping a quelli salvati sul Database. */
	static void putToppingText(Pizzeria pizzeria) {
		try {
			System.out.print(TextColorServices.colorSystemOut("Inserisci il nome del nuovo ingrediente:\t", Color.YELLOW, false, false));
			String name = scan.nextLine().toUpperCase();
			if (name.length() == 0 || !putTopping(name))
				throw new Exception();
			else{
				pizzeria.getIngredientsPizzeria().put(name,name);
				String ok = name + " aggiunto correttamente.";
				System.out.println(TextColorServices.colorSystemOut(ok,Color.YELLOW,false,false));
			}
		} catch (Exception e) {
			String err = "Errore nell'aggiunta dell'ingrediente al Database.";
			System.out.println(TextColorServices.colorSystemOut(err, Color.RED, false, false));
		}
	}

	/** Consente alla pizzeria di rimuovere, tramite interfaccia testuale, un topping da quelli salvati sul Database. */
	static void removeToppingText(Pizzeria pizzeria) {
		try {
			System.out.print(TextColorServices.colorSystemOut("Inserisci il nome dell'ingrediente da rimuovere:\t", Color.YELLOW, false, false));
			String name = scan.nextLine().toUpperCase();
			if (!removeTopping(pizzeria,name))
				throw new SQLException();
			else{
				pizzeria.getMenu().remove(name);
				String ok = name + " rimosso correttamente.";
				System.out.println(TextColorServices.colorSystemOut(ok,Color.YELLOW,false,false));
			}
		} catch (SQLException sqle) {
			String err = "Errore nella rimozione dell'ingrediente dal Database.";
			System.out.println(TextColorServices.colorSystemOut(err, Color.RED, false, false));
		}
	}
}
