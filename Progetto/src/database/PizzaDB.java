package database;


import pizzeria.Pizza;

import pizzeria.services.SettleStringsServices;

import java.sql.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class PizzaDB {

	/** Aggiunge una pizza al DB.
	 * Tutto il controllo è fatto per verificare che la pizza non fosse già presente nel DB
	 * (rconfrontando le pizze prima e dopo l'inserimento della nuova pizza, verifico
	 * che il DB sia effettivamente stato modificato). */
	public static boolean putPizza(String nome, String ingred, double prezzo) {
		try {
			ResultSet rs1 = Database.getStatement("select * from sql2298759.Pizze");
			rs1.last();
			int prima = rs1.getRow();
			Database.insertStatement("insert into sql2298759.Pizze values ('" + nome.toUpperCase() + "', '" + ingred + "', '" + prezzo + "');");
			ResultSet rs2 = Database.getStatement("select * from sql2298759.Pizze");
			rs2.last();
			int dopo = rs2.getRow();
			return prima != dopo;
		} catch (SQLException e) {
			return false;
		}
	}

	/** Rimuove la pizza indicata dal DB. */
	public static boolean removePizza(String pizza){
		try{
			ResultSet rs1 = Database.getStatement("select * from sql2298759.Pizze");
			rs1.last();
			int prima = rs1.getRow();
			Database.insertStatement("delete from sql2298759.Pizze where nome = '" + pizza + "';");
			ResultSet rs2 = Database.getStatement("select * from sql2298759.Pizze");
			rs2.last();
			int dopo = rs2.getRow();
			return prima != dopo;
		} catch (SQLException e) {
			return false;
		}
	}

	/** @return tutte le pizze presenti nel DB, formattate come Hashmap. */
	public static HashMap<String, Pizza> getPizzeDB(HashMap<String, Pizza> menu) throws SQLException{
		ResultSet rs = Database.getStatement("select * from sql2298759.Pizze");
		while (rs.next()) {
			HashMap<String, String> ingr = new HashMap<>();
			String nomePizza = rs.getString(1);
			String ingrediente = rs.getString(2);
			StringTokenizer stAgg = new StringTokenizer(ingrediente);
			while (stAgg.hasMoreTokens()) {
				try {
					String ingredienteAggiuntoString = SettleStringsServices.arrangeIngredientString(stAgg); // FIXME: 19/07/2019
					ingr.put(ingredienteAggiuntoString, ingredienteAggiuntoString);
				} catch (Exception ignored) {/* continue */}
			}
			double prezzo = rs.getDouble(3);
			Pizza p = new Pizza(nomePizza, ingr, prezzo);
			menu.put(nomePizza, p);
		}
		return menu;
	}
}