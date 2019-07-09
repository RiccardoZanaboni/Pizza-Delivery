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

    public static void putOrder(Order order) {
        DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataString = dateFormatYMD.format(order.getTime());
        java.sql.Timestamp data = java.sql.Timestamp.valueOf(dataString);
        String requestSql = "update sql7293749.Orders set username = '" + order.getCustomer().getUsername()
                + "', address = '" + order.getAddress() + "', citofono = '" + order.getName() + "', quantity = "
                + order.getNumPizze() + ", date = '" + data + "' where orderID = '" + order.getOrderCode() + "'";
        Database.insertStatement(requestSql);
        for (Pizza p : order.getOrderedPizze()) {
            OrderDB.putOrderedPizzas(order, p);
        }
    }

    public static void putOrderedPizzas(Order order, Pizza p) {
        Database.insertStatement("insert into sql7293749.OrderedPizza values ('" + order.getOrderCode() + "', '" + p.getName() + "', '" + p.getDescription() + "', '" + p.getPrice() + "'); ");
    }

    public static HashMap<String, Order> getOrders(Pizzeria pizzeria, HashMap<String, Order> orders) throws SQLException {
        ResultSet rs = Database.getStatement("SELECT * FROM sql7293749.Orders left JOIN sql7293749.Users ON Orders.username = Users.User;");
        int i = 0;
        while (rs.next()) {
            String orderID = rs.getString(1);
            String username = rs.getString(2);
            String address = rs.getString(3);
            String citofono = rs.getString(4);
            int quantity = rs.getInt(5);
            Date date = rs.getTimestamp(6);
            //String psw = rs.getString(8);
            Order order = new Order(i);
            if (!orders.containsKey(orderID) && quantity > 0) {
                order.setCustomer(new Customer(username));
                order.setName(citofono);
                order.setAddress(address);
                order.setTime(date);
                order.setCompletedDb(pizzeria, quantity, date);
                ResultSet rsPizza = OrderDB.getOrderedPizzasById(orderID);
                while (rsPizza.next()) {
                    HashMap<String, String> ingr = new HashMap<>();
                    String ingrediente = rsPizza.getString(2);
                    StringTokenizer stAgg = new StringTokenizer(ingrediente);
                    while (stAgg.hasMoreTokens()) {
                        try {
                            String ingredienteAggiuntoString = SettleStringsServices.arrangeIngredientString(stAgg);
                            ingr.put(ingredienteAggiuntoString, ingredienteAggiuntoString);
                        } catch (Exception ignored) {
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


    public static ResultSet getOrderedPizzasById( String orderID) {
        return Database.getStatement("select nome, ingrediente, prezzo from sql7293749.Orders natural join sql7293749.OrderedPizza where orderID = " + "\"" + orderID + "\"");

    }
}