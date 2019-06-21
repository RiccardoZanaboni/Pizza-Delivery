package pizzeria;

import java.sql.*;

public class OrderDB {
    public static PreparedStatement putOrder(Connection con, Order order, Timestamp date) {
        PreparedStatement preparedStatement = null;
        try {
            String requestSql = "insert into sql7293749.Orders (orderID, username, address, citofono, quantity, date) VALUES (?,?,?,?,?,?)";
            preparedStatement = con.prepareStatement(requestSql);
            preparedStatement.setString(1, order.getOrderCode());
            preparedStatement.setString(2, order.getCustomer().getUsername());
            preparedStatement.setString(3, order.getAddress());
            preparedStatement.setString(4, order.getName());
            preparedStatement.setInt(5, order.getNumPizze());
            preparedStatement.setTimestamp(6, date);

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
            rs = statement.executeQuery("SELECT * FROM sql7293749.Orders JOIN sql7293749.Users ON Orders.username = Users.User;");
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
