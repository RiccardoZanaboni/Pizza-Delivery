package pizzeria;

import javafx.scene.paint.Color;

import java.sql.*;

public class ToppingDB {

	public static PreparedStatement putTopping(Connection con, String nome){
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement("insert into sql7293749.Toppings values ('" + nome + "');");
		} catch(SQLException ignored){ }
		return preparedStatement;
	}

	public static PreparedStatement removeTopping(Connection con, String nome){
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement("delete from sql7293749.Toppings where Topping = '" + nome + "';");
		} catch(SQLException ignored){ }
		return preparedStatement;
	}

	public static PreparedStatement getToppingsDB(Connection con){
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement("select * from sql7293749.Toppings");
		} catch (NullPointerException | SQLException e){
			/* Chiude il programma, se non c'è connessione. */
			System.out.println(Services.colorSystemOut("\nSpiacenti: impossibile connettersi al momento.\nControllare connessione di rete.", Color.RED,true,false));
			System.exit(1);
		}
		return preparedStatement;
	}
}
