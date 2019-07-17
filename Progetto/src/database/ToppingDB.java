package database;

import pizzeria.Pizza;
import pizzeria.Pizzeria;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class ToppingDB {

	private static Scanner scan = new Scanner(System.in);

	/** Consente alla pizzeria di aggiungere un topping a quelli salvati sul Database. */
	public static boolean putTopping(String nome){
		try {
			ResultSet rs1 = Database.getStatement("select * from sql2298759.Toppings");
			rs1.last();
			int prima = rs1.getRow();
			Database.insertStatement("insert into sql2298759.Toppings values ('" + nome + "');");
			ResultSet rs2 =  Database.getStatement("select * from sql2298759.Toppings");
			rs2.last();
			int dopo = rs2.getRow();
			return prima != dopo;
		} catch (SQLException sqle) {
			return false;
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
			ResultSet rs1 = Database.getStatement("select * from sql2298759.Toppings");
			rs1.last();
			int prima = rs1.getRow();
			Database.insertStatement("delete from sql2298759.Toppings where Topping = '" + rmvTopping + "';");
			ResultSet rs2 =  Database.getStatement("select * from sql2298759.Toppings");
			rs2.last();
			int dopo = rs2.getRow();
			if(prima != dopo)
				return true;
		} catch (SQLException sqle) {
			return false;
		}
		return false;
	}

	/** Consente alla pizzeria di recuperare i toppings salvati sul Database. */
	public static HashMap<String, String> getToppings(HashMap<String, String> ingredienti)  throws SQLException{
		ResultSet rs = Database.getStatement("select * from sql2298759.Toppings");
		while (rs.next()) {
			String ingrediente = rs.getString(1);
			ingredienti.put(ingrediente,ingrediente);
		}
		return ingredienti;
	}
}
