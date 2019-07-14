package database;

import javafx.scene.paint.Color;
import pizzeria.Pizza;
import pizzeria.Pizzeria;
import pizzeria.services.TextColorServices;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class ToppingDB {

	private static Scanner scan = new Scanner(System.in);

	/** Consente alla pizzeria di aggiungere un topping a quelli salvati sul Database. */
	public static boolean putTopping(String nome){
		try {
			ResultSet rs1 = Database.getStatement("select * from sql7293749.Toppings");
			rs1.last();
			int prima = rs1.getRow();
			Database.insertStatement("insert into sql7293749.Toppings values ('" + nome + "');");
			ResultSet rs2 =  Database.getStatement("select * from sql7293749.Toppings");
			rs2.last();
			int dopo = rs2.getRow();
			return prima != dopo;
		} catch (SQLException sqle) {
			return false;
		}
	}

	/** Consente alla pizzeria di aggiungere, tramite interfaccia testuale, un topping a quelli salvati sul Database. */
	public static void putToppingText(Pizzeria pizzeria) {
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

	/** Consente alla pizzeria di rimuovere un topping da quelli salvati sul Database,
	 * e le pizze che lo contengono. */
	public static boolean removeTopping(Pizzeria pizzeria, String rmvTopping){
		try {
			/* elimina tutte le pizze che contengono quell'ingrediente */
			Iterator<String> iter = pizzeria.getMenu().keySet().iterator();
			while (iter.hasNext()){
				String pizzaName = iter.next();
				Pizza pizza = pizzeria.getMenu().get(pizzaName);
				for (String topping: pizza.getToppings().values()) {
					if(topping.equals(rmvTopping)){
						iter.remove();
						PizzaDB.removePizza(pizza.getName(false));
					}
				}
			}
			ResultSet rs1 = Database.getStatement("select * from sql7293749.Toppings");
			rs1.last();
			int prima = rs1.getRow();
			Database.insertStatement("delete from sql7293749.Toppings where Topping = '" + rmvTopping + "';");
			ResultSet rs2 =  Database.getStatement("select * from sql7293749.Toppings");
			rs2.last();
			int dopo = rs2.getRow();
			if(prima != dopo)
				return true;
		} catch (SQLException sqle) {
			return false;
		}
		return false;
	}

	/** Consente alla pizzeria di rimuovere, tramite interfaccia testuale, un topping da quelli salvati sul Database. */
	public static void removeToppingText(Pizzeria pizzeria) {
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

	/** Consente alla pizzeria di recuperare i toppings salvati sul Database. */
	public static HashMap<String, String> getToppings(HashMap<String, String> ingredienti)  throws SQLException{
		ResultSet rs = Database.getStatement("select * from sql7293749.Toppings");
		while (rs.next()) {
			String ingrediente = rs.getString(1);
			ingredienti.put(ingrediente,ingrediente);
		}
		return ingredienti;
	}
}
