package database;

import javafx.scene.paint.Color;
import pizzeria.*;
import pizzeria.services.TextColorServices;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static database.CustomerDB.getInfoCustomerFromMailAddress;

public class Database {
	private static Connection con;

	public static void openDatabase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7293749?autoReconnect=true&useSSL=false", "sql7293749", "geZxKTlyi1");
		} catch (SQLException sqle) {
			missingConnection();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}

	/** Chiude il programma, se la connessione alla rete viene a mancare. */
	public static void missingConnection(){
		System.out.println(TextColorServices.colorSystemOut("\nSpiacenti: impossibile connettersi al momento.\nControllare connessione di rete.", Color.RED,true,false));
		System.exit(1);
	}

	public static void insertStatement(String query){
		PreparedStatement preparedStatement;
		try {
			preparedStatement= con.prepareStatement(query);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet getStatement(String query){
		ResultSet rs = null;
		try {
			Statement statement = con.createStatement();
			rs = statement.executeQuery(query);
		} catch (SQLException sqle){
			Database.missingConnection();
		}
		return rs;
	}

	public static void addNewVoidOrderToDB(Order order) {
		try {
			DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dataString = dateFormatYMD.format(new Date());
			java.sql.Timestamp data = java.sql.Timestamp.valueOf(dataString);
			String requestSql = "insert into sql7293749.Orders (orderID, username, address, citofono, quantity, date) VALUES (?,?,?,?,?,?)";
			PreparedStatement preparedStatement = con.prepareStatement(requestSql);
			preparedStatement.setString(1, order.getOrderCode());
			preparedStatement.setString(2, "");
			preparedStatement.setString(3, "");
			preparedStatement.setString(4, "");
			preparedStatement.setInt(5, 0);
			preparedStatement.setTimestamp(6, data);		// mette il Time attuale, ma non è un problema
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int countOrdersDB() {
		String requestSql = "select count(*) from sql7293749.Orders";
		int num = -1;
		try {
			PreparedStatement preparedStatement = con.prepareStatement(requestSql);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			num = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}


	public static Date getLastUpdate() {
		Date last = null;
		String requestSql = "select * from sql7293749.LastUpdate";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement(requestSql);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			last = rs.getDate(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return last;
	}

	public static void setLastUpdate(java.util.Date oldDate) {
		DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd");
		String newDateString = dateFormatYMD.format(new Date());
		String oldDateString = dateFormatYMD.format(oldDate);
		java.sql.Date newSQLData = java.sql.Date.valueOf(newDateString);
		java.sql.Date oldSQLData = java.sql.Date.valueOf(oldDateString);
		String requestSql = "update sql7293749.LastUpdate set Date = '" + newSQLData + "' where Date = '" + oldSQLData + "' ";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(requestSql);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean checkMail(String mail){
		return (getInfoCustomerFromMailAddress(mail,1) != null);
	}
}