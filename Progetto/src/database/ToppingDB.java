package database;

import javafx.scene.paint.Color;
import pizzeria.Pizzeria;
import pizzeria.services.TextColorServices;

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class ToppingDB {

	private static Scanner scan = new Scanner(System.in);

	public static boolean putTopping( String nome){
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

	public static void putTopping(Pizzeria pizzeria) {		// todo: va in testuale
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

	public static boolean removeTopping(String nome){
		try {
			ResultSet rs1 = Database.getStatement("select * from sql7293749.Toppings");
			rs1.last();
			int prima = rs1.getRow();
			Database.insertStatement("delete from sql7293749.Toppings where Topping = '" + nome + "';");
			ResultSet rs2 =  Database.getStatement("select * from sql7293749.Toppings");
			rs2.last();
			int dopo = rs2.getRow();
			return (prima != dopo);
		} catch (SQLException sqle) {
			return false;
		}
	}

	public static void removeToppingText(Pizzeria pizzeria) {	// todo: va in testuale
		try {
			System.out.print(TextColorServices.colorSystemOut("Inserisci il nome dell'ingrediente da rimuovere:\t", Color.YELLOW, false, false));
			String name = scan.nextLine().toUpperCase();
			if (!removeTopping(name))
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

	public static HashMap<String, String> getToppings(HashMap<String, String> ingredienti)  throws SQLException{
		ResultSet rs = Database.getStatement("select * from sql7293749.Toppings");
		while (rs.next()) {
			String ingrediente = rs.getString(1);
			ingredienti.put(ingrediente,ingrediente);
		}
		return ingredienti;
	}
}
