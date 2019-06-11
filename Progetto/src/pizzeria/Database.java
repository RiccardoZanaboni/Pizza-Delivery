package pizzeria;

import Interfaces.TextInterface;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.scene.paint.Color;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
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
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean putPizza(String name, String ingred, double prezzo) {
        try {
            PizzaDB.putPizza(con, name.toUpperCase(), ingred.toUpperCase(), prezzo).execute();
            return true;
        } catch (SQLException sqle) {
            return false;
        }
    }

    public static boolean removePizza(String name) {
        try {
            PizzaDB.removePizza(con, name.toUpperCase()).execute();
            return true;
        } catch (SQLException sqle) {
            System.out.println("Errore nell'inserimento della pizza nel DB");
            return false;
        }
    }

    public static void removePizza() {
        try {
            System.out.print(Services.colorSystemOut("Inserisci il nome della pizza da rimuovere: (usa \"_\" al posto di \" \"):\t", Color.YELLOW, false, false));
            String name = scan.nextLine();
            if (!removePizza(name.toUpperCase())) throw new SQLException();
        } catch (SQLException sqle) {
            String err = "Errore nella rimozione della pizza nel database. Riprovare:";
            System.out.println(Services.colorSystemOut(err, Color.RED, false, false));
        }
    }

    /**
     * Consente alla pizzeria di aggiungere una pizza al Menu
     * che è salvato sul Database.
     */
    public static void putPizza(Pizzeria pizzeria) {
        try {
            System.out.println(Services.colorSystemOut("Inserisci il nome della pizza da aggiungere: (usa \"_\" al posto di \" \"):\t", Color.YELLOW, false, false));
            String name = scan.nextLine();
            String ingred;
            do {
                String adding = Services.colorSystemOut("Inserisci gli ingredienti da aggiungere, separati da virgola, poi invio:", Color.YELLOW, false, false);
                System.out.println(adding);
                System.out.println(TextInterface.possibleAddictions(pizzeria));
                ingred = scan.nextLine();

            } while (ingred.toUpperCase().equals("OK"));
            System.out.print(Services.colorSystemOut("Inserisci prezzo della nuova pizza (usa il punto per i decimali):\t", Color.YELLOW, false, false));
            double prezzo = Double.parseDouble(scan.nextLine());
            if (!putPizza(name.toUpperCase(), ingred.toUpperCase(), prezzo)) throw new SQLException();
        } catch (NumberFormatException nfe) {
            String err = "Errore nell'inserimento dei dati della pizza. Riprovare:";
            System.out.println(Services.colorSystemOut(err, Color.RED, false, false));
        } catch (SQLException sqle) {
            String err = "Errore nell'inserimento della pizza nel database. Riprovare:";
            System.out.println(Services.colorSystemOut(err, Color.RED, false, false));
        }
    }

    public static HashMap<String, Pizza> getPizze(HashMap<String, Pizza> menu) throws SQLException {
        ResultSet rs = PizzaDB.getPizzaByName(con);
        while (rs.next()) {
            HashMap<String, Toppings> ingr = new HashMap<>();
            String nomePizza = rs.getString(1);
            String ingrediente = rs.getString(2);
            StringTokenizer stAgg = new StringTokenizer(ingrediente);
            while (stAgg.hasMoreTokens()) {
                try {
                    String ingredienteAggiuntoString = Services.arrangeIngredientString(stAgg);
                    Toppings toppings = Toppings.valueOf(ingredienteAggiuntoString);
                    ingr.put(toppings.name(), toppings);
                } catch (Exception ignored) {}
            }
            double prezzo = rs.getDouble(3);
            Pizza p = new Pizza(nomePizza, ingr, prezzo);
            menu.put(nomePizza, p);
        }
        return menu;
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
        boolean rows = false;
        while (rs.next()) {
            rows = true;
        }
        return rows;
    }

    public static boolean putOrder(Order order){
        DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s=dateFormatYMD.format(order.getTime());
        java.sql.Timestamp data= java.sql.Timestamp.valueOf(s);
        try {
            OrderDB.putOrder(con,order,data).execute();
            OrderDB.putOrderedPizzas(con,order);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static HashMap<String,Order> getOrder(HashMap<String,Order> orders,Pizzeria pizzeria) throws SQLException {
        ResultSet rs = OrderDB.getOrders(con);
        int i=0;
        while (rs.next()) {
            String orderID=rs.getString(1);
            String username=rs.getString(2);
            String address=rs.getString(3);
            Date date=rs.getTimestamp(4);
            Order order=new Order(i);
            if(!orders.containsKey(orderID)){
                order.setName(username);
                order.setAddress(address);
                order.setTime(date);
               // order.setCompleted(pizzeria);
               //pizzeria.addOrder(order);
                ResultSet rsPizza=OrderDB.getOrderedPizzasById(con,orderID);
                while (rsPizza.next()){
                    HashMap<String, Toppings> ingr = new HashMap<>();
                    String ingrediente = rsPizza.getString(2);
                    StringTokenizer stAgg = new StringTokenizer(ingrediente);
                    while (stAgg.hasMoreTokens()) {
                        try {
                            String ingredienteAggiuntoString = Services.arrangeIngredientString(stAgg);
                            Toppings toppings = Toppings.valueOf(ingredienteAggiuntoString);
                            ingr.put(toppings.name(), toppings);
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
        orders=sortedByValue;
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
        try {
                Order o=new Order(1);
                o.setTime(new Date());
                o.setName("c");
                o.setAddress("ss");
                HashMap<String, Toppings> pizzeriaIngredients = new HashMap<>();
                pizzeriaIngredients.put(Toppings.MOZZARELLA_DI_BUFALA.name(), Toppings.MOZZARELLA_DI_BUFALA);
                o.addPizza(new Pizza("cotto",pizzeriaIngredients,1.),4);
                //putOrder(o);
            HashMap<String,Order> orders=new HashMap<>();
            getOrder(orders,wolf);
            for(String s : wolf.getOrders().keySet()){
                System.out.println(getOrder(orders,wolf).get(s).toString());
            }
            System.out.println("ciao");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}