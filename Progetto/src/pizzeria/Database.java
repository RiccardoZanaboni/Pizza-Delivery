package pizzeria;

import Interfaces.TextInterface;
import exceptions.TryAgainExc;
import javafx.scene.paint.Color;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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

	public static void missingConnection(){
		System.out.println(Services.colorSystemOut("\nSpiacenti: impossibile connettersi al momento.\nControllare connessione di rete.", Color.RED,true,false));
		System.exit(1);
	}

	/** Tutto il controllo è fatto per verificare che la pizza non fosse già presente nel DB
	 * (richiamando il getPizzeDB sia prima che dopo l'insierimento della nuova pizza, verifico
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

	public static void putTopping(Pizzeria pizzeria) {
		try {
			System.out.print(Services.colorSystemOut("Inserisci il nome del nuovo ingrediente:\t", Color.YELLOW, false, false));
			String name = scan.nextLine().toUpperCase();
			if (!putTopping(name) || name.length() == 0)
				throw new Exception();
			else{
				pizzeria.getIngredientsPizzeria().put(name,name);
				String ok = name + " aggiunto correttamente.";
				System.out.println(Services.colorSystemOut(ok,Color.YELLOW,false,false));
			}
		} catch (Exception e) {
			String err = "Errore nell'aggiunta dell'ingrediente al Database.";
			System.out.println(Services.colorSystemOut(err, Color.RED, false, false));
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
			return prima != dopo;
		} catch (SQLException sqle) {
			return false;
		}
	}

	public static void removePizza(Pizzeria pizzeria) {
		try {
			System.out.print(Services.colorSystemOut("Inserisci il nome della pizza da rimuovere:\t", Color.YELLOW, false, false));
			String name = scan.nextLine().toUpperCase();
			if (!removePizza(name))
				throw new SQLException();
			else{
				pizzeria.getMenu().remove(name);
				String ok = name + " rimossa correttamente.";
				System.out.println(Services.colorSystemOut(ok,Color.YELLOW,false,false));
			}
		} catch (SQLException sqle) {
			String err = "Errore nella rimozione della pizza dal Database.";
			System.out.println(Services.colorSystemOut(err, Color.RED, false, false));
		}
	}

	public static void removeTopping(Pizzeria pizzeria) {
		try {
			System.out.print(Services.colorSystemOut("Inserisci il nome dell'ingrediente da rimuovere:\t", Color.YELLOW, false, false));
			String name = scan.nextLine().toUpperCase();
			if (!removeTopping(name))
				throw new SQLException();
			else{
				pizzeria.getMenu().remove(name);
				String ok = name + " rimosso correttamente.";
				System.out.println(Services.colorSystemOut(ok,Color.YELLOW,false,false));
			}
		} catch (SQLException sqle) {
			String err = "Errore nella rimozione dell'ingrediente dal Database.";
			System.out.println(Services.colorSystemOut(err, Color.RED, false, false));
		}
	}

	/**
	 * Consente alla pizzeria di aggiungere una pizza al Menu
	 * che è salvato sul Database.
	 */
	public static void putPizza(Pizzeria pizzeria) {
		HashMap<String,String> ingredMap = new HashMap<>();
		try {
			System.out.println(Services.colorSystemOut("Inserire il nome della pizza da aggiungere:\t", Color.YELLOW, false, false));
			String name = scan.nextLine().toUpperCase();
			String descriz;
			StringBuilder descrizCorretta = new StringBuilder();
			String adding = Services.colorSystemOut("Inserire gli ingredienti da aggiungere, separati da virgola, poi invio:", Color.YELLOW, false, false);
			System.out.println(adding);
			System.out.println(TextInterface.possibleAddictions(pizzeria));
			descriz = scan.nextLine().toUpperCase();

			StringTokenizer st = new StringTokenizer(descriz,",");
			while (st.hasMoreTokens()) {
				String ingr = Services.arrangeIngredientString(st);
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
			System.out.print(Services.colorSystemOut("Inserire il prezzo della nuova pizza (usa il punto per i decimali):\t", Color.YELLOW, false, false));
			double prezzo = Double.parseDouble(scan.nextLine());
			if(name.length() == 0 || pizzeria.getMenu().containsKey(name) || descriz.equals("ERR")){
				throw new TryAgainExc();
			}
			if (putPizza(name, descriz, prezzo)) {
				pizzeria.getMenu().put(name, new Pizza(name,ingredMap,prezzo));
				String ok = name + " (" + descriz.toLowerCase() + ") aggiunta correttamente.";
				System.out.println(Services.colorSystemOut(ok,Color.YELLOW,false,false));
			} else {
				throw new SQLException();
			}
		} catch (TryAgainExc | NumberFormatException e) {
			String err = "Errore nell'inserimento dei dati della pizza.";
			System.out.println(Services.colorSystemOut(err, Color.RED, false, false));
		} catch (SQLException sqle) {
			String err = "Errore nell'inserimento della pizza nel Database.";
			System.out.println(Services.colorSystemOut(err, Color.RED, false, false));
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
					String ingredienteAggiuntoString = Services.arrangeIngredientString(stAgg);
					ingr.put(ingredienteAggiuntoString, ingredienteAggiuntoString);
				} catch (Exception ignored) {}
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

	public static boolean putCustomer(String username, String password, String mailAddress) {
		try {
			CustomerDB.putCostumer(con, username, password, mailAddress).execute();		// inserisce account nel DB
			return true;
		} catch (NumberFormatException nfe) {
			String err = "\nErrore nell'inserimento dei dati utente. Riprovare:";
			System.out.println(Services.colorSystemOut(err, Color.RED, false, false));
			return false;
		} catch (SQLException sqle) {
			return false;
		}
	}

	public static boolean getCustomers(String username, String password) throws SQLException {
		ResultSet rs = CustomerDB.getCustomers(con, username, password);
		boolean hasRows = false;
		while (rs.next()) {
			hasRows = true;
		}
		return hasRows;
	}

	public static String getPasswordCustomer(String username){
		ResultSet rs;
		String info = null;
		try {
			rs = CustomerDB.getInfoCustomer(con, username).executeQuery();
			while (rs.next()) {
				info = rs.getString(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	public static String getUsernameCustomer(String mail){
		ResultSet rs;
		String user = null;
		try {
			rs = CustomerDB.getUserCustomer(con, mail).executeQuery();
			while (rs.next()) {
				user = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static boolean putOrder(Order order){
		DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dataString = dateFormatYMD.format(order.getTime());
		java.sql.Timestamp data = java.sql.Timestamp.valueOf(dataString);
		try {
			OrderDB.putOrder(con,order,data).execute();
			for(Pizza p: order.getOrderedPizze()){
				OrderDB.putOrderedPizzas(con,order,p).execute();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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
			preparedStatement.setTimestamp(6, data);
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
		openDatabase();
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

	static HashMap<String,Order> getOrdersDB(Pizzeria pizzeria, HashMap<String,Order> orders) throws SQLException {
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
				order.setCustomer(new Customer(username,psw));
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
							String ingredienteAggiuntoString = Services.arrangeIngredientString(stAgg);
							ingr.put(ingredienteAggiuntoString, ingredienteAggiuntoString);
						} catch (Exception ignored) {}
					}
					Pizza p = new Pizza(rsPizza.getString(1),ingr,rsPizza.getDouble(3));
					//if(!order.getOrderedPizze().contains(p)){ // FIXME @zana DA VERIFICARE IL CONTAINS CHE HO MODIFICATO IN PIZZA --Pensavo servisse ma probabilmente no,lascio ancora per poco
					order.getOrderedPizze().add(p);
					//}
				}
				orders.put(order.getOrderCode(),order);
			}
			i++;
		}
		return Services.sortOrders(orders);
	}

	public static void main(String[] args) {
		openDatabase();
		Pizzeria wolf = new Pizzeria("wolf","via bolzano 10", LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
				LocalTime.MIN.plus(Services.getMinutes(0,0), ChronoUnit.MINUTES),
				LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
				LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
				LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
				LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
				LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
				// orari di chiusura, da domenica a sabato
				LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
				LocalTime.MIN.plus(Services.getMinutes(0,0), ChronoUnit.MINUTES),
				LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
				LocalTime.MIN.plus(Services.getMinutes(23,59), ChronoUnit.MINUTES),
				LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
				LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
				LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES)
		);
		int i = 0;
		int j = 0;
		int k=0;
		PreparedStatement preparedStatement = null;
        /*try {
            for (i = 15; i < 120; i += 5) {
                if(j>9){
                    preparedStatement = con.prepareStatement("ALTER TABLE `sql7293749`.`Oven` ADD COLUMN `" + j + ":" + i + "` INT NOT NULL DEFAULT 16  AFTER `" + j + ":" + (i-5));
                    preparedStatement.execute();
                    if (i==55){
                        k+=1;
                        if(k==24){
                            break;
                        }
                        i=10;
                        j+=1;
                        preparedStatement = con.prepareStatement("ALTER TABLE `sql7293749`.`Oven` ADD COLUMN `" + j + ":00` INT NOT NULL DEFAULT 16 AFTER `" + (j - 1) + ":55");
                        preparedStatement.execute();
                        preparedStatement = con.prepareStatement("ALTER TABLE `sql7293749`.`Oven` ADD COLUMN `" + j + ":05` INT NOT NULL DEFAULT 16  AFTER `" + j + ":00");
                        preparedStatement.execute();
                        preparedStatement = con.prepareStatement("ALTER TABLE `sql7293749`.`Oven` ADD COLUMN `" + j + ":10` INT NOT NULL DEFAULT 16 AFTER `" + j + ":05");
                        preparedStatement.execute();
                    }
                }else {
                    preparedStatement = con.prepareStatement("ALTER TABLE `sql7293749`.`Oven` ADD COLUMN `0" + j + ":" + i + "` INT NOT NULL DEFAULT 16 AFTER `0" + j + ":" + (i - 5));
                    preparedStatement.execute();
                    if (i == 55) {
                        k+=1;
                        i = 10;
                        j += 1;
                        if(j==10) {
                            preparedStatement = con.prepareStatement("ALTER TABLE `sql7293749`.`Oven` ADD COLUMN `" + j + ":00` INT NOT NULL DEFAULT 16 AFTER `0" + (j - 1) + ":55");
                            preparedStatement.execute();
                            preparedStatement = con.prepareStatement("ALTER TABLE `sql7293749`.`Oven` ADD COLUMN `" + j + ":05` INT NOT NULL DEFAULT 16 AFTER `" + j + ":00");
                            preparedStatement.execute();
                            preparedStatement = con.prepareStatement("ALTER TABLE `sql7293749`.`Oven` ADD COLUMN `" + j + ":10` INT NOT NULL DEFAULT 16 AFTER `" + j + ":05");
                            preparedStatement.execute();
                        }else {
                            preparedStatement = con.prepareStatement("ALTER TABLE `sql7293749`.`Oven` ADD COLUMN `0" + j + ":00` INT NOT NULL DEFAULT 16 AFTER `0" + (j - 1) + ":55");
                            preparedStatement.execute();
                            preparedStatement = con.prepareStatement("ALTER TABLE `sql7293749`.`Oven` ADD COLUMN `0" + j + ":05` INT NOT NULL DEFAULT 16 AFTER `0" + j + ":00");
                            preparedStatement.execute();
                            preparedStatement = con.prepareStatement("ALTER TABLE `sql7293749`.`Oven` ADD COLUMN `0" + j + ":10` INT NOT NULL DEFAULT 16 AFTER `0" + j + ":05");
                            preparedStatement.execute();
                        }
                    }
                }
            }*/try{
			String s = "insert into sql7293749.Oven  values (16";
			for(i=0;i<286;i++) {
				s+=",16";
				if(i==285){
					s+=")";
					con.prepareStatement(s).execute();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}