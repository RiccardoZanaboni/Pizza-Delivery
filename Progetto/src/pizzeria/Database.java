package pizzeria;

import Interfaces.TextInterface;
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

	//TODO: fare in modo che ingredienti non esistenti diano errore!!! (controllare ogni ingrediente)
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
			if (!putTopping(name))
				throw new SQLException();
			else{
				pizzeria.getIngredientsPizzeria().put(name,name);
				String ok = name + " aggiunto correttamente.";
				System.out.println(Services.colorSystemOut(ok,Color.YELLOW,false,false));
			}
		} catch (SQLException sqle) {
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
		try {
			System.out.println(Services.colorSystemOut("Inserisci il nome della pizza da aggiungere:\t", Color.YELLOW, false, false));
			String name = scan.nextLine().toUpperCase();
			String descriz;
			StringBuilder descrizCorretta = new StringBuilder();
			String adding = Services.colorSystemOut("Inserisci gli ingredienti da aggiungere, separati da virgola, poi invio:", Color.YELLOW, false, false);
			System.out.println(adding);
			System.out.println(TextInterface.possibleAddictions(pizzeria));
			descriz = scan.nextLine().toUpperCase();

			HashMap<String,String> ingredMap = new HashMap<>();
			StringTokenizer st = new StringTokenizer(descriz);
			while (st.hasMoreTokens()) {
				String ingr = Services.arrangeIngredientString(st);
				if(pizzeria.getIngredientsPizzeria().containsKey(ingr) && !ingredMap.containsKey(ingr)) {
					ingredMap.put(ingr, ingr);
					descrizCorretta.append(ingr).append(",");
				}
			}
			if(descrizCorretta.toString().length()>0)
				descriz = descrizCorretta.toString().substring(0,descrizCorretta.toString().length()-1);
			else
				descriz = "";

			System.out.print(Services.colorSystemOut("Inserisci prezzo della nuova pizza (usa il punto per i decimali):\t", Color.YELLOW, false, false));
			double prezzo = Double.parseDouble(scan.nextLine());
			if (putPizza(name, descriz, prezzo)) {
				pizzeria.getMenu().put(name, new Pizza(name,ingredMap,prezzo));
				String ok = name + " aggiunta correttamente.";
				System.out.println(Services.colorSystemOut(ok,Color.YELLOW,false,false));
			} else {
				throw new SQLException();
			}
		} catch (NumberFormatException nfe) {
			String err = "Errore nell'inserimento dei dati della pizza.";
			System.out.println(Services.colorSystemOut(err, Color.RED, false, false));
		} catch (SQLException sqle) {
			String err = "Errore nell'inserimento della pizza nel database.";
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

	public static boolean putCustomer(String username, String password) {
		try {
			CustomerDB.putCostumer(con, username, password).execute();
			return true;
		} catch (NumberFormatException nfe) {
			String err = "\nErrore nell'inserimento dei dati utente. Riprovare:";
			System.out.println(Services.colorSystemOut(err, Color.RED, false, false));
			return false;
		} catch (SQLException sqle) {
			//String err = "\nErrore nell'inserimento dell'utente nel database. Riprovare:";
			//System.out.println(Services.colorSystemOut(err,Color.RED,false,false));
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

	public static boolean putOrder(Order order){
		DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = dateFormatYMD.format(order.getTime());
		java.sql.Timestamp data = java.sql.Timestamp.valueOf(s);
		try {
			OrderDB.putOrder(con,order,data).execute();
			OrderDB.putOrderedPizzas(con,order);
			//todo: aggiornare anche la hashmap in Pizzeria
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static HashMap<String,Order> getOrdersDB(HashMap<String,Order> orders, Pizzeria pizzeria) throws SQLException {
		ResultSet rs = OrderDB.getOrders(con);
		int i = 0;
		while (rs.next()) {
			String orderID = rs.getString(1);
			String username = rs.getString(2);
			String address = rs.getString(3);
			Date date = rs.getTimestamp(4);
			int quantity = rs.getInt(5);
			Order order = new Order(i);
			if(!orders.containsKey(orderID)){
				order.setName(username);
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
					Pizza p=new Pizza(rsPizza.getString(1),ingr,rsPizza.getDouble(3));
					//if(!order.getOrderedPizze().contains(p)){ // FIXME @zana DA VERIFICARE IL CONTAINS CHE HO MODIFICATO IN PIZZA --Pensavo servisse ma probabilmente no,lascio ancora per poco
					order.getOrderedPizze().add(p);
					//}
				}
				orders.put(order.getOrderCode(),order);
			}
			i++;
		}
		Set<Map.Entry<String, Order>> entries = orders.entrySet();
		Comparator<Map.Entry<String, Order>> valueComparator=new Comparator<Map.Entry<String, Order>>() {
			@Override
			public int compare(Map.Entry<String, Order> o1, Map.Entry<String, Order> o2) {
				Order v1 = o1.getValue();
				Order v2 = o2.getValue();
				return v1.compareTo(v2);
			}
		};
		List<Map.Entry<String, Order>> listOfEntries = new ArrayList<Map.Entry<String, Order>>(entries); // Sort method needs a List, so let's first convert Set to List
		Collections.sort(listOfEntries, valueComparator);// sorting HashMap by values using comparator
		// copying entries from List to Map
		LinkedHashMap<String, Order> sortedByValue = new LinkedHashMap<String, Order>(listOfEntries.size());
		for(Map.Entry<String, Order> entry : listOfEntries){
			sortedByValue.put(entry.getKey(), entry.getValue());
		}
		orders = sortedByValue;
		return orders;
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

		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}