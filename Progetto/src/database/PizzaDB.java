package database;

import exceptions.TryAgainExc;
import javafx.scene.paint.Color;
import pizzeria.Pizza;
import pizzeria.Pizzeria;
import pizzeria.services.SettleStringsServices;
import textualElements.TextColorServices;
import textualElements.TextCustomerSide;

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PizzaDB {
	private static Scanner scan = new Scanner(System.in);

	/** Tutto il controllo è fatto per verificare che la pizza non fosse già presente nel DB
	 * (richiamando il getPizzeDB sia prima che dopo l'inserimento della nuova pizza, verifico
	 * che il DB sia effettivamente stato modificato). */
	public static boolean putPizza( String nome, String ingred, double prezzo) {
		try {
			ResultSet rs1 = Database.getStatement("select * from sql7293749.Pizze");
			rs1.last();
			int prima = rs1.getRow();
			Database.insertStatement("insert into sql7293749.Pizze values ('" + nome + "', '" + ingred + "', '" + prezzo + "');");
			ResultSet rs2 = Database.getStatement("select * from sql7293749.Pizze");
			rs2.last();
			int dopo = rs2.getRow();
			return prima != dopo;
		} catch (SQLException e) {
			return false;
		}
	}

	public static boolean removePizza(String nome){
		try{
			ResultSet rs1 = Database.getStatement("select * from sql7293749.Pizze");
			rs1.last();
			int prima = rs1.getRow();
			Database.insertStatement("delete from sql7293749.Pizze where nome = '" + nome + "';");
			ResultSet rs2 = Database.getStatement("select * from sql7293749.Pizze");
			rs2.last();
			int dopo = rs2.getRow();
			return prima != dopo;
		} catch (SQLException e) {
			return false;
		}
	}

	public static HashMap<String, Pizza> getPizzeDB(HashMap<String, Pizza> menu) throws SQLException{
		ResultSet rs= Database.getStatement("select * from sql7293749.Pizze");
		while (rs.next()) {
			HashMap<String, String> ingr = new HashMap<>();
			String nomePizza = rs.getString(1);
			String ingrediente = rs.getString(2);
			StringTokenizer stAgg = new StringTokenizer(ingrediente);
			while (stAgg.hasMoreTokens()) {
				try {
					String ingredienteAggiuntoString = SettleStringsServices.arrangeIngredientString(stAgg);
					ingr.put(ingredienteAggiuntoString, ingredienteAggiuntoString);
				} catch (Exception ignored) {
					/* continue */
				}
			}
			double prezzo = rs.getDouble(3);
			Pizza p = new Pizza(nomePizza, ingr, prezzo);
			menu.put(nomePizza, p);
		}
		return menu;
	}
	/**
	 * Consente alla pizzeria di aggiungere una pizza al Menu
	 * che è salvato sul Database.
	 */
	public static void putPizza(Pizzeria pizzeria){
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

	public static void removePizzaText(Pizzeria pizzeria) {
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

}