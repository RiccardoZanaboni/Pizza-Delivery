package database;

import pizzeria.Customer;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;
import pizzeria.services.PizzeriaServices;
import pizzeria.services.SettleStringsServices;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

public class OrderDB {

    /** Aggiunge al DB i dati definitivi dell'ordine. */
    public static void putOrder(Order order) {
        DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataString = dateFormatYMD.format(order.getTime());
        java.sql.Timestamp data = java.sql.Timestamp.valueOf(dataString);
        String requestSql = "update sql2298759.Orders set username = '" + order.getCustomer().getUsername()
                + "', address = '" + order.getAddress() + "', citofono = '" + order.getName() + "', quantity = "
                + order.getNumPizze() + ", date = '" + data + "' where orderID = '" + order.getOrderCode() + "'";
        Database.insertStatement(requestSql);
        for (Pizza p : order.getOrderedPizze()) {
            putOrderedPizzas(order, p);  // FIXME: 19/07/2019
        }
    }

    /** Aggiorna nel DB l'elenco delle pizze ordinate. */
    private static void putOrderedPizzas(Order order, Pizza p) {
        Database.insertStatement("insert into sql2298759.OrderedPizza values ('" + order.getOrderCode() + "', '" + p.getName() + "', '" + p.getDescription() + "', '" + p.getPrice() + "'); ");
    }

    /** Recupera dal DB tutti gli ordini relativi al giorno corrente. */
    public static HashMap<String, Order> getOrders(Pizzeria pizzeria, HashMap<String, Order> orders) throws SQLException {
        Date today=new Date();
        ResultSet rs = Database.getStatement("SELECT * FROM sql2298759.Orders left JOIN sql2298759.Users ON Orders.username = Users.User;");
        int i = 0;
        while (rs.next()) {
            String orderID = rs.getString(1);
            int quantity = rs.getInt(5);
            if (!orders.containsKey(orderID) && quantity > 0) {
                String username = rs.getString(2);
                String address = rs.getString(3);
                String citofono = rs.getString(4);
                Date date = rs.getTimestamp(6);
                Order order = new Order(i);
                order.setCustomer(new Customer(username));
                order.setName(citofono);
                order.setAddress(address);
                order.setTime(date);
                if(pizzeria.isOpen(today)) {
                    order.updateAvailability(pizzeria, quantity, date); /* Aggiorno le disponibilità solo se è aperta e posso fare un ordine */
                }
                ResultSet rsPizza = OrderDB.getPizzasByOrderId(orderID);
                while (rsPizza.next()) {
                    HashMap<String, String> ingr = new HashMap<>();
                    String ingrediente = rsPizza.getString(2);
                    StringTokenizer stAgg = new StringTokenizer(ingrediente);
                    while (stAgg.hasMoreTokens()) {
                        try {
                            String ingredienteAggiuntoString = SettleStringsServices.arrangeIngredientString(stAgg);
                            ingr.put(ingredienteAggiuntoString, ingredienteAggiuntoString);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Pizza p = new Pizza(rsPizza.getString(1), ingr, rsPizza.getDouble(3));
                    order.getOrderedPizze().add(p);
                }
                orders.put(order.getOrderCode(), order);
            }
            i++;
        }
        return PizzeriaServices.sortOrders(orders);
    }

    /** Recupera dal DB tutte le pizze presenti in un determinato ordine. */
    private static ResultSet getPizzasByOrderId(String orderID) {
        return Database.getStatement("select pizza, ingredienti, prezzo from sql2298759.Orders natural join sql2298759.OrderedPizza where orderID = '" + orderID + "'");
    }
}