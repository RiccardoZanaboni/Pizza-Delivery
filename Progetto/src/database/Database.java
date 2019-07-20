package database;

import javafx.scene.paint.Color;
import pizzeria.Order;
import pizzeria.services.TextColorServices;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static database.CustomerDB.getInfoCustomerFromMailAddress;

/** ATTENZIONE: Per poter utilizzare il Database, è necessario estrarre il contenuto di "ExternalLibraries.zip"
 * (tra i file del progetto) in una vostra cartella locale;
 * nel contenuto vi è il file: mysql-connector-java-5.1.42-bin.jar.
 * A questo punto, su Intellij, cliccare in alto File/Project_Structure/Libraries/+/
 * [qui seleziono il percorso del file].
 * */
public class Database {
	private static Connection con;

	/** Apre una connessione con il Database (se c'è connessione). */
	public static void openDatabase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://sql2.freesqldatabase.com:3306/sql2298759?autoReconnect=true&useSSL=false", "sql2298759", "pM7!mR1*");
		} catch (SQLException sqle) {
			String err = TextColorServices.colorSystemOut("\nSpiacenti: impossibile connettersi al momento.\nControllare connessione di rete.", Color.RED,true,false);
			criticalError(err);
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}

	/** Chiude il programma, se c'è un errore critico. */
	public static void criticalError(String err){
		System.out.println(err);
		System.exit(1);
	}

	/** Esegue la query di inserimento nel DB. */
	static boolean insertStatement(String query){
		PreparedStatement preparedStatement;
		try {
			preparedStatement= con.prepareStatement(query);
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/** Esegue la query di recupero dati dal DB. */
	static ResultSet getStatement(String query){
		ResultSet rs = null;
		try {
			Statement statement = con.createStatement();
			rs = statement.executeQuery(query);
		} catch (SQLException sqle){
			String err = TextColorServices.colorSystemOut("\nErrore critico per SQL.\nIl programma viene terminato.\n",Color.RED,false,false);
			criticalError(err);
		}
		return rs;
	}

	/** Aggiunge al DB un nuovo ordine, costituito al momento soltanto dal codice e una data fittizia. */
	public static void addNewVoidOrderToDB(Order order) {
		try {
			DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dataString = dateFormatYMD.format(new Date());
			java.sql.Timestamp data = java.sql.Timestamp.valueOf(dataString);
			String requestSql = "insert into sql2298759.Orders (orderID, username, address, citofono, quantity, date) VALUES (?,?,?,?,?,?)";
			PreparedStatement preparedStatement = con.prepareStatement(requestSql);
			preparedStatement.setString(1, order.getOrderCode());
			preparedStatement.setString(2, "");
			preparedStatement.setString(3, "");
			preparedStatement.setString(4, "");
			preparedStatement.setInt(5, 0);
			preparedStatement.setTimestamp(6, data);	/* per il momento inserisce il Time attuale */
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/** Controlla che l'indirizzo e-amil inserito sia effettivamente nella tabella degli utenti. */
	public static boolean checkMail(String mail){
		return (getInfoCustomerFromMailAddress(mail,1) != null);
	}
}