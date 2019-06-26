package pizzeria;

import java.sql.*;

public class OrderDB {
    public static PreparedStatement putOrder(Connection con, Order order, Timestamp date) {
        PreparedStatement preparedStatement = null;
        try {
            String requestSql = "update sql7293749.Orders set username = '" + order.getCustomer().getUsername()
                    + "', address = '" + order.getAddress() + "', citofono = '" + order.getName() + "', quantity = "
                    + order.getNumPizze() + ", date = '" + date + "' where orderID = '" + order.getOrderCode() + "'";
            preparedStatement = con.prepareStatement(requestSql);
        } catch (SQLException sqle) {
            Database.missingConnection();
        }
        return preparedStatement;
    }

    public static PreparedStatement putOrderedPizzas(Connection con, Order order, Pizza p) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement("insert into sql7293749.OrderedPizza values ('" + order.getOrderCode() + "', '" + p.getName() + "', '" + p.getDescription() + "', '" + p.getPrice() + "'); ");
        } catch (SQLException sqle) {
            Database.missingConnection();
        }
        return preparedStatement;
    }

    public static ResultSet getOrders(Connection con) {
        ResultSet rs = null;
        try {
            Statement statement = con.createStatement();
            rs = statement.executeQuery("SELECT * FROM sql7293749.Orders left JOIN sql7293749.Users ON Orders.username = Users.User;");
            // todo rs = statement.executeQuery("select * from sql7293749.Orders where date >= (\'"+ date +"\')");
        } catch (SQLException sqle) {
            Database.missingConnection();
        }
        return rs;
    }

    public static ResultSet getOrderedPizzasById(Connection con, String orderID) {
        ResultSet rs = null;
        try {
            Statement statement = con.createStatement();
            rs = statement.executeQuery("select nome, ingrediente, prezzo from sql7293749.Orders natural join sql7293749.OrderedPizza where orderID = " + "\"" + orderID + "\"");
        } catch (SQLException sqle) {
            Database.missingConnection();
        }
        return rs;
    }
}
