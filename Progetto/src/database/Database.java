package database;

import interfaces.TextualInterface;
import exceptions.TryAgainExc;
import javafx.scene.paint.Color;
import pizzeria.*;
import services.PizzeriaServices;
import services.SettleStringsServices;
import services.TextualColorServices;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Database {
	private static Connection con;
	private static Scanner scan = new Scanner(System.in);

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
		System.out.println(TextualColorServices.colorSystemOut("\nSpiacenti: impossibile connettersi al momento.\nControllare connessione di rete.", Color.RED,true,false));
		System.exit(1);
	}

	/** Tutto il controllo è fatto per verificare che la pizza non fosse già presente nel DB
	 * (richiamando il getPizzeDB sia prima che dopo l'inserimento della nuova pizza, verifico
	 * che il DB sia effettivamente stato modificato). */
	public static boolean putPizza(String name, String ingred, double prezzo) {
		try {
			ResultSet rs1 = PizzaDB.getPizzeDB(con).executeQuery();
			rs1.last();
			int prima = rs1.getRow();
			PizzaDB.putPizza(con, name.toUpperCase(), ingred.toUpperCase(), prezzo).execute();
			ResultSet rs2 = PizzaDB.getPizzeDB(con).executeQuery();
			rs2.last();
			int dopo = rs2.getRow();
			return prima != dopo;
		} catch (SQLException sqle) {
			return false;
		}
	}

	public static boolean putTopping(String name) {
		try {
			ResultSet rs1 = ToppingDB.getToppingsDB(con).executeQuery();
			rs1.last();
			int prima = rs1.getRow();
			ToppingDB.putTopping(con, name.toUpperCase()).execute();
			ResultSet rs2 = ToppingDB.getToppingsDB(con).executeQuery();
			rs2.last();
			int dopo = rs2.getRow();
			return prima != dopo;
		} catch (SQLException sqle) {
			return false;
		}
	}

	public static void putTopping(Pizzeria pizzeria) {		// todo: va in testuale
		try {
			System.out.print(TextualColorServices.colorSystemOut("Inserisci il nome del nuovo ingrediente:\t", Color.YELLOW, false, false));
			String name = scan.nextLine().toUpperCase();
			if (name.length() == 0 || !putTopping(name))
				throw new Exception();
			else{
				pizzeria.getIngredientsPizzeria().put(name,name);
				String ok = name + " aggiunto correttamente.";
				System.out.println(TextualColorServices.colorSystemOut(ok,Color.YELLOW,false,false));
			}
		} catch (Exception e) {
			String err = "Errore nell'aggiunta dell'ingrediente al Database.";
			System.out.println(TextualColorServices.colorSystemOut(err, Color.RED, false, false));
		}
	}


	public static boolean removePizza(String name) {
		try {
			ResultSet rs1 = PizzaDB.getPizzeDB(con).executeQuery();
			rs1.last();
			int prima = rs1.getRow();
			PizzaDB.removePizza(con, name.toUpperCase()).execute();
			ResultSet rs2 = PizzaDB.getPizzeDB(con).executeQuery();
			rs2.last();
			int dopo = rs2.getRow();
			return prima != dopo;
		} catch (SQLException sqle) {
			return false;
		}
	}

	public static boolean removeTopping(String name) {
		try {
			ResultSet rs1 = ToppingDB.getToppingsDB(con).executeQuery();
			rs1.last();
			int prima = rs1.getRow();
			ToppingDB.removeTopping(con, name.toUpperCase()).execute();
			ResultSet rs2 = ToppingDB.getToppingsDB(con).executeQuery();
			rs2.last();
			int dopo = rs2.getRow();
			return (prima != dopo);
		} catch (SQLException sqle) {
			return false;
		}
	}

	public static void removePizzaText(Pizzeria pizzeria) {		// todo: va in testuale
		try {
			System.out.print(TextualColorServices.colorSystemOut("Inserisci il nome della pizza da rimuovere:\t", Color.YELLOW, false, false));
			String name = scan.nextLine().toUpperCase();
			if (!removePizza(name))
				throw new SQLException();
			else{
				pizzeria.getMenu().remove(name);
				String ok = name + " rimossa correttamente.";
				System.out.println(TextualColorServices.colorSystemOut(ok,Color.YELLOW,false,false));
			}
		} catch (SQLException sqle) {
			String err = "Errore nella rimozione della pizza dal Database.";
			System.out.println(TextualColorServices.colorSystemOut(err, Color.RED, false, false));
		}
	}

	public static void removeToppingText(Pizzeria pizzeria) {	// todo: va in testuale
		try {
			System.out.print(TextualColorServices.colorSystemOut("Inserisci il nome dell'ingrediente da rimuovere:\t", Color.YELLOW, false, false));
			String name = scan.nextLine().toUpperCase();
			if (!removeTopping(name))
				throw new SQLException();
			else{
				pizzeria.getMenu().remove(name);
				String ok = name + " rimosso correttamente.";
				System.out.println(TextualColorServices.colorSystemOut(ok,Color.YELLOW,false,false));
			}
		} catch (SQLException sqle) {
			String err = "Errore nella rimozione dell'ingrediente dal Database.";
			System.out.println(TextualColorServices.colorSystemOut(err, Color.RED, false, false));
		}
	}

	/**
	 * Consente alla pizzeria di aggiungere una pizza al Menu
	 * che è salvato sul Database.
	 */
	public static void putPizza(Pizzeria pizzeria) {	// todo: va in testuale!
		HashMap<String,String> ingredMap = new HashMap<>();
		try {
			System.out.println(TextualColorServices.colorSystemOut("Inserire il nome della pizza da aggiungere:\t", Color.YELLOW, false, false));
			String name = scan.nextLine().toUpperCase();
			String descriz;
			StringBuilder descrizCorretta = new StringBuilder();
			String adding = TextualColorServices.colorSystemOut("Inserire gli ingredienti da aggiungere, separati da virgola, poi invio:", Color.YELLOW, false, false);
			System.out.println(adding);
			System.out.println(TextualInterface.possibleAddictions(pizzeria));
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
			System.out.print(TextualColorServices.colorSystemOut("Inserire il prezzo della nuova pizza (usa il punto per i decimali):\t", Color.YELLOW, false, false));
			double prezzo = Double.parseDouble(scan.nextLine());

			if(name.length() == 0 || pizzeria.getMenu().containsKey(name) || descriz.equals("ERR") || prezzo <= 0){
				throw new TryAgainExc();
			}
			if (putPizza(name, descriz, prezzo)) {
				pizzeria.getMenu().put(name, new Pizza(name,ingredMap,prezzo));
				String ok = name + " (" + descriz.toLowerCase() + ") aggiunta correttamente.";
				System.out.println(TextualColorServices.colorSystemOut(ok,Color.YELLOW,false,false));
			} else {
				throw new SQLException();
			}
		} catch (TryAgainExc | NumberFormatException e) {
			String err = "Errore nell'inserimento dei dati della pizza.";
			System.out.println(TextualColorServices.colorSystemOut(err, Color.RED, false, false));
		} catch (SQLException sqle) {
			String err = "Errore nell'inserimento della pizza nel Database.";
			System.out.println(TextualColorServices.colorSystemOut(err, Color.RED, false, false));
		}
	}

	public static HashMap<String, Pizza> getPizze(HashMap<String, Pizza> menu) throws SQLException {
		ResultSet rs = PizzaDB.getPizzeDB(con).executeQuery();
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

	public static HashMap<String, String> getToppings(HashMap<String, String> ingredienti) throws SQLException {
		ResultSet rs = ToppingDB.getToppingsDB(con).executeQuery();
		while (rs.next()) {
			String ingrediente = rs.getString(1);
			ingredienti.put(ingrediente,ingrediente);
		}
		return ingredienti;
	}

	public static boolean putCustomer(String username, String password, String mailAddress) {	// todo: testuale?
		try {
			CustomerDB.putCostumer(con, username, password, mailAddress).execute();		// inserisce account nel DB
			return true;
		} catch (NumberFormatException nfe) {
			String err = "\nErrore nell'inserimento dei dati utente. Riprovare:";
			System.out.println(TextualColorServices.colorSystemOut(err, Color.RED, false, false));
			return false;
		} catch (SQLException sqle) {
			return false;
		}
	}

	public static boolean addInfoCustomer(String username, String name, String surname, String address){
		try {
			CustomerDB.addInfoCostumer(con, username, name, surname, address).executeUpdate();	// aggiorna account nel DB
			return true;
		} catch (SQLException sqle) {
			String err = "\nErrore nell'aggiornamento dei dati utente. Riprovare:";
			System.out.println(TextualColorServices.colorSystemOut(err, Color.RED, false, false));
			return false;
		}
	}

	public static boolean getCustomers(String username, String password) throws SQLException {
		ResultSet rs = CustomerDB.getCustomer(con, username, password);
		boolean hasRows = false;
		while (rs.next()) {
			hasRows = true;
		}
		return hasRows;
	}

	public static String getInfoCustomerFromUsername(String username, int columnIndex){
		ResultSet rs;
		String info = null;
		try {
			rs = CustomerDB.getCustomerFromUsername(con, username).executeQuery();
			while (rs.next()) {
				info = rs.getString(columnIndex);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	public static String getInfoCustomerFromMailAddress(String mail, int columnIndex){
		ResultSet rs;
		String user = null;
		try {
			rs = CustomerDB.getCustomerFromMailAddress(con, mail).executeQuery();
			while (rs.next()) {
				user = rs.getString(columnIndex);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static void putCompletedOrder(Order order){
		DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dataString = dateFormatYMD.format(order.getTime());
		java.sql.Timestamp data = java.sql.Timestamp.valueOf(dataString);
		try {
			OrderDB.putOrder(con,order,data).execute();
			for(Pizza p: order.getOrderedPizze()){
				OrderDB.putOrderedPizzas(con,order,p).execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	public static HashMap<String,Order> getOrdersDB(Pizzeria pizzeria, HashMap<String,Order> orders) throws SQLException {
		ResultSet rs = OrderDB.getOrders(con);
		int i = 0;
		while (rs.next()) {
			String orderID = rs.getString(1);
			String username = rs.getString(2);
			String address = rs.getString(3);
			String citofono = rs.getString(4);
			int quantity = rs.getInt(5);
			Date date = rs.getTimestamp(6);
			String psw = rs.getString(8);
			Order order = new Order(i);
			if(!orders.containsKey(orderID) && quantity > 0){
				order.setCustomer(new Customer(username));
				order.setName(citofono);
				order.setAddress(address);
				order.setTime(date);
				order.setCompletedDb(pizzeria, quantity, date);
				ResultSet rsPizza = OrderDB.getOrderedPizzasById(con,orderID);
				while (rsPizza.next()){
					HashMap<String, String> ingr = new HashMap<>();
					String ingrediente = rsPizza.getString(2);
					StringTokenizer stAgg = new StringTokenizer(ingrediente);
					while (stAgg.hasMoreTokens()) {
						try {
							String ingredienteAggiuntoString = SettleStringsServices.arrangeIngredientString(stAgg);
							ingr.put(ingredienteAggiuntoString, ingredienteAggiuntoString);
						} catch (Exception ignored) {}
					}
					Pizza p = new Pizza(rsPizza.getString(1),ingr,rsPizza.getDouble(3));
					order.getOrderedPizze().add(p);
					}
				orders.put(order.getOrderCode(),order);
			}
			i++;
		}
		return PizzeriaServices.sortOrders(orders);
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